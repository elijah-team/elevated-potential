package tripleo.elijah.nextgen.outputtree;

import java.util.*;

import org.jetbrains.annotations.*;

public interface EOT_OutputTree {
	void add(@NotNull EOT_OutputFile aOff);

	void addAll(@NotNull List<EOT_OutputFile> aLeof);

	@NotNull
	List<EOT_OutputFile> getList();

	void recompute();

	void set(@NotNull List<EOT_OutputFile> aLeof);
}
