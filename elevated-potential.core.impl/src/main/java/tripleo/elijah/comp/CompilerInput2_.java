package tripleo.elijah.comp;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.__Extensionable;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.comp.queries.CompilerInstructions_Result;
import tripleo.elijah.comp.queries.QSEZ_Reasoning;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.Operation2;
import tripleo.wrap.File;

import java.util.regex.Pattern;

public class CompilerInput2_ extends __Extensionable implements CompilerInput {
	private final Compilation.Modelo.Ref modelo;

	private Maybe<ILazyCompilerInstructions> accept_ci;
	private File dir_carrier;	@Override
	public File getFile() {
		throw new UnintendedUseException();
	}
	@Getter
	@Accessors(fluent = true)
	private Ty                          ty;
	private String                      hash;	@Override
	public File getFileForDirectory() {
		final File directory = new File(inp);
		this.setDirectory(directory);
		//directory.advise(...); // gradual, not progressive??
		return directory;
	}
	private CompilerInputMaster         master;
	private CompilerInstructions_Result directoryResults;
	public CompilerInput2_(final String inp, final @Nullable Compilation aCompilation) {
		this.modelo = aCompilation.modelo().jalisco(inp);
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
	String inp;

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
	public String toString() {
		return "CompilerInput{ty=%s, inp='%s'}".formatted(ty, inp);
	}






	@Override
	public void setDirectoryResults(final CompilerInstructions_Result aLoci) {
		this.directoryResults = aLoci;

		for (Pair<Operation2<CompilerInstructions>, QSEZ_Reasoning> locus : aLoci.getDirectoryResult2()) {
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
