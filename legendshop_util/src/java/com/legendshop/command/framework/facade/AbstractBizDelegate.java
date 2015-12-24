/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.command.framework.facade;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public abstract class AbstractBizDelegate implements BizDelegate {

	/** The delegate. */
	private DelegateType delegate;

	/**
	 * Sets the delegate.
	 * 
	 * @param delegate
	 *            the new delegate
	 */
	public void setDelegate(DelegateType delegate) {
		this.delegate = delegate;
	}

	/**
	 * Gets the delegate.
	 * 
	 * @return the delegate
	 */
	public DelegateType getDelegate() {
		return delegate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.command.framework.facade.BizDelegate#init(java.lang.String
	 * )
	 */
	public synchronized boolean init(String jndiName) {
		return delegate.init(jndiName);
	}

}
