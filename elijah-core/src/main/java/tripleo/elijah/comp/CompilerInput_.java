package tripleo.elijah.comp;

import com.google.common.base.*;
import lombok.*;
import tripleo.elijah.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class CompilerInput_ extends __Extensionable implements CompilerInput {
	@Getter
	private final String                           inp;
	private       Maybe<ILazyCompilerInstructions> accept_ci;
	private       File                             dir_carrier;
	// @Getter(fluent)
	private       Ty                               ty;
	private       String                           hash;
	private       CompilerInputMaster              master;

	@SuppressWarnings("unchecked") // README squiggly line in idea
	@Getter
	private List<Operation2<CompilerInstructions>> directoryResults = Collections.EMPTY_LIST;

	public CompilerInput_(final String aS) {
		inp = aS;
		ty  = Ty.NULL;
	}

	@Override
	public void accept_ci(final Maybe<ILazyCompilerInstructions> compilerInstructionsMaybe) {
		accept_ci = compilerInstructionsMaybe;

		if (master != null)
			master.notifyChange(this, CompilerInputField.ACCEPT_CI);
	}

	@Override
	public void accept_hash(final String hash) {
		this.hash = hash;

		if (master != null)
			master.notifyChange(this, CompilerInputField.HASH);
	}

	@Override
	public Maybe<ILazyCompilerInstructions> acceptance_ci() {
		return accept_ci;
	}

	@Override
	public void certifyRoot() {
		ty = Ty.ROOT;

		if (master != null)
			master.notifyChange(this, CompilerInputField.TY);
	}

	@Override
	public File getDirectory() {
		Preconditions.checkNotNull(dir_carrier);

		return dir_carrier;
	}

	@Override
	public void setDirectory(File f) {
		ty          = Ty.SOURCE_ROOT;
		dir_carrier = f;

		if (master != null)
			master.notifyChange(this, CompilerInputField.TY);
	}

	@Override
	public boolean isElijjahFile() {
		return Pattern.matches(".+\\.elijjah$", inp) || Pattern.matches(".+\\.elijah$", inp);
	}

	@Override
	public boolean isEzFile() {
		// new QuerySearchEzFiles.EzFilesFilter().accept()
		return Pattern.matches(".+\\.ez$", inp);
	}

	@Override
	public boolean isNull() {
		return ty == Ty.NULL;
	}

	@Override
	public boolean isSourceRoot() {
		return ty == Ty.SOURCE_ROOT;
	}

	@Override
	public void setArg() {
		ty = Ty.ARG;

		if (master != null)
			master.notifyChange(this, CompilerInputField.TY);
	}

	@Override
	public void setDirectoryResults(final List<Operation2<CompilerInstructions>> aLoci) {
		this.directoryResults = aLoci;

        for (Operation2<CompilerInstructions> locus : aLoci) {
            var focus = locus.success();
            focus.advise(this);
        }

		if (master != null)
			master.notifyChange(this, CompilerInputField.DIRECTORY_RESULTS);
	}

	@Override
	public void setMaster(CompilerInputMaster master) {
		this.master = master;
	}

	@Override
	public void setSourceRoot() {
		ty = Ty.SOURCE_ROOT;

		if (master != null)
			master.notifyChange(this, CompilerInputField.TY);
	}

	@Override
	public String toString() {
		return "CompilerInput{ty=%s, inp='%s'}".formatted(ty, inp);
	}

	@Override
	public Ty ty() {
		return ty;
	}

    @Override
	public File makeFile() {
	    return switch (ty) {
		    case SOURCE_ROOT -> dir_carrier;
		    case ROOT -> new File(new File(inp).getParent());
		    default -> null;
	    };
    }

}
