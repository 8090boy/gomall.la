/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.process.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.core.security.model.UserDetail;
import com.legendshop.event.processor.ThreadProcessor;
import com.legendshop.spi.service.LoginHistoryService;

/**
 * 用户登录操作
 */
public class LoginHistoryProcessor extends ThreadProcessor<UserDetail> {
	private final Logger log = LoggerFactory.getLogger(LoginHistoryProcessor.class);

	private LoginHistoryService loginHistoryService;

	public boolean isSupport(UserDetail userDetail) {
		return true;
	}

	@Override
	public void process(UserDetail userDetail) {
		log.debug("LoginHistoryProcessor calling : " + this);
		loginHistoryService.saveLoginHistory(userDetail.getUsername(), userDetail.getIpAddress());
	}

	public void setLoginHistoryService(LoginHistoryService loginHistoryService) {
		this.loginHistoryService = loginHistoryService;
	}

}
