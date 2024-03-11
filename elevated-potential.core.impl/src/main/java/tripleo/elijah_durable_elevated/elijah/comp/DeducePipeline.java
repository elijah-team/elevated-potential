/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.util.Ok;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.pipeline_impl.DeducePipelineImpl;

/**
 * Created 8/21/21 10:10 PM
 */
public class DeducePipeline extends PipelineMember {
	private final @NotNull DeducePipelineImpl impl;

	public DeducePipeline(final @NotNull GPipelineAccess pa0) {
		// logProgress("***** Hit DeducePipeline constructor");
		final IPipelineAccess pa = (IPipelineAccess) pa0;

		impl = new DeducePipelineImpl(pa);
	}

	protected void logProgress(final String g) {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_2(g);
	}

	@Override
	public void run(final Ok aSt, final CB_Output aOutput) {
		// logProgress("***** Hit DeducePipeline #run");
		impl.run();
	}

	@Override
	public String finishPipeline_asString() {
		return this.getClass().toString();
	}

}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
