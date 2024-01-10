package tripleo.elijah;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.IO_;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.factory.comp.CompilationFactory;

import java.util.ArrayList;
import java.util.Arrays;

public enum Main { ;
	public static void main(final String[] args) throws Exception {
		final Compilation comp = CompilationFactory.mkCompilation(new StdErrSink(), new IO_());

        comp.feedCmdLine(new ArrayList<>(Arrays.asList(args)));
	}
}
