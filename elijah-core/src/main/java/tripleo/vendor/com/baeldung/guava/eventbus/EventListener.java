package tripleo.vendor.com.baeldung.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class EventListener {
	private static final Logger LOG = LoggerFactory.getLogger(EventListener.class);
	private static       int    eventsHandled;

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
