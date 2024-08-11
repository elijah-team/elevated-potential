package tripleo.elijah.stages.logging;

import java.util.*;

import org.jetbrains.annotations.*;

public interface ElLog {
	@NotNull
	List<LogEntry> getEntries();

	String getFileName();

	String getPhase();

	void err(String aMessage);

	void info(String aMessage);

	public enum Progress {INFO, ERR}

	public enum Verbosity {
		SILENT, VERBOSE
	}
}
