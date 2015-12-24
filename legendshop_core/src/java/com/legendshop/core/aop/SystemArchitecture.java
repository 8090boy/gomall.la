package com.legendshop.core.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * 系统事务配置
 *
 */
@Aspect
public class SystemArchitecture {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SystemArchitecture.class);

	@Pointcut("execution(* com.legendshop.business.dao.*.*(..))")
	public void inDataAccessLayer() {
	}

	@Pointcut("within(com.legendshop.model.entity..*)")
	public void inDomainModel() {
	}

	@Pointcut("execution(* *..service.*.*(..))")
	public void inServiceLayer() {
	}

	// module operations
	// ==================

	@Pointcut("execution(* *..dao.*.*(..))")
	public void daoOperation() {
		log.debug("dao operation");
	}

	@Pointcut("execution(**...service.impl.*.*(..))")
	public void businessService() {
		log.debug("service layer operation");
	}

	@Before("inDataAccessLayer()")
	public void printDaoLayer() {
		log.debug("In DAO layer");
	}

	@Before("inServiceLayer()")
	public void printServiceLayer() {
		log.debug("In Service layer");
	}
	// omitted the domain layer as it is the same

}