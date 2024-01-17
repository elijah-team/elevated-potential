package tripleo.elijah.javac_model.lang.model.element;

public interface ElementVisitor<R, P> {
	default R visit(Element e) {
		return visit(e, null);
	}

	R visit(Element e, P p);

	R visitPackage(PackageElement e, P p);

	R visitType(TypeElement e, P p);

	R visitVariable(VariableElement e, P p);

	R visitExecutable(ExecutableElement e, P p);

	R visitTypeParameter(TypeParameterElement e, P p);

	default R visitModule(ModuleElement e, P p) {
		return visitUnknown(e, p);
	}

	R visitUnknown(Element e, P p);

	default R visitRecordComponent(RecordComponentElement e, P p) {
		return visitUnknown(e, p);
	}
}
