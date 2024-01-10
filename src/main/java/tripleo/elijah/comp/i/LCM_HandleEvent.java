package tripleo.elijah.comp.i;

import tripleo.elijah.comp.internal.LCM;

import java.util.Objects;

public final class LCM_HandleEvent {
	private final LCM_CompilerAccess compilation;
	private final LCM                lcm;
	private final Object             obj;
	private final LCM_Event          event;

	public LCM_HandleEvent(LCM_CompilerAccess compilation,
						   LCM lcm, // TODO 11/24 this leaks. maybe should ignore
						   Object obj,
						   LCM_Event event) {
		this.compilation = compilation;
		this.lcm         = lcm;
		this.obj         = obj;
		this.event       = event;
	}

	public LCM_CompilerAccess compilation() {
		return compilation;
	}

	public LCM lcm() {
		return lcm;
	}

	public Object obj() {
		return obj;
	}

	public LCM_Event event() {
		return event;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (LCM_HandleEvent) obj;
		return Objects.equals(this.compilation, that.compilation) &&
				Objects.equals(this.lcm, that.lcm) &&
				Objects.equals(this.obj, that.obj) &&
				Objects.equals(this.event, that.event);
	}

	@Override
	public int hashCode() {
		return Objects.hash(compilation, lcm, obj, event);
	}

	@Override
	public String toString() {
		return "LCM_HandleEvent[" +
				"compilation=" + compilation + ", " +
				"lcm=" + lcm + ", " +
				"obj=" + obj + ", " +
				"event=" + event + ']';
	}

}
