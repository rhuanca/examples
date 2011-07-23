package renidev.examples.actors;

import static akka.actor.Actors.actorOf;
import static akka.actor.Actors.poisonPill;

import java.util.Random;
import java.util.Scanner;
import akka.actor.ActorRef;
import akka.actor.Channel;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.japi.Procedure;
import akka.routing.Routing.Broadcast;

public class FinderMaxMaster extends UntypedActor {
	private static int MAX_NUMBER_OF_WORKERS = 4;
	static final int DEFAULT_CHUNK_SIZE = 500000;
	static final int ABC = 500000000 * 2;

	private ActorRef router;
	private String name;
	private int max = 0;
	private int numberOfResult = 0;
	private int numberOfChunks = 0;

	public FinderMaxMaster(String name) {
		this.name = name;

		// create the workers
		final ActorRef[] workers = new ActorRef[MAX_NUMBER_OF_WORKERS];

		for (int i = 0; i < MAX_NUMBER_OF_WORKERS; i++) {
			final String workerName = "Worker #" + i;
			workers[i] = actorOf(new UntypedActorFactory() {
				public UntypedActor create() {
					return new FinderMaxWorker(workerName);
				}
			}).start();
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
			if(object instanceof Messages.CalculateMax) {
				Random r = new Random();
				int limit = 1000;
				int counter = 0;
				while (counter < limit) {
					++numberOfChunks;
					router.sendOneWay(createChunk(numberOfChunks, r),
							getContext());
					++counter;

				}
				System.out.println("[" + name + "] Number of chunks created:"
						+ numberOfChunks);
				become(gather(getContext().getChannel()));
	
			}
			
		}

		private Messages.CalculateMaxWork createChunk(int chunkNumber, Random r) {
			return new Messages.CalculateMaxWork(DEFAULT_CHUNK_SIZE, chunkNumber);
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
					System.out.println("[" + name + "] Current max is:" + max);
				}

				if (numberOfResult == numberOfChunks) {
					recipient.sendOneWay(max);
				}
			}
		};
	}

	/**
	 * Handles messages that comes from other actors.
	 */
	public void onReceive(Object message) throws Exception {
		throw new IllegalStateException("Should be gather or scatter");
	}
}