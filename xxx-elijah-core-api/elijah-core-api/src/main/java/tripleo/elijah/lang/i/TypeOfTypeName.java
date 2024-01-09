package tripleo.elijah.lang.i;

import tripleo.elijah.g.*;

import java.io.*;

public interface TypeOfTypeName extends TypeName {
	// TODO what about keyword
	@Override
	int getColumn();

	@Override
	int getColumnEnd();

	@Override
	Context getContext();

	@Override
	File getFile();

	// TODO what about keyword
	@Override
	int getLine();

	@Override
	int getLineEnd();

	@Override
	boolean isNull();

	@Override
	Type kindOfType();

	TypeName resolve(Context ctx, GDeduceTypes2 deduceTypes2) throws GResolveError;

	void set(TypeModifiers modifiers_);

	@Override
	void setContext(Context context);

	Qualident typeOf();

	void typeOf(Qualident xy);
}
