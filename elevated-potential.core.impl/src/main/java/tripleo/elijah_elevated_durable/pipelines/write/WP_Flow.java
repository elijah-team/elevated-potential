package tripleo.elijah_elevated_durable.pipelines.write;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevated_durable.pipelines.WritePipeline;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah_fluffy.util.Ok;
import tripleo.elijah_fluffy.util.Operation;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class WP_Flow extends CK_AbstractStepsContext {
	private final List<WP_Individual_Step> steps = new ArrayList<>();
	private       WritePipeline            writePipeline;
	private final OPS                     myOps;

	public WP_Flow(final WritePipeline aWritePipeline, final @NotNull IPipelineAccess aPa, final @NotNull Collection<? extends WP_Individual_Step> s) {
		writePipeline = aWritePipeline;
		steps.addAll(s);
		myOps = new OPS(writePipeline, this);
	}

	public OPS act() {
		sc = new WP_State_Control_1();

		myOps.init();

		for (final WP_Individual_Step step : steps) {
			sc.cur(step, writePipeline.getSt(), ops());
			if (sc.hasException()) {
				break;
			}
		}

		return myOps;
	}

	private OPS ops() {
		return myOps;
	}

	public WritePipelineSharedState context_st() {
		return writePipeline.getSt();
	}

	public WP_State_Control_1 sc;

	public WP_State_Control context_sc() {
		return sc;
	}

	public OPS context_ops() {
		return this.myOps;
	}

	public enum FlowStatus {
		FAILED, NOT_TRIED, TRIED
	}

	public class OPS {
		private final HashMap<WP_Individual_Step, Pair<FlowStatus, Operation<Ok>>> ops = new HashMap<WP_Individual_Step, Pair<FlowStatus, Operation<Ok>>>();
		private final WP_Flow                                                      flow;
		private final WritePipeline _writePipeline;

		public OPS(final WritePipeline aWritePipeline, final WP_Flow aWPFlow) {
			flow = aWPFlow;
			_writePipeline = aWritePipeline;
			//_writePipeline.st.pa
		}

		//public void put(final WP_Indiviual_Step aStep, final Pair<FlowStatus, Operation<Ok>> aOf) {
		//	ops.put(aStep, aOf);
		//}

		public void put(final WP_Individual_Step aStep, final FlowStatus fs, final Operation<Ok> ob) {
			ops.put(aStep, Pair.of(fs, ob)); // TODO 10/18 time (maybe somewhere else) chain
		}

		public void init() {
			for (final WP_Individual_Step step : steps) {
				ops.put(step, Pair.of(FlowStatus.NOT_TRIED, null)); // TODO 10/18 initailize (my) chain
			}
		}
	}

	@Override
	public void begin() {
		//throw new UnintendedUseException();
	}

}
