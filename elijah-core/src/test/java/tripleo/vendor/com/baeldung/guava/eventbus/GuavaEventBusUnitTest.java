package tripleo.vendor.com.baeldung.guava.eventbus;

import com.google.common.eventbus.EventBus;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GuavaEventBusUnitTest {
	private EventListener listener;
	private EventBus      eventBus;

	@BeforeEach
	public void setUp() {
		eventBus = new EventBus();
		listener = new EventListener();

		eventBus.register(listener);
	}

	@AfterEach
	public void tearDown() {
		eventBus.unregister(listener);
	}

	@org.junit.jupiter.api.Test
	public void givenStringEvent_whenEventHandled_thenSuccess() {
		listener.resetEventsHandled();

		eventBus.post("String Event");
		Assert.assertEquals(1, listener.getEventsHandled());
	}

	@org.junit.jupiter.api.Test
	public void givenCustomEvent_whenEventHandled_thenSuccess() {
		listener.resetEventsHandled();

		CustomEvent customEvent = new CustomEvent("Custom Event");
		eventBus.post(customEvent);

		Assert.assertEquals(1, listener.getEventsHandled());
	}

	@Test
	public void givenUnSubscribedEvent_whenEventHandledByDeadEvent_thenSuccess() {
		listener.resetEventsHandled();

		eventBus.post(12345);
		Assert.assertEquals(1, listener.getEventsHandled());
	}
}
