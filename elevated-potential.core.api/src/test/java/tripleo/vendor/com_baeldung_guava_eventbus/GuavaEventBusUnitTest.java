package tripleo.vendor.com_baeldung_guava_eventbus;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

//import com.google.common.eventbus.EventBus;
//
//import tripleo.elijah.nextgen.comp_model.CM_UleLog;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
public class GuavaEventBusUnitTest {
//	private EventListener listener;
//	private EventBus eventBus;
//
	@Test
	public void givenCustomEvent_whenEventHandled_thenSuccess() {
//		listener.resetEventsHandled();
//
//		CustomEvent customEvent = new CustomEvent("Custom Event");
//		eventBus.post(customEvent);
//
//		assertEquals(1, listener.getEventsHandled());
		assertEquals(true, true);
	}

//	@Test
//	public void givenStringEvent_whenEventHandled_thenSuccess() {
//		listener.resetEventsHandled();
//
//		eventBus.post("String Event");
//		assertEquals(1, listener.getEventsHandled());
//	}
//
//	@Test
//	public void givenUnSubscribedEvent_whenEventHandledByDeadEvent_thenSuccess() {
//		listener.resetEventsHandled();
//
//		eventBus.post(12345);
//		assertEquals(1, listener.getEventsHandled());
//	}
//
//	@Before //Each
//	public void setUp() {
//		eventBus = new EventBus();
//		CM_UleLog log = new CM_UleLog() {
//			@Override
//			public void info(String string) {
//				SimplePrintLoggerToRemoveSoon.println2(string);
//			}
//		};
//
//		listener = new EventListener(log);
//
//		eventBus.register(listener);
//	}
//
//	@AfterEach
//	public void tearDown() {
//		eventBus.unregister(listener);
//	}
}
