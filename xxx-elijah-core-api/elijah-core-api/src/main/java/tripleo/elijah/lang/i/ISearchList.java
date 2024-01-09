package tripleo.elijah.lang.i;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;

public interface ISearchList {
	void add(Context c);

	boolean contains(Context context);

	@NotNull ImmutableList<Context> getList();
}
