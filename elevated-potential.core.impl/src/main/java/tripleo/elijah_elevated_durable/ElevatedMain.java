package tripleo.elijah_elevated_durable;

import tripleo.elijah.comp.i.CompilerController;
import tripleo.elijah_elevated_durable.comp.EDL_ErrSink;
import tripleo.elijah_elevated_durable.comp.EDL_IO;
import tripleo.elijah_elevateder.comp.*;
import tripleo.elijah_elevateder.factory.NonOpinionatedBuilder;
import tripleo.elijah_elevateder.factory.comp.CompilationFactory;

import java.util.*;

public enum ElevatedMain {
	;

	public static void main(final String[] args) throws Exception {
		final List<String>          stringList = new ArrayList<>(Arrays.asList(args));

		final Compilation           comp       = CompilationFactory.mkCompilation(new EDL_ErrSink(), new EDL_IO());
		final NonOpinionatedBuilder nob        = new NonOpinionatedBuilder();

		final CompilerController    controller = nob.createCompilerController(comp); // contrast with defaultCompilerController

		//comp.feedCmdLine(stringList); // TODO 24/01/21 ElijahCli
		comp.feedInputs(nob.inputs(stringList), controller);
	}
}
