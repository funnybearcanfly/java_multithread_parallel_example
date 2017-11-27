package com.funnybear.multithread.basic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockApp {

	private static int counter = 0;
	private static Lock lock = new ReentrantLock();

	public static void increment() {

		lock.lock();

		try {
			for (int i = 0; i < 100; i++) {
				counter++;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				increment();
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				increment();
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

		System.out.println(counter);

	}
}
