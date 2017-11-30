package com.funnybear.multithread.basic;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueApp {

	private static class DelayedWorker implements Delayed {

		private long duration;
		private String message;

		public long getDuration() {
			return duration;
		}

		public DelayedWorker(long duration, String message) {
			this.duration = duration;
			this.message = message;
		}

		@Override
		public int compareTo(Delayed arg0) {

			if (this.duration < ((DelayedWorker) arg0).getDuration())
				return -1;

			if (this.duration > ((DelayedWorker) arg0).getDuration())
				return 1;

			return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}

		@Override
		public String toString() {
			return message;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<DelayedWorker> queue = new DelayQueue<>();

		queue.put(new DelayedWorker(1000, "This is the first message"));
		queue.put(new DelayedWorker(10000, "This is the second message"));
		queue.put(new DelayedWorker(4000, "This is the third message"));

		while (!queue.isEmpty()) {
			System.out.println(queue.take());
		}
	}

}
