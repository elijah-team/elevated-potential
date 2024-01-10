package tripleo.elijah.stages.logging;

import org.jetbrains.annotations.*;

import java.util.*;

public interface ElLog {
	@NotNull List<LogEntry> getEntries();

	String getFileName();

	String getPhase();

	void err(String aMessage);

	void info(String aMessage);

	public enum Verbosity {
		SILENT, VERBOSE
	}
}
