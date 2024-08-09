package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.types.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.stages.logging.*;

class GCM_GF implements GCM_D {
	private final GenerateC gc;
	private final EvaFunction gf;
	private final ElLog LOG;

	public GCM_GF(final EvaFunction aGf, final ElLog aLOG, final GenerateC aGc) {
		gf = aGf;
		LOG = aLOG;
		gc = aGc;
	}

	@Override
	public void find_return_type(final Generate_Method_Header aGenerate_method_header__, final Eventual<@NotNull String> ev) {
		var fd = gf.getFD();

		if (fd.returnType() instanceof RegularTypeName rtn) {
			var n = rtn.getName();
			if ("Unit".equals(n)) {
				ev.resolve("void");  // why not??
				return;
			} else if ("SystemInteger".equals(n)) {
				// FIXME doesn't seem like much to do, but
				ev.resolve("int");  // why not??
				return;
			}
		}

		String returnType;
		final TypeTableEntry tte;
		final OS_Type type;

		@Nullable InstructionArgument result_index = gf.vte_lookup("Result");
		if (result_index == null) {
			// if there is no Result, there should be Value
			result_index = gf.vte_lookup("Value");
			// but Value might be passed in. If it is, discard value
			@NotNull
			final VariableTableEntry vte = ((IntegerIA) result_index).getEntry();
			if (vte.getVtt() != VariableTableType.RESULT)
				result_index = null;
			if (result_index == null) {
				// README Assuming Unit
				ev.resolve("void");  // why not??
				return;
			}
		}

		// Get it from resolved
		tte = gf.getTypeTableEntry(((IntegerIA) result_index).getIndex());
		final EvaNode res = tte.resolved();
		if (res instanceof final @NotNull EvaContainerNC nc) {
			final DeducedEvaNode den;
			if (nc instanceof EvaClass rec) {
				den = this.gc.a_lookup(rec).ool();
			} else if (nc instanceof EvaNamespace ren) {
				den = this.gc.a_lookup(ren).ool();
			} else {
				den = null;
			}
			if (den != null) {
				int code = den.getCode();
				final String s = String.format("Z%d*", code);
				ev.resolve(s);
				return;
			}
		}

		// Get it from type.attached
		type = tte.getAttached();

		LOG.info("228 " + type);
		if (type == null) {
			// FIXME request.operation.fail(655) 06/16
			// as opposed to current-operation
			LOG.err("655 Shouldn't be here (type is null)");
			returnType = "ERR_type_attached_is_null/*2*/";
		} else if (type.isUnitType()) {
			// returnType = "void/*Unit-74*/";
			returnType = "void";
		} else {
			if (type instanceof final @NotNull OS_GenericTypeNameType genericTypeNameType) {
				final TypeName tn = genericTypeNameType.getRealTypeName();

				final ClassInvocation.CI_GenericPart genericPart = gf.fi.getClassInvocation().genericPart();

				final OS_Type realType = genericPart.valueForKey(tn);

				assert realType != null;
				returnType = String.format("/*267*/%s*", gc.getTypeName(realType));
			} else
				returnType = String.format("/*267-1*/%s*", gc.getTypeName(type));
		}

		ev.resolve(returnType);
		return;
	}
}
