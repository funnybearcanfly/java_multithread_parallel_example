package com.funnybear.multithread.basic;

public class WaitAndNotifyApp {

	private static class Product {
		public void produce() throws InterruptedException {
			synchronized (this) {
				System.out.println("in produce method");
				wait();
				System.out.println("out produce method");
			}
		}
		
		public void consume() throws InterruptedException {
			synchronized (this) {
				System.out.println("in consume method");
				notify();
				Thread.sleep(1000);
			}
		}
	}
	
	public static void main(String[] args) {
		Product p = new Product();
		
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