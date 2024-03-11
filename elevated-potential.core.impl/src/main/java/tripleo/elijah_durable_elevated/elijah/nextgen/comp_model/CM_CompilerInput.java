package tripleo.elijah_durable_elevated.elijah.nextgen.comp_model;

import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.nextgen.outputtree.EOT_Nameable;
import tripleo.elijah.util.Maybe;
import tripleo.wrap.File;

public class CM_CompilerInput implements EOT_Nameable {
	private final Compilation                      comp;
	private final CompilerInput    carrier;
	private final String           inp;
	private       CompilerInput.Ty ty;
	private       File                             dir_carrier;

	public CM_CompilerInput(final CompilerInput aCompilerInput, final Compilation aCompilation) {
		carrier = aCompilerInput;
		comp = aCompilation;
		inp = carrier.getInp();
	}

	public boolean inpSameAs(final String aS) {
		return aS.equals(this.inp);
	}

	//public void setInp(final String aInp) {
	//	inp = aInp;
	//}

	//public void setSourceRoot() {
	//	ty = CompilerInput.Ty.SOURCE_ROOT;
	//}

	public void setDirectory(final File aF) {
		ty          = CompilerInput.Ty.SOURCE_ROOT;
		dir_carrier = aF;
	}

	public File fileOf() {
		final File    f         = new File(inp);
		return f;
	}

	public void onIsEz() {
		final ILazyCompilerInstructions ilci = comp.con().createLazyCompilerInstructions(carrier);

		final Maybe<ILazyCompilerInstructions> m4 = new Maybe<>(ilci, null);
		carrier.accept_ci(m4);
	}

	@SuppressWarnings("SuspiciousGetterSetter")
	@Override
	public String getNameableString() {
		return inp;
	}

	@SuppressWarnings({"LombokGetterMayBeUsed", "RedundantSuppression"})
	public CompilerInput.Ty getTy() {
		return ty;
	}
}
