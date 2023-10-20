package tripleo.elijah.comp.nextgen;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.nextgen.*;

public interface CP_Paths {
	void addNode(CP_RootType t, ER_Node aNode);

	CP_Path outputRoot();

	void renderNodes();

	void signalCalculateFinishParse();

	@NotNull CP_StdlibPath stdlibRoot();
}
