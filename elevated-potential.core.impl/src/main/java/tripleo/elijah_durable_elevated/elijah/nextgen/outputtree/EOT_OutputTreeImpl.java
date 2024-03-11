package tripleo.elijah_durable_elevated.elijah.nextgen.outputtree;

import com.google.common.collect.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;

import java.util.*;

/**
 * @author tripleo
 */
public class EOT_OutputTreeImpl implements EOT_OutputTree {
	private final List<EOT_OutputFile> list = new ArrayList<>();

	@Override
	public void add(final @NotNull EOT_OutputFile aOff) {
		// 05/18 System.err.printf("[add] %s %s%n", aOff.getFilename(),
		// aOff.getStatementSequence().getText());

		list.add(aOff);
	}

	@Override
	public void addAll(final @NotNull List<EOT_OutputFile> aLeof) {
		list.addAll(aLeof);
	}

	@Override
	public @NotNull List<EOT_OutputFile> getList() {
		return list;
	}

	@Override
	public void recompute() {
		// TODO big wtf
		final Multimap<String, EOT_OutputFile> mmfn = ArrayListMultimap.create();
		for (final EOT_OutputFile outputFile : list) {
			mmfn.put(outputFile.getFilename(), outputFile);
		}

		for (Map.Entry<String, Collection<EOT_OutputFile>> sto : mmfn.asMap().entrySet()) {
			var tt = sto.getValue();
			if (tt.size() > 1) {
				list.removeAll(tt);

				var model = tt.iterator().next();

				var type = model.getType();
				var inputs = model.getInputs(); // FIXME can possibly contain others
				var filename = sto.getKey();

				final List<EG_Statement> list2 = _EOT_OutputTree__Utils._extractStatementSequenceFromAllOutputFiles(tt);

				var seq = new EG_SequenceStatement(new EG_Naming("redone"), list2);
				var ofn = new EOT_OutputFileImpl(inputs, filename, type, seq);

				list.add(ofn);
			}
		}
	}

	@Override
	public void set(final @NotNull List<EOT_OutputFile> aLeof) {
		list.addAll(aLeof);
	}
}
