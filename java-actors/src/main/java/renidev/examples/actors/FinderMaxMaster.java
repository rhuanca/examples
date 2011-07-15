package renidev.examples.actors;

import static akka.actor.Actors.actorOf;
import static akka.actor.Actors.poisonPill;

import akka.actor.ActorRef;
import akka.actor.Channel;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.japi.Procedure;
import akka.routing.Routing.Broadcast;

public class FinderMaxMaster extends UntypedActor {
	private static int MAX_NUMBER_OF_WORKERS = 10;

	private ActorRef router;
	private int max = 0;
	private int numberOfResult = 0;
	private int rangeSize = 0;

	public FinderMaxMaster() {
		// create the workers
		final ActorRef[] workers = new ActorRef[MAX_NUMBER_OF_WORKERS];
		for (int i = 0; i < MAX_NUMBER_OF_WORKERS; i++) {
			workers[i] = actorOf(FinderMaxWorker.class).start();
		}

		// wrap them with a load-balancing router
		router = actorOf(new UntypedActorFactory() {
			public UntypedActor create() {
				return new FinderMaxRouter(workers);
			}
		}).start();
	}

	public void preStart() {
		become(scatter);
	}

	public void postStop() {
		// send a PoisonPill to all workers telling them to shut down themselves
		router.sendOneWay(new Broadcast(poisonPill()));
		// send a PoisonPill to the router, telling him to shut himself down
		router.sendOneWay(poisonPill());
	}

	/**
	 * To distribute work to workers.
	 */
	private final Procedure<Object> scatter = new Procedure<Object>() {
		public void apply(Object object) {
			System.out.println(getContext().getUuid()  + " started to distribute work.");	
			Messages.CalculateMax message = (Messages.CalculateMax) object;
			rangeSize = message.numbers.length / MAX_NUMBER_OF_WORKERS;
			for (int i = 0; i < MAX_NUMBER_OF_WORKERS; i++) {
				router.sendOneWay(buildCalculateMaxWork(i, rangeSize, message),
						getContext());
			}
			become(gather(getContext().getChannel()));
		}
	};

	/**
	 * To collect all results from workers.
	 * 
	 * @param recipient
	 * @return
	 */
	private Procedure<Object> gather(final Channel<Object> recipient) {
		return new Procedure<Object>() {
			public void apply(Object msg) {
				Messages.Result resultMessage = (Messages.Result) msg;

				// incrementing number of received results.
				numberOfResult++;

				if (resultMessage.value > max) {
					max = resultMessage.value;
				}

				if (numberOfResult == MAX_NUMBER_OF_WORKERS) {
					System.out.println(getContext().getUuid()  + " finalized collecting results.");
					System.out.println("Final Result: "+max);
					recipient.sendOneWay(max);
				}
			}
		};
	}

	private Messages.CalculateMaxWork buildCalculateMaxWork(int i,
			int rangeSize, Messages.CalculateMax message) {
		int startRange = i * rangeSize;
		int endRange = startRange + rangeSize - 1;
		if (i == MAX_NUMBER_OF_WORKERS - 1) {
			endRange = message.numbers.length - 1;
		}

		return new Messages.CalculateMaxWork(message.numbers, 
				startRange, endRange);
	}

	/**
	 * Handles messages that comes from other actors.
	 */
	public void onReceive(Object message) throws Exception {
		throw new IllegalStateException("Should be gather or scatter");
	}

}
