package tripleo.elijah.contexts;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

public interface MatchContext extends Context {
	@Override
	Context getParent();

	@Override
	LookupResultList lookup(String name, int level, LookupResultList Result,
							@NotNull ISearchList alreadySearched, boolean one);
}
