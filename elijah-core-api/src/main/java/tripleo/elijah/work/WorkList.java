package tripleo.elijah.work;

import com.google.common.collect.*;
import org.jetbrains.annotations.*;

public interface WorkList {
	void addJob(WorkJob aJob);

	@NotNull ImmutableList<WorkJob> getJobs();

	boolean isDone();

	boolean isEmpty();

	void setDone();
}
