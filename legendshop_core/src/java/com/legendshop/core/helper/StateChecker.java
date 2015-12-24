/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.helper;

import javax.servlet.http.HttpServletRequest;

import com.legendshop.command.framework.State;
import com.legendshop.core.exception.InternalException;
import com.legendshop.core.model.UserMessages;

public class StateChecker implements Checker<State> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.helper.Checker#check(java.lang.Object,
	 * javax.servlet.http.HttpServletRequest)
	 */
	public boolean check(State state, HttpServletRequest request) {

		if (!state.isOK()) {
			UserMessages uem = new UserMessages();
			uem.setTitle("系统状态异常");
			if (state.getThrowable() != null) {
				uem.setDesc(state.getThrowable().getMessage());
			}
			request.setAttribute(UserMessages.MESSAGE_KEY, uem);
			throw new InternalException("State Check Fail", state.getErrCode());
		}

		return true;
	}

}
