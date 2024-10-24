/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.lang_impl;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.IClassContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah_elevated_durable.names_impl.ENU_ClassName;
import tripleo.elijah_elevateder.contexts.ClassContext;
import tripleo.elijah_elevateder.lang.types.OS_UserClassType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a "class"
 * <p>
 * items -> ClassItems docstrings variables
 */
public class ClassStatementImpl extends _CommonNC implements ClassItem, ClassStatement {

	static final List<TypeName> emptyTypeNameList = ImmutableList.<TypeName>of();
	private final OS_Element parent;
	private OS_Type __cached_osType;
	private ClassHeader hdr;

	public ClassStatementImpl(final @NotNull OS_Element parentElement, final Context parentContext) {
		parent = parentElement; // setParent

		@NotNull
		final ElObjectType x = DecideElObjectType.getElObjectType(parentElement);
		switch (x) {
		case MODULE:
			final OS_Module module = (OS_Module) parentElement;
			//
			this.setPackageName(module.pullPackageName());
			_packageName.addElement(this);
			module.add(this);
			break;
		case FUNCTION:
			// do nothing
			break;
		default:
			// we kind of fail the switch test here because OS_Container is not an
			// OS_Element,
			// so we have to test explicitly, messing up the pretty flow we had.
			// hey sh*t happens.
			if (parentElement instanceof OS_Container) {
				((OS_Container) parentElement).add(this);
			} else {
				throw new IllegalStateException(String.format("Cant add ClassStatement to %s", parentElement));
			}
		}

		_a = new AttachedImpl(new ClassContext(parentContext, this));
	}

	@Override // OS_Container
	public void add(final OS_Element anElement) {
		if (!(anElement instanceof ClassItem))
			throw new IllegalStateException(String.format("Cant add %s to ClassStatement", anElement));
		items.add((ClassItem) anElement);
	}

	@Override
	public @NotNull ConstructorDef addCtor(final IdentExpression aConstructorName) {
		return new ConstructorDefImpl(aConstructorName, this, getContext());
	}

	@Override
	public @NotNull DestructorDef addDtor() {
		return new DestructorDefImpl(this, getContext());
	}

	@Override
	public @NotNull List<AnnotationPart> annotationIterable() {
		List<AnnotationClause> annotations = hdr.annos();

		List<AnnotationPart> aps = new ArrayList<AnnotationPart>();
		if (annotations == null)
			return aps;
		for (AnnotationClause annotationClause : annotations) {
			aps.addAll(annotationClause.aps());
		}
		return aps;
	}

	@Override
	public ClassInheritance classInheritance() {
		return hdr.inheritancePart();
	}

	@Override
	public @NotNull DefFunctionDef defFuncDef() {
		return new DefFunctionDefImpl(this, getContext());
	}

	@Override
	public List<OS_NamedElement> findFunction(final String name) {
		return items().stream().filter(item -> item instanceof FunctionDef && !(item instanceof ConstructorDef))
				.filter(item -> item.name().equals(name)).collect(Collectors.toList());
	}

	@Override
	public @NotNull FunctionDef funcDef() {
		return new FunctionDefImpl(this, getContext());
	}

	@Override
	public @NotNull Collection<ConstructorDef> getConstructors() {
		var y = items.stream().filter(__GetConstructorsHelper::selectForConstructors)
				.map(__GetConstructorsHelper::castClassItemToConstructor).collect(Collectors.toList());
		return y;
	}

	@Override // OS_Element
	public IClassContext getContext() {
		return (IClassContext) _a.getContext();
	}

	@Override
	public @org.jetbrains.annotations.Nullable EN_Name getEnName() {
		if (hdr == null)
			return null;

		return hdr.nameToken().getName();
	}

	@Override
	public @NotNull List<TypeName> getGenericPart() {
		if (hdr.genericPart() == null)
			return emptyTypeNameList;
		else
			return hdr.genericPart().p();
	}

