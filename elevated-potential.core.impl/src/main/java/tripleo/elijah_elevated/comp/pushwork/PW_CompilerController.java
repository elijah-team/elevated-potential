package tripleo.elijah_elevated.comp.pushwork;

import io.smallrye.mutiny.Multi;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.util.Ok;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevateder.comp.i.CompFactory;

import java.time.Duration;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class PW_CompilerController implements PW_Controller, Runnable {
	private final EDL_Compilation  compilation;
	private final PW_PushWorkQueue wq;
	private Multi<PW_PushWork> mm;
	private Publisher<PW_PushWork> pp;
	private final Eventual<Ok> abusingIt = new Eventual<>();

	public PW_CompilerController(final EDL_Compilation aC) {
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
////             FIXME passing sh*t between threads (P.O.!)
		//_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5784, new Object[]{});
		boolean x = true;
		while (xy[0] && x) {
			final PW_PushWork poll = wq.poll();

			if (poll != null) {
//                _defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5757, new Object[]{poll.name()});
				poll.execute(this);
			} else {
				final CompilationEnclosure compilationEnclosure = compilation.getCompilationEnclosure();
				compilationEnclosure.waitCompilationRunner(cr -> {
					final ICompilationBus compilationBus = cr.getCompilationEnclosure().getCompilationBus();

					assert compilationBus != null;

					final IProgressSink sink = compilationBus.defaultProgressSink();
					sink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5758, new Object[]{poll});

					// README 10/20 fails everything after one failed poll
					// eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
					//x = false;
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
					.invoke(i -> System.out.println(i))
				.ifNoItem()
					.after(Duration.ofMillis(10000))
					.recoverWithCompletion();
		
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
