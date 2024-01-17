package tripleo.elijah.stages.deduce;

import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.stages.logging.ElLog;

import java.util.Objects;

public final class DT_Env {
	private final ElLog         LOG;
	private final ErrSink       errSink;
	private final DeduceCentral central;

	public DT_Env(ElLog LOG, ErrSink errSink, DeduceCentral central) {
		this.LOG     = LOG;
		this.errSink = errSink;
		this.central = central;
	}

	public ElLog LOG() {
		return LOG;
	}

	public ErrSink errSink() {
		return errSink;
	}

	public DeduceCentral central() {
		return central;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (DT_Env) obj;
		return Objects.equals(this.LOG, that.LOG) &&
				Objects.equals(this.errSink, that.errSink) &&
				Objects.equals(this.central, that.central);
	}

	@Override
	public int hashCode() {
		return Objects.hash(LOG, errSink, central);
	}

	@Override
	public String toString() {
		return "DT_Env[" +
				"LOG=" + LOG + ", " +
				"errSink=" + errSink + ", " +
				"central=" + central + ']';
	}

}
