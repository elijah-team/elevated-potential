package tripleo.vendor.com.baeldung.guava.eventbus;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah.nextgen.comp_model.CM_UleLog;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

@SuppressWarnings("unused")
public class EventListener {
//	private static final Logger LOG = LoggerFactory.getLogger(EventListener.class);
	private final CM_UleLog LOG;
	private static int eventsHandled;

	public EventListener(CM_UleLog log2) {
		this.LOG = log2;
	}

	int getEventsHandled() {
		return eventsHandled;
	}

	@Subscribe
	public void handleDeadEvent(@NotNull DeadEvent deadEvent) {
		LOG.info("unhandled event [" + deadEvent.getEvent() + "]");
		eventsHandled++;
	}

	void resetEventsHandled() {
		eventsHandled = 0;
	}

	@Subscribe
	public void someCustomEvent(@NotNull CustomEvent customEvent) {
		LOG.info("do event [" + customEvent.getAction() + "]");
		eventsHandled++;
	}

	@Subscribe
	public void stringEvent(String event) {
		LOG.info("do event [" + event + "]");
		eventsHandled++;
	}
}
