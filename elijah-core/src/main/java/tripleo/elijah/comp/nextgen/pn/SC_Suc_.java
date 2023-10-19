package tripleo.elijah.comp.nextgen.pn;

import tripleo.elijah.util.*;

public final class SC_Suc_ { // Factory
	public static SC_Suc i(final SC_I sci) {
		return new SC_Suc() {
			@Override
			public void signal() {
				// just marking
				NotImplementedException.raise();
			}

			@Override
			public String asString() {
				return sci.sc_i_asString();
			}
		};
	}

	//public static <T> SC_S i(final T ao) {
	//	return null;
	//}
}
