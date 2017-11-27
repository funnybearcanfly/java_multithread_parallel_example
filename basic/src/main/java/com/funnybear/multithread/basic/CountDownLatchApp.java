package com.funnybear.multithread.basic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchApp {

	private static class Worker implements Runnable {

		private int id;
		private CountDownLatch countDownLatch;

		public Worker(int id, CountDownLatch countDownLatch) {
			this.id = id;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			doWork();
			countDownLatch.countDown();
		}

		private void doWork() {
			System.out.println("Thread with id " + this.id + " starts working...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		CountDownLatch latch = new CountDownLatch(5);

		for (int i = 0; i < 5; i++) {
			service.execute(new Worker(i, latch));
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("All the preparations are done...");
		service.shutdown();
	}

}
