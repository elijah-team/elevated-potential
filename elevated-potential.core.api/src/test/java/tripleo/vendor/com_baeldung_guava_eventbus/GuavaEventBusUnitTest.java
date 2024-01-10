package tripleo.vendor.com_baeldung_guava_eventbus;

import com.google.common.eventbus.EventBus;

import tripleo.elijah.nextgen.comp_model.CM_UleLog;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuavaEventBusUnitTest {
	private EventListener listener;
	private EventBus eventBus;

	@org.junit.jupiter.api.Test
	public void givenCustomEvent_whenEventHandled_thenSuccess() {
		listener.resetEventsHandled();

		CustomEvent customEvent = new CustomEvent("Custom Event");
		eventBus.post(customEvent);

		assertEquals(1, listener.getEventsHandled());
	}

	@org.junit.jupiter.api.Test
	public void givenStringEvent_whenEventHandled_thenSuccess() {
		listener.resetEventsHandled();

		eventBus.post("String Event");
		assertEquals(1, listener.getEventsHandled());
	}

	@Test
	public void givenUnSubscribedEvent_whenEventHandledByDeadEvent_thenSuccess() {
		listener.resetEventsHandled();

		eventBus.post(12345);
		assertEquals(1, listener.getEventsHandled());
	}

	@BeforeEach
	public void setUp() {
		eventBus = new EventBus();
		CM_UleLog log = new CM_UleLog() {
			@Override
			public void info(String string) {
				SimplePrintLoggerToRemoveSoon.println2(string);
			}
		};
		
		listener = new EventListener(log);

		eventBus.register(listener);
	}

	@AfterEach
	public void tearDown() {
		eventBus.unregister(listener);
	}
}
