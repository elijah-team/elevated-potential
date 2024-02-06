package tripleo.elijah.comp.nextgen;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Operation;

import java.io.InputStream;

public interface CX_ElijahSpecReader {
	@NotNull Operation<InputStream> get();
}
