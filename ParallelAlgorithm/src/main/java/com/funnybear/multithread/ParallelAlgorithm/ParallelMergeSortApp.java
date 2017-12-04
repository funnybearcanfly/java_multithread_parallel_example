package com.funnybear.multithread.ParallelAlgorithm;

import java.util.Random;

public class ParallelMergeSortApp {

	private static class MergeSort {

		public MergeSort(int[] numbers, int numberOfThreads) {
			mergeSort(numbers, 0, numbers.length - 1, numberOfThreads);
		}

		private void mergeSort(int[] numbers, int leftIndex, int rightIndex, int numberOfThreads) {

			if (leftIndex == rightIndex)
				return;

			int middleIndex = (leftIndex + rightIndex) / 2;

			if (numberOfThreads >= 2) {

				Thread t1 = new Thread(new Runnable() {
					@Override
					public void run() {
						mergeSort(numbers, leftIndex, middleIndex, numberOfThreads / 2);
					}
				});

				Thread t2 = new Thread(new Runnable() {
					@Override
					public void run() {
						mergeSort(numbers, middleIndex + 1, rightIndex, numberOfThreads / 2);
					}
				});

				t1.start();
				t2.start();

				try {
					t1.join();
					t2.join();
					sort(numbers, leftIndex, middleIndex, rightIndex);
					printNumbers(numbers);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				mergeSort(numbers, leftIndex, middleIndex, numberOfThreads / 2);
				mergeSort(numbers, middleIndex + 1, rightIndex, numberOfThreads / 2);
				sort(numbers, leftIndex, middleIndex, rightIndex);
				printNumbers(numbers);
			}
		}

		private void sort(int[] numbers, int leftIndex, int middleIndex, int rightIndex) {

			int leftCursor = leftIndex;
			int rightCursor = middleIndex + 1;

			int sortedCursor = 0;
			int[] sortedNumbers = new int[rightIndex - leftIndex + 1];

			while (true) {

				if (leftCursor <= middleIndex && rightCursor <= rightIndex) {
					if (numbers[leftCursor] < numbers[rightCursor]) {
						sortedNumbers[sortedCursor] = numbers[leftCursor];
						leftCursor++;
					} else {
						sortedNumbers[sortedCursor] = numbers[rightCursor];
						rightCursor++;
					}
				} else if (leftCursor <= middleIndex) {
					sortedNumbers[sortedCursor] = numbers[leftCursor];
					leftCursor++;
				} else if (rightCursor <= rightIndex) {
					sortedNumbers[sortedCursor] = numbers[rightCursor];
					rightCursor++;
				} else {
					break;
				}

				sortedCursor++;
			}

			for (int i = leftIndex; i <= rightIndex; i++) {
				numbers[i] = sortedNumbers[i - leftIndex];
			}
		}

		private void printNumbers(int[] numbers) {

			String s = "";

			for (int number : numbers) {
				s += (number + " ");
			}

			System.out.println("Thread" + Thread.currentThread().getId() + ":" + s);

		}
	}

	public static void main(String[] args) {
		int[] numbers = getRandomNumbers(30);
		new MergeSort(numbers, Runtime.getRuntime().availableProcessors());
	}

	private static int[] getRandomNumbers(int count) {

		Random random = new Random();
		int[] numbers = new int[count];

		for (int i = 0; i < count; i++) {
			numbers[i] = random.nextInt(500) - 250;
		}

		return numbers;

	}

}
