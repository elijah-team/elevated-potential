package tripleo.elijah.lang.i;

import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface NamespaceStatement extends ModuleItem, StatementItem, FunctionItem, OS_Container, OS_Element2 {
	OS_Type getOS_Type();

	public enum Kind {

	}

	@Override
	// OS_Container
	void add(OS_Element anElement);

	void addAccess(AccessNotation aAcs);

	void addAnnotations(List<AnnotationClause> aAs);

	FunctionDef funcDef();

	@Override
	// OS_Element
	Context getContext();

	List<ClassItem> getItems();

	NamespaceTypes getKind();

	String getName();

	OS_Package getPackageName();

	InvariantStatement invariantStatement();

	void postConstruct();

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setContext(NamespaceContext aCtx);

	void setName(IdentExpression aI1);

	void setType(NamespaceTypes aNamespaceTypes);

	StatementClosure statementClosure();

	TypeAliasStatement typeAlias();

	@Override
	// OS_Element
	void visitGen(ElElementVisitor visit);

	ProgramClosure XXX();
}
