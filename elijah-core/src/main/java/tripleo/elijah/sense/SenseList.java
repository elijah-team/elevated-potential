package tripleo.elijah.sense;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.CCI;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.util.*;

import java.util.*;

public class SenseList implements Iterable<SenseList.Sensible> {
	public enum U {
		SKIP, USE, ADD
	}

	public static final class Sensible {
		private final CompilerInput input;
		private final U             u;
		private final Sensable      indexable;

		public Sensible(CompilerInput input, U u, Sensable indexable) {
			this.input     = input;
			this.u         = u;
			this.indexable = indexable;
		}

		public void checkDirectoryResults(CCI cci, IProgressSink progressSink) {
				final CompilerInput                          compilerInput    = this.input();
				final List<Operation2<CompilerInstructions>> directoryResults = compilerInput.getDirectoryResults();

				if (directoryResults != null) {
					if (!directoryResults.isEmpty()) {
						for (Operation2<CompilerInstructions> directoryResult : directoryResults) {
							if (directoryResult.mode() == Mode.SUCCESS) {
								cci.accept(new Maybe<>(ILazyCompilerInstructions_.of(directoryResult.success()), null),
										   progressSink);
							}
						}
					}
				}
			}

		public CompilerInput input() {
			return input;
		}

		public U u() {
			return u;
		}

		public Sensable indexable() {
			return indexable;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (Sensible) obj;
			return Objects.equals(this.input, that.input) &&
					Objects.equals(this.u, that.u) &&
					Objects.equals(this.indexable, that.indexable);
		}

		@Override
		public int hashCode() {
			return Objects.hash(input, u, indexable);
		}

		@Override
		public String toString() {
			return "Sensible[" +
					"input=" + input + ", " +
					"u=" + u + ", " +
					"indexable=" + indexable + ']';
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
