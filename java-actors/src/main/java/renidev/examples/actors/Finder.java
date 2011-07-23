package renidev.examples.actors;

import static akka.actor.Actors.actorOf;

import java.util.Random;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.dispatch.Future;

public class Finder {
	
	static final int DEFAULT_CHUNK_SIZE = 500000;
	
	/**
	 * Function in charge to calculate the max integer value within a random number of integers.
	 * @return
	 */
	public int sequentialMax() {
		int max = 0;
		int number;
		int numberOfChunks = 0;
		int lastChunkSize = 0;
		
		int limit = 1000;
		int counter = 0;
		Random r = new Random();
		int abc = 500000000 * 2;
		while(counter < limit) {
			++numberOfChunks;
			System.out.println("Working Chunk #"+numberOfChunks+".");
			// create chunk
			int numbers[] = new int[DEFAULT_CHUNK_SIZE];
			int chunkSize = DEFAULT_CHUNK_SIZE;
			for (int i = 0; i < DEFAULT_CHUNK_SIZE; i++) {
				numbers[i] = r.nextInt(abc); 
			}
			
			// work chunk
			for (int i = 0; i < chunkSize; i++) {
				number = numbers[i];
				if(number>max) 
					max = number; 
			}
			++counter;
			System.out.println("Current max is:"+max);
		}
				
		System.out.println("Number of chunks created:"+numberOfChunks);
		System.out.println("Last Chunk Size:"+lastChunkSize);
		return max;
	}
	
	/**
	 * Function in charge to calculate the max integer value within a random number of integers. But in concurrent mode using actors.
	 * @return
	 */
	public int concurrentMax() {
		int result = 0;
		
		// instantiating master actor.
	    ActorRef master = actorOf(new UntypedActorFactory() {
		  public UntypedActor create() {
		    return new FinderMaxMaster("Master");
		   }
		}).start();
		
	    // send message to calculate max.
	    Future<Integer> future = master.sendRequestReplyFuture(new Messages.CalculateMax(), 1000*60*60, master);
	    future.await();
	    
	    if(future.isCompleted()) {
	    	result = future.result().get();
	    } else {
	    	// throw new RuntimeException("Unable to calculate max");
	    }
	    
		return result;
	}
	
}
