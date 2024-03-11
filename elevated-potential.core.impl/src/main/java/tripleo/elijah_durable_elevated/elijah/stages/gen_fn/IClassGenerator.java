package tripleo.elijah_durable_elevated.elijah.stages.gen_fn;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeducePhase;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.work.WorkList;


public interface IClassGenerator {
	ICodeRegistrar getCodeRegistrar();

	DeducePhase.GeneratedClasses getGeneratedClasses();

	FunctionInvocation newFunctionInvocation(FunctionDef fd, ProcTableEntry pte, ClassInvocation ci);

	@Nullable
	ClassInvocation registerClassInvocation(ClassStatement cs, String className);

	void submitGenerateClass(ClassInvocation ci, GenerateFunctions gf);

	void submitGenerateFunction(FunctionInvocation ci, GenerateFunctions gf);

	WorkList wl();
}
