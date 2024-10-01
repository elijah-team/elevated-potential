package tripleo.elijah_elevateder.stages.gen_fn;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.util.Eventual;
import tripleo.elijah_elevateder.stages.deduce.*;
import tripleo.elijah_elevateder.stages.gen_generic.Dependency;
import tripleo.elijah_elevateder.stages.gen_generic.IDependencyReferent;

public interface IEvaConstructor extends
		IEvaFunctionBase,
		EvaNode,
		DependencyTracker,
		IDependencyReferent,
		DeduceTypes2.ExpectationBase {

	Eventual<DeduceElement3_Constructor> de3_Promise();

	@Override
	@NotNull FunctionDef getFD();

	@Override
	@Nullable VariableTableEntry getSelf();

	@Override
	String identityString();

	@Override
	@NotNull OS_Module module();

	String name();

	void setFunctionInvocation(@NotNull FunctionInvocation fi);

	@Override
	String toString();

	void noteDependencies(Dependency aDependency);

	interface BaseEvaConstructor_Reactive extends Reactive {

	}
}
