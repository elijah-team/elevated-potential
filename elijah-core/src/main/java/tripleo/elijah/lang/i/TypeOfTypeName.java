package tripleo.elijah.lang.i;

import tripleo.elijah.stages.deduce.*;

import java.io.*;

public interface TypeOfTypeName extends TypeName {
	// TODO what about keyword
	int getColumn();

	int getColumnEnd();

	Context getContext();

	File getFile();

	// TODO what about keyword
	int getLine();

	int getLineEnd();


	boolean isNull();

	Type kindOfType();

	TypeName resolve(Context ctx, DeduceTypes2 deduceTypes2) throws ResolveError;

	void set(TypeModifiers modifiers_);

	void setContext(Context context);

	Qualident typeOf();

	void typeOf(Qualident xy);
}
