package tripleo.elijah.work;

import org.jetbrains.annotations.*;

import com.google.common.collect.*;

public interface WorkList {
	void addJob(WorkJob aJob);

	@NotNull
	ImmutableList<WorkJob> getJobs();

	boolean isDone();

	boolean isEmpty();

	void setDone();
}
