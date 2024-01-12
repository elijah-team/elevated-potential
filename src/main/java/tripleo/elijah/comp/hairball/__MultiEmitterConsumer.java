package tripleo.elijah.comp.hairball;

import com.google.common.base.Preconditions;
import io.smallrye.mutiny.subscription.MultiEmitter;
import tripleo.elijah.comp.i.CB_Process;
import tripleo.elijah.comp.impl.DefaultCompilationBus;
import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.scaffold.__AbstractPushWork;

import java.util.Queue;
import java.util.function.Consumer;

public class __MultiEmitterConsumer implements Consumer<MultiEmitter<? super PW_PushWork>> {
	private final DefaultCompilationBus defaultCompilationBus;
//	private final PW_PushWorkQueue pq;
	private final Queue<CB_Process> pq;

	public __MultiEmitterConsumer(final DefaultCompilationBus aDefaultCompilationBus,
	                              final Queue<CB_Process> aPushWorkQueue) {
		defaultCompilationBus = aDefaultCompilationBus;
		pq                    = aPushWorkQueue;
	}

	@Override
	public void accept(final MultiEmitter<? super PW_PushWork> emitter) {
		pq.stream()
		  .forEach(process -> emitter.emit(___MultiEmitterPushWork(process)));

		emitter.complete();
	}

	private PW_PushWork ___MultiEmitterPushWork(final CB_Process process) {
		Preconditions.checkNotNull(process);

		return new __AbstractPushWork() {
			@Override
			public void handle(final PW_Controller pwc, final PW_PushWork otherInstance) {
				System.err.println("777111 " + process.name()); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
				defaultCompilationBus.logProgress(DefaultCompilationBus.DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, process.name());

				//CompilationUnitTree
				//Compilation.Cheat.executeCB_Action(aProcess);

				process.execute(defaultCompilationBus);
			}
		};
	}
}