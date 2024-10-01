package tripleo.elijah_elevateder.stages.deduce.post_bytecode;

import tripleo.elijah.util.Eventual;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah_elevateder.stages.deduce.DeducePhase;
import tripleo.elijah_elevateder.stages.deduce.FunctionInvocation;
import tripleo.elijah_elevateder.stages.deduce.NamespaceInvocation;
import tripleo.elijah_elevateder.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_elevateder.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_elevateder.stages.gen_generic.ICodeRegistrar;

import org.jetbrains.annotations.NotNull;

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
