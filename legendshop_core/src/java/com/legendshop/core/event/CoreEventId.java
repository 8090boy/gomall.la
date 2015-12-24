package com.legendshop.core.event;

import com.legendshop.event.BaseEventId;

public enum CoreEventId implements BaseEventId {
	LICENSE_STATUS_CHECK_EVENT("LICENSE_STATUS_CHECK"),

	LICENSE_UPGRADE_CHECK_EVENT("LICENSE_UPGRADE_CHECK"),

	FUNCTION_CHECK_EVENT("FUNCTION_CHECK"),

	// 系统启动
	SYS_INIT_EVENT("SYS_INIT"),

	// 系统关闭
	SYS_DESTORY_EVENT("SYS_DESTORY"),

	/**
	 * log event
	 **/
	FIRE_EVENT("FIRE_EVENT"), ;

	/** The value. */
	private final String value;

	public String getEventId() {
		return this.value;
	}

	private CoreEventId(String value) {
		this.value = value;
	}

	public boolean instance(String name) {
		// TODO Auto-generated method stub
		return false;
	}

}
