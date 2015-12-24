/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.dynamic;
/**
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
import java.io.Serializable;

/**
 * The Class Model.
 */
public class Model implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2267776997265702880L;
	
	/** The id. */
	private String id;
	
	/** may by select text radio checkbox. */
//	private ModelType type;
	private String type;
	/** The items. */
	private Item[] items;
	
	public Model(){
		
	}

	/**
	 * Gets the items.
	 * 
	 * @return the items
	 */
	public Item[] getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 * 
	 * @param items
	 *            the new items
	 */
	public void setItems(Item[] items) {
		this.items = items;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Model clone() {
		Model model = new Model();
		model.setId(getId());
		model.setItems(getItems());
		model.setType(getType());
		return model;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
