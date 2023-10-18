package tripleo.elijah.comp.internal;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.IProgressSink;

import java.util.List;
import java.util.Objects;

public final class CompilerBeginning {
	private final Compilation                   compilation;
	private final CompilerInstructions          compilerInstructions;
	private final List<CompilerInput>           compilerInput;
	private final IProgressSink                 progressSink;
	private final Compilation.CompilationConfig cfg;

	public CompilerBeginning(Compilation compilation, CompilerInstructions compilerInstructions,
							 List<CompilerInput> compilerInput, IProgressSink progressSink, Compilation.CompilationConfig cfg) {
		this.compilation          = compilation;
		this.compilerInstructions = compilerInstructions;
		this.compilerInput        = compilerInput;
		this.progressSink         = progressSink;
		this.cfg                  = cfg;
	}

	public Compilation compilation() {
		return compilation;
	}

	public CompilerInstructions compilerInstructions() {
		return compilerInstructions;
	}

	public List<CompilerInput> compilerInput() {
		return compilerInput;
	}

	public IProgressSink progressSink() {
		return progressSink;
	}

	public Compilation.CompilationConfig cfg() {
		return cfg;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (CompilerBeginning) obj;
		return Objects.equals(this.compilation, that.compilation) &&
				Objects.equals(this.compilerInstructions, that.compilerInstructions) &&
				Objects.equals(this.compilerInput, that.compilerInput) &&
				Objects.equals(this.progressSink, that.progressSink) &&
				Objects.equals(this.cfg, that.cfg);
	}

	@Override
	public int hashCode() {
		return Objects.hash(compilation, compilerInstructions, compilerInput, progressSink, cfg);
	}

	@Override
	public String toString() {
		return "CompilerBeginning[" +
				"compilation=" + compilation + ", " +
				"compilerInstructions=" + compilerInstructions + ", " +
				"compilerInput=" + compilerInput + ", " +
				"progressSink=" + progressSink + ", " +
				"cfg=" + cfg + ']';
	}

}
