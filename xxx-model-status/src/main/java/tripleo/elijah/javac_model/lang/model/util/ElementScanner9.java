package tripleo.elijah.javac_model.lang.model.util;

import tripleo.elijah.javac_model.lang.model.element.ModuleElement;

import javax.annotation.processing.SupportedSourceVersion;

import static javax.lang.model.SourceVersion.RELEASE_14;


@SupportedSourceVersion(RELEASE_14)
public class ElementScanner9<R, P> extends ElementScanner8<R, P> {
    protected ElementScanner9(){
        super(null);
    }

    protected ElementScanner9(R defaultValue){
        super(defaultValue);
    }

    @Override
    public R visitModule(ModuleElement e, P p) {
        return scan(e.getEnclosedElements(), p); // TODO: Hmmm, this might not be right
    }
}
