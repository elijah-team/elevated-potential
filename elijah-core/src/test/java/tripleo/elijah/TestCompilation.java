package tripleo.elijah;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.factory.comp.CompilationFactory;

import static tripleo.elijah.util.Helpers.List_of;

public class TestCompilation {
	private static final TestCompilation_SimpleTest testCompilationSimpleTest = new TestCompilation_SimpleTest();

	public static SimpleTest simpleTest() {
		return testCompilationSimpleTest;
	}

	private static class TestCompilation_SimpleTest implements SimpleTest {
		private CompilationImpl c;
		private String          _f;

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

		@Override
		public boolean assertLiveClass(final String aClassName) {
			var ce = c.getCompilationEnclosure();
			var world = c.world();
			var classes = world.findClass(aClassName);

			boolean result = classes.stream()
					.findAny()
					.isPresent();

			return result;
		}

		@Override
		public AssertingLiveClass assertingLiveClass(final String aClassName) {
			var ce = c.getCompilationEnclosure();
			var world = c.world();
			var classes = world.findClass(aClassName);

			var result = new AssertingLiveClass();
			result.setName(aClassName);

			boolean present = classes.stream()
					.findAny()
					.isPresent();

			result.setPresent(present);

			return result;
		}

		private Compilation build() {
			if (c != null) return c;

			c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());

			return c;
		}
	}
}

//
//
//
