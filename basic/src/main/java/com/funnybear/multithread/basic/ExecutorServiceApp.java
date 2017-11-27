package com.funnybear.multithread.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceApp {

	private static class Worker implements Runnable {

		@Override
		public void run() {

			for (int i = 0; i < 10; i++) {

				System.out.println(i);

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 12; i++) {
			executorService.execute(new Worker());
		}

		executorService.shutdown();
	}
}
