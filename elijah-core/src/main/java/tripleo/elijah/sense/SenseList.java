package tripleo.elijah.sense;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.*;

import java.util.*;

public class SenseList implements Iterable<SenseList.Sensible> {
	public enum U {
		SKIP, USE, ADD
	}

	public record Sensible(CompilerInput input, SenseList.U u, Sensable indexable) {
		public void checkDirectoryResults(CCI cci, IProgressSink progressSink) {
			final CompilerInput compilerInput = this.input();
			final List<Operation2<CompilerInstructions>> directoryResults = compilerInput.getDirectoryResults();

			if (directoryResults != null) {
				if (!directoryResults.isEmpty()) {
					for (Operation2<CompilerInstructions> directoryResult : directoryResults) {
						if (directoryResult.mode() == Mode.SUCCESS) {
							cci.accept(new Maybe<>(ILazyCompilerInstructions.of(directoryResult.success()), null),
									progressSink);
						}
					}
				}
			}
		}
	}

	private final List<Sensible> x = new ArrayList<>();

	public void add(final CompilerInput aInp) {
		x.add(new Sensible(aInp, U.ADD, null));
	}

	public void add(CompilerInput aCompilerInput, U u, Sensable aIndexable) {
		x.add(new Sensible(aCompilerInput, u, aIndexable));
	}

	@NotNull
	@Override
	public Iterator<Sensible> iterator() {
		return x.iterator();
	}

	public List<Sensible> list() {
		return x;
	}

	public void skip(CompilerInput aCompilerInput, U u, Sensable aIndexable) {
		x.add(new Sensible(aCompilerInput, u, aIndexable));
	}
}
