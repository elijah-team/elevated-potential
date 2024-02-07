package tripleo.elijah_elevated.util;

import org.apache.commons.lang3.tuple.Pair;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.USE_Reasoning;

import java.lang.reflect.Array;
import java.util.*;

public enum DebugProbe {;

	private static final List<Pair<e,Map<String,Object>>> l = new ArrayList<>();

	public static void hit(final e aE, final Map<String,Object> a) {
		String s;
		switch (aE) {
		case COMPILATION_IMPL__use -> s="COMPILATION_IMPL.use";
		default -> s=aE.name();
		}
		l.add(Pair.of(aE, a));
		System.err.println("{{PROBE}} "+s+" "+ Arrays.asList(a));
	}

	public static Map<String, Object> dictOf(final Object... os) {
		Map<String, Object> result = new HashMap<>();
		for (int j = 0; j < os.length; j++) {
			final String a = (String) os[j];
			final Map<String,Object> b = (Map<String, Object>) os[j + 1];
			j++;++j;
			result.put(a,b);
		}
		return result;
	}

	public static List<Pair<e,Map<String,Object>>> get_COMPILATION_IMPL__use() {
		return l.stream().filter((x) -> x.getLeft()==e.COMPILATION_IMPL__use).toList();
	}

	public enum e{COMPILATION_IMPL__use};
}
