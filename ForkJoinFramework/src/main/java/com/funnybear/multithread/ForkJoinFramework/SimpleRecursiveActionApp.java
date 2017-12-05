package com.funnybear.multithread.ForkJoinFramework;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class SimpleRecursiveActionApp {

	private static class SimpleRecursiveAction extends RecursiveAction {

		private int simulatedWork;

		public SimpleRecursiveAction(int simulatedWork) {
			this.simulatedWork = simulatedWork;
		}

		@Override
		protected void compute() {
			if (simulatedWork > 100) {

				System.out.println("Parallel execution and split task..." + simulatedWork);

				SimpleRecursiveAction action1 = new SimpleRecursiveAction(simulatedWork / 2);
				SimpleRecursiveAction action2 = new SimpleRecursiveAction(simulatedWork / 2);

				action1.fork();
				action2.fork();
				
			}
			else
			{
				System.out.println("No need for parallel execution, sequential algorithem is OK..." + simulatedWork);
			}
		}

	}

	
	public static void main(String[] args) {
		
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		SimpleRecursiveAction action = new SimpleRecursiveAction(200);
		pool.invoke(action);
		
	}

}
