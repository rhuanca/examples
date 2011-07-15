package renidev.examples.actors;

import akka.actor.UntypedActor;

public class FinderMaxWorker extends UntypedActor{
	
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
		System.out.println(getContext().getUuid()  + " finding max on range: ["+message.startRange + "," + message.endRange+"]");
		int max = 0;
		for(int i=message.startRange; i<=message.endRange; i++) {
			if(message.numbers[i]>max) max = message.numbers[i];
		}
		
        // reply with the result
        getContext().replyUnsafe(new Messages.Result(max));
	}
}
