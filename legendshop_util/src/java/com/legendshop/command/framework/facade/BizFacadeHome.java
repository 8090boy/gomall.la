/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public interface BizFacadeHome extends javax.ejb.EJBHome {

	/** The Constant COMP_NAME. */
	public static final String COMP_NAME = "java:comp/env/ejb/BizFacade";

	/** The Constant JNDI_NAME. */
	public static final String JNDI_NAME = "BizFacade";

	/**
	 * Creates the.
	 * 
	 * @return the biz facade remote
	 * @throws CreateException
	 *             the create exception
	 * @throws RemoteException
	 *             the remote exception
	 */
	public BizFacadeRemote create() throws CreateException, RemoteException;

}
