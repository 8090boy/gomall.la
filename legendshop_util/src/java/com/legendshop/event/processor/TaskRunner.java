/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event.processor;

/**
 * The Class TaskRunner.
 * 
 * @param <T>
 *            the generic type
 */
public class TaskRunner<T> implements Runnable {

	/** The task. */
	T task;

	AbstractProcessor<T> processor;

	/**
	 * Instantiates a new task runner.
	 * 
	 * @param task
	 *            the task
	 */
	public TaskRunner(T task, AbstractProcessor<T> processor) {
		this.task = task;
		this.processor = processor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		processor.process(task);
	}

}
