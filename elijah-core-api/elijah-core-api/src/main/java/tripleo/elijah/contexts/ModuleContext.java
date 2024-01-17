package tripleo.elijah.contexts;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

public interface ModuleContext extends Context {
	OS_Module getCarrier();

	@Override
	@Nullable Context getParent();

	@Override
	LookupResultList lookup(String name, int level, @NotNull LookupResultList Result,
							@NotNull ISearchList alreadySearched, boolean one);

	void setCarrier(OS_Module aCarrier);
}
