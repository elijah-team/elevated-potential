package tripleo.elijah_elevateder.comp.nextgen;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah.paths_impl.CP_OutputPath;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah_elevateder.factory.comp.CompilationFactory;

import org.junit.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CP_SubFileTest {
	private CP_OutputPath op;

	@Test
	public void one() {
		op.signalCalculateFinishParse();

		final CP_Path sf0 = op.child("foo");
		final String actual0 = "" + sf0.getPath();
		final String expected0 = "COMP/e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855/<date>/foo";
		assertThat(actual0).isEqualTo(expected0);

		final CP_Path sf1 = op.child("foo").child("bar");
		final String  actual1 = "" + sf1.getPath();
		final String expected1 = "COMP/e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855/<date>/foo/bar";
		assertThat(actual1).isEqualTo(expected1);

		final CP_Path sf2 = op.child("foo").child("bar").child("cat");
		final String  actual2 = "" + sf2.getPath();
		final String  expected2 = "COMP/e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855/<date>/foo/bar/cat";
		assertThat(actual2).isEqualTo(expected2);
	}

	//@Before //Each
	@org.junit.Before
	public void setUp() {
		final @NotNull Compilation cc = CompilationFactory.mkCompilation0();
		op = new CP_OutputPath(cc);
		op.testShim();
	}
}
