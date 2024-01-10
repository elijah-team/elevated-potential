package tripleo.elijah.javac_model.annotation.processing;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Documented
@Retention(SOURCE)
@Target({PACKAGE, TYPE, METHOD, CONSTRUCTOR, FIELD,
		LOCAL_VARIABLE, PARAMETER})
public @interface Generated {

	String[] value();

	String date() default "";

	String comments() default "";
}
