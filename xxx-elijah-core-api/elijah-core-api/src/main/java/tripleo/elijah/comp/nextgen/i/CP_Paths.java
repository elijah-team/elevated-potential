package tripleo.elijah.comp.nextgen.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.nextgen.*;

public interface CP_Paths {
	void addNode(CP_RootType t, ER_Node aNode);

	CP_Path outputRoot();
	CP_Path preludeRoot();
	@NotNull CP_StdlibPath stdlibRoot();
	CP_Path sourcesRoot();

	void renderNodes();

	void signalCalculateFinishParse();

	void subscribeCalculateFinishParse(CPX_CalculateFinishParse cp_OutputPath);
}
