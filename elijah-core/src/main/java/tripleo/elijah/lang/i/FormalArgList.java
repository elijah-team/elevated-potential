package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;

import java.util.*;

public interface FormalArgList {
	List<FormalArgListItem> falis();

	@NotNull List<FormalArgListItem> items();

	FormalArgListItem next();

	void serializeTo(SmallWriter sw);

	@Override
	String toString();
}
