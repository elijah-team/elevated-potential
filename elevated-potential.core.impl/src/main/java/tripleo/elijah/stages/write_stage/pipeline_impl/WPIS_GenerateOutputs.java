package tripleo.elijah.stages.write_stage.pipeline_impl;

import com.google.common.base.*;
import com.google.common.collect.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.comp.nextgen.pn.*;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.output.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.generate.*;
import tripleo.elijah.util.*;

import java.util.*;
import java.util.function.*;

public class WPIS_GenerateOutputs implements WP_Individual_Step, PN_signalCalculateFinishParse {

	private final List<NG_OutputRequest>   ors = new ArrayList<>();
	private       WritePipelineSharedState st;
	private       List<Amazing>            amazings;

	@Override
	public void act(final @NotNull WritePipelineSharedState st, final WP_State_Control sc) {
		Preconditions.checkState(st.getGr() != null);
		Preconditions.checkState(st.sys != null);

		GenerateResult result = st.getGr();

		//final SPrintStream sps = new SPrintStream();
		//DebugBuffersLogic.debug_buffers_logic(result, sps);

		//final Default_WPIS_GenerateOutputs_Behavior_PrintDBLString printDBLString = new Default_WPIS_GenerateOutputs_Behavior_PrintDBLString();
		//printDBLString.print(sps.getString());

		this.st = st;

		final OutputItems itms = new OutputItems();

		st.c.pushWork(PW_signalCalculateFinishParse.instance(), new PN_Ping() {
			@Override
			public void ping(final Object t) {
//				final OutputItems itms = new OutputItems(); // maybe belongs here?







				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("999053 "+t.getClass().getName());







				var cs = st.pa.getActiveClasses();
				var ns = st.pa.getActiveNamespaces();
				var fs = st.pa.getActiveFunctions();

				pmPN_signalCalculateFinishParse(result, cs, ns, fs, itms);
			}
		});
	}

	private void pmPN_signalCalculateFinishParse(final @NotNull GenerateResult result,
												 final @NotNull List<EvaClass> cs,
												 final @NotNull List<EvaNamespace> ns,
												 final @NotNull List<BaseEvaFunction> fs,
												 final OutputItems itms) {
		var p = new PM_signalCalculateFinishParse(result, cs, ns, fs, itms);
		ping(p);
	}

	@Override
	public void ping(final PM_signalCalculateFinishParse signal) {
		var cs     = signal.getCs();
		var ns     = signal.getNs();
		var fs     = signal.getFs();
		var itms   = signal.getItms();
		var result = signal.getResult();

		final int totalCount = cs.size() + ns.size() + fs.size();
		itms.readyCount(totalCount); // looks like it should work, but also looks like it won't

		amazings = new ArrayList<>(totalCount);

		for (EvaClass c : cs) {
			final AmazingClass amazingClass = new AmazingClass(c, itms, st.pa);
			waitGenC(amazingClass.mod(), amazingClass::waitGenC);
			amazings.add(amazingClass);
		}
		for (BaseEvaFunction f : fs) {
			final AmazingFunction amazingFunction = new AmazingFunction(f, itms, result, st.pa);
			waitGenC(amazingFunction.mod(), amazingFunction::waitGenC);
			amazings.add(amazingFunction);
		}
		for (EvaNamespace n : ns) {
			final AmazingNamespace amazingNamespace = new AmazingNamespace(n, itms, st.pa);
			waitGenC(amazingNamespace.mod(), amazingNamespace::waitGenC);
			amazings.add(amazingNamespace);
		}

		for (Amazing amazing : amazings) {
			amazing.run();
		}
	}

	void waitGenC(final OS_Module mod, final Consumer<GenerateC> cb) {
		this.st.pa.waitGenC(mod, cb);
	}

	public void pnPN_signalCalculateFinishParse() {

	}

	//@Override
	public Operation<Ok> execute(final CK_Monitor aMonitor) {
		return null;
	}

	@FunctionalInterface
	public interface WPIS_GenerateOutputs_Behavior_PrintDBLString {
		void print(String sps);
	}

	interface Writable {
		EOT_FileNameProvider filename();

		List<EIT_Input> inputs();

		EG_Statement statement();
	}

	static class Default_WPIS_GenerateOutputs_Behavior_PrintDBLString implements WPIS_GenerateOutputs_Behavior_PrintDBLString {
		@Override
		public void print(final String sps) {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(sps);
		}
	}

	// TODO 09/04 Duplication madness
	private static class MyWritable implements Writable {
		final          Collection<EG_Statement> value;
		final          EOT_FileNameProvider     filename;
		final @NotNull List<EG_Statement>       list;
		final @NotNull EG_SequenceStatement            statement;
		private final  NG_OutputRequest                outputRequest;

