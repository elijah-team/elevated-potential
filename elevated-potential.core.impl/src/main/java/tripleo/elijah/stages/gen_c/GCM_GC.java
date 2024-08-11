package tripleo.elijah.stages.gen_c;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.stages.logging.*;

class GCM_GC implements GCM_D {
	private final GenerateC       gc;
	private final IEvaConstructor gf;
	private final ElLog           LOG;

	public GCM_GC(final IEvaConstructor aGf, final ElLog aLOG, final GenerateC aGc) {
		gf = aGf;
		LOG = aLOG;
		gc = aGc;
	}

	@Override
	public void find_return_type(final Generate_Method_Header aGenerate_method_header__, final Eventual<String> ev) {
		final OS_Type type;
		final TypeTableEntry tte;
		String returnType = null;

		@Nullable
		final InstructionArgument result_index = gf.vte_lookup("self");
		if (result_index instanceof IntegerIA integerIA) {
			@NotNull
			final VariableTableEntry vte = integerIA.getEntry();
			assert vte.getVtt() == VariableTableType.SELF;

			// Get it from resolved
			tte = gf.getTypeTableEntry(integerIA.getIndex());
			final EvaNode res = tte.resolved();
			if (res instanceof final @NotNull EvaContainerNC nc) {
				if (nc instanceof EvaNamespace ens) {
					ESwitch.flep(ens, new DoneCallback<DeducedEvaNamespace>() {
						@Override
						public void onDone(final DeducedEvaNamespace result) {
							final int code = result.getCode();
							ev.resolve(String.format("Z%d*", code));
						}
					});
				}

				if (ev.isResolved()) {
					return;
				}
			}

			// Get it from type.attached
			type = tte.getAttached();

			LOG.info("228-1 " + type);
			assert type != null;
			if (type.isUnitType()) {
				assert false;
			} else {
				returnType = String.format("/*267*/%s*", gc.getTypeName(type));
			}

			ev.resolve(returnType);
			return;
		} else {
			ev.resolve("<<cant find self for ctor:57>>");
			return;
		}
	}
}
