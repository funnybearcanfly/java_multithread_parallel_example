package com.funnybear.multithread.diningphilosophers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
	
	public static void main(String[] args) throws InterruptedException {

		ExecutorService service = null;
		Philosopher[] philosophers = null;

		try {

			service = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);

			philosophers = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
			Chopstick[] chopsticks = new Chopstick[Constants.NUMBER_OF_CHOPSTICKS];

			for (int i = 0; i < Constants.NUMBER_OF_CHOPSTICKS; i++) {
				chopsticks[i] = new Chopstick(i);
			}

			for (int i = 0; i < Constants.NUMBER_OF_PHILOSOPHERS; i++) {
				philosophers[i] = new Philosopher(i, chopsticks[i],
						chopsticks[(i + 1) % Constants.NUMBER_OF_CHOPSTICKS]);
				service.execute(philosophers[i]);
			}

			Thread.sleep(Constants.SIMULATION_RUNNING_TIME);

			for (Philosopher p : philosophers) {
				p.setFull(true);
			}

		} finally {

			service.shutdown();

			while (!service.isTerminated()) {
				Thread.sleep(1000);
			}

			for (Philosopher p : philosophers) {
				System.out.println(p + " eats " + p.getEatingCounter());
			}

		}

	}
}
