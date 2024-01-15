package tripleo.elijah.comp.nextgen;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tripleo.elijah.comp.ElijahTestCli;
import tripleo.elijah.comp.IO_;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.factory.comp.CompilationFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CP_SubFileTest {

	private CP_OutputPathImpl op;

	@Test
	public void one() {
		op.testShim();
		op.signalCalculateFinishParse();

		op.calculate_hda();

		CP_Path sf = op.child("foo");
		assertEquals("COMP/e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855/<date>/foo",
				"" + sf.getPath());

		final CP_Path sf1 = op.child("foo").child("bar");
		assertEquals("COMP/e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855/<date>/foo/bar",
				"" + sf1.getPath());

		final CP_Path sf2 = op.child("foo").child("bar").child("cat");
		assertEquals("COMP/e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855/<date>/foo/bar/cat",
				"" + sf2.getPath());
	}

	@BeforeEach
	public void setUp() {
		final ElijahTestCli            cli = ElijahTestCli.createDefault();
		final @NotNull CompilationImpl cc  = (CompilationImpl) cli.cli.getComp();
		op = new CP_OutputPathImpl(cc);
	}
}
