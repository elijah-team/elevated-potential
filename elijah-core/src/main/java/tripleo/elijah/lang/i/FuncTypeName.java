package tripleo.elijah.lang.i;

import java.io.*;

public interface FuncTypeName extends TypeName {
	void argList(FormalArgList op);

	void argList(TypeNameList tnl);

	boolean argListIsGeneric();

	@Override
	int getColumn();

	@Override
	int getColumnEnd();

	@Override
	Context getContext();

	@Override
	File getFile();

	@Override
	int getLine();

	@Override
	int getLineEnd();

	@Override
	boolean isNull();

	@Override
	Type kindOfType();

	void returnValue(TypeName rtn);


	@Override
	void setContext(Context context);


	// @Override
	void type(TypeModifiers typeModifiers);


}
