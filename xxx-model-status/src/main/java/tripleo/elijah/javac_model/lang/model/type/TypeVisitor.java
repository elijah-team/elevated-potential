package tripleo.elijah.javac_model.lang.model.type;

public interface TypeVisitor<R, P> {
	default R visit(TypeMirror t) {
		return visit(t, null);
	}

	R visit(TypeMirror t, P p);

	R visitPrimitive(PrimitiveType t, P p);

	R visitNull(NullType t, P p);

	R visitArray(ArrayType t, P p);

	R visitDeclared(DeclaredType t, P p);

	R visitError(ErrorType t, P p);

	R visitTypeVariable(TypeVariable t, P p);

	R visitWildcard(WildcardType t, P p);

	R visitExecutable(ExecutableType t, P p);

	R visitNoType(NoType t, P p);

	R visitUnknown(TypeMirror t, P p);

	R visitUnion(UnionType t, P p);

	R visitIntersection(IntersectionType t, P p);
}
