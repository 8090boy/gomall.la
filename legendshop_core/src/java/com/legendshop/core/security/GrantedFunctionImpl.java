/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.core.security;

/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class GrantedFunctionImpl implements GrantedFunction, Comparable<GrantedFunction> {

	private static final long serialVersionUID = -5538145818148135353L;
	/** The function. */
	private String function;

	/**
	 * Instantiates a new granted function impl.
	 * 
	 * @param function
	 *            the function
	 */
	public GrantedFunctionImpl(String function) {
		super();
		this.function = function;
	}

	/**
	 * Instantiates a new granted function impl.
	 */
	protected GrantedFunctionImpl() {
		throw new IllegalArgumentException("Cannot use default constructor");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.core.security.GrantedFunction#getFunction()
	 */
	public String getFunction() {
		return this.function;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			return obj.equals(this.function);
		}

		if (obj instanceof GrantedFunction) {
			GrantedFunction attr = (GrantedFunction) obj;

			return this.function.equals(attr.getFunction());
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.function.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.function;
	}

	public int compareTo(GrantedFunction function) {
		return function.getFunction().compareTo(this.function);
	}

}
