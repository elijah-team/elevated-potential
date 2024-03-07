package tripleo.elijah.lang.i;

import tripleo.elijah.diagnostic.*;

/**
 * Created 8/16/20 2:16 AM
 */
public interface TypeName extends Locatable {
	boolean nameEquals(String aName);

	boolean isNull();

	Type kindOfType();

	Context getContext();

	void setContext(Context context);

	enum Type {
		FUNCTION, GENERIC, NORMAL, TYPE_OF
	}
}
