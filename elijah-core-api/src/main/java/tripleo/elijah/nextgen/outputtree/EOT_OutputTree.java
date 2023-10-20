package tripleo.elijah.nextgen.outputtree;

import org.jetbrains.annotations.*;

import java.util.*;

public interface EOT_OutputTree {
	void add(@NotNull EOT_OutputFile aOff);

	void addAll(@NotNull List<EOT_OutputFile> aLeof);

	@NotNull List<EOT_OutputFile> getList();

	void recompute();

	void set(@NotNull List<EOT_OutputFile> aLeof);
}
