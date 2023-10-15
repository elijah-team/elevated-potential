package tripleo.elijah;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.factory.comp.*;

import static tripleo.elijah.util.Helpers.*;

public class TestCompilation {
	public static SimpleTest simpleTest() {
		return new SimpleTest() {

			private CompilationImpl c;
			private String _f;

			@Override
			public SimpleTest setFile(final String aS) {
				_f = aS;
				return this;
			}

			@Override
			public SimpleTest run() throws Exception {
				assert _f != null;

				Compilation c = build();
				c.feedCmdLine(List_of(_f));

				return this;
			}

			@Override
			public int errorCount() {
				assert c != null;
				return c.errorCount();
			}

			private Compilation build() {
				if (c != null) return c;

				c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());

				return c;
			}
		};
	}

	public interface SimpleTest {
		SimpleTest setFile(String aS);

		SimpleTest run() throws Exception;

		int errorCount();
	}
}
