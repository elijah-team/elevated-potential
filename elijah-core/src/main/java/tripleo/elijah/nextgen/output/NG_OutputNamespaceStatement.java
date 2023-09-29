package tripleo.elijah.nextgen.output;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.stages.gen_generic.GenerateResult.*;
import tripleo.elijah.util.*;
import tripleo.util.buffer.*;

public class NG_OutputNamespaceStatement implements NG_OutputStatement {
	private final          TY                       ty;
	private final @NotNull NG_OutDep                moduleDependency;
	private final          Buffer                   buf;

	public NG_OutputNamespaceStatement(final BufferTabbedOutputStream aBufferTabbedOutputStream,
	                                   final @NotNull OS_Module aModule,
	                                   final TY aTY) {
		buf              = aBufferTabbedOutputStream.getBuffer();
		ty               = aTY;
		moduleDependency = new NG_OutDep(aModule);
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("NG_OutputNamespaceStatement");
	}

	@Override
	@NotNull
	public EIT_ModuleInput getModuleInput() {
		var m = moduleDependency().module();

		final EIT_ModuleInput moduleInput = new EIT_ModuleInput(m, m.getCompilation());
		return moduleInput;
	}

	@Override
	public String getText() {
		return buf.getText();
	}

	@Override
	public TY getTy() {
		return ty;
	}

	public @NotNull NG_OutDep moduleDependency() {
		return moduleDependency;
	}
}
