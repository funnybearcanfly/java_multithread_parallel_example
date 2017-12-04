package com.funnybear.multithread.ForkJoinFramework;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelMaxFindingApp {

	private static class ParallelMaxFinding extends RecursiveTask<Integer> {
		private int[] numbers;
		private int lowIndex;
		private int highIndex;

		public ParallelMaxFinding(int[] numbers, int lowIndex, int highIndex) {
			this.numbers = numbers;
			this.lowIndex = lowIndex;
			this.highIndex = highIndex;
		}

		@Override
		protected Integer compute() {
			if (highIndex - lowIndex < THREASHOLD) {
				return sequantialMaxFind();
			} else {

				int middleIndex = (lowIndex + highIndex) / 2;
				ParallelMaxFinding taskl = new ParallelMaxFinding(numbers, lowIndex, middleIndex);
				ParallelMaxFinding task2 = new ParallelMaxFinding(numbers, middleIndex + 1, highIndex);
				invokeAll(taskl, task2);
				return Math.max(taskl.join(), task2.join());
			}
		}

		private int sequantialMaxFind() {

			int max = numbers[lowIndex];

			for (int i = lowIndex; i < highIndex; i++) {
				if (numbers[i] > max) {
					max = numbers[i];
				}
			}

			System.out.println("Thread" + Thread.currentThread().getId() + " Max: " + max);
			return max;

		}
	}

	private static int THREASHOLD = 0;

	public static void main(String[] args) {

		int[] numbers = getRandomNumbers(10);
		THREASHOLD = numbers.length / Runtime.getRuntime().availableProcessors();

		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		ParallelMaxFinding task = new ParallelMaxFinding(numbers, 0, numbers.length - 1);
		System.out.println(pool.invoke(task));

	}

	private static int[] getRandomNumbers(int count) {

		Random random = new Random();
		int[] numbers = new int[count];
		for (int i = 0; i < count; i++) {
			numbers[i] = random.nextInt(500) - 200;
		}

		return numbers;

	}

}
