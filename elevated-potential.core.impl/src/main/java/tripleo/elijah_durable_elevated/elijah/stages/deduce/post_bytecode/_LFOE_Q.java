package tripleo.elijah_durable_elevated.elijah.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.*;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.ICodeRegistrar;

import java.util.function.Consumer;
import java.util.function.Supplier;

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
