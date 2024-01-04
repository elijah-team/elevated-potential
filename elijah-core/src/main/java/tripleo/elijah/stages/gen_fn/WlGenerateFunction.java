/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkManager;

/**
 * Created 5/16/21 12:46 AM
 */
public class WlGenerateFunction implements WorkJob {
	private final          ICodeRegistrar     cr;
	private final          GenerateFunctions  generateFunctions;
	private final          FunctionDef        functionDef;
	private final @NotNull FunctionInvocation functionInvocation;
	private                boolean            _isDone = false;
	private @Nullable      EvaFunction        result;

	public WlGenerateFunction(GenerateFunctions aGenerateFunctions, @NotNull FunctionInvocation aFunctionInvocation, final ICodeRegistrar aCr) {
		functionDef        = aFunctionInvocation.getFunction();
		generateFunctions  = aGenerateFunctions;
		functionInvocation = aFunctionInvocation;
		cr                 = aCr;
	}

	public WlGenerateFunction(final OS_Module aModule, final FunctionInvocation aFunctionInvocation, final @NotNull Deduce_CreationClosure aCl) {
		this(aCl.generatePhase().getGenerateFunctions(aModule), aFunctionInvocation, aCl.generatePhase().getCodeRegistrar());
	}

	public EvaFunction getResult() {
		return result;
	}

	@Override
	public boolean isDone() {
		return _isDone;
	}

	@Override
	public void run(WorkManager aWorkManager) {
		if (_isDone) return;

		if (functionInvocation.getGenerated() == null) {
			OS_Element           parent = functionDef.getParent();
			@NotNull EvaFunction gf     = generateFunctions.generateFunction(functionDef, parent, functionInvocation);

			{
				int i = 0;
				for (TypeTableEntry tte : functionInvocation.getArgs()) {
					i = i + 1;
					if (tte.getAttached() == null) {
						tripleo.elijah.util.Stupidity.println_err_2(String.format("4949 null tte #%d %s in %s", i, tte, gf));
					}
				}
			}

//			lgf.add(gf);

			if (parent instanceof NamespaceStatement) {
				final NamespaceInvocation nsi = functionInvocation.getNamespaceInvocation();
				assert nsi != null;
				nsi.resolvePromise().done(result -> {
					__registerNamespace(result, gf);
				});
			} else {
				final ClassInvocation ci = functionInvocation.getClassInvocation();
				ci.resolvePromise().done((EvaClass result) -> {
					__registerClass(result, gf);
				});
			}
			result = gf;
			functionInvocation.setGenerated(result);
			functionInvocation.generateDeferred().resolve(result);
		} else {
			result = (EvaFunction) functionInvocation.getGenerated();
		}
		_isDone = true;
	}

	private void __registerNamespace(final @NotNull EvaNamespace result, final @NotNull EvaFunction gf) {
		if (result.getFunction(functionDef) == null) {
			cr.registerNamespace(result);
			//cr.registerFunction1(gf);
			result.addFunction(gf);
		}
		result.functionMapDeferreds.put(functionDef, new FunctionMapDeferred() {
			@Override
			public void onNotify(final EvaFunction aGeneratedFunction) {
				int y = 2;
			}
		});
		gf.setClass(result);
	}

	private void __registerClass(final @NotNull EvaClass result, final @NotNull EvaFunction gf) {
		if (result.getFunction(functionDef) == null) {
			cr.registerClass1(result);
			result.addFunction(gf);
		}
		result.functionMapDeferreds.put(functionDef, new FunctionMapDeferred() {
			@Override
			public void onNotify(final EvaFunction aGeneratedFunction) {
				int y = 2;
			}
		});
		gf.setClass(result);
	}
}

//
//
//
