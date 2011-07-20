package renidev.examples.actors;

import akka.actor.UntypedActor;

public class FinderMaxWorker extends UntypedActor {
	
	private String name;
	
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
		
		for(int i=0; i<message.chunkSize; i++) {
			if(message.numbers[i]>max) max = message.numbers[i];
		}
		
		System.out.println("["+name+"]- Chunk's max: " + max);
		
        // reply with the result
        getContext().replyUnsafe(new Messages.Result(max));
	}
}
