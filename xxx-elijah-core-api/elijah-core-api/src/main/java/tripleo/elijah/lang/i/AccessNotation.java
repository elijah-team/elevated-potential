package tripleo.elijah.lang.i;

import antlr.*;
import tripleo.elijah.lang2.*;

public interface AccessNotation extends OS_Element {
	Token getCategory();

	@Override
	Context getContext();

	@Override
	OS_Element getParent();

	void setCategory(Token category);

	void setShortHand(Token shorthand);

	void setTypeNames(TypeNameList tnl);

	@Override
	void visitGen(ElElementVisitor visit);
}
