package tripleo.elijah.comp.inputs;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.comp.queries.CompilerInstructions_Result;
import tripleo.elijah.comp.queries.QSEZ_Reasoning;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.util2.UnintendedUseException;
import tripleo.elijah.util2.__Extensionable;
import tripleo.wrap.File;

import java.util.Optional;
import java.util.regex.Pattern;

public class CompilerInput_ extends __Extensionable implements CompilerInput {
	private final Optional<Compilation> oc;
	//	@Getter
	private final String                inp;
	private                 Maybe<ILazyCompilerInstructions> accept_ci;
	private                 File                             dir_carrier;
	@Getter @Accessors(fluent = true)
	private                 Ty                               ty;
	private                 String                           hash;
	private                 CompilerInputMaster              master;
	private                 CompilerInstructions_Result      directoryResults;

	public CompilerInput_(final String aS, final Optional<Compilation> aCompilation) {
		inp = aS;
		oc  = aCompilation;
	}
	public CompilerInput_(final String aS) {
		inp = aS;
		ty  = Ty.NULL;
		oc  = null;
	}

	@Override
	public tripleo.wrap.File getFile() {
		throw new UnintendedUseException();
	}

	@Override
	public tripleo.wrap.File getFileForDirectory() {
		final tripleo.wrap.File directory = new tripleo.wrap.File(inp);
		this.setDirectory(directory);
		return directory;
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
	public void setDirectoryResults(final CompilerInstructions_Result aLoci) {
		this.directoryResults = aLoci;

		for (Pair<Operation2<CompilerInstructions>, QSEZ_Reasoning> locus : aLoci.getDirectoryResult2()) {
			final CompilerInstructions focus = locus.getKey().success();
			focus.advise(this);
		}

		if (master != null)
			master.notifyChange(this, CompilerInputField.DIRECTORY_RESULTS);
	}

	@Override
	public String getInp() {
		// aback and forth
		return inp;
	}

	public Optional<Compilation> getOc() {
		return oc;
	}
}
