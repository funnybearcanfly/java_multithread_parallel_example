package com.funnybear.multithread.diningphilosophers;

import java.util.Random;

public class Philosopher implements Runnable {

	private int id;
	private Chopstick leftChopstick;
	private Chopstick rightChopstick;
	private Random random;
	private int eatingCounter;
	private volatile boolean isFull = false;

	public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {

		this.id = id;
		this.leftChopstick = leftChopstick;
		this.rightChopstick = rightChopstick;
		this.random = new Random();

	}

	public void run() {

		try {

			while (!isFull) {

				think();

				if (leftChopstick.pickUp(this, States.LEFT)) {
					if (rightChopstick.pickUp(this, States.RIGHT)) {
						eat();
						rightChopstick.putDown(this, States.RIGHT);
					}

					leftChopstick.putDown(this, States.LEFT);
				}

			}

		} catch (Exception e) {
		}

	}

	private void think() throws InterruptedException {
		System.out.println(this + " is thinking...");
		Thread.sleep(random.nextInt(1000));
	}

	private void eat() throws InterruptedException {
		System.out.println(this + " is eating...");
		eatingCounter++;
		Thread.sleep(random.nextInt(1000));
	}

	public int getEatingCounter() {
		return eatingCounter;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	@Override
	public String toString() {
		return "Philosopher" + id;
	}

}
