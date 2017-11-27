package com.funnybear.multithread.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreApp {

	private static enum Downloader {
		INSTANT;

		private Semaphore semaphore = new Semaphore(3, true);

		public void downloadData() {
			try {
				semaphore.acquire();
				download();
			} catch (Exception e) {
			} finally {
				semaphore.release();
			}
		}

		private void download() throws InterruptedException {
			System.out.println("Downloading data from the web.");
			Thread.sleep(2000);
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 0; i < 12; i++) {
			executorService.execute(new Runnable() {
				
				@Override
				public void run() {
					Downloader.INSTANT.downloadData();
				}
			});
		}

	}

}