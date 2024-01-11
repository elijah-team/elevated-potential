package tripleo.elijah.util3;

import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.util2.Eventual;
import tripleo.elijah.util2.EventualRegister;

import java.util.ArrayList;
import java.util.List;

public abstract class _AbstractEventualRegister implements EventualRegister {
	private final List<Eventual<?>> _eventuals = new ArrayList<>();

	@Override
	public void checkFinishEventuals() {
		_eventuals.stream()
		          .filter(eventual -> !eventual.isResolved())
		          .map(eventual ->
				               "[PipelineLogic::checkEventual] failed for " + eventual.description())
		          .forEach(SimplePrintLoggerToRemoveSoon::println_err_4);
	}

	@Override
	public <P> void register(final Eventual<P> e) {
		_eventuals.add(e);
	}
}
