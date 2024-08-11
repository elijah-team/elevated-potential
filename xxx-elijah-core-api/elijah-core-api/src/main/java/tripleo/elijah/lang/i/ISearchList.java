package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;

import com.google.common.collect.*;

public interface ISearchList {
	void add(Context c);

	boolean contains(Context context);

	@NotNull
	ImmutableList<Context> getList();
}
