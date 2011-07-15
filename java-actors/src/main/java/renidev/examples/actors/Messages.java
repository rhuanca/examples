package renidev.examples.actors;

/**
 * This class contains the messages that will be used within
 * the actors (Master and Workers).
 * @author rhuanca
 *
 */
public class Messages {
	
	/**
	 * Message Types
	 */
	public enum MESSAGE_TYPES {
		CALCULATE_MAX, 
		CALCULATE_MAX_WORK, 
		RESULT
	}
	
	/**
	 * Main interface for messages.
	 */
	public static interface Message {
		public MESSAGE_TYPES getType();
	}
	
	/**
	 * Main message that goes to the Master Actor
	 * to calculate the max of a big array of numbers;
	 */
	public static class CalculateMax implements Message{
		public final int numbers[];

		public CalculateMax(int[] numbers) {
			this.numbers = numbers;
		}

		public MESSAGE_TYPES getType() {
			return MESSAGE_TYPES.CALCULATE_MAX;
		}
	}
	
	/**
	 * Message that will be delivered to workers
	 * in order to calculate the max of a subdata.
	 */
	public static class CalculateMaxWork implements Message {

		public final int numbers[];
		public final int startRange;
		public final int endRange;
		
		public CalculateMaxWork(int[] numbers, int startRange, int endRange) {
			super();
			this.numbers = numbers;
			this.startRange = startRange;
			this.endRange = endRange;
		}

		public MESSAGE_TYPES getType() {
			return MESSAGE_TYPES.CALCULATE_MAX_WORK;
		}
	}
	
	/**
	 * Message that will be delivered from workers to the main Actor,
	 * that contains the result of the calculation.
	 */
	public static class Result implements Message{
		
		public final int value;
		
		public Result(int value) {
			this.value = value;
		}
		
		public MESSAGE_TYPES getType() {
			return MESSAGE_TYPES.RESULT;
		}
		
	}
}
