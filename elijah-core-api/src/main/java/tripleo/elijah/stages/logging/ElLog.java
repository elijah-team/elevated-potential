package tripleo.elijah.stages.logging;

import org.jetbrains.annotations.*;

import java.util.*;

public interface ElLog {
	void err(String aMessage);

	@NotNull List<LogEntry> getEntries();

	String getFileName();

	String getPhase();

	void info(String aMessage);

	public enum Verbosity {
		SILENT, VERBOSE
	}
}
