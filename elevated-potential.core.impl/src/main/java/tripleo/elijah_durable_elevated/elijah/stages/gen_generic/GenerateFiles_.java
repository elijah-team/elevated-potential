package tripleo.elijah_durable_elevated.elijah.stages.gen_generic;

import com.google.common.collect.Collections2;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaFunction;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.IEvaConstructor;

import java.util.Collection;

public enum GenerateFiles_ {
	;

	@NotNull public static Collection<EvaNode> classes_to_list_of_generated_nodes(@NotNull Collection<EvaClass> aEvaClasses) {
		return Collections2.transform(aEvaClasses, t -> t);
	}

	@NotNull public static Collection<EvaNode> constructors_to_list_of_generated_nodes(@NotNull Collection<IEvaConstructor> aEvaConstructors) {
		return Collections2.transform(aEvaConstructors, input -> input);
	}

	@NotNull public static Collection<EvaNode> functions_to_list_of_generated_nodes(@NotNull Collection<EvaFunction> generatedFunctions) {
		return Collections2.transform(generatedFunctions, input -> input);
	}
}
