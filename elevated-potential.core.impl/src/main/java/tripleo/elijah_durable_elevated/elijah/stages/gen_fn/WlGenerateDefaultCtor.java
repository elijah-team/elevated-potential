/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.stages.gen_fn;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah_durable_elevated.elijah.lang.impl.*;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.nextgen.DeduceCreationContext;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.ICodeRegistrar;

/**
 * Created 5/31/21 2:26 AM
 */
public class WlGenerateDefaultCtor implements WorkJob {
	private final          FunctionInvocation    functionInvocation;
	private final @NotNull GenerateFunctions     generateFunctions;
	private final          ICodeRegistrar        codeRegistrar;
	private                boolean               _isDone = false;
	private       BaseEvaFunction       Result;
	private final DeduceCreationContext dcc;

	public Eventual<BaseEvaFunction> getGenerated() {
		return generated;
	}

	private final Eventual<BaseEvaFunction> generated = new Eventual<>();

	public WlGenerateDefaultCtor(final OS_Module module,
								 final FunctionInvocation aFunctionInvocation,
								 final @NotNull DeduceCreationContext crcl) {
		this(crcl.getGeneratePhase().getGenerateFunctions(module),
			 aFunctionInvocation,
			 crcl,
			 crcl.getDeducePhase().getCodeRegistrar());
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

	@Override
	public boolean isDone() {
		return _isDone;
	}

	@Override
	public void run(WorkManager aWorkManager) {
		if (functionInvocation.generateDeferred().isPending()) {
			final ClassStatement   klass     = functionInvocation.getClassInvocation().getKlass();

			// TODO 10/17 PromiseExpectation/EventualRegister
			functionInvocation.getClassInvocation().resolvePromise().then(result -> {
				EvaClass genClass = result;

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
				ci.resolvePromise().done((@NotNull EvaClass result2) -> {
					codeRegistrar.registerFunction1(gf);
					// gf.setCode(generateFunctions.module.getCompilation().nextFunctionCode());

					gf.setClass(result2);
					result2.constructors.put(cd, gf);
				});

				functionInvocation.generateDeferred().resolve(gf);
				functionInvocation.setGenerated(gf);

				generated.resolve(gf);
			});
		} else {
			functionInvocation.generatePromise().then(generated::resolve);
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
