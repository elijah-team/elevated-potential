package tripleo.elijah.comp;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ElijahCli;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.world.i.LivingRepo;

import java.util.List;

public class ElijahTestCli {
	public /*final*/ ElijahCli cli;

	public static ElijahTestCli createDefault() {
		final ElijahTestCli instance = new ElijahTestCli();
		instance.cli = ElijahCli.createDefault();
		return instance;
	}

	public static ElijahTestCli createDefaultWithArgs(final List<String> aStringList, final String aS) {
		final ElijahTestCli instance = new ElijahTestCli();
		instance.cli = ElijahCli.createDefaultWithArgs(aStringList, aS);
		return instance;
	}

	public void main(final String[] args) throws Exception {
		cli.main(args);
	}

	public void feedCmdLine(final List<String> args) {
		cli.feedCmdLine(args);
	}

	public @NotNull List<CompilerInput> stringListToInputList(final @NotNull List<String> args) {
		return cli.stringListToInputList(args);
	}

	public int obtainErrorCount() {
		return cli.obtainErrorCount();
	}

	public boolean _calledFeedCmdLine() {
		return cli._calledFeedCmdLine();
	}

	public int errorCount() {
		return cli.errorCount();
	}

	public LivingRepo world() {
		return cli.getComp().world();
	}

	public IO getIO() {
		return cli.getComp().getIO();
	}

	public Finally reports() {
		return cli.getComp().reports();
	}

	public boolean isPackage(final String aPackageName) {
		return cli.getComp().world().isPackage(aPackageName);
	}

	public boolean outputTree_isEmpty() {
		return cli.getComp().getOutputTree().getList().isEmpty();
	}

	public CompilationEnclosure getCompilationEnclosure() {
		return cli.getComp().getCompilationEnclosure();
	}

	public List<Pair<ErrSink.Errors, Object>> errSinkList() {
		return cli.getComp().getErrSink().list();
	}
}
