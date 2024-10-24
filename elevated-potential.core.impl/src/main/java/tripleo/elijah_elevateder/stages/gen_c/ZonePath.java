package tripleo.elijah_elevateder.stages.gen_c;

import tripleo.elijah_elevateder.stages.instructions.IdentIA;
import tripleo.elijah_elevateder.stages.instructions.InstructionArgument;

import java.util.List;

public class ZonePath implements ZoneMember {
	private final IdentIA identIA;
	private final List<InstructionArgument> s;

	public ZonePath(final IdentIA aIdentIA, final List<InstructionArgument> aS) {
		identIA = aIdentIA;
		s = aS;
	}
}
