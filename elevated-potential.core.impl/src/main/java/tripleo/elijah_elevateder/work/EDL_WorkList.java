/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.work;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.work.WorkJob;
import tripleo.elijah.work.WorkList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 4/26/21 4:24 AM
 */
public class EDL_WorkList implements WorkList {
	private boolean             _done;
	private final List<WorkJob> jobs = new ArrayList<>();

	@Override
	public void addJob(final WorkJob aJob) {
		jobs.add(aJob);
	}

	@Override
	public @NotNull ImmutableList<WorkJob> getJobs() {
		return ImmutableList.copyOf(jobs);
	}

	@Override
	public boolean isDone() {
		return _done;
	}

	@Override
	public boolean isEmpty() {
		return jobs.size() == 0;
	}

	@Override
	public void setDone() {
		_done = true;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
