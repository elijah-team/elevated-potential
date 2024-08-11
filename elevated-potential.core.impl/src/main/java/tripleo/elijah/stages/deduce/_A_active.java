package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.gen_fn.*;

import java.util.*;

class _A_active {
	private final DeduceTypes2     deduceTypes2;
	private final List<DE3_Active> _actives;

	_A_active(final DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
		_actives     = deduceTypes2._inj().new_ArrayList__DE3_Active();
	}

	public void activePTE(final DeduceTypes2 aDeduceTypes2, final ProcTableEntry aProcTableEntry, final ClassInvocation aClassInvocation) {
		final DE3_Active pte = createPTE(aDeduceTypes2, aProcTableEntry, aClassInvocation);
		_actives.add(pte);
	}

	private DE3_Active createPTE(final DeduceTypes2 aDeduceTypes2, final ProcTableEntry aProcTableEntry, final ClassInvocation aClassInvocation) {
		return deduceTypes2._inj().new_DE3_ActivePTE(aDeduceTypes2, aProcTableEntry, aClassInvocation);
	}

	public void addToPhase(final DeducePhase aPhase2) {
		aPhase2.addActives(this._actives);
	}
}
