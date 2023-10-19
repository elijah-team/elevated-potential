package tripleo.elijah.lang.i;

import com.google.common.collect.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.lang.nextgen.names.i.*;

import java.util.*;

public interface Context {
	class SearchList {
		@NotNull
		List<Context> alreadySearched = new ArrayList<>();

		public void add(Context c) {
			alreadySearched.add(c);
		}

		public boolean contains(Context context) {
			return alreadySearched.contains(context);
		}

		public @NotNull ImmutableList<Context> getList() {
			return ImmutableList.copyOf(alreadySearched);
		}
	}

	void addName(EN_Name aName);

	@NotNull
	Compilation compilation();

	@Nullable
	Context getParent();

	LookupResultList lookup(@NotNull String name);

	LookupResultList lookup(String name, int level, LookupResultList Result, SearchList alreadySearched, boolean one);

	@NotNull
	OS_Module module();
}
