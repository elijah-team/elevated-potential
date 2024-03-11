package tripleo.elijah_durable_elevated.elijah.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.util.BufferTabbedOutputStream;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.elijah_durable_elevated.elijah.nextgen.inputtree.EIT_ModuleInputImpl;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateResult.TY;
import tripleo.util.buffer.Buffer;

public class NG_OutputClassStatement implements NG_OutputStatement {
	private final          Buffer    buf;
	private final          TY        ty;
	private final @NotNull NG_OutDep moduleDependency;

	public NG_OutputClassStatement(final @NotNull BufferTabbedOutputStream aBufferTabbedOutputStream,
	                               final @NotNull OS_Module aModuleDependency,
	                               final TY aTy) {
		buf              = aBufferTabbedOutputStream.getBuffer();
		ty               = aTy;
		moduleDependency = new NG_OutDep(aModuleDependency);
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("NG_OutputClassStatement");
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
		return buf.getText();
	}

	@Override
	public @NotNull TY getTy() {
		return ty;
	}

	public NG_OutDep moduleDependency() {
		return moduleDependency;
	}
}
