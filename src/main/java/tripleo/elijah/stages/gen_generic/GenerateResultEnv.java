package tripleo.elijah.stages.gen_generic;

import tripleo.elijah.comp.notation.GM_GenerateModule;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

import java.util.Objects;

public final class GenerateResultEnv {
	private final GenerateResultSink resultSink;
	private final GenerateResult     gr;
	private final WorkManager        wm;
	private final WorkList           wl;
	private final GM_GenerateModule  gmgm;

	public GenerateResultEnv(
			GenerateResultSink resultSink,
			GenerateResult gr,
			WorkManager wm,
			WorkList wl,
			GM_GenerateModule gmgm
							) {
		this.resultSink = resultSink;
		this.gr         = gr;
		this.wm         = wm;
		this.wl         = wl;
		this.gmgm       = gmgm;
	}

	public GenerateResultSink resultSink() {
		return resultSink;
	}

	public GenerateResult gr() {
		return gr;
	}

	public WorkManager wm() {
		return wm;
	}

	public WorkList wl() {
		return wl;
	}

	public GM_GenerateModule gmgm() {
		return gmgm;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (GenerateResultEnv) obj;
		return Objects.equals(this.resultSink, that.resultSink) &&
				Objects.equals(this.gr, that.gr) &&
				Objects.equals(this.wm, that.wm) &&
				Objects.equals(this.wl, that.wl) &&
				Objects.equals(this.gmgm, that.gmgm);
	}

	@Override
	public int hashCode() {
		return Objects.hash(resultSink, gr, wm, wl, gmgm);
	}

	@Override
	public String toString() {
		return "GenerateResultEnv[" +
				"resultSink=" + resultSink + ", " +
				"gr=" + gr + ", " +
				"wm=" + wm + ", " +
				"wl=" + wl + ", " +
				"gmgm=" + gmgm + ']';
	}

}
