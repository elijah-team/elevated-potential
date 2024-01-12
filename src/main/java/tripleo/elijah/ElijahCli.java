package tripleo.elijah;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.IO_;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.i.CompilerController;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.impl.DefaultCompilerController;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.comp.inputs.CompilerInput_;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.factory.comp.CompilationFactory;
import tripleo.elijah.nextgen.comp_model.CM_CompilerInput;

import java.util.*;
import java.util.stream.Collectors;

public class ElijahCli {
	final             Compilation  comp;
	private @Nullable List<String> args;
	private boolean __calledFeedCmdLine;

	public ElijahCli(final ErrSink aStdErrSink, final IO aIO) {
		comp = CompilationFactory.mkCompilation(aStdErrSink, aIO);
	}

	public static ElijahCli createDefault() {
		return new ElijahCli(new StdErrSink(), new IO_());
	}

	public static ElijahCli createDefaultWithArgs(final List<String> aStringList, final String aS) {
		final List<String> args     = new ArrayList<String>();
		args.addAll(aStringList);
		args.add(aS);

		final ElijahCli cli = new ElijahCli(new StdErrSink(), new IO_());
		return cli;
	}

	public void feedCmdLine(final List<String> args) {
		this.__calledFeedCmdLine = true;

		final CompilerController controller = new DefaultCompilerController(((CompilationImpl)comp).getCompilationAccess3());

/*
		final List<CompilerInput> inputs = args.stream()
		                                       .map((String s) -> {
			                                       final CompilerInput input = new CompilerInput_(s);

			                                       if (s.startsWith("-")) {
				                                       input.setArg();
			                                       } else {
				                                       // TODO 09/24 check this
				                                       input.setSourceRoot();
			                                       }

			                                       return input;
		                                       }).collect(Collectors.toList());

		assert Objects.equals(inputs, stringListToInputList(args));
*/

		@NotNull final List<CompilerInput> inputs = stringListToInputList(args);
		comp.feedInputs(inputs, controller);
	}

	@NotNull
	public List<CompilerInput> stringListToInputList(final @NotNull List<String> args) {
		final List<CompilerInput> inputs = args.stream()
		                                       .map(this::_convertCompilerInput)
		                                       .collect(Collectors.toList());
		return inputs;
	}

	@NotNull
	private CompilerInput _convertCompilerInput(final String s) {
		final CompilerInput    input = new CompilerInput_(s, Optional.of(comp));
		final CM_CompilerInput cm    = comp.get(input);

		if (cm.inpSameAs(s)) {
			input.setSourceRoot();
		} else {
			assert false;
		}

		return input;
	}

	public static void main(final String[] args) throws Exception {
		final ElijahCli cli = new ElijahCli(new StdErrSink(), new IO_());
		final List<String> args1 = new ArrayList<>(Arrays.asList(args));
		cli.args = args1;
		cli.feedCmdLine(args1);
	}

	public int obtainErrorCount() {
		if (args == null)
			return 0;
		if (!_calledFeedCmdLine())
			feedCmdLine(args);
		return comp.errorCount();
	}

	public boolean _calledFeedCmdLine() {
		return __calledFeedCmdLine;
	}

	public int errorCount() {
		return comp.errorCount();
	}

	public Compilation getComp() {
		return this.comp;
	}
}
