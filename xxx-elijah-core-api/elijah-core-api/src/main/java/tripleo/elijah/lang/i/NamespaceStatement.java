package tripleo.elijah.lang.i;

import tripleo.elijah.contexts.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface NamespaceStatement extends ModuleItem, StatementItem, FunctionItem, OS_Container, OS_NamedElement {
	OS_Type getOS_Type();

	enum Kind { // TODO 10/20 ??

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

	IInvariantStatement invariantStatement();

	void postConstruct();

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

//
//
//
