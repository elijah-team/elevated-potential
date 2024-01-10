package tripleo.elijah.javac_model.lang.model.element;

import tripleo.elijah.javac_model.lang.model.type.TypeMirror;

import java.util.List;

public interface ModuleElement extends Element, QualifiedNameable {
	@Override
	TypeMirror asType();

	@Override
	Name getSimpleName();

	@Override
	Element getEnclosingElement();

	@Override
	List<? extends Element> getEnclosedElements();

	@Override
	Name getQualifiedName();

	boolean isOpen();

	boolean isUnnamed();

	List<? extends Directive> getDirectives();

	enum DirectiveKind {
		REQUIRES,
		EXPORTS,
		OPENS,
		USES,
		PROVIDES
	}

	interface Directive {
		DirectiveKind getKind();

		<R, P> R accept(DirectiveVisitor<R, P> v, P p);
	}

	interface DirectiveVisitor<R, P> {
		default R visit(Directive d) {
			return d.accept(this, null);
		}

		default R visit(Directive d, P p) {
			return d.accept(this, p);
		}

		R visitRequires(RequiresDirective d, P p);

		R visitExports(ExportsDirective d, P p);

		R visitOpens(OpensDirective d, P p);

		R visitUses(UsesDirective d, P p);

		R visitProvides(ProvidesDirective d, P p);

		default R visitUnknown(Directive d, P p) {
			throw new UnknownDirectiveException(d, p);
		}
	}

	interface RequiresDirective extends Directive {
		boolean isStatic();

		boolean isTransitive();

		ModuleElement getDependency();
	}

	interface ExportsDirective extends Directive {

		PackageElement getPackage();

		List<? extends ModuleElement> getTargetModules();
	}

	interface OpensDirective extends Directive {

		PackageElement getPackage();

		List<? extends ModuleElement> getTargetModules();
	}

	interface ProvidesDirective extends Directive {
		TypeElement getService();

		List<? extends TypeElement> getImplementations();
	}

	interface UsesDirective extends Directive {
		TypeElement getService();
	}
}
