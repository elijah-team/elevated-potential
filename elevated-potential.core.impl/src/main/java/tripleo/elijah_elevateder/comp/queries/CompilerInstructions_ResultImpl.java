package tripleo.elijah_elevateder.comp.queries;

import org.apache.commons.lang3.tuple.Pair;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.queries.CompilerInstructions_Result;
import tripleo.elijah.comp.queries.QSEZ_Reasoning;
import tripleo.elijah.util.Operation2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompilerInstructions_ResultImpl implements CompilerInstructions_Result {
	private final List<Pair<Operation2<CompilerInstructions>, QSEZ_Reasoning>> directoryResult = new ArrayList<>();

	@Override public List<Operation2<CompilerInstructions>> getDirectoryResult() {
		return directoryResult.stream()
				.map(it -> it.getKey())
				.collect(Collectors.toList());
	}
	@Override public List<Pair<Operation2<CompilerInstructions>,QSEZ_Reasoning>> getDirectoryResult2() {
		return directoryResult;
	}

	@Override
	public void add(final Operation2<CompilerInstructions> aCio, final QSEZ_Reasoning aReasoning) {
		directoryResult.add(Pair.of(aCio, aReasoning));
	}
}
