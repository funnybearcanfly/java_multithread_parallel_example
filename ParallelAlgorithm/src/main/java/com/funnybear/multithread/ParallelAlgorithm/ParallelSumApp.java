package com.funnybear.multithread.ParallelAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelSumApp {

	private static class ParallelSum {

		private int[] numbers;
		private int numberOfThreads;

		public ParallelSum(int[] numbers, int numberOfThreads) {
			this.numbers = numbers;
			this.numberOfThreads = numberOfThreads;
		}

		public int getSum() {

			ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
			List<Future<Integer>> subSums = new ArrayList<Future<Integer>>();
			int step = numbers.length / numberOfThreads;

			for (int i = 0; i < numberOfThreads; i++) {

				int inner_i = i;
				subSums.add(service.submit(new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {

						int subSum = 0;
						int leftIndex = inner_i * step;
						int rightIndex = numbers.length - 1;
						if (inner_i < numberOfThreads - 1) {
							rightIndex = (inner_i + 1) * step - 1;
						}

						for (int j = leftIndex; j <= rightIndex; j++) {
							subSum += numbers[j];
						}

						System.out.println("Thread" + Thread.currentThread().getId() + " Sum: " + subSum);
						
						return subSum;

					}
				}));

			}

			service.shutdown();
			
			int sum = 0;
			for (Future<Integer> future : subSums) {
				try {
					sum += future.get();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return sum;

		}
	}

	public static void main(String[] args) {
		int[] numbers = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		ParallelSum p = new ParallelSum(numbers, Runtime.getRuntime().availableProcessors());
		int sum = p.getSum();
		System.out.println("Total Sum: " + sum);
	}

}