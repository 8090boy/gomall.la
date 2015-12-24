/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.event;

/**
 * 调用事件.
 * 
 * @param <T>
 *            the generic type
 */
public abstract class SystemEvent<T> {

	/** The event id. */
	private final BaseEventId eventId;

	/** The system source. */
	protected T source;

	/**
	 * Instantiates a new base event.
	 * 
	 * @param source
	 *            the source
	 * @param eventId
	 *            the event id
	 */
	public SystemEvent(T source, BaseEventId eventId) {
		if (source == null) {
			throw new IllegalArgumentException("null source");
		}
		this.source = source;
		if (eventId == null) {
			throw new IllegalArgumentException("null eventId");
		}
		this.eventId = eventId;
	}

	public SystemEvent(BaseEventId eventId) {
		if (eventId == null) {
			throw new IllegalArgumentException("null eventId");
		}
		this.eventId = eventId;
	}

	/**
	 * Gets the event id.
	 * 
	 * @return the event id
	 */
	public BaseEventId getEventId() {
		return eventId;
	}

	/**
	 * Gets the source.
	 * 
	 * @return the source
	 */
	public T getSource() {
		return source;
	}

	public void setSource(T source) {
		if (source == null) {
			throw new IllegalArgumentException("null source");
		}
		this.source = source;
	}

}
