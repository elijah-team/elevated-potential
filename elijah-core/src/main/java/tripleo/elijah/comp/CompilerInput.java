package tripleo.elijah.comp;

import com.google.common.base.Preconditions;
import lombok.Getter;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.Operation2;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class CompilerInput {
	@Getter
	private final String                                 inp;
	private       Maybe<ILazyCompilerInstructions>       accept_ci;
	private       File                             dir_carrier;
	private       Ty                               ty;
	private       String                                 hash;
	@Getter
	private       List<Operation2<CompilerInstructions>> directoryResults;

	public CompilerInput(final String aS) {
		inp = aS;
		ty  = Ty.NULL;
	}

	public void accept_ci(final Maybe<ILazyCompilerInstructions> aM3) {
		accept_ci = aM3;
	}

	public Maybe<ILazyCompilerInstructions> acceptance_ci() {
		return accept_ci;
	}

	public boolean isNull() {
		return ty == Ty.NULL;
	}

	public boolean isSourceRoot() {
		return ty == Ty.SOURCE_ROOT;
	}

	public void setSourceRoot() {
		ty = Ty.SOURCE_ROOT;
	}

	public void setDirectory(File f) {
		ty          = Ty.SOURCE_ROOT;
		dir_carrier = f;
	}

	public void setArg() {
		ty = Ty.ARG;
	}

	public void accept_hash(final String hash) {
		this.hash = hash;
	}

	public boolean isEzFile() {
		//new QuerySearchEzFiles.EzFilesFilter().accept()
		return Pattern.matches(".+\\.ez$", inp);
	}

	public boolean isElijjahFile() {
		return Pattern.matches(".+\\.elijjah$", inp) ||
				Pattern.matches(".+\\.elijah$", inp);
	}

	public File getDirectory() {
		Preconditions.checkNotNull(dir_carrier);

		return dir_carrier;
	}

	public void setDirectoryResults(final List<Operation2<CompilerInstructions>> aLoci) {
		this.directoryResults = aLoci;
	}

	public enum Ty {NULL, SOURCE_ROOT, ARG}

	public Ty ty() {
		return ty;
	}
}
