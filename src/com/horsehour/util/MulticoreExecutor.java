package com.horsehour.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author haifeng.li
 * @version 1.0
 * @since 11:31:15 PM, Aug 6, 2016
 *
 */
public class MulticoreExecutor {
	private static int nProcessor = -1;

	private static ThreadPoolExecutor exe = null;

	static {
		nProcessor = Runtime.getRuntime().availableProcessors();
		if (nProcessor > 1) {
			exe = (ThreadPoolExecutor) Executors.newFixedThreadPool(nProcessor, new SimpleDeamonThreadFactory());
		}
	}

	/**
	 * @return number of threads in the thread pool (0 & 1 - no thread pool)
	 */
	public static int getThreadPoolSize() {
		return nProcessor;
	}

	/**
	 * Execute tasks serially or parallel based on the number of cores.
	 * 
	 * @param taskList
	 *            the collection of tasks.
	 * @return A list of results in the same sequential order as input
	 * @throws Exception
	 *             if unable to compute a result.
	 */
	public static <T> List<T> run(Collection<? extends Callable<T>> taskList) throws Exception {
		List<T> results = new ArrayList<T>();
		if (exe == null) {
			for (Callable<T> task : taskList) {
				results.add(task.call());
			}
		} else {
			if (exe.getActiveCount() < nProcessor) {
				List<Future<T>> futures = exe.invokeAll(taskList);
				for (Future<T> future : futures) {
					results.add(future.get());
				}
			} else {
				// Thread pool is busy. Just run in the caller's thread.
				for (Callable<T> task : taskList)
					results.add(task.call());
			}
		}

		return results;
	}

	/**
	 * Shutdown the thread pool.
	 */
	public static void shutdown() {
		if (exe != null) {
			exe.shutdown();
		}
	}

	static class SimpleDeamonThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
	}
}
