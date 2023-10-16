/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jdeferred2.DoneCallback;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah.stages.deduce.Deduce_CreationClosure;
import tripleo.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah.stages.deduce.nextgen.DeduceCreationContext;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.util.Holder;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkManager;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Created 5/31/21 2:26 AM
 */
public class WlGenerateDefaultCtor implements WorkJob {
	private final          FunctionInvocation    functionInvocation;
	private final @NotNull GenerateFunctions     generateFunctions;
	private final          ICodeRegistrar        codeRegistrar;
	private                boolean               _isDone = false;
	private                BaseEvaFunction       Result;
	private                DeduceCreationContext dcc;

	public WlGenerateDefaultCtor(final OS_Module module,
								 final FunctionInvocation aFunctionInvocation,
								 final @NotNull Deduce_CreationClosure crcl) {
		this(crcl.generatePhase().getGenerateFunctions(module),
			 aFunctionInvocation,
			 crcl.dcc(),
			 crcl.deducePhase().getCodeRegistrar());
	}

	@Contract(pure = true)
	public WlGenerateDefaultCtor(final @NotNull GenerateFunctions aGenerateFunctions,
								 final FunctionInvocation aFunctionInvocation,
								 final DeduceCreationContext aDcc,
								 final ICodeRegistrar aCodeRegistrar) {
		generateFunctions  = aGenerateFunctions;
		functionInvocation = aFunctionInvocation;
		dcc                = aDcc;
		codeRegistrar      = aCodeRegistrar;
	}

	private boolean getPragma(String aAuto_construct) {
		return false;
	}

	public BaseEvaFunction getResult() {
		return Result;
	}

	@Override
	public boolean isDone() {
		return _isDone;
	}

	@Override
	public void run(WorkManager aWorkManager) {
		if (functionInvocation.generateDeferred().isPending()) {
			final ClassStatement   klass     = functionInvocation.getClassInvocation().getKlass();
			final Holder<EvaClass> hGenClass = new Holder<>();

			functionInvocation.getClassInvocation().resolvePromise().then(new DoneCallback<EvaClass>() {
				@Override
				public void onDone(EvaClass result) {
					hGenClass.set(result);
				}
			});
			EvaClass genClass = hGenClass.get();
			assert genClass != null;

			final ConstructorDef cd     = new ConstructorDefImpl(null, (_CommonNC) klass, klass.getContext());
			final Scope3Impl     scope3 = new Scope3Impl(cd);
			cd.setName(LangGlobals.emptyConstructorName);
			cd.scope(scope3);
			for (final EvaContainer.VarTableEntry varTableEntry : genClass.varTable) {
				OS_Element element;
				element = __getElement(varTableEntry, cd);
				if (element != null) {
					scope3.add(element);
				}
			}

			final OS_Element classStatement = cd.getParent();
			assert classStatement instanceof ClassStatement;

			final @NotNull EvaConstructor gf = generateFunctions.generateConstructor(cd, (ClassStatement) classStatement, functionInvocation);
			// lgf.add(gf);

			final ClassInvocation ci = functionInvocation.getClassInvocation();
			ci.resolvePromise().done((@NotNull EvaClass result) -> {
				codeRegistrar.registerFunction1(gf);
				// gf.setCode(generateFunctions.module.getCompilation().nextFunctionCode());

				gf.setClass(result);
				result.constructors.put(cd, gf);
			});

			functionInvocation.generateDeferred().resolve(gf);
			functionInvocation.setGenerated(gf);
			Result = gf;
		} else {
			functionInvocation.generatePromise().then(new DoneCallback<BaseEvaFunction>() {
				@Override
				public void onDone(final BaseEvaFunction result) {
					Result = result;
				}
			});
		}

		_isDone = true;
	}

	@NotNull
	private static OS_Element __getElement(final EvaContainer.VarTableEntry varTableEntry, final ConstructorDef cd) {
		OS_Element element;
		if (varTableEntry.initialValue != LangGlobals.UNASSIGNED) {
			final IExpression left  = varTableEntry.nameToken;
			final IExpression right = varTableEntry.initialValue;

			final IExpression wrapped = ExpressionBuilder.build(left, ExpressionKind.ASSIGNMENT, right);

			final VariableStatementImpl   vs      = (VariableStatementImpl) varTableEntry.vs();
			final WrappedStatementWrapper wrapper = new WrappedStatementWrapper(wrapped, cd.getContext(), cd, vs);

			element = wrapper;
		} else {
			final ConstructStatementImpl element3 = new ConstructStatementImpl(cd, cd.getContext(), varTableEntry.nameToken, null, null);
			element = element3;
		}
		return element;
	}
}

//
//
//
