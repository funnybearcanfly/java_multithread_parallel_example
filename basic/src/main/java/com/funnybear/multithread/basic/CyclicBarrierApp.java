package com.funnybear.multithread.basic;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierApp {

	private static class Worker implements Runnable {

		private int id;
		private CyclicBarrier cyclicBarrier;
		private Random random;

		public Worker(int id, CyclicBarrier cyclicBarrier) {
			this.id = id;
			this.cyclicBarrier = cyclicBarrier;
			this.random = new Random();
		}

		@Override
		public void run() {
			doWork();
		}
		
		private void doWork(){
			System.out.println("Thread with id " + this.id + " starts working...");
		
			try {
				Thread.sleep(random.nextInt(3000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Thread with id " + this.id + " finished working...");
			
			try {
				this.cyclicBarrier.await();
				System.out.println("Thread with id " + this.id + " after await...");
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CyclicBarrier barrier = new CyclicBarrier(3, new Runnable(){

			@Override
			public void run() {
				System.out.println("All tasks are finished...");
			}});
		
		for (int i = 0; i < 10; i++) {
			executorService.execute(new Worker(i, barrier));
		}

		executorService.shutdown();
	}

}
