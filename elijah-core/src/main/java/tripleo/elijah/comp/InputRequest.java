package tripleo.elijah.comp;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.world.i.WorldModule;

import java.io.File;

public class InputRequest { // TODO 09/08 Convert to record
	private final File                    _file;
	private final boolean                 _do_out;
	private final LibraryStatementPart    lsp;
	private       Operation2<WorldModule> op;

	public InputRequest(final File aFile, final boolean aDoOut, final @Nullable LibraryStatementPart aLsp) {
		_file   = aFile;
		_do_out = aDoOut;
		lsp     = aLsp;
	}

	public File file() {
		return _file;
	}

	public boolean do_out() {
		return _do_out;
	}

	public LibraryStatementPart lsp() {
		return lsp;
	}

	public void setOp(final Operation2<WorldModule> aOwm) {
		op = aOwm;
	}
}
