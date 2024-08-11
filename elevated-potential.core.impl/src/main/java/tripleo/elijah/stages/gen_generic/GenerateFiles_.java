package tripleo.elijah.stages.gen_generic;

import java.util.*;

import org.jetbrains.annotations.*;

import com.google.common.collect.*;

import tripleo.elijah.stages.gen_fn.*;

public enum GenerateFiles_ {
	;

	@NotNull
	public static Collection<EvaNode> classes_to_list_of_generated_nodes(@NotNull Collection<EvaClass> aEvaClasses) {
		return Collections2.transform(aEvaClasses, t -> t);
	}

	@NotNull
	public static Collection<EvaNode> constructors_to_list_of_generated_nodes(
			@NotNull Collection<IEvaConstructor> aEvaConstructors) {
		return Collections2.transform(aEvaConstructors, input -> input);
	}

	@NotNull
	public static Collection<EvaNode> functions_to_list_of_generated_nodes(
			@NotNull Collection<EvaFunction> generatedFunctions) {
		return Collections2.transform(generatedFunctions, input -> input);
	}
}
