import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
	
	public static void main(String... args) {
		// Instantiating countdownlatch class.
		CountDownLatch countDown = new CountDownLatch(1);
		System.out.println("Creating threads...");
		
		// going to create 50 threads.
		for(int i=0;i<50;i++) {
			Worker worker = new Worker(countDown, "Worker #"+i);
			new Thread(worker).start();
		}
		
		// Now start all threads at the same time.
		countDown.countDown();
		
	}
	
	/**
	 * This class is just a simple dummy thread.
	 */
	private static class  Worker implements Runnable {
		CountDownLatch startLatch;
		String name;
		
		public Worker(CountDownLatch startLatch, String name) {
			super();
			this.startLatch = startLatch;
			this.name = name;
		}

		public void run() {
			try {
				// Going to wait until the signal is being fired.
				this.startLatch.await();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println("Running " + name+".");
		}
	}
}
