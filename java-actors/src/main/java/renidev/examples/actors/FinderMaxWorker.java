package renidev.examples.actors;

import java.util.Random;

import akka.actor.UntypedActor;

public class FinderMaxWorker extends UntypedActor {
	
	private String name;
	
	static final int DEFAULT_CHUNK_SIZE = 500000;
	static final int LIMIT_FOR_RANDOM_NUMBERS = 500000000 * 2; 

	public FinderMaxWorker(String name) {
		super();
		this.name = name;
	}

	public void onReceive(Object message) throws Exception {
		switch (((Messages.Message) message).getType()) {
			case CALCULATE_MAX_WORK:
				handleCalculateMaxWork((Messages.CalculateMaxWork)message);
				break;
			default:
				throw new RuntimeException("Unknow message");	
		}
	}

	private void handleCalculateMaxWork(Messages.CalculateMaxWork message) {
		System.out.println("["+name+"]- Chunk #: "+message.chunkNumber);
		int max = 0;
		int numbers[] = new int[DEFAULT_CHUNK_SIZE];
		Random r = new Random();
		
		// Create array
		for (int i = 0; i < DEFAULT_CHUNK_SIZE; i++) {
			numbers[i] = r.nextInt(LIMIT_FOR_RANDOM_NUMBERS); 
		}

		// find max
		for(int i=0; i< DEFAULT_CHUNK_SIZE; i++) {
			if(numbers[i]>max) max = numbers[i];
		}
		
		System.out.println("["+name+"]- Chunk's max: " + max);
		
        // reply with the result
        getContext().replyUnsafe(new Messages.Result(max));
	}
}
