/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

/**
 * The Class BaseProcessor.
 */
public abstract class ThreadProcessor<T> extends AbstractProcessor<T> {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ThreadProcessor.class);
	/** The task executor. */
	private TaskExecutor taskExecutor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.event.processor.Processor#onEvent(java.lang.Object)
	 */
	public void onEvent(T task) {
		log.debug("{} ThreadProcessor execute {}", this, task);
		taskExecutor.execute(new TaskRunner<T>(task, this));

		// Runnable runnable = new TaskRunner(task);
		// Thread thread = new Thread(runnable) ;
		// thread.start();
	}

	/**
	 * Sets the task executor.
	 * 
	 * @param taskExecutor
	 *            the new task executor
	 */
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

}
