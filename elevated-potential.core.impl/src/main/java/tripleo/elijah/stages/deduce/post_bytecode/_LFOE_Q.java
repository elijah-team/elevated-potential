package tripleo.elijah.stages.deduce.post_bytecode;

import java.util.function.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;

interface _LFOE_Q {
	void enqueue_ctor(final GenerateFunctions aGenerateFunctions,
			final @NotNull FunctionInvocation aFi,
					  final IdentExpression aConstructorName);

	void enqueue_default_ctor(GenerateFunctions generateFunctions1,
			@NotNull FunctionInvocation fi,
							  ICodeRegistrar codeRegistrar,
							  Consumer<Eventual<BaseEvaFunction>> cef);

	void enqueue_function(final GenerateFunctions aGenerateFunctions,
			final @NotNull FunctionInvocation aFi,
						  final ICodeRegistrar cr);

	void enqueue_function(final Supplier<GenerateFunctions> som,
			final @NotNull FunctionInvocation aFi,
						  final ICodeRegistrar aCr);

	void enqueue_namespace(final Supplier<GenerateFunctions> som,
						   NamespaceInvocation aNsi,
						   DeducePhase.GeneratedClasses aGeneratedClasses,
						   final ICodeRegistrar aCr);

	void enqueue_default_ctor(GenerateFunctions generateFunctions1,
			@NotNull FunctionInvocation fi,
							  Consumer<Eventual<BaseEvaFunction>> cef);
}
