package com.funnybear.multithread.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFutureApp {

	private static class Processor implements Callable<String> {

		private int id;

		public Processor(int id) {
			this.id = id;
		}

		@Override
		public String call() throws Exception {
			Thread.sleep(1000);
			return "Id: " + id;
		}

	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService service = Executors.newFixedThreadPool(2);
		List<Future<String>> list = new ArrayList<Future<String>>();

		for (int i = 0; i < 5; i++) {
			Future<String> future = service.submit(new Processor(i));
			list.add(future);
		}

		for (Future<String> future : list) {
			System.out.println(future.get());
		}

		service.shutdown();
	}

}
