package tripleo.elijah_elevated_durable.comp;

import com.google.common.eventbus.*;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.comp_model.CM_UleLog;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.comp.i.LCM_Event;
import tripleo.elijah_elevateder.comp.i.LCM_HandleEvent;

public class LCM {
	private final EventBus          eventBus;
	@SuppressWarnings("FieldCanBeLocal")
	private final LCM_EventListener listener;
	private final Compilation       _compilation;

	public LCM(final Compilation aCompilation) {
		_compilation = aCompilation;

		eventBus     = new EventBus();
		listener     = new LCM_EventListener();
		listener._setLog(_compilation);

		eventBus.register(listener);
	}

	public void asv(final Object obj, final LCM_Event event) {
		eventBus.post(new LCM_HandleEvent(_compilation.getLCMAccess(), this, obj, event));
	}

	public void exception(final LCM_HandleEvent aHandleEvent, final Exception aE) {
		throw new RuntimeException(aE); // FIXME 11/24 rethrow
	}

	public class LCM_EventListener {
		private        CM_UleLog LOG;
//		private static final Logger LOG = LoggerFactory.getLogger(LCM_EventListener.class);
		private static int       eventsHandled;

		@Subscribe
		public void handleDeadEvent(@NotNull final DeadEvent deadEvent) {
			LOG.info("unhandled event [" + deadEvent.getEvent() + "]");
			eventsHandled++;
		}

		public void _setLog(Compilation _compilation) {
			LOG = _compilation.con().getULog();
		}

		@Subscribe
		public void someLCM_Event(@NotNull final LCM_HandleEvent aHandleEvent) {
			LOG.info("do event [" + aHandleEvent + "]");
			aHandleEvent.event().handle(aHandleEvent);
			eventsHandled++;
		}

		void resetEventsHandled() {
			eventsHandled = 0;
		}

		int getEventsHandled() {
			return eventsHandled;
		}
	}
}
