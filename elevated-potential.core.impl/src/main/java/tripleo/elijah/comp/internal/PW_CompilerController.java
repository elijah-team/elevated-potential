package tripleo.elijah.comp.internal;

import io.smallrye.mutiny.Multi;
import org.awaitility.core.ConditionTimeoutException;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.util.Ok;

import java.time.Duration;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class PW_CompilerController implements PW_Controller, Runnable {
	private final CompilationImpl        compilation;
	private final PW_PushWorkQueue       wq;
	private final Eventual<Ok>          abusingIt = new Eventual<>();
	private       Eventual<PW_PushWork> epw       = new Eventual<>();
	private       Multi<PW_PushWork>    mm;
	private       Publisher<PW_PushWork> pp;

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
		this.abusingIt.then(ok -> xy[0] = false);

		epw.then(this::aka_handle);

		try {
			await()
					.atMost(5, TimeUnit.SECONDS)
					.until(() -> !xy[0]);
		} catch (ConditionTimeoutException cte) {
			System.err.println("9998-171 cte timeout in PWCC");
		}

		// FIXME 10/18 this is also a steps: A+O
////             FIXME passing sh*t between threads (P.O.!)
/*
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
*/

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

	private void aka_handle(PW_PushWork pw) {
		final CompilationEnclosure compilationEnclosure = compilation.getCompilationEnclosure();
		compilationEnclosure.waitCompilationBus(cb -> {
			final ICompilationBus compilationBus = cb;//r.getCompilationEnclosure().getCompilationBus();

			assert compilationBus != null;

			final IProgressSink sink = compilationBus.defaultProgressSink();
			sink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5758, new Object[]{pw});
		});

		pw.execute(this);
		epw = new Eventual<>();
		epw.then(this::aka_handle);
	}

	public Eventual<Ok> signalEnd() {
		Eventual<Ok> result = new Eventual<>();

		if (this.abusingIt.isPending()) {
			this.abusingIt.resolve(Ok.instance());
		} else {
			//System.err.println("9998-151 double resolve");
			this.abusingIt.then(result::resolve);
			//this.abusingIt.onFail(result::fail); // wtf, man??
		}

		return result;
	}
}
