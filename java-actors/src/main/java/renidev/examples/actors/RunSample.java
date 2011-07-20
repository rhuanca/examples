package renidev.examples.actors;

import java.net.URL;

public class RunSample {
	
	private String fileName = "sampledata.txt";
	
	/**
	 * create sample data, call sequential function and call concurrent function.
	 */
	public void run(){
		checkSampleData();
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
		String path = this.getClass().getClassLoader().getResource(fileName).getFile();
		long t1 = System.currentTimeMillis();
		int max = finder.sequentialMax(path);
		long t2 = System.currentTimeMillis();
		return new long[]{max,t2-t1}; 
	}
	
	/**
	 * It calls the concurrent function.
	 */
	private long[] callConcurrentMax(){
		Finder finder = new Finder();
		String path = this.getClass().getClassLoader().getResource(fileName).getFile();
		long t1 = System.currentTimeMillis();
		int max = finder.concurrentMax(path);
		long t2 = System.currentTimeMillis();
		return new long[]{max,t2-t1};
	}


	/**
	 * Checks if sample data file exists. if not, it created a new file and 
	 * returns the max value created within the file.
	 */
	private void checkSampleData() {
		URL resource = this.getClass().getClassLoader().getResource(fileName);
		if(resource==null) {
			System.out.println("Creating sample data");
			String path = this.getClass().getClassLoader().getResource(".")+"/"+fileName;
			int max = Utils.generateFileWithNInts(path.substring(6), 50000000);
			System.out.println("max value in file is:"+max);
		} else {
			System.out.println("Test file already exists.");
		}
	}
	
	
	
	public static void main(String... args) {
		new RunSample().run();
	}
}
