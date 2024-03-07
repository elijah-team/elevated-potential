package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang2.*;

public interface MC1 extends OS_Element, Documentable {
	void add(FunctionItem aItem);

	//@Override
	//Context getContext();

	Iterable<? extends FunctionItem> getItems();

	@Override
	default void visitGen(@NotNull ElElementVisitor visit) {
		visit.visitMC1(this);
	}
}
