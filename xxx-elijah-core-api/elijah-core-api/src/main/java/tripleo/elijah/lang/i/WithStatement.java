package tripleo.elijah.lang.i;

import antlr.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface WithStatement extends FunctionItem {
	void add(OS_Element anElement);

	void addDocString(Token aText);

	@Override
	Context getContext();

	List<FunctionItem> getItems();

	@Override
	OS_Element getParent();

	Collection<VariableStatement> getVarItems();

	List<OS_NamedElement> items();

	VariableStatement nextVarStmt();

	void postConstruct();

	void scope(Scope3 sco);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setContext(IWithContext ctx);

	@Override
	void visitGen(ElElementVisitor visit);
}
