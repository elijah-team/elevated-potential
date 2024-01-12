package tripleo.elijah;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.world.i.LivingRepo;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public final class TestCompilation {
	private static final TestCompilation_SimpleTest INSTANCE = new TestCompilation_SimpleTest();

	// HEY INTELLIJ: idea doesnt suggest this
	private TestCompilation() {
	}

	public static SimpleTest simpleTest() {
		return INSTANCE;
	}

	private static class TestCompilation_SimpleTest implements SimpleTest {
		private ElijahCli c;
		private String    _f;

		@Override
		public SimpleTest setFile(final String aS) {
			_f = aS;
			return this;
		}

		@Override
		public SimpleTest run() {
			assert _f != null;

			c = ElijahCli.createDefault();

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
			final Compilation          cc      = c.comp;
			final LivingRepo           world   = cc.world();
			final List<ClassStatement> classes = world.findClass(aClassName);

			boolean result = classes.stream()
			                        .findAny()
			                        .isPresent();
			return result;
		}

		@Override
		public AssertingLiveClass assertingLiveClass(final String aClassName) {
			final Compilation          cc      = c.comp;
			final LivingRepo           world   = cc.world();
			final List<ClassStatement> classes = world.findClass(aClassName);

			boolean present = classes.stream()
			                         .findAny()
			                         .isPresent();

			final AssertingLiveClass result = new AssertingLiveClass();
			result.setName(aClassName);
			result.setPresent(present);
			return result;
		}

		@Override
		public Compilation0 c() {
			return c.comp;
		}
	}
}

//
//
//
