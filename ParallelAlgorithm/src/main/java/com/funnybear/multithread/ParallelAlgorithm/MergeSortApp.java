package com.funnybear.multithread.ParallelAlgorithm;

import java.util.Random;

public class MergeSortApp {

	public static void main(String args[]) {
		int[] numbers = getRandomNumbers(30);
		mergeSort(numbers, 0, numbers.length - 1);
	}

	private static int[] getRandomNumbers(int count) {
		
		Random random = new Random();
		int[] numbers = new int[count];

		for (int i = 0; i < count; i++) {
			numbers[i] = random.nextInt(500) - 250;
		}
		
		return numbers;
	}

	private static void mergeSort(int[] numbers, int leftIndex, int rightIndex) {

		if (leftIndex == rightIndex)
			return;

		int middleIndex = (leftIndex + rightIndex) / 2;

		mergeSort(numbers, leftIndex, middleIndex);
		mergeSort(numbers, middleIndex + 1, rightIndex);
		sort(numbers, leftIndex, middleIndex, rightIndex);

		printNumbers(numbers);
	}

	private static void sort(int[] numbers, int leftIndex, int middleIndex, int rightIndex) {

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

	private static void printNumbers(int[] numbers) {
		for (int number : numbers) {
			System.out.print(number + " ");
		}

		System.out.print('\r');
	}
}