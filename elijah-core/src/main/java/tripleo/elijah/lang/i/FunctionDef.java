package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.types.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface FunctionDef extends OS_Element, OS_Element2, Documentable, ClassItem {
	enum Species {
		CTOR, DEF_FUN, DTOR, FUNC_EXPR, PROP_GET, PROP_SET, REG_FUN
	}

	void add(FunctionItem seq);

	FormalArgList fal();

	Collection<FormalArgListItem> getArgs();

	List<FunctionItem> getItems();

	IdentExpression getNameNode();

	OS_FuncType getOS_Type();

	@Override
	OS_Element getParent();

	Species getSpecies();

	Collection<OS_Element2> items();

	void postConstruct();

	@Nullable
	TypeName returnType();

	void scope(Scope3 sco);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void set(FunctionModifiers mod);

	void setAbstract(boolean b);

	void setAnnotations(List<AnnotationClause> aAs);

	void setBody(FunctionBody aFunctionBody);

	void setContext(FunctionContext aContext);

	void setFal(FormalArgList aFal);

	void setHeader(FunctionHeader aFunctionHeader);

	void setName(IdentExpression string_to_ident);

	void setReturnType(TypeName tn);

	void setSpecies(Species propGet);

	@Override
	String toString();

	@Override // OS_Element
	void visitGen(ElElementVisitor visit);

}

//
//
//
