package tripleo.elijah_elevated.comp.input;

import com.google.common.base.*;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.*;
import tripleo.elijah.ci.*;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.CompilerInputMaster;

import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.CompilerInstructions_Result;
import tripleo.elijah.comp.queries.QSEZ_Reasoning;
import tripleo.elijah.util.*;
import tripleo.wrap.File;

import java.util.Optional;
import java.util.regex.Pattern;

public class CompilerInput_ extends _ElTaggableMixin implements CompilerInput {
	private final @NotNull Optional<Compilation> oc;

	public CompilerInput_(final String aS,
						  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
						  	final @Nullable Optional<Compilation> aCompilation) {
		inp = aS;
		//noinspection OptionalAssignedToNull
		if (aCompilation == null || aCompilation.isEmpty()) {
			oc = Optional.empty();
		} else {
			oc = aCompilation;
		}
	}

	@Override
	public tripleo.wrap.File getFile() {
		throw new UnintendedUseException();
	}

	@Override
	public tripleo.wrap.File getFileForDirectory() {
		final tripleo.wrap.File directory = new tripleo.wrap.File(inp);
		this.setDirectory(directory);
		//directory.advise(...); // gradual, not progressive??
		return directory;
	}

//	@Getter
	private final String                           inp;
	private       Maybe<ILazyCompilerInstructions> accept_ci;
	private       File                             dir_carrier;
	// @Getter(fluent)
	private       Ty                               ty;
	private String              hash;
	private CompilerInputMaster master;

	private CompilerInstructions_Result directoryResults;

	public CompilerInput_(final String aS) {
		inp = aS;
		ty  = Ty.NULL;
		oc  = null;
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
	public void setDirectoryResults(final CompilerInstructions_Result aLoci) {
		this.directoryResults = aLoci;

        for (Pair<Operation2<CompilerInstructions>,QSEZ_Reasoning> locus : aLoci.getDirectoryResult2()) {
            CompilerInstructions focus = locus.getKey().success();
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
			// TODO 12/03 see #getFileForDirectory
		    case ROOT -> new File(new File(inp).getParentFile().wrapped()); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		    default -> null;
	    };
    }

	@Override
	public CompilerInstructions_Result getDirectoryResults() {
		return this.directoryResults;
	}

	@Override
	public String getInp() {
		// TODO Auto-generated method stub
		return inp;
	}
}
