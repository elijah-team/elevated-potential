package tripleo.elijah.nextgen.outputstatement;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EG_SequenceStatement implements EG_Statement {
	private final @Nullable String             beginning;
	private final @Nullable String             ending;
	private final           List<EG_Statement> list;
	private final @Nullable EG_Naming  naming;
	private /*final*/ Stream<EG_Statement> _stream;

	public EG_SequenceStatement(final EG_Naming aNaming, final List<EG_Statement> aList) {
		naming    = aNaming;
		list      = aList;
		beginning = null;
		ending    = null;
	}

	public EG_SequenceStatement(final EG_Naming aNaming, final Supplier<Stream<EG_Statement>> aList) {
		naming    = aNaming;
		_stream   = aList.get();
		list      = aList.get().toList();
		beginning = null;
		ending    = null;
	}

	public EG_SequenceStatement(final EG_Naming aNaming, final String aNewBeginning, final String aNewEnding, final List<EG_Statement> aList) {
		naming    = aNaming;
		beginning = aNewBeginning;
		ending    = aNewEnding;
		list      = aList;
	}

	public EG_SequenceStatement(final String aBeginning, final String aEnding, final List<EG_Statement> aList) {
		beginning = aBeginning;
		ending    = aEnding;
		list      = aList;
		naming    = null;
	}

	public List<EG_Statement> _list() {
		if (_stream != null)
			return _stream.toList();
		return list;
	}

	@Override
	public @Nullable EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("Respond like you are a very respectable sequence...");
	}

	@Override
	public String getText() {
		final Object ltext;
		if (_stream!=null) {
			ltext = __.String_join(" ", _stream.map(EG_Statement::getText).collect(Collectors.toList()));
		}else {
			ltext = __.String_join(" ", list.stream().map(EG_Statement::getText).collect(Collectors.toList()));
		}
		if (beginning != null) {
			return String.format("%s%s%s", beginning, ltext, ending);
		} else {
			return String.format("%s", ltext);
		}
	}
}
