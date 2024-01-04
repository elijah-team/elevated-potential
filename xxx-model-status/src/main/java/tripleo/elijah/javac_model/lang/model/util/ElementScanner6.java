package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.element.*;
import tripleo.elijah.javac_model.lang.model.element.*;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_6;


@SupportedSourceVersion(RELEASE_6)
public class ElementScanner6<R, P> extends AbstractElementVisitor6<R, P> {
    protected final R DEFAULT_VALUE;

    @Deprecated(since="9")
    protected ElementScanner6(){
        DEFAULT_VALUE = null;
    }

    @Deprecated(since="9")
    protected ElementScanner6(R defaultValue){
        DEFAULT_VALUE = defaultValue;
    }

    public final R scan(Iterable<? extends Element> iterable, P p) {
        R result = DEFAULT_VALUE;
        for(Element e : iterable)
            result = scan(e, p);
        return result;
    }

    public R scan(Element e, P p) {
        return e.accept(this, p);
    }

    public final R scan(Element e) {
        return scan(e, null);
    }

    @Override
	public R visitPackage(PackageElement e, P p) {
        return scan(e.getEnclosedElements(), p);
    }

    @Override
	public R visitType(TypeElement e, P p) {
        return scan(e.getEnclosedElements(), p);
    }

    @Override
	public R visitVariable(VariableElement e, P p) {
        if (e.getKind() != ElementKind.RESOURCE_VARIABLE)
            return scan(e.getEnclosedElements(), p);
        else
            return visitUnknown(e, p);
    }

    @Override
	public R visitExecutable(ExecutableElement e, P p) {
        return scan(e.getParameters(), p);
    }

    @Override
	public R visitTypeParameter(TypeParameterElement e, P p) {
        return scan(e.getEnclosedElements(), p);
    }
}
