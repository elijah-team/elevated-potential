package tripleo.elijah.stages.logging;

public interface LogEntry {
	String message();

	long time();

	Level level();

	enum Level {
		ERROR, INFO
	}
}
