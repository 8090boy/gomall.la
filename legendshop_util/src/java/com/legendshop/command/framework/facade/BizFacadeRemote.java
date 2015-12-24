/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.legendshop.command.framework.Request;
import com.legendshop.command.framework.Response;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public interface BizFacadeRemote extends EJBObject {

	/**
	 * An example business method.
	 * 
	 * @param request
	 *            the request
	 * @return the response
	 * @throws RemoteException
	 *             the remote exception
	 */
	public Response execute(Request request) throws RemoteException;

}
