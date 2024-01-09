package tripleo.elijah.work;

import org.jetbrains.annotations.*;

public interface WorkManager {
	void addJobs(WorkList aList);

	void drain();

	@Nullable WorkJob next();

	int totalSize();
}
