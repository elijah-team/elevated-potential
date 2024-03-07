package tripleo.elijah.lang.i;

import antlr.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface SyntacticBlock extends FunctionItem {
	void add(OS_Element anElement);

	void addDocString(Token s1);

	@Override
	Context getContext();

	List<FunctionItem> getItems();

	@Override
	OS_Element getParent();

	List<OS_NamedElement> items();

	void postConstruct();

	void scope(Scope3 sco);

	//@Override
	//default void serializeTo(SmallWriter sw) {
	//
	//}

	void setContext(ISyntacticBlockContext ctx);

	@Override
	void visitGen(ElElementVisitor visit);
}
