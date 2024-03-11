package tripleo.elijah_durable_elevated.elijah.comp.notation;

import org.jetbrains.annotations.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_durable_elevated.elijah.comp.PipelineLogic;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeducePhase;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_durable_elevated.elijah.world.i.WorldModule;

import java.util.*;

class ResolvedNodes {
	final         List<EvaNode>  resolved_nodes = new ArrayList<EvaNode>();
	private final ICodeRegistrar cr;

	public ResolvedNodes(final ICodeRegistrar aCr) {
		cr = aCr;
	}

	public void init(final DeducePhase.@NotNull GeneratedClasses c) {
		// 09/26 tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("2222 " + c);

		for (final EvaNode evaNode : c) {
			if (!(evaNode instanceof final @NotNull GNCoded coded)) {
				throw new IllegalStateException("node must be coded");
			}

			switch (coded.getRole()) {
			case FUNCTION -> {
				cr.registerFunction1((BaseEvaFunction) evaNode);
			}
			case CLASS -> {
				final EvaClass evaClass = (EvaClass) evaNode;

				// assert (evaClass.getCode() != 0);
				if (evaClass.getCode() == 0) {
					cr.registerClass1(evaClass);
				}

//					if (generatedClass.getCode() == 0)
//						generatedClass.setCode(mod.getCompilation().nextClassCode());
				for (EvaClass evaClass2 : evaClass.classMap.values()) {
					if (evaClass2.getCode() == 0) {
						// evaClass2.setCode(mod.getCompilation().nextClassCode());
						cr.registerClass1(evaClass2);
					}
				}
				for (EvaFunction generatedFunction : evaClass.functionMap.values()) {
					for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
						if (identTableEntry.isResolved()) {
							EvaNode node = identTableEntry.resolvedType();
							resolved_nodes.add(node);
						}
					}
				}
			}
			case NAMESPACE -> {
				final EvaNamespace evaNamespace = (EvaNamespace) evaNode;
				if (coded.getCode() == 0) {
					// coded.setCode(mod.getCompilation().nextClassCode());
					cr.registerNamespace(evaNamespace);
				}
				for (EvaClass evaClass3 : evaNamespace.classMap.values()) {
					if (evaClass3.getCode() == 0) {
						// evaClass.setCode(mod.getCompilation().nextClassCode());
						cr.registerClass1(evaClass3);
					}
				}
				for (EvaFunction generatedFunction : evaNamespace.functionMap.values()) {
					for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
						if (identTableEntry.isResolved()) {
							EvaNode node = identTableEntry.resolvedType();
							resolved_nodes.add(node);
						}
					}
				}
			}
			default -> throw new IllegalStateException("Unexpected value: " + coded.getRole());
			}
		}
	}

	public void part2() {
		resolved_nodes.stream()
				.filter(evaNode -> evaNode instanceof GNCoded)
				.map(evaNode -> (GNCoded) evaNode)
				.filter(coded -> coded.getCode() == 0).forEach(coded -> {
					tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("-*-*- __processResolvedNodes [NOT CODED] " + coded);
					coded.register(cr);
				});
	}

	public void part3(final @NotNull PipelineLogic pipelineLogic,
					  final WorldModule mod,
					  final DeducePhase.GeneratedClasses lgc) {
		pipelineLogic.pl.submit(mod);
		pipelineLogic.dp.deduceModule(mod, lgc, pipelineLogic.getVerbosity());
	}
}
