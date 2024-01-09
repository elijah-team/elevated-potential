package tripleo.elijah.contexts;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

public interface IWithContext extends Context {
	@Override
	Context getParent();

	@Override
	LookupResultList lookup(String name, int level, @NotNull LookupResultList Result,
							@NotNull ISearchList alreadySearched, boolean one);
}
