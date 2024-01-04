package tripleo.elijah.javac_model.annotation.processing;

import javax.lang.model.SourceVersion;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface SupportedSourceVersion {
    SourceVersion value();
}