	@Override
	public @NotNull String getName() {
		if (hdr == null)
			throw new IllegalStateException("null hdr");
		if (hdr.nameToken() == null)
			throw new IllegalStateException("null name");
		return hdr.nameToken().getText();
	}

	// region inheritance

	@Override
	public IdentExpression getNameNode() {
		return hdr.nameToken();
	}

	@Override
	public @NotNull OS_Type getOS_Type() {
		if (__cached_osType == null)
			__cached_osType = new OS_UserClassType(this);
		return __cached_osType;
	}

	@Override
	public OS_Element getParent() {
		return parent;
	}

	// endregion

	// region annotations

	@Override
	public ClassTypes getType() {
		return hdr.type();
	}

	// endregion

	// region called from parser

	@Override
	public IInvariantStatement invariantStatement() {
		NotImplementedException.raise();
		return null;
	}

	@Override
	public void postConstruct() {
		assert hdr.nameToken() != null;
		int destructor_count = 0;
		for (ClassItem item : items) {
			if (item instanceof DestructorDef)
				destructor_count++;
		}
		assert destructor_count == 0 || destructor_count == 1;
	}

	@Override
	public @NotNull PropertyStatement prop() {
		PropertyStatement propertyStatement = new PropertyStatementImpl(this, getContext());
		add(propertyStatement);
		return propertyStatement;
	}

	@Override
	public void serializeTo(final @NotNull SmallWriter sw) {
		// name inheritance items
		// packagename parent

		// header: annos genericPart inheritancePart (name)
		// type: ABSTRACT, ANNOTATION, EXCEPTION, INTERFACE, NORMAL, SIGNATURE,
		// STRUCTURE

		sw.fieldIdent("name", hdr.nameToken());
		sw.fieldString("type", getType().toString());
		sw.fieldString("packageName", _packageName.getName());
		var pr = sw.createRef(getParent());
		sw.fieldRef("parent", pr);

		var inh = sw.createTypeNameList();
		for (TypeName tn : classInheritance().tns()) {
			inh.add(tn);
		}
		sw.fieldTypenameList("inheritance", inh);

		var genr = sw.createTypeNameList();
		for (TypeName tn : getGenericPart()) {
			genr.add(tn);
		}
		sw.fieldTypenameList("genericPart", inh);

		sw.fieldList("items", getItems());
	}

	@Override
	public void setContext(final IClassContext ctx) {
		_a.setContext(ctx);
	}

	public void setGenericPart(TypeNameList genericPart) {
//		this.genericPart = genericPart;
		throw new NotImplementedException();
	}

	@Override
	public void setHeader(ClassHeader aCh) {
		hdr = aCh;

		getEnName().addUnderstanding(new ENU_ClassName());
	}

	public void setInheritance(ClassInheritance inh) {
//		_inh = inh;
		throw new NotImplementedException();
	}

	@Override
	public void setType(final ClassTypes aType) {
//		_type = aType;
		throw new NotImplementedException();
	}

	// endregion

	@Override
	public @NotNull StatementClosure statementClosure() {
		return new AbstractStatementClosure(this);
	}

	@Override
	public String toString() {
		final String package_name;
		if (getPackageName() != null && getPackageName().getName2() != null) {
			final Qualident package_name_q = getPackageName().getName2();
			package_name = package_name_q.toString();
		} else
			package_name = "`'";
		return String.format("<Class %s %s>", package_name, getName());
	}

	public @org.jetbrains.annotations.Nullable TypeAliasStatement typeAlias() {
		NotImplementedException.raise();
		return null;
	}

	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.addClass(this); // TODO visitClass
	}

	public @NotNull ProgramClosure XXX() {
		return new ProgramClosureImpl() {
		};
	}

	public static final class __GetConstructorsHelper {
		public static ConstructorDef castClassItemToConstructor(@Nullable ClassItem input) {
			return (ConstructorDef) input;
		}

		public static boolean selectForConstructors(final ClassItem input) {
			return input instanceof ConstructorDef;
		}
	}
}

//
//
//
