package renidev.examples.actors;

import static java.util.Arrays.asList;
import akka.actor.ActorRef;
import akka.routing.CyclicIterator;
import akka.routing.InfiniteIterator;
import akka.routing.UntypedLoadBalancer;

public class FinderMaxRouter extends UntypedLoadBalancer {
	private final InfiniteIterator<ActorRef> workers;

	public FinderMaxRouter(ActorRef[] workers) {
		this.workers = new CyclicIterator<ActorRef>(asList(workers));
	}
	
	public InfiniteIterator<ActorRef> seq() {
		return workers;
	}

}
