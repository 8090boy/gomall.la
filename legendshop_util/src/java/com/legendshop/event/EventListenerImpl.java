/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.event.processor.Processor;

/**
 * The Class EventListenerImpl.
 */
public class EventListenerImpl<T> extends AbstractEventListenerImpl<T> {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(EventListenerImpl.class);

	/** The processors. */
	private List<Processor<T>> processors;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.event.BaseEventListener#onEvent(com.legendshop.event.
	 * SystemEvent)
	 */
	public void onEvent(SystemEvent<T> event) {
		if (processors != null) {
			for (Processor<T> processor : processors) {
				if (processor.isSupport(event.getSource())) {
					log.debug("processor {} calling, eventId {}", processor, event.getEventId());
					processor.onEvent(event.getSource());
				}
			}
		}
	}

	/**
	 * Sets the processors.
	 * 
	 * @param processors
	 *            the new processors
	 */
	public void setProcessors(List<Processor<T>> processors) {
		this.processors = processors;
	}
}
