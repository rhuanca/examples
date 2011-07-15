package renidev.examples.actors;

import static akka.actor.Actors.actorOf;

import akka.actor.ActorRef;
import akka.dispatch.Future;
import akka.japi.Option;

public class Finder {
	
	public int sequencialMax(int numbers[]) {
		int max = 0;
		for(int i = 0;i<numbers.length; i++)
			if(numbers[i] > max)
				max = numbers[i];
		return max;
	}
	
	public int concurrentMax(int numbers[]) {
		int result = 0;
	    ActorRef master = actorOf(FinderMaxMaster.class).start();
	    Future future = (Future)master.sendRequestReplyFuture(new Messages.CalculateMax(numbers));
	    future.await();
	    
	    if(future.isCompleted()) {
	    	result = (Integer)future.result().get();
	    } else {
	    	throw new RuntimeException("Unable to calculate max");
	    }
		return result;
	}
}
