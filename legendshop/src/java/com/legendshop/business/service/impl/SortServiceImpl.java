/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.legendshop.business.dao.HotsearchDao;
import com.legendshop.core.UserManager;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.exception.ConflictException;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.ProdSearchArgs;
import com.legendshop.model.entity.Brand;
import com.legendshop.model.entity.Hotsearch;
import com.legendshop.model.entity.Nsort;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.Sort;
import com.legendshop.spi.constants.PageADV;
import com.legendshop.spi.dao.NsortDao;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.dao.SortDao;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.SortService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.constant.ProductTypeEnum;

/**
 * 产品分类服务.
 */
public class SortServiceImpl extends BaseServiceImpl implements SortService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SortServiceImpl.class);

	/** The sort dao. */
	protected SortDao sortDao;

	/** The nsort dao. */
	protected NsortDao nsortDao;

	/** The product dao. */
	protected ProductDao productDao;

	/** The hotsearch dao. */
	protected HotsearchDao hotsearchDao;



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.SortService#querySort(java.lang.Long)
	 */
	@Override
	public Sort getSortById(Long id) {
		return sortDao.getSort(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.SortService#deleteSort(java.lang.Long)
	 */
	@Override
	public void deleteSort(Long sortId) {
		List<Product> list = sortDao.getProductBySortId(sortId);
		if (!AppUtils.isBlank(list)) {
			throw new ConflictException("请删除该类型对应的商品");
		}

		List<Nsort> nsortList = sortDao.getNsortBySortId(sortId);
		if (!AppUtils.isBlank(nsortList)) {
			throw new ConflictException("请删除该类型对应的二级商品类型");
		}
		sortDao.deleteSortById(sortId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.SortService#save(com.legendshop.model
	 * .entity.Sort)
	 */
	@Override
	public Long save(Sort sort) {
		if (!AppUtils.isBlank(sort.getSortId())) {
			updateSort(sort);
			return sort.getSortId();
		}
		return sortDao.saveSort(sort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.SortService#updateSort(com.legendshop
	 * .model.entity.Sort)
	 */
	@Override
	public void updateSort(Sort sort) {
		sortDao.updateSort(sort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.SortService#getDataByCriteriaQuery(com
	 * .legendshop.core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getSortList(CriteriaQuery cq) {
		return sortDao.find(cq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.SortService#getSort(java.lang.String,
	 * Boolean)
	 */
	@Override
	public List<Sort> getSort(String shopName, Boolean loadAll) {
		return sortDao.getSort(shopName, ProductTypeEnum.PRODUCT.value(), null, null, loadAll);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.SortService#delete(com.legendshop.model
	 * .entity.Sort)
	 */
	@Override
	public void delete(Sort sort) {
		//检查数据约束
		if (sortDao.hasChildNsort(sort.getSortId())) {
			throw new ConflictException("发现子商品分类，不能删除该商品分类！");
		}
		
		if (sortDao.hasChildProduct(sort.getUserName(),sort.getSortId())) {
			throw new ConflictException("商品分类下有产品, 不能删除该商品分类！");
		}
		
		sortDao.deleteSort(sort);
	}

	/**
	 * Sets the sort dao.
	 * 
	 * @param sortDao
	 *            the new sort dao
	 */
	@Required
	public void setSortDao(SortDao sortDao) {
		this.sortDao = sortDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.SortService#getSort(java.lang.String,
	 * java.lang.String, Boolean)
	 */
	@Override
	public List<Sort> getSort(String name, String sortType, Boolean loadAll) {
		return sortDao.getSort(name, sortType, null, null, loadAll);
	}
	/**
	 * 根据名称和类型找到对应的商品分类
	 * 发布产品是选择分类
	 */
	@Override
	public List<Sort> getSort(String userName, String sortType, String sortName) {
		return sortDao.getSort(userName, sortType, sortName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.spi.service.SortService#getSort(java.lang.String,
	 * java.lang.String, java.lang.Integer, java.lang.Integer, Boolean)
	 */
	@Override
	public List<Sort> getSort(String name, String sortType, Integer headerMenu, Integer navigationMenu, Boolean loadAll) {
		return sortDao.getSort(name, sortType, headerMenu, navigationMenu, loadAll);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.impl.BusinessService#nsort(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String getSecSort(HttpServletRequest request, HttpServletResponse response, Sort sort, Long nsortId, Long subNsortId) {
		String curPageNO = request.getParameter("curPageNO");
		if (curPageNO == null) {
			curPageNO = "1";
		}

		// 一级菜单
		//ThreadLocalContext.setCurrentShopName(request, response, sort.getUserName());
		getAndSetAdvertisement(request, response, sort.getUserName(), PageADV.SORT.name());
		request.setAttribute("sort", sort);
		// 二级菜单
		Nsort nsort = nsortDao.getNsort(nsortId);
		if ((nsort != null) && !AppUtils.isBlank(nsort.getSubSort())) {
			request.setAttribute("hasSubSort", true);
		}
		// 三级菜单
		if (subNsortId != null) {
			Nsort subNsort = nsortDao.getNsort(subNsortId);
			request.setAttribute("subNsort", subNsort);
			if (subNsort != null) {
				request.setAttribute("CurrentSubNsortId", subNsort.getNsortId());
			}
		}

		// 二级菜单列表
		List<Nsort> nsortList = nsortDao.getNsortList(sort.getSortId());
		request.setAttribute("nsort", nsort);

		request.setAttribute("nsortList", nsortDao.getOthorNsort(nsortList));
		request.setAttribute("subNsortList", nsortDao.getOthorSubNsort(nsortId, nsortList));
		if (nsort != null) {
			request.setAttribute("CurrentNsortId", nsort.getNsortId());
		}
		PageSupport ps = productDao.getProdDetail(curPageNO, sort.getSortId(), nsortId, subNsortId, PropertiesUtil.getDefaultShopName().equals(sort.getUserName()));
		ps.savePage(request);

		return PathResolver.getPath(request, response, TilesPage.NSORT);
	}

	/**
	 * Sets the nsort dao.
	 * 
	 * @param nsortDao
	 *            the new nsort dao
	 */
	public void setNsortDao(NsortDao nsortDao) {
		this.nsortDao = nsortDao;
	}

	/**
	 * Sets the product dao.
	 * 
	 * @param productDao
	 *            the new product dao
	 */
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.spi.service.SortService#parseSort(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public String parseSort(HttpServletRequest request, HttpServletResponse response, String curPageNO, Sort sort) {
		if (curPageNO == null) {
			curPageNO = "1";
		}
		getAndSetAdvertisement(request, response, sort.getUserName(), PageADV.SORT.name());
		// 热门搜索
		List<Hotsearch> searchList = hotsearchDao.getHotsearch(sort.getSortId());
		request.setAttribute("hotProdList", searchList);

		List<Nsort> nsortList = nsortDao.getNsortBySortId(sort.getSortId());
		request.setAttribute("sort", sort);
		request.setAttribute("nsortList", nsortList);
		String userName = UserManager.getUserName(request.getSession());

		if (log.isInfoEnabled()) {
			log.info("[{}],{},{},sort", new Object[] { request.getRemoteAddr(), userName == null ? "" : userName,
					ThreadLocalContext.getCurrentShopName(request, response) });
		}
		PageSupport ps = null;
		if(PropertiesUtil.getDefaultShopName().equals(sort.getUserName())){ //默认商城的商品
			 ps = productDao.getGlobalProdDetail(curPageNO, sort.getSortId());
		}else{
			ps = productDao.getProdDetail(curPageNO, sort.getSortId());

		}
		ps.savePage(request);
		return PathResolver.getPath(request, response, TilesPage.PRODUCT_SORT);
	}

	/**
	 * Sets the hotsearch dao.
	 * 
	 * @param hotsearchDao
	 *            the new hotsearch dao
	 */
	public void setHotsearchDao(HotsearchDao hotsearchDao) {
		this.hotsearchDao = hotsearchDao;
	}

	@Override
	public Sort getSortAndBrand(Long sortId) {
		Sort sort = sortDao.getSort(sortId);
		List<Brand> brandList = sortDao.getBrandList(sortId);
		if (AppUtils.isNotBlank(brandList)) {
			sort.setBrandList(brandList);
		}
		return sort;
	}

	@Override
	public String sortList(HttpServletRequest request, HttpServletResponse response, Sort sort) {
		return null;
	}

	@Override
	public String parseSort(HttpServletRequest request, HttpServletResponse response, String curPageNO, Long sortId,
			ProdSearchArgs args) {
		return null;
	}

	@Override
	public String parseSecSort(HttpServletRequest request, HttpServletResponse response, String curPageNO, Long sortId,
			Long nsortId, Long subNsortId, ProdSearchArgs args) {
		return null;
	}

	@Override
	public List<KeyValueEntity> loadSorts(String shopName, String sortType) {
		//return sortDao.loadSorts(shopName,sortType);
		 List<Sort> sortList = sortDao.getSort(shopName, sortType, null, null, true);
		 if(AppUtils.isBlank(sortList)){
			 return null;
		 }
		 List<KeyValueEntity> result = new ArrayList<KeyValueEntity>(sortList.size());
		 for (Sort sort : sortList) {
			 KeyValueEntity entity = new KeyValueEntity();
			 entity.setKey(String.valueOf(sort.getSortId()));
			 entity.setValue(sort.getSortName());
			 result.add(entity);
		}
		return result;
	}

	@Override
	public List<KeyValueEntity> loadNSorts(Long sortId) {
		return nsortDao.loadNSorts(sortId);
	}

	@Override
	public List<KeyValueEntity> loadSubNSorts(Long nsortId) {
		return nsortDao.loadSubNSorts(nsortId);
	}

	@Override
	public List<Nsort> getNsortBySortId(Long sortId) {
		return sortDao.getNsortBySortId(sortId);
	}

	@Override
	public List<Nsort> getNsortBySortId(Long sortId, String nsortName) {
		return sortDao.getNsortBySortId(sortId, nsortName);
	}

	@Override
	public List<Nsort> getSubNsortBySortId(Long nsortId, String nsortName) {
		return nsortDao.getSubNsortBySortId(nsortId, nsortName);
	}



}
