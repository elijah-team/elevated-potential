package tripleo.elijah.comp;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.world.i.*;

import java.util.*;
import java.util.stream.*;

public class Coder {
	private static void extractNodes_toResolvedNodes(@NotNull final Map<FunctionDef, EvaFunction> aFunctionMap,
			@NotNull final List<EvaNode> resolved_nodes) {
		aFunctionMap.values().stream()
				.map(generatedFunction -> (generatedFunction.idte_list).stream().filter(IdentTableEntry::isResolved)
						.map(IdentTableEntry::resolvedType).collect(Collectors.toList()))
				.forEach(resolved_nodes::addAll);
	}

	private final ICodeRegistrar codeRegistrar;

	@Contract(pure = true)
	public Coder(final ICodeRegistrar aCodeRegistrar) {
		codeRegistrar = aCodeRegistrar;
	}

	public void codeNode(final EvaNode generatedNode, final WorldModule mod) {
		final Coder coder = this;

		if (generatedNode instanceof final @NotNull EvaFunction generatedFunction) {
			coder.codeNodeFunction(generatedFunction, mod);
		} else if (generatedNode instanceof final @NotNull EvaClass generatedClass) {
			coder.codeNodeClass(generatedClass, mod);
		} else if (generatedNode instanceof final @NotNull EvaNamespace generatedNamespace) {
			coder.codeNodeNamespace(generatedNamespace, mod);
		}
	}

	public void codeNodeClass(@NotNull final EvaClass generatedClass, final WorldModule wm) {
		assert generatedClass.getLiving().getCode() == 0;
		codeRegistrar.registerClass(generatedClass);
	}

	public void codeNodeFunction(@NotNull final BaseEvaFunction generatedFunction, final WorldModule mod) {
		ESwitch.flep(generatedFunction, new DoneCallback<DeducedBaseEvaFunction>() {
			@Override
			public void onDone(final DeducedBaseEvaFunction result) {
				if (result.getCode() == 0) {
					codeRegistrar.registerFunction(generatedFunction);
				} else {
					throw new AssertionError("9993-0056: double register of function");
				}
			}
		});
	}

	public void codeNodeNamespace(@NotNull final EvaNamespace generatedNamespace, final WorldModule mod) {
		assert (generatedNamespace.getCode() == 0);
		codeRegistrar.registerNamespace(generatedNamespace);
	}

	public void codeNodes(final WorldModule wm, final @NotNull List<EvaNode> resolved_nodes,
			final EvaNode generatedNode) {
		var mod = wm.module();

		if (generatedNode instanceof final @NotNull EvaFunction generatedFunction) {
			codeNodeFunction(generatedFunction, wm);
		} else if (generatedNode instanceof final @NotNull EvaClass generatedClass) {
			// assert generatedClass.getCode() == 0;
			if (generatedClass.getLiving().getCode() == 0) {
				codeNodeClass(generatedClass, wm);
			}

			setClassmapNodeCodes(generatedClass.classMap, wm);

			extractNodes_toResolvedNodes(generatedClass.functionMap, resolved_nodes);
		} else if (generatedNode instanceof final @NotNull EvaNamespace generatedNamespace) {
			if (generatedNamespace.getLiving().getCode() != 0) {
				codeNodeNamespace(generatedNamespace, wm);
			}

			setClassmapNodeCodes(generatedNamespace.classMap, wm);

			extractNodes_toResolvedNodes(generatedNamespace.functionMap, resolved_nodes);
		}
	}

	private void setClassmapNodeCodes(@NotNull final Map<ClassStatement, EvaClass> aClassMap, final WorldModule mod) {
		aClassMap.values().forEach(generatedClass -> codeNodeClass(generatedClass, mod));
	}
}
