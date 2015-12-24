package com.legendshop.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.util.StopWatch;

/**
 * 打印方法的运行时间，采用AOP配置
 *
 */
public class SimpleProfiler implements Ordered {
	private final Logger log = LoggerFactory.getLogger(SimpleProfiler.class);

	private int order;

	// allows us to control the ordering of advice
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	// this method is the around advice
	public Object profile(ProceedingJoinPoint call) throws Throwable {
		Object returnValue;
		StopWatch clock = new StopWatch(getClass().getName());
		try {
			clock.start(call.toShortString());
			returnValue = call.proceed();
		} finally {
			clock.stop();
			log.info(clock.prettyPrint());
		}
		return returnValue;
	}
}
