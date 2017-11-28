package com.funnybear.multithread.basic;

public class VolatileApp {

	private static class Worker implements Runnable {

		private volatile boolean isTerminated = false;

		public boolean isTerminated() {
			return isTerminated;
		}

		public void setTerminated(boolean isTerminated) {
			this.isTerminated = isTerminated;
		}

		@Override
		public void run() {
			while (!isTerminated) {
				System.out.println("Hello from worker class...");
				
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		Worker worker = new Worker();
		new Thread(worker).start();
		Thread.sleep(3000);
		
		worker.setTerminated(true);
		System.out.print("Finished...");
	}

}
