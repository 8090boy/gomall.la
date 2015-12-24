/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;



/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class Logo extends UploadFile{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8146764684614656689L;

	/** The banner. */
	private String logoPic;

	/** The user id. */
	private String userId;

	/** The user name. */
	private String userName;

	/**
	 * default constructor.
	 */
	public Logo() {
	}


	public String getId() {
		return userId;
	}

	public String getLogoPic() {
		return logoPic;
	}


	public void setLogoPic(String logoPic) {
		this.logoPic = logoPic;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getUserName() {
		return userName;
	}


	
}