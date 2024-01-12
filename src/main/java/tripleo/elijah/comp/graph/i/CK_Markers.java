package tripleo.elijah.comp.graph.i;

import java.util.Optional;

/**
 * Hold errors/diagnostics
 */
public interface CK_Markers {
	int count();
	Optional<CK_Marker> get(int index);
}
