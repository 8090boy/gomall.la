package com.legendshop.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.legendshop.util.AppUtils;

/**
 * 事件处理相关操作工具类
 */
@SuppressWarnings("rawtypes")
public class EventHome {
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(EventHome.class);

	/** KEY：事件类的ID，值：所有监听此事件的处理类实例 */
	private static Map<String, List<BaseEventListener>> listeners = new HashMap<String, List<BaseEventListener>>();

	private static boolean inited = false;

	/**
	 * 扫瞄所有bean,进行事件监听（业务类按事件类型归类），此方法要在系统启动完后立即调用 此方法大概过程是
	 * 1、从SPRING中找出所实现了BaseEventListener的具体业务类实例
	 * 2、把这些实例归类装进MAP变量listeners中，此MAP变量的结构是：
	 * "UserDeleteEvent.class",{ortherServiceImpl,xxxServiceImpl,...}
	 * "UserUpdateEvent.class",{yyyServiceImpl,zzzServiceImpl,...} key,valueList
	 */
	public synchronized static Map<String, BaseEventListener> initBaseEventListener() {
		if (!inited) {
			inited = true;
		} else {
			return null;
		}
		// 从SPRING中得到实现了某接口的业务类
		Map<String, BaseEventListener> eventListeners = ContextLoader.getCurrentWebApplicationContext().getBeansOfType(
				BaseEventListener.class);
		if (eventListeners == null || eventListeners.size() == 0) {
			log.info("no listener registered");
			return null;
		}
		log.debug("EventHome initing");
		// 下面循环进行归类
		Collection<BaseEventListener> listenerServices = eventListeners.values();

		for (BaseEventListener listener : listenerServices) {
			String eventId = listener.getEventId();
			if (AppUtils.isBlank(eventId)) {
				continue;
			}
			List<BaseEventListener> list = listeners.get(eventId);
			if (list == null) {
				list = new ArrayList<BaseEventListener>();
				listeners.put(eventId, list);
				list.add(listener);
			} else {
				if (list.contains(listener)) {
					continue;
				} else {
					list.add(listener);
				}
			}
		}

		Collection<List<BaseEventListener>> unSortList = listeners.values();
		for (List<BaseEventListener> list2 : unSortList) {
			if (AppUtils.isNotBlank(list2)) {
				// 排序
				Collections.sort(list2, new Comparator<BaseEventListener>() {
					public int compare(BaseEventListener o1, BaseEventListener o2) {
						if (o1 == null || o2 == null || o1.getOrder() == null || o2.getOrder() == null) {
							return -1;
						} else if (o1.getOrder() < o2.getOrder()) {
							return -1;
						} else if (o1.getOrder() == o2.getOrder()) {
							return 0;
						} else {
							return 1;
						}
					}
				});
			}
		}

		for (String eventId : listeners.keySet()) {
			List<BaseEventListener> listener = listeners.get(eventId);
			for (BaseEventListener baseEventListener : listener) {
				log.debug("register event with id eventId {}, baseEventListener {}", eventId, baseEventListener);
			}
		}

		log.debug("EventHome init finish");
		return eventListeners;
	}

	/**
	 * 发布事件的静态方法
	 */
	public static void publishEvent(SystemEvent event) {

		List<BaseEventListener> list = listeners.get(event.getEventId().getEventId());
		if (list != null) {
			for (BaseEventListener listener : list) {
				listener.onEvent(event);
			}
		}
	}
}
