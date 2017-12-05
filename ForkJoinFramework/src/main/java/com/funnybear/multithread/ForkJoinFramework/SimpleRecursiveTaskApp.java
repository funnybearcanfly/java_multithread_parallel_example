package com.funnybear.multithread.ForkJoinFramework;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SimpleRecursiveTaskApp {

	private static class SimpleRecursiveTask extends RecursiveTask<Integer> {

		private int simulatedWork;

		public SimpleRecursiveTask(int simulatedWork) {
			this.simulatedWork = simulatedWork;
		}

		@Override
		protected Integer compute() {
			if (simulatedWork > 100) {

				System.out.println("Parallel execution and split task..." + simulatedWork);

				SimpleRecursiveTask task1 = new SimpleRecursiveTask(simulatedWork / 2);
				SimpleRecursiveTask task2 = new SimpleRecursiveTask(simulatedWork / 2);

				task1.fork();
				task2.fork();

				int solution = 0;
				solution += task1.join();
				solution += task2.join();

				return solution;

			} else {
				System.out.println("No need for parallel execution, sequential algorithem is OK..." + simulatedWork);
				return 2 * simulatedWork;
			}
		}

	}

	public static void main(String[] args) {

		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		SimpleRecursiveTask task = new SimpleRecursiveTask(400);
		System.out.println(pool.invoke(task));

	}
}