		public MyWritable(final Map.@NotNull Entry<NG_OutputRequest, Collection<EG_Statement>> aEntry) {
			this.outputRequest = aEntry.getKey();
			filename           = outputRequest.fileName();
			value              = aEntry.getValue();

			list = value.stream().toList();

			statement = new EG_SequenceStatement(new EG_Naming("writable-combined-file"), list);
		}

		@Override
		public EOT_FileNameProvider filename() {
			return filename;
		}

		@Override
		public @NotNull List<EIT_Input> inputs() {
			if (outputRequest == null) {
				throw new IllegalStateException("shouldn't be here");
			}

			final EIT_ModuleInput moduleInput = outputRequest.outputStatement().getModuleInput();

			return Helpers.List_of(moduleInput);
		}

		@Override
		public EG_Statement statement() {
			return statement;
		}

	}

	class OutputItems {
		final   OutputStrategy         osg             = st.sys.outputStrategyCreator.get();
		final   OutputStrategyC        outputStrategyC = new OutputStrategyC(osg);
		final   List<NG_OutputRequest> ors1            = new ArrayList<>();
		final   List<NG_OutputItem>    itms            = new ArrayList<>();
		private int                    _readyCount;
		private int                    _addTally;

		private static @NotNull List<EG_Statement> relist3_flatten(final EG_Statement sequence) {
			var llll = new ArrayList<EG_Statement>();

			if (sequence instanceof EG_SequenceStatement seqst) {
				llll.addAll(seqst._list());
			} else {
				llll.add(sequence);
			}

			return llll;
		}

		@NotNull
		private static EG_Statement __relist3(final List<EG_Statement> list2) {
			final String             uuid      = "elinc_%s".formatted(UUID.randomUUID().toString().replace('-', '_'));
			final String             b_s       = "#ifndef %s\n#define %s 1\n\n".formatted(uuid, uuid);
			final EG_Statement       b         = EG_Statement.of(b_s, EX_Explanation.withMessage("Header file prefix"));
			final EG_Statement       e         = EG_Statement.of("\n#endif\n", EX_Explanation.withMessage("Header file postfix"));
			final List<EG_Statement> list3     = Helpers.__combine_list_elements(b, list2, e);
			final EG_Statement       statement = new EG_SequenceStatement(new EG_Naming("relist3"), list3);
			return statement;
		}

		public void addItem(final NG_OutputItem aOutputItem) {
			itms.add(aOutputItem);

			++_addTally;
			if (_addTally == _readyCount) {
				new WPIS_GenerateOutputs.MyRunnable(this, WPIS_GenerateOutputs.this).run();
			}
		}

		public void readyCount(final int aI) {
			this._readyCount = aI;
		}

	}

	private static class MyRunnable implements Runnable {
		private final OutputItems  outputItems;
		private final Compilation c;

		public MyRunnable(final OutputItems 			aOutputItems,
						  final WPIS_GenerateOutputs 	aWPISGenerateOutputs) {
			outputItems = aOutputItems;
			c           = aWPISGenerateOutputs.st.c;
		}

		@Override
		public void run() {
			for (final NG_OutputItem o : outputItems.itms) {
				final List<NG_OutputStatement> oxs = o.getOutputs();
				for (final NG_OutputStatement ox : oxs) {
					final GenerateResult.TY               oxt = ox.getTy();
					final String               oxb = ox.getText();
					final EOT_FileNameProvider s   = o.outName(outputItems.outputStrategyC, oxt);
					final NG_OutputRequest     or  = new NG_OutputRequest(s, ox, ox, o);

					outputItems.ors1.add(or);
				}
			}

			final Multimap<NG_OutputRequest, EG_Statement> mfss = ArrayListMultimap.create();
			final EOT_OutputTree       cot = c.getOutputTree();
			final CompilationEnclosure ce  = c.getCompilationEnclosure();

			for (final NG_OutputRequest or : outputItems.ors1) {
				ce.AssertOutFile(or);
			}

			// README combine output requests into file requests
			for (final NG_OutputRequest or : outputItems.ors1) {
				mfss.put(or, or.statement());
			}

			final List<Writable> writables = new ArrayList<>();

			for (final Map.Entry<NG_OutputRequest, Collection<EG_Statement>> entry : mfss.asMap().entrySet()) {
				writables.add(new MyWritable(entry));
			}

			for (final Writable writable : writables) {
				final String             filename   = writable.filename().getFilename();
				final EG_Statement       statement0 = writable.statement();
				final List<EG_Statement> list2      = OutputItems.relist3_flatten(statement0);
				final EG_Statement       statement;

				if (filename.endsWith(".h")) {
					statement = OutputItems.__relist3(list2);
				} else {
					statement = statement0;
				}

				final EOT_OutputFileImpl off = new EOT_OutputFileImpl(writable.inputs(), filename, EOT_OutputType.SOURCES, statement);
				cot.add(off);
			}
		}
	}
}
