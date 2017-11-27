package com.funnybear.multithread.basic;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueApp {

	private static class FirstWorker implements Runnable {

		private BlockingQueue<Integer> blockingQueue;

		public FirstWorker(BlockingQueue<Integer> blockingQueue) {
			this.blockingQueue = blockingQueue;
		}

		@Override
		public void run() {
			int counter = 0;

			while (true) {
				try {
					this.blockingQueue.put(counter);
					System.out.println("Putting " + counter + " to the queue...");
					counter++;
					Thread.sleep(300);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static class SecondWorker implements Runnable {

		private BlockingQueue<Integer> blockingQueue;

		public SecondWorker(BlockingQueue<Integer> blockingQueue) {
			this.blockingQueue = blockingQueue;
		}

		@Override
		public void run() {

			while (true) {
				try {
					int counter = this.blockingQueue.take();
					System.out.println("Taking " + counter + " from the queue...");
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
		
		new Thread(new FirstWorker(queue)).start();
		new Thread(new SecondWorker(queue)).start();
	}

}
