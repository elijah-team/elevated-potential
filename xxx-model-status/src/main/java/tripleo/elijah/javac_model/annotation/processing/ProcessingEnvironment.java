package tripleo.elijah.javac_model.annotation.processing;

import tripleo.elijah.javac_model.lang.model.util.Elements;
import tripleo.elijah.javac_model.lang.model.util.Types;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.SourceVersion;
import java.util.Locale;
import java.util.Map;

public interface ProcessingEnvironment {
    Map<String,String> getOptions();

    Messager getMessager();

    Filer getFiler();

    Elements getElementUtils();

    Types getTypeUtils();

    SourceVersion getSourceVersion();

    Locale getLocale();

    default boolean isPreviewEnabled() {
        return false;
    }
}
