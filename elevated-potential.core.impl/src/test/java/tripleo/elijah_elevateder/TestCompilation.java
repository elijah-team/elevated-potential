package tripleo.elijah_elevateder;

import tripleo.elijah.comp.*;
import tripleo.elijah_elevated_durable.comp.EDL_IO;
import tripleo.elijah_elevated_durable.comp.EDL_ErrSink;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevateder.factory.comp.CompilationFactory;

import static tripleo.elijah.util.Helpers.List_of;

public class TestCompilation {
	private static final TestCompilation_SimpleTest testCompilationSimpleTest = new TestCompilation_SimpleTest();

	public static SimpleTest simpleTest() {
		return testCompilationSimpleTest;
	}

	private static class TestCompilation_SimpleTest implements SimpleTest {
		private Compilation0 c;
		private String       _f;

		@Override
		public SimpleTest setFile(final String aS) {
			_f = aS;
			return this;
		}

		@Override
		public SimpleTest run() throws Exception {
			assert _f != null;

			c = build();
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
			var cc = (EDL_Compilation) c;
//			var ce = cc.getCompilationEnclosure();
			var world = cc.world();
			var classes = world.findClass(aClassName);

			boolean result = classes.stream()
					.findAny()
					.isPresent();

			return result;
		}

		@Override
		public AssertingLiveClass assertingLiveClass(final String aClassName) {
			EDL_Compilation cc = (EDL_Compilation) c;
//			var ce = cc.getCompilationEnclosure();
			var world = cc.world();
			var classes = world.findClass(aClassName);

			var result = new AssertingLiveClass();
			result.setName(aClassName);

			boolean present = classes.stream()
					.findAny()
					.isPresent();

			result.setPresent(present);

			return result;
		}

		@Override
		public Compilation0 c() {
			return c;
		}

		private Compilation0 build() {
			if (c != null) return c;

			c = CompilationFactory.mkCompilation(new EDL_ErrSink(), new EDL_IO());

			return c;
		}
	}
}

//
//
//
