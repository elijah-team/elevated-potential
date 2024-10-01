/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.pipelines;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah_fluffy.util.Ok;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_elevateder.stages.deduce.pipeline_impl.DeducePipelineImpl;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

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
		SimplePrintLoggerToRemoveSoon.println_err_2(g);
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
