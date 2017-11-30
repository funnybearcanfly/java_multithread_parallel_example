package com.funnybear.multithread.diningphilosophers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {

	private int id;
	private Lock lock;

	public Chopstick(int id) {
		this.id = id;
		this.lock = new ReentrantLock();
	}

	public boolean pickUp(Philosopher p, States states) throws InterruptedException {

		if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
			System.out.println(p + " picked up " + states.toString() + " " + this);
			return true;
		}

		return false;

	}

	public void putDown(Philosopher p, States states) {
		lock.unlock();
		System.out.println(p + " put down " + states.toString() + " " + this);
	}

	@Override
	public String toString() {
		return "Chopstick" + id;
	}

}
