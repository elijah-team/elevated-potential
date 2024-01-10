package tripleo.elijah.comp.i;

import tripleo.elijah.comp.internal.LCM;

import java.util.Objects;

public record LCM_HandleEvent(LCM_CompilerAccess compilation, LCM lcm, Object obj, LCM_Event event) {
//	@Override
//	public String toString() {
//		return "LCM_HandleEvent[" +
//				"compilation=" + compilation + ", " +
//				"lcm=" + lcm + ", " +
//				"obj=" + obj + ", " +
//				"event=" + event + ']';
//	}
}
