package tripleo.elijah.nextgen.comp_model;

import com.google.common.base.MoreObjects;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.Finally_;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.nextgen.outputtree.EOT_Nameable;
import tripleo.elijah.util.Maybe;
import tripleo.wrap.File;

public class CM_CompilerInput implements EOT_Nameable {
	private final Compilation                      comp;
	private final CompilerInput                    carrier;
	private       String                           inp;
	private       CompilerInput.Ty                 ty;
	private       File                             dir_carrier;
	private       String                           hash;
	private       Maybe<ILazyCompilerInstructions> accept_ci;

	public CM_CompilerInput(final CompilerInput aCompilerInput, final Compilation aCompilation) {
		carrier = aCompilerInput;
		comp    = aCompilation;
		inp     = carrier.getInp();
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

	//public void setArg() {
	//	ty = CompilerInput.Ty.ARG;
	//}

	public void accept_hash(final String aHash) {
		this.hash = aHash;
	}

	public String printableString() {
		return MoreObjects.toStringHelper(this)
				.add("ty", ty)
				.add("inp", inp)
				.add("accept_ci", accept_ci.toString())
				.add("dir_carrier", dir_carrier)
				.add("hash", hash)
				.toString();
	}

	public void accept_ci(final Maybe<ILazyCompilerInstructions> mci) {
		accept_ci = mci;
	}

	public Maybe<ILazyCompilerInstructions> acceptance_ci() {
		return accept_ci;
	}

	public String getInp() {
		return inp;
	}

	public File fileOf() {
		final File f = new File(inp);
		return f;
	}

	public void onIsEz() {
		final ILazyCompilerInstructions ilci = comp.con().createLazyCompilerInstructions(carrier);

		final Maybe<ILazyCompilerInstructions> m4 = new Maybe<>(ilci, null);
		carrier.accept_ci(m4);
	}

	public Finally_Input createInput(final Finally.Out2 aTy) {
		return new Finally_.FinallyInput_(this, aTy);
	}

	@Override
	public String getNameableString() {
		return inp;
	}

	public CompilerInput.Ty getTy() {
		return ty;
	}
}
