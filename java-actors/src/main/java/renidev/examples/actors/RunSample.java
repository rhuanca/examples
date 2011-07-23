package renidev.examples.actors;

public class RunSample {
	
	/**
	 * Calls sequential and concurrent function to find a max.
	 */
	public void run(){
		long[] sequentialResult = callSequentialMax();
		long[] concurrentResult = callConcurrentMax();
		printSummary(sequentialResult, concurrentResult);
	}

	/**
	 * Prints the summary of calculation.
	 */
	private void printSummary(long []sequentialResult, long []concurrentResult){
		System.out.println("   Summary");
		System.out.println("----------");
		System.out.println("   Sequential - Max is: "+sequentialResult[0]);
		System.out.println("   Sequential - Took: "+(sequentialResult[1]/1000)+"secs.");
		System.out.println("");
		System.out.println("   Concurrent - Max is: "+concurrentResult[0]);
		System.out.println("   Concurrent - Took: "+(concurrentResult[1]/1000)+"secs.");
	}

	/**
	 * It calls the sequential function.
	 */
	private long[] callSequentialMax() {
		Finder finder = new Finder();
		long t1 = System.currentTimeMillis();
		int max = finder.sequentialMax();
		long t2 = System.currentTimeMillis();
		return new long[]{max,t2-t1}; 
	}
	
	/**
	 * It calls the concurrent function.
	 */
	private long[] callConcurrentMax(){
		Finder finder = new Finder();
		long t1 = System.currentTimeMillis();
		int max = finder.concurrentMax();
		long t2 = System.currentTimeMillis();
		return new long[]{max,t2-t1};
	}
	
	public static void main(String... args) {
		new RunSample().run();
	}
}
