package tripleo.elijah.comp.hairball;

//import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
//import io.smallrye.mutiny.helpers.queues.MpscLinkedQueue;
import org.jctools.queues.MpscLinkedQueue;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.BackPressureStrategy;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.CB_Process;
import tripleo.elijah.comp.impl.DefaultCompilationBus;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;

import java.util.Queue;

public final class H {
	public static void runProcesses_(final DefaultCompilationBus cb, final Queue<CB_Process> pq, final @NotNull Compilation c) {
		final __MultiEmitterConsumer multiEmitterConsumer = new __MultiEmitterConsumer(cb, pq);
		final Multi<PW_PushWork>     m                    = Multi.createFrom().emitter(multiEmitterConsumer, BackPressureStrategy.BUFFER);

		m.subscribe().with((PW_PushWork item) -> {
			//System.out.println("Received item: " + item);

			item.handle(c.__pw_controller(), null);
		});
	}

	public static Queue<CB_Process> createQueue() {
//		return new MpscLinkedQueue<>();
		return MpscLinkedQueue.newMpscLinkedQueue();
	}
}
