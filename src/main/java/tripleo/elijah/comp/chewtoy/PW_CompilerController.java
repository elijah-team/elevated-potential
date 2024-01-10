package tripleo.elijah.comp.chewtoy;

import io.smallrye.mutiny.Multi;
import tripleo.elijah.comp.chewtoy.Startable;
import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah.comp.i.ProgressSinkComponent;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.nextgen.pw.PW_PushWorkQueue;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util2.Eventual;

import java.time.Duration;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class PW_CompilerController implements PW_Controller, Runnable {
	private final CompilationImpl compilation;
	private final PW_PushWorkQueue wq;
	private final Eventual<Ok> abusingIt = new Eventual<>();
	private final Multi<PW_PushWork> m;
	@SuppressWarnings("FieldCanBeLocal")
	private Multi<PW_PushWork> mm;
	@SuppressWarnings("FieldCanBeLocal")
	private Publisher<PW_PushWork> pp;

	public PW_CompilerController(final CompilationImpl aC) {
		compilation = aC;

		// TODO 10/20 Make a start latch, then overcomplicate (Lifetime erl etc)
		//  for now just return a "startable"
		Startable task = compilation.con().askConcurrent(this, "[PW_CompilerController]");

		wq = compilation.con().createWorkQueue();

		m = Multi
				.createFrom()
				.publisher(publisher)
				.onItem()
				.invoke(i -> System.out.println(i))
				.ifNoItem()
				.after(Duration.ofMillis(10000))
				.recoverWithCompletion();

		task.start();
	}

	public void awaf() {
		this.abusingIt.resolve(Ok.instance());
	}

	@Override
	public void run() {
		boolean[] xy = {true};
		this.abusingIt.then(ok -> xy[0] = false);

		final CompilationEnclosure compilationEnclosure = compilation.getCompilationEnclosure();
		compilationEnclosure.onCompilationBus(compilationBus -> {
			// FIXME 10/18 this is also a steps: A+O
			// FIXME passing sh*t between threads (P.O.!)
			// _defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5784, new Object[]{});
			boolean x = true;
			final IProgressSink sink = compilationBus.defaultProgressSink();

			while (xy[0] && x) {
				final PW_PushWork poll = wq.poll();

				if (poll != null) {
//                _defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5757, new Object[]{poll.name()});
					poll.execute(this);
				} else {
					sink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5758, new Object[]{poll});

					// README 10/20 fails everything after one failed poll
					// eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
//					x = false;
				}
			}
		});

		this.mm = m;
		this.pp = publisher;
	}

	public void submitWork(final PW_PushWork aInstance) {
		wq.add(aInstance);
	}

	public CP_Paths paths() {
		return compilation._paths();
	}

	final Publisher<PW_PushWork> publisher = new Publisher<>() {
		private Subscriber<? super PW_PushWork> s;

		@Override
		public void subscribe(Subscriber<? super PW_PushWork> subscriber) {
			this.s = subscriber;
		}
	};
}

//
//
//
