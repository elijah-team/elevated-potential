package tripleo.vendor.com_baeldung_guava_eventbus;

public class CustomEvent {
	private String action;

	CustomEvent(String action) {
		this.action = action;
	}

	String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
