package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.element.*;
import tripleo.elijah.javac_model.lang.model.element.ModuleElement.*;

import java.util.*;


public enum ElementFilter {
	;

	private static final Set<ElementKind> CONSTRUCTOR_KIND =
			Collections.unmodifiableSet(EnumSet.of(ElementKind.CONSTRUCTOR));

	private static final Set<ElementKind> FIELD_KINDS =
			Collections.unmodifiableSet(EnumSet.of(ElementKind.FIELD,
												   ElementKind.ENUM_CONSTANT));
	private static final Set<ElementKind> METHOD_KIND =
			Collections.unmodifiableSet(EnumSet.of(ElementKind.METHOD));

	private static final Set<ElementKind> PACKAGE_KIND =
			Collections.unmodifiableSet(EnumSet.of(ElementKind.PACKAGE));

	private static final Set<ElementKind> MODULE_KIND =
			Collections.unmodifiableSet(EnumSet.of(ElementKind.MODULE));

	private static final Set<ElementKind> TYPE_KINDS =
			Collections.unmodifiableSet(EnumSet.of(ElementKind.CLASS,
												   ElementKind.ENUM,
												   ElementKind.INTERFACE,
												   ElementKind.RECORD,
												   ElementKind.ANNOTATION_TYPE));

	private static final Set<ElementKind> RECORD_COMPONENT_KIND =
			Set.of(ElementKind.RECORD_COMPONENT);

	public static List<VariableElement>
	fieldsIn(Iterable<? extends Element> elements) {
		return listFilter(elements, FIELD_KINDS, VariableElement.class);
	}

	// Assumes targetKinds and E are sensible.
	private static <E extends Element> List<E> listFilter(Iterable<? extends Element> elements,
														  Set<ElementKind> targetKinds,
														  Class<E> clazz) {
		List<E> list = new ArrayList<>();
		for (Element e : elements) {
			if (targetKinds.contains(e.getKind()))
				list.add(clazz.cast(e));
		}
		return list;
	}

	public static Set<VariableElement>
	fieldsIn(Set<? extends Element> elements) {
		return setFilter(elements, FIELD_KINDS, VariableElement.class);
	}

	// Assumes targetKinds and E are sensible.
	private static <E extends Element> Set<E> setFilter(Set<? extends Element> elements,
														Set<ElementKind> targetKinds,
														Class<E> clazz) {
		// Return set preserving iteration order of input set.
		Set<E> set = new LinkedHashSet<>();
		for (Element e : elements) {
			if (targetKinds.contains(e.getKind()))
				set.add(clazz.cast(e));
		}
		return set;
	}

	public static List<RecordComponentElement>
	recordComponentsIn(Iterable<? extends Element> elements) {
		return listFilter(elements, RECORD_COMPONENT_KIND, RecordComponentElement.class);
	}

	public static Set<RecordComponentElement>
	recordComponentsIn(Set<? extends Element> elements) {
		return setFilter(elements, RECORD_COMPONENT_KIND, RecordComponentElement.class);
	}

	public static List<ExecutableElement>
	constructorsIn(Iterable<? extends Element> elements) {
		return listFilter(elements, CONSTRUCTOR_KIND, ExecutableElement.class);
	}

	public static Set<ExecutableElement>
	constructorsIn(Set<? extends Element> elements) {
		return setFilter(elements, CONSTRUCTOR_KIND, ExecutableElement.class);
	}

	public static List<ExecutableElement>
	methodsIn(Iterable<? extends Element> elements) {
		return listFilter(elements, METHOD_KIND, ExecutableElement.class);
	}

	public static Set<ExecutableElement>
	methodsIn(Set<? extends Element> elements) {
		return setFilter(elements, METHOD_KIND, ExecutableElement.class);
	}

	public static List<TypeElement>
	typesIn(Iterable<? extends Element> elements) {
		return listFilter(elements, TYPE_KINDS, TypeElement.class);
	}

	public static Set<TypeElement>
	typesIn(Set<? extends Element> elements) {
		return setFilter(elements, TYPE_KINDS, TypeElement.class);
	}

	public static List<PackageElement>
	packagesIn(Iterable<? extends Element> elements) {
		return listFilter(elements, PACKAGE_KIND, PackageElement.class);
	}

	public static Set<PackageElement>
	packagesIn(Set<? extends Element> elements) {
		return setFilter(elements, PACKAGE_KIND, PackageElement.class);
	}

	public static List<ModuleElement>
	modulesIn(Iterable<? extends Element> elements) {
		return listFilter(elements, MODULE_KIND, ModuleElement.class);
	}

	public static Set<ModuleElement>
	modulesIn(Set<? extends Element> elements) {
		return setFilter(elements, MODULE_KIND, ModuleElement.class);
	}

	public static List<ExportsDirective>
	exportsIn(Iterable<? extends Directive> directives) {
		return listFilter(directives, DirectiveKind.EXPORTS, ExportsDirective.class);
	}

	// Assumes directiveKind and D are sensible.
	private static <D extends Directive> List<D> listFilter(Iterable<? extends Directive> directives,
															DirectiveKind directiveKind,
															Class<D> clazz) {
		List<D> list = new ArrayList<>();
		for (Directive d : directives) {
			if (d.getKind() == directiveKind)
				list.add(clazz.cast(d));
		}
		return list;
	}

	public static List<OpensDirective>
	opensIn(Iterable<? extends Directive> directives) {
		return listFilter(directives, DirectiveKind.OPENS, OpensDirective.class);
	}

	public static List<ProvidesDirective>
	providesIn(Iterable<? extends Directive> directives) {
		return listFilter(directives, DirectiveKind.PROVIDES, ProvidesDirective.class);
	}

	public static List<RequiresDirective>
	requiresIn(Iterable<? extends Directive> directives) {
		return listFilter(directives, DirectiveKind.REQUIRES, RequiresDirective.class);
	}

	public static List<UsesDirective>
	usesIn(Iterable<? extends Directive> directives) {
		return listFilter(directives, DirectiveKind.USES, UsesDirective.class);
	}
}
