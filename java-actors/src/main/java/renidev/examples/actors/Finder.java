package renidev.examples.actors;

import static akka.actor.Actors.actorOf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import renidev.examples.actors.Messages.Result;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.dispatch.Future;

public class Finder {
	
	static final int DEFAULT_CHUNK_SIZE = 500000;
	
	/**
	 * Function in charge to calculate the max integer value within the given filepath.
	 * @param filepath
	 * @return
	 */
	public int sequentialMax(String filepath) {
		int max = 0;
		int number;
		int numberOfChunks = 0;
		int lastChunkSize = 0;
		
		Scanner scanner;
		try {
			scanner = new Scanner(new File(filepath));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Unable to open file");
		}
		
		while(scanner.hasNextInt()) {
			++numberOfChunks;
			System.out.println("Working Chunk #"+numberOfChunks+".");
			// create chunk
			int numbers[] = new int[DEFAULT_CHUNK_SIZE];
			int chunkSize = DEFAULT_CHUNK_SIZE;
			for (int i = 0; i < DEFAULT_CHUNK_SIZE; i++) {
				if(scanner.hasNextInt()) {
					numbers[i] = scanner.nextInt();
				} else {
					chunkSize = i + 1;
					lastChunkSize = chunkSize;	
					break;
				}
			}
			
			
			// work chunk
			for (int i = 0; i < chunkSize; i++) {
				number = numbers[i];
				if(number>max) 
					max = number; 
			}
			
			System.out.println("Current max is:"+max);
		}
				
		System.out.println("Number of chunks created:"+numberOfChunks);
		System.out.println("Last Chunk Size:"+lastChunkSize);
		return max;
	}
	
	/**
	 * Function in charge to find the max integer value within the given file path, but in concurrent mode using actors.
	 * @param filePath
	 * @return
	 */
	public int concurrentMax(String filePath) {
		int result = 0;
		
		// instantiating master actor.
	    ActorRef master = actorOf(new UntypedActorFactory() {
		  public UntypedActor create() {
		    return new FinderMaxMaster("Master");
		   }
		}).start();
		
	    // send message to calculate max.
	    Future<Integer> future = master.sendRequestReplyFuture(new Messages.CalculateMax(filePath), 1000*60*60, master);
	    future.await();
	    
	    if(future.isCompleted()) {
	    	result = future.result().get();
	    } else {
	    	throw new RuntimeException("Unable to calculate max");
	    }
	    
		return result;
	}
	
}
