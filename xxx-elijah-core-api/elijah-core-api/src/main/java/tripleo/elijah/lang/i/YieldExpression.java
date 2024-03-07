package tripleo.elijah.lang.i;

import tripleo.elijah.lang2.*;

public interface YieldExpression extends IExpression, OS_Element {
	@Override
	Context getContext();

	@Override
	OS_Element getParent();

	@Override
	void serializeTo(SmallWriter sw);

	@Override
	void visitGen(ElElementVisitor visit);
}
