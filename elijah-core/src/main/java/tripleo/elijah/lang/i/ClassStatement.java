package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang2.*;

import java.util.*;
import java.util.stream.*;

public interface ClassStatement
		extends ModuleItem, StatementItem, FunctionItem, OS_Element, OS_Element2, Documentable, OS_Container {
	final class __GetConstructorsHelper {
		public static ConstructorDef castClassItemToConstructor(@Nullable ClassItem input) {
			return (ConstructorDef) input;
		}

		public static boolean selectForConstructors(final ClassItem input) {
			return input instanceof ConstructorDef;
		}
	}

	void addAccess(AccessNotation aAcs);

	ConstructorDef addCtor(IdentExpression aX1);

	DestructorDef addDtor();

	List<AnnotationPart> annotationIterable();

	ClassInheritance classInheritance();

	DefFunctionDef defFuncDef();

	List<OS_Element2> findFunction(String string);

	FunctionDef funcDef();

	Collection<ConstructorDef> getConstructors();

	@Override
		// OS_Element
	ClassContext getContext();

	@NotNull
	List<TypeName> getGenericPart();

	default List<ClassItem> getItems() {
		return items().stream().filter(x -> x instanceof ClassItem).map(x -> (ClassItem) x)
				.collect(Collectors.toList());
	}

	String getName();

	IdentExpression getNameNode();

	OS_Type getOS_Type();

	OS_Package getPackageName();

	@Override
	OS_Element getParent();

	ClassTypes getType();

	InvariantStatement invariantStatement();

	void postConstruct();

	PropertyStatement prop();

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setContext(ClassContext ctx);

	void setHeader(ClassHeader aCh);

	void setName(IdentExpression aCapitalX);

	void setPackageName(OS_Package aPackage1);

	void setType(ClassTypes aClassTypes);

	StatementClosure statementClosure();

	@Override
	void visitGen(ElElementVisitor visit);

	void walkAnnotations(AnnotationWalker aWalker);
}
