package com.funnybear.multithread.basic;

import java.util.ArrayList;
import java.util.List;

public class ProducerAndConsumerApp {

	private static class Processor {
		private List<Integer> list = new ArrayList<Integer>();
		private final int LIMIT = 5;
		private final int BOTTOM = 0;
		private final Object lock = new Object();
		private int value = 0;

		public void produce() throws InterruptedException {
			synchronized (lock) {
				while (true) {
					if (list.size() == LIMIT) {
						System.out.println("list is full");
						lock.wait();
					}
					else {
						System.out.println("Adding: " + value);
						list.add(value);
						value++;
						lock.notify();
					}
					
					Thread.sleep(500);
				}
			}
		}

		public void consume() throws InterruptedException {
			synchronized (lock) {
				while (true) {
					if (list.size() == BOTTOM) {
						System.out.println("list is empty");
						lock.wait();
					}
					else {
						System.out.println("Removed: " + list.remove(--value));
						lock.notify();
					}
					
					Thread.sleep(500);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Processor p = new Processor();

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					p.produce();
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
					p.consume();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
	}

}