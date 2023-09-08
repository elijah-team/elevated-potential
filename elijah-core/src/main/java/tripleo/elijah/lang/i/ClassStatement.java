package tripleo.elijah.lang.i;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.ClassContext;
import tripleo.elijah.lang.impl.InvariantStatement;
import tripleo.elijah.lang2.ElElementVisitor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface ClassStatement
		extends ModuleItem, StatementItem, FunctionItem, OS_Element, OS_Element2, Documentable, OS_Container {
	List<AnnotationPart> annotationIterable();

	void addAccess(AccessNotation aAcs);

	ConstructorDef addCtor(IdentExpression aX1);

	DestructorDef addDtor();

	DefFunctionDef defFuncDef();

	ClassInheritance classInheritance();

	FunctionDef funcDef();

	List<OS_Element2> findFunction(String string);

	Collection<ConstructorDef> getConstructors();

	@NotNull
	List<TypeName> getGenericPart();

	@Override
		// OS_Element
	ClassContext getContext();

	@Override
	OS_Element getParent();

	@Override
	void visitGen(ElElementVisitor visit);

	void setContext(ClassContext ctx);

	IdentExpression getNameNode();

	default List<ClassItem> getItems() {
		return items().stream().filter(x -> x instanceof ClassItem).map(x -> (ClassItem) x)
				.collect(Collectors.toList());
	}

	String getName();

	void setName(IdentExpression aCapitalX);

	OS_Type getOS_Type();

	OS_Package getPackageName();

	void setPackageName(OS_Package aPackage1);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	ClassTypes getType();

	void setType(ClassTypes aClassTypes);

	InvariantStatement invariantStatement();

	void postConstruct();

	PropertyStatement prop();

	void setHeader(ClassHeader aCh);

	StatementClosure statementClosure();

	void walkAnnotations(AnnotationWalker aWalker);

	final class __GetConstructorsHelper {
		public static ConstructorDef castClassItemToConstructor(@Nullable ClassItem input) {
			return (ConstructorDef) input;
		}

		public static boolean selectForConstructors(final ClassItem input) {
			return input instanceof ConstructorDef;
		}
	}
}
