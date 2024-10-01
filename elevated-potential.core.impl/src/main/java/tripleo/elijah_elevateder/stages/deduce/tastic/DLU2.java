package tripleo.elijah_elevateder.stages.deduce.tastic;

import tripleo.elijah.lang.i.LookupResultList;
import tripleo.elijah.lang.i.OS_Element;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

class DLU2 {
	static LookupNode lookupNodeSimple(String lnis) {
		return new LookupNode() {
			@Override
			public String getLookupNodeIdentifierString() {
				return lnis;
			}
		};
	}

	interface LookupNode {
		String getLookupNodeIdentifierString();
	}

	interface LookupChain {
		LookupChain getParent();

		LookupNode getCurrent();
	}

	public void lookup2(LookupChain lc,
						Lookupable l,
						List<Predicate<OS_Element>> chooseCriteria,
						Packet p,
						BiConsumer<@Nullable OS_Element, Packet> cb) {
		final LookupResultList     lrl  = l.ctx().lookup(l.s());
		final @Nullable OS_Element best = lrl.chooseBest(chooseCriteria);

		cb.accept(best, p);
	}
}
