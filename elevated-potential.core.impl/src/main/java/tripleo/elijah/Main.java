package tripleo.elijah;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.IO_;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.i.CompilerController;
import tripleo.elijah.factory.NonOpinionatedBuilder;
import tripleo.elijah.factory.comp.CompilationFactory;

import java.util.*;

public enum Main {
	;

	public static void main(final String[] args) throws Exception {
		final List<String>          stringList = new ArrayList<>(Arrays.asList(args));

		final Compilation           comp       = CompilationFactory.mkCompilation(new StdErrSink(), new IO_());
		final NonOpinionatedBuilder nob        = new NonOpinionatedBuilder();

		final CompilerController    controller = nob.createCompilerController(comp); // contrast with defaultCompilerController

		//comp.feedCmdLine(stringList); // TODO 24/01/21 ElijahCli
		comp.feedInputs(nob.inputs(stringList), controller);
	}
}
