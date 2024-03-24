package tripleo.elijah_durable_elevated.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.elijah.stages.instructions.IdentIA;
import tripleo.elijah_durable_elevated.elijah.stages.instructions.InstructionArgument;

import java.util.*;

import static tripleo.elijah_durable_elevated.elijah.stages.gen_c.CReference._getIdentIAPathList;

class Zone {
	private final Map<Object, ZoneMember> members = new HashMap<Object, ZoneMember>();
	private final GenerateC generateC;

	Zone(final GenerateC aGenerateC) {
		generateC = aGenerateC;
	}

	public ZoneVTE get(final VariableTableEntry aVarTableEntry, final BaseEvaFunction aGf) {
		if (members.containsKey(aVarTableEntry))
			return (ZoneVTE) members.get(aVarTableEntry);

		final ZoneVTE r = new ZoneVTE__1(aVarTableEntry, aGf, this.generateC);
		members.put(aVarTableEntry, r);
		return r;
	}

	public ZonePath getPath(final @NotNull IdentIA aIdentIA) {
		final List<InstructionArgument> s = _getIdentIAPathList(aIdentIA);

		if (members.containsKey(aIdentIA))
			return (ZonePath) members.get(aIdentIA);

		final ZonePath r = new ZonePath(aIdentIA, s);
		members.put(aIdentIA, r);
		return r;
	}

	public ZoneITE get(final IdentTableEntry aIdentTableEntry, final BaseEvaFunction aGf) {
		if (members.containsKey(aIdentTableEntry))
			return (ZoneITE) members.get(aIdentTableEntry);

		final ZoneITE r = new ZoneITE__1(aIdentTableEntry, aGf, this.generateC);
		members.put(aIdentTableEntry, r);
		return r;
	}

	public ZoneITE get(final IdentIA target) {
		BaseEvaFunction gf              = target.gf;
		IdentTableEntry identTableEntry = gf.getIdentTableEntry(target.getIndex());
		ZoneITE zi = this.get(identTableEntry, gf);
		return zi;
	}

	// public GI_Item get(final EvaNode aGeneratedNode) {
	// }
}
