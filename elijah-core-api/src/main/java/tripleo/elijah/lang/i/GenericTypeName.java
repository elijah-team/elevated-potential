package tripleo.elijah.lang.i;

import java.io.*;

public interface GenericTypeName extends TypeName {
	int getColumn();

	int getColumnEnd();

	Context getContext();

	File getFile();

	int getLine();

	int getLineEnd();

	boolean isNull();

	Type kindOfType();

	void set(TypeModifiers modifiers_);

	void setConstraint(TypeName aConstraint);

	void setContext(Context context);

	void typeName(Qualident xy);
}
