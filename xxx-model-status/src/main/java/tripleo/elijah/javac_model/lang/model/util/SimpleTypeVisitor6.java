package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.type.*;
import tripleo.elijah.javac_model.type.*;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_6;


@SupportedSourceVersion(RELEASE_6)
public class SimpleTypeVisitor6<R, P> extends AbstractTypeVisitor6<R, P> {
    protected final R DEFAULT_VALUE;

    @Deprecated(since="9")
    protected SimpleTypeVisitor6(){
        DEFAULT_VALUE = null;
    }

    @Deprecated(since="9")
    protected SimpleTypeVisitor6(R defaultValue){
        DEFAULT_VALUE = defaultValue;
    }

    protected R defaultAction(TypeMirror t, P p) {
        return DEFAULT_VALUE;
    }

    @Override
	public R visitPrimitive(PrimitiveType t, P p) {
        return defaultAction(t, p);
    }

    @Override
	public R visitNull(NullType t, P p){
        return defaultAction(t, p);
    }

    @Override
	public R visitArray(ArrayType t, P p){
        return defaultAction(t, p);
    }

    @Override
	public R visitDeclared(DeclaredType t, P p){
        return defaultAction(t, p);
    }

    @Override
	public R visitError(ErrorType t, P p){
        return defaultAction(t, p);
    }

    @Override
	public R visitTypeVariable(TypeVariable t, P p){
        return defaultAction(t, p);
    }

    @Override
	public R visitWildcard(WildcardType t, P p){
        return defaultAction(t, p);
    }

    @Override
	public R visitExecutable(ExecutableType t, P p) {
        return defaultAction(t, p);
    }

    @Override
	public R visitNoType(NoType t, P p){
        return defaultAction(t, p);
    }
}
