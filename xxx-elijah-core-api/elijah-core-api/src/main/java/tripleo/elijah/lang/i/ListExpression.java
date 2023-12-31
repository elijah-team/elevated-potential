package tripleo.elijah.lang.i;

import tripleo.elijah.diagnostic.Locatable;

import java.io.File;

public interface ListExpression extends IExpression, Locatable {
	@Override
	int getColumn();

	@Override
	int getColumnEnd();

	@Override
	File getFile();

	@Override
	int getLine();

	@Override
	int getLineEnd();

	@Override
	boolean is_simple();

	void setContents(ExpressionList aList);
}
