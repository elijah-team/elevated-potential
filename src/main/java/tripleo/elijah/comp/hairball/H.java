package tripleo.elijah.comp.hairball;

//import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
//import io.smallrye.mutiny.helpers.queues.MpscLinkedQueue;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.BackPressureStrategy;
import org.jctools.queues.MessagePassingQueue;
import org.jctools.queues.MpscLinkedQueue;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.CB_Process;
import tripleo.elijah.comp.impl.DefaultCompilationBus;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;

public final class H {
	public static void runProcesses_(final DefaultCompilationBus cb,
	                                 final MessagePassingQueue<CB_Process> pq,
	                                 final @NotNull Compilation c) {
		final __MultiEmitterConsumer multiEmitterConsumer = new __MultiEmitterConsumer(cb, pq);
		final Multi<PW_PushWork> m = Multi.createFrom()
		                                  .emitter(multiEmitterConsumer, BackPressureStrategy.BUFFER)
		                                  .onFailure().invoke((Throwable failureThrowable) ->
		                                                      {
			                                                      System.err.println("3030 " + failureThrowable);
		                                                      });

		m.subscribe()
		 .with((PW_PushWork item) -> {
			 //System.out.println("Received item: " + item);

			 item.handle(c.__pw_controller(), null);
		 });

		m.onFailure().recoverWithCompletion();
	}

	public static MessagePassingQueue<CB_Process> createQueue() {
//		return new MpscLinkedQueue();
		return MpscLinkedQueue.newMpscLinkedQueue();
	}
}
