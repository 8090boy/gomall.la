/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.group.dao.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.event.EventHome;
import com.legendshop.group.dao.GroupProductDao;
import com.legendshop.model.entity.GroupProduct;
import com.legendshop.model.entity.Product;
import com.legendshop.spi.cache.ProductUpdate;
import com.legendshop.spi.event.ProductSaveEvent;
import com.legendshop.spi.event.ProductUpdateEvent;
import com.legendshop.util.AppUtils;

/**
 * The Class GroupProductDaoImpl.
 */
public class GroupProductDaoImpl extends BaseDaoImpl implements GroupProductDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.group.dao.GroupProductDao#getProductById(java.lang.Long)
	 */

	public Product getProductById(Long prodId) {
		return get(Product.class, prodId);
	}

	public GroupProduct getGroupProductById(Long prodId) {
		return get(GroupProduct.class, prodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.group.dao.GroupProductDao#updateProduct(com.legendshop
	 * .model.entity.GroupProduct)
	 */

	@ProductUpdate
	public void updateProduct(GroupProduct product) {
		EventHome.publishEvent(new ProductUpdateEvent(product.getProduct()));
		update(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.group.dao.GroupProductDao#saveProduct(com.legendshop.model
	 * .entity.GroupProduct)
	 */

	public void saveProduct(GroupProduct product) {
		EventHome.publishEvent(new ProductSaveEvent(product.getProduct()));
		save(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.group.dao.GroupProductDao#getGroupProduct(java.lang.Long)
	 */

	@Cacheable(value = "Product", key = "#prodId")
	public Product getGroupProduct(Long prodId) {
		// 团购产品
		String strHQL = "select p,g from Product p, GroupProduct g where p.prodId = g.prodId and p.prodId = ? and p.prodType = 'G'";
		List<Object[]> list = findByHQL(strHQL, prodId);
		if (AppUtils.isNotBlank(list)) {
			Product p = (Product) list.get(0)[0];
			GroupProduct g = (GroupProduct) list.get(0)[1];
			g.setProduct(p);
			p.setGroupProduct(g);
			return p;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.group.dao.GroupProductDao#deleteProduct(java.lang.Long)
	 */

	public void deleteProduct(Long prodId) {
		GroupProduct product = getGroupProductById(prodId);
		if (product != null) {
			deleteProduct(product);
		}
	}

	@ProductUpdate
	private void deleteProduct(GroupProduct product) {
		delete(product);
	}

}
