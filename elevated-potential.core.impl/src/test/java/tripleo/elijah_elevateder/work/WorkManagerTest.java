/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.work;

import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.work.*;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.util.ArrayList;
import java.util.List;

public class WorkManagerTest {

	static class AppendChar implements WorkJob {

		private final int level;
		private final List<String> sink;
		private final @NotNull String state;
		private boolean _done;

		public AppendChar(String s, int level, List<String> aSink) {
			state = s + (char) (level + 'A');
			this.level = level;
			sink = aSink;
		}

		@Override
		public boolean isDone() {
			return _done;
		}

		@Override
		public void run(@NotNull WorkManager aWorkManager) {
			if (level < 4) {
				WorkList wl = new EDL_WorkList();
				wl.addJob(new AppendChar(state, level + 1, sink));
				aWorkManager.addJobs(wl);
			}
			sink.add(state);
			_done = true;
		}
	}

	@Ignore
	@Test
	public void testWorkManager() {
		List<String> sink = new ArrayList<>();

		WorkManager workManager = new EDL_WorkManager();

		WorkList wl = new EDL_WorkList();
		wl.addJob(new AppendChar("A", 0, sink));
		wl.addJob(new AppendChar("B", 0, sink));
		wl.addJob(new AppendChar("C", 0, sink));

		workManager.addJobs(wl);

		workManager.drain();

		SimplePrintLoggerToRemoveSoon.println_err_4(""+sink);
	}
}

//
//
//
