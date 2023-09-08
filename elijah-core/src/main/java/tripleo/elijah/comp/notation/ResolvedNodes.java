package tripleo.elijah.comp.notation;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.world.i.WorldModule;

import java.util.ArrayList;
import java.util.List;

class ResolvedNodes {
	final         List<EvaNode>  resolved_nodes = new ArrayList<EvaNode>();
	private final ICodeRegistrar cr;

	public ResolvedNodes(final ICodeRegistrar aCr) {
		cr = aCr;
	}

	public void init(final DeducePhase.GeneratedClasses c) {
		__processNodes(c, resolved_nodes, cr);
	}

	@SuppressWarnings("TypeMayBeWeakened")
	private void __processNodes(final DeducePhase.@NotNull GeneratedClasses lgc,
								final @NotNull List<EvaNode> resolved_nodes,
								final @NotNull ICodeRegistrar cr) {
		for (final EvaNode evaNode : lgc) {
			if (!(evaNode instanceof final @NotNull GNCoded coded)) {
				throw new IllegalStateException("node must be coded");
			}

			switch (coded.getRole()) {
			case FUNCTION -> {
				cr.registerFunction1((BaseEvaFunction) evaNode);
			}
			case CLASS -> {
				final EvaClass evaClass = (EvaClass) evaNode;

				//assert (evaClass.getCode() != 0);
				if (evaClass.getCode() == 0) {
					cr.registerClass1(evaClass);
				}

//					if (generatedClass.getCode() == 0)
//						generatedClass.setCode(mod.getCompilation().nextClassCode());
				for (EvaClass evaClass2 : evaClass.classMap.values()) {
					if (evaClass2.getCode() == 0) {
						//evaClass2.setCode(mod.getCompilation().nextClassCode());
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
					//coded.setCode(mod.getCompilation().nextClassCode());
					cr.registerNamespace(evaNamespace);
				}
				for (EvaClass evaClass3 : evaNamespace.classMap.values()) {
					if (evaClass3.getCode() == 0) {
						//evaClass.setCode(mod.getCompilation().nextClassCode());
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
		__processResolvedNodes(resolved_nodes, cr);
	}

	private void __processResolvedNodes(final @NotNull List<EvaNode> resolved_nodes, final ICodeRegistrar cr) {
		resolved_nodes.stream()
				.filter(evaNode -> evaNode instanceof GNCoded)
				.map(evaNode -> (GNCoded) evaNode)
				.filter(coded -> coded.getCode() == 0)
				.forEach(coded -> {
					System.err.println("-*-*- __processResolvedNodes [NOT CODED] " + coded);
					coded.register(cr);
				});
	}

	public void part3(final @NotNull PipelineLogic pipelineLogic, final WorldModule mod, final DeducePhase.GeneratedClasses lgc) {
		pipelineLogic.dp.deduceModule(mod, lgc, pipelineLogic.getVerbosity());
	}
}
