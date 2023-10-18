package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

import java.io.*;

public class InputRequest { // TODO 09/08 Convert to record
	private final File _file;
	private final boolean _do_out;
	private final LibraryStatementPart lsp;
	private Operation2<WorldModule> op;

	public InputRequest(final File aFile, final boolean aDoOut, final @Nullable LibraryStatementPart aLsp) {
		_file = aFile;
		_do_out = aDoOut;
		lsp = aLsp;
	}

	public boolean do_out() {
		return _do_out;
	}

	public File file() {
		return _file;
	}

	public LibraryStatementPart lsp() {
		return lsp;
	}

	public Operation2<WorldModule> op() {
		return op;
	}

	public void setOp(final Operation2<WorldModule> aOwm) {
		op = aOwm;
	}
}
//public record InputRequest (File file, boolean do_out, @Nullable LibraryStatementPart lsp) {
//	private       Holder<Operation2<WorldModule>> hop = new Holder<>(); // FIXME record members mus be static; use lombok, but eclipse, arrg -- no, use a generator!! (xtend??)
//
//	public void setOp(final Operation2<WorldModule> op) {
//		//op = op;
//		hop.set(op);
//	}
//
//	public Operation2<WorldModule> op() {
//		return hop.get();
//	}
//
//}
