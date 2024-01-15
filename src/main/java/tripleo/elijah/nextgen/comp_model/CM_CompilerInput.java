package tripleo.elijah.nextgen.comp_model;

import com.google.common.base.MoreObjects;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.comp.Finally_;
import tripleo.elijah.comp.Finally_Input;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.nextgen.outputtree.EOT_Nameable;
import tripleo.elijah.util.Maybe;
import tripleo.wrap.File;

public class CM_CompilerInput implements EOT_Nameable {
	private final Compilation                      comp;
	private final CompilerInput                    carrier;
	private       CompilerInput.Ty                 ty;
	private       File                             dir_carrier;
	private       String                           hash;
	private       Maybe<ILazyCompilerInstructions> accept_ci;
	private       EzSpec                           spec;

	public CM_CompilerInput(final CompilerInput aCompilerInput, final Compilation aCompilation) {
		carrier = aCompilerInput;
		comp    = aCompilation;
	}

	//public void setSourceRoot() {
	//	ty = CompilerInput.Ty.SOURCE_ROOT;
	//}

	public boolean inpSameAs(final String aS) {
		return aS.equals(this.carrier.getInp());
	}

	//public void setArg() {
	//	ty = CompilerInput.Ty.ARG;
	//}

	public void setDirectory(final File aF) {
		ty          = CompilerInput.Ty.SOURCE_ROOT;
		dir_carrier = aF;
	}

	public void accept_hash(final String aHash) {
		this.hash = aHash;
	}

	public String printableString() {
		final String inp11 = this.getNameableString();
		return MoreObjects.toStringHelper(this)
		                  .add("ty", ty)
		                  .add("inp", inp11)
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
		return this.getNameableString();
	}

	public File fileOf() {
		final String specifiedFilePath = this.getNameableString();
		return new File(specifiedFilePath);
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
		return carrier.getInp();
	}

	public CompilerInput.Ty getTy() {
		return ty;
	}

	public void advise(final EzSpec aSpec) {
		this.spec = aSpec;
		ty = null;//aSpec.
		dir_carrier = null;
		hash = null;
		accept_ci = null;
	}
}
