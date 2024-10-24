package tripleo.elijah_elevateder.nextgen.output;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.*;

import tripleo.elijah_elevateder.stages.gen_c.C2C_Result;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult.*;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.nextgen.inputtree.EIT_ModuleInputImpl;

public class NG_OutputFunctionStatement implements NG_OutputStatement {
	private final EG_Statement x;
	private final TY y;
	private final @NotNull NG_OutDep  moduleDependency;
	private final @NotNull C2C_Result __c2c;

	public NG_OutputFunctionStatement(final @NotNull C2C_Result ac2c) {
		__c2c = ac2c;

		x = __c2c.getStatement();
		y = __c2c.ty();

		moduleDependency = new NG_OutDep(ac2c.getDefinedModule());
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("NG_OutputFunctionStatement");
	}

	@Override
	@NotNull
	public EIT_ModuleInput getModuleInput() {
		var m = moduleDependency().module();

		final EIT_ModuleInput moduleInput = new EIT_ModuleInputImpl(m, (Compilation) m.getCompilation());
		return moduleInput;
	}

	@Override
	public String getText() {
		return x.getText();
	}

	@Override
	public TY getTy() {
		return y;
	}

	public NG_OutDep moduleDependency() {
		return moduleDependency;
	}
}
