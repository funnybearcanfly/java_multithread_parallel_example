package com.funnybear.multithread.basic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumerkWithLockApp {
	
	private static class Processor {
		private final ReentrantLock lock = new ReentrantLock();
		private final Condition condition = lock.newCondition();

		public void produce() throws InterruptedException {
			lock.lock();

			System.out.println("Method produce in.");
			Thread.sleep(200);
			condition.await();
			System.out.println("Method product out.");

			lock.unlock();
		}

		public void consume() throws InterruptedException {
			lock.lock();

			System.out.println("Method consume in.");
			Thread.sleep(200);
			condition.signal();
			System.out.println("Method consume out.");

			lock.unlock();
		}
	}
	
	public static void main(String[] args) {

		Processor processor = new Processor();
		
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					processor.produce();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					processor.consume();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}