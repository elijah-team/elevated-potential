/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.logging;

import org.jetbrains.annotations.*;

import java.util.*;

/**
 * Created 8/3/21 3:46 AM
 */
public class ElLog_ implements ElLog {

	private final List<LogEntry> entries = new ArrayList<>();
	private final Verbosity verbose;
	private final String fileName;

	private final String phase;

	public ElLog_(String aFileName, Verbosity aVerbose, String aPhase) {
		fileName = aFileName;
		verbose = aVerbose;
		phase = aPhase;
	}

	@Override
	public void err(String aMessage) {
		long time = System.currentTimeMillis();
		entries.add(new LogEntry_(time, LogEntry_.Level.ERROR, aMessage));
		if (verbose == Verbosity.VERBOSE)
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_2(aMessage);
	}

	@Override
	public @NotNull List<LogEntry> getEntries() {
		return entries;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public String getPhase() {
		return phase;
	}

	@Override
	public void info(String aMessage) {
		long time = System.currentTimeMillis();
		entries.add(new LogEntry_(time, LogEntry_.Level.INFO, aMessage));
		if (verbose == Verbosity.VERBOSE)
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_2(aMessage);
	}
}

//
//
//
