package tripleo.elijah.comp.chewtoy;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.BackPressureStrategy;
import io.smallrye.mutiny.subscription.MultiEmitter;
import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;

import java.util.concurrent.Flow;
import java.util.function.Consumer;

public final class P {
	private static Multi<PW_PushWork>          m;
	static final   Flow.Publisher<PW_PushWork> publisher = new Flow.Publisher<>() {
		private Flow.Subscriber<? super PW_PushWork> s;

		@Override
		public void subscribe(Flow.Subscriber<? super PW_PushWork> subscriber) {
			this.s = subscriber;
		}
	};
	@SuppressWarnings("FieldCanBeLocal")
	private static Multi<PW_PushWork>          mm;
	@SuppressWarnings("FieldCanBeLocal")
	private static Flow.Publisher<PW_PushWork> pp;

	private static String generateItem(final int aI) {
		return "" + aI;
	}

	public static void starting(final PW_Controller aPWController) {
		m = Multi.createFrom().emitter(new Consumer<MultiEmitter<? super PW_PushWork>>() {
			@Override
			public void accept(final MultiEmitter<? super PW_PushWork> emitter) {
				doing(emitter);

				// Once all items are emitted, complete the emitter
				emitter.complete();
			}
		}, BackPressureStrategy.BUFFER);

		m.subscribe().with((PW_PushWork item) -> {

			item.handle(aPWController, null);

			//System.out.println("Received item: " + item);
		});

		m.onFailure().invoke(failureThrowable -> {
			System.err.println("5050 "+failureThrowable);
		});

		mm = m;
		pp = publisher;

	}

	private static void doing(final MultiEmitter<? super PW_PushWork> emitter) {
		for (int i = 0; i < 10; i++) {
			String item0 = generateItem(i); // Replace with your item generation logic
			emitter.emit(new PW_PushWork() {
				private final String item;

				{
					this.item = item0;
				}

				@Override
				public void handle(final PW_Controller pwc, final PW_PushWork otherInstance) {
//					System.err.println("777100 " + item);
				}

				@Override
				public void execute(final PW_Controller aController) {
					System.err.println("777101 " + item);
				}
			});
		}
	}


}
