package tripleo.elijah.comp.internal;

import java.time.Duration;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

import io.smallrye.mutiny.Multi;
import tripleo.elijah.Eventual;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.nextgen.pw.PW_PushWorkQueue;
import tripleo.elijah.util.Ok;

public class PW_CompilerController implements PW_Controller, Runnable {
	private final CompilationImpl compilation;
	private final PW_PushWorkQueue wq;
	private Multi<PW_PushWork> mm;
	private Publisher<PW_PushWork> pp;
	private final Eventual<Ok> abusingIt = new Eventual<>();

	PW_CompilerController(final CompilationImpl aC) {
		compilation = aC;

		// TODO 10/20 Make a start latch, then overcomplicate (Lifetime erl etc)
		//  for now just return a "startable"
		var x = this;
		var s = new CompFactory.StartableI() {
			@Override
			public void run() {
				x.run();
			}

			@Override
			public boolean isSignalled() {
				return x.abusingIt.isResolved();
			}

			@Override
			public String getThreadName() {
				return "[PW_CompilerController]";
			}
		};
		Startable task = compilation.con().askConcurrent(s);

		wq = compilation.con().createWorkQueue();

		task.start();
	}

	@Override
	public void run() {
		boolean[] xy = {true};
		this.abusingIt .then(ok -> xy[0] = false);

		
		// FIXME 10/18 this is also a steps: A+O
		//   FIXME passing sh*t between threads (P.O.!)
		//      ARRRGH lost the link
		//_defaultProgressSink.note(IProgressSink.Codes.PW_CompilerController__pollProcess, ProgressSinkComponent.PW_CompilerController, 5784, new Object[]{});
		boolean x = true;
		while (xy[0] && x) {
			final PW_PushWork poll = wq.poll();

			if (poll != null) {
//                _defaultProgressSink.note(IProgressSink.Codes.PW_CompilerController__pollProcess, ProgressSinkComponent.PW_CompilerController, 5757, new Object[]{poll.name()});
				poll.execute(this);
			} else {
				final CompilationEnclosure compilationEnclosure = compilation.getCompilationEnclosure();
				compilationEnclosure.waitCompilationRunner(cr -> {
					final ICompilationBus compilationBus = cr.getCompilationEnclosure().getCompilationBus();

					assert compilationBus != null;

					final IProgressSink sink = compilationBus.defaultProgressSink();
					sink.note(IProgressSink.Codes.PW_CompilerController__pollProcess, ProgressSinkComponent.PW_CompilerController, 5758, new Object[]{poll});
				});
			}
		}
		final Publisher<PW_PushWork> publisher = new Publisher<>() {
			private Subscriber<? super PW_PushWork> s;

			@Override
			public void subscribe(Subscriber<? super PW_PushWork> subscriber) {
				this.s = subscriber;
			}
		};
		Multi<PW_PushWork> m = Multi
				.createFrom()
					.publisher(publisher)
				.onItem()
					.invoke(i -> System.out.println("107098 "+i))
				.ifNoItem()
					.after(Duration.ofSeconds(10))
					.recoverWithCompletion();

		var tallguy = m.ifNoItem()
				.after(Duration.ofSeconds(5))
				.failWith(new Exception("nelson"));
		var hiscar = tallguy.collect().first().onItem().invoke((hisactualcar->{
			System.err.println("107107 "+hisactualcar);
		}));
		
		this.mm = m;
		this.pp = publisher;
	}

	public void submitWork(final PW_PushWork aInstance) {
		wq.add(aInstance);
	}

	public CP_Paths paths() {
		return compilation._paths();
	}
}

//
//
//
