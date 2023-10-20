package tripleo.elijah.comp.internal;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.sense.*;
import tripleo.elijah.stateful.DefaultStateful;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public class CR_FindCIs extends DefaultStateful implements CR_Action, Sensable {
	@Getter private SenseList senseList = new SenseList();

	public CR_FindCIs(final Compilation comp, final IProgressSink progressSink) {
		// TODO 09/05 look at 2 different progressSinks
		@NotNull CCI cci = new DefaultCCI(comp, comp._cis(), progressSink);
//        _ps = comp.getCompilationEnclosure().getCompilationBus().defaultProgressSink();;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

		if (comp.getCompilerInputListener() instanceof CCI_Acceptor__CompilerInputListener cci_listener) {
			cci_listener.set(cci, progressSink);
		}
	}


	@Override
	public void attach(@NotNull final CompilationRunner cr) {

	}

	@Override
	public @NotNull Operation<Ok> execute(@NotNull final CR_State st, final CB_Output aO) {
		throw new UnintendedUseException();
	}

	@Override
	public @NotNull String name() {
		return "find cis";
	}

	@Override
	public SenseIndex index() {
		return null;
	}
}
