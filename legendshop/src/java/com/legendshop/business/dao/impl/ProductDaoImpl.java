/**
 *
 * LegendShop 多用户商城系统
 *
 *  版权所有,并保留所有权利。
 *
 */
package com.legendshop.business.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import com.legendshop.business.dao.ImgFileDao;
import com.legendshop.business.dao.ProductCommentDao;
import com.legendshop.core.constant.PageProviderEnum;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.QueryMap;
import com.legendshop.core.dao.support.SimpleHqlQuery;
import com.legendshop.core.helper.FileProcessor;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.RealPathUtil;
import com.legendshop.event.EventHome;
import com.legendshop.model.ProdSearchArgs;
import com.legendshop.model.dynamic.Item;
import com.legendshop.model.dynamic.Model;
import com.legendshop.model.entity.DynamicTemp;
import com.legendshop.model.entity.ImgFile;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.RelProduct;
import com.legendshop.spi.cache.ProductUpdate;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.dao.ShopDetailDao;
import com.legendshop.spi.event.ProductDeleteEvent;
import com.legendshop.spi.event.ProductUpdateEvent;
import com.legendshop.util.AppUtils;
import com.legendshop.util.JSONUtil;
import com.legendshop.util.sql.ConfigCode;

/**
 * 产品Dao.
 */
@SuppressWarnings("unchecked")
public abstract class ProductDaoImpl extends BaseDaoImpl implements ProductDao {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(ProductDaoImpl.class);

	private ImgFileDao imgFileDao;
	
	private ProductCommentDao productCommentDao;
	
	private ShopDetailDao shopDetailDao;
	
	//缩略图列表
	private Map<String, List<Integer>> scaleList;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDao#getCommendProd(java.lang.
	 * String, int)
	 */
	@Override
	@Cacheable(value = "ProductDetailList")
	public List<Product> getCommendProd(final String shopName, final int total) {
		log.debug("getCommendProd, shopName = {}", shopName);
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.getCommend"), 0, total, shopName, date, date);
	}

	@Cacheable(value = "ProductDetailList")
	public List<Product> getGlobalCommendProd(final int total) {
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.getGlobalCommend"), 0, total, date, date);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.dao.ProductDao#getCommendProdBySort(java.lang.String,
	 * java.lang.Long, int)
	 */
	@Cacheable(value = "ProductDetailList")
	public List<Product> getCommendProdBySort(final String shopName, final Long sortId, final int total) {
		log.debug("getCommendProd, shopName = {}, sortId = {}", shopName, sortId);
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.getCommendBySort"), 0, total, sortId, shopName, date, date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDao#getReleationProd(java.lang
	 * .String, java.lang.Long, int)
	 */
	@Override
	@Cacheable(value = "ProductDetailList")
	public List<Product> getReleationProd(final String shopName, final Long prodId, final int total) {
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.getRelationProd"), 0, total, shopName, date, date, prodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDao#getNewestProd(java.lang.String
	 * , int)
	 */
	@Override
	@Cacheable(value = "ProductDetailList")
	public List<Product> getNewestProd(final String shopName, final int total) {
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.getNewestProd"), 0, total, shopName, date, date);
	}
	
	@Cacheable(value = "ProductDetailList")
	public  List<Product> getGlobalNewestProd(final int total){
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.getGlobalNewestProd"), 0, total, date, date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDao#gethotsale(java.lang.String)
	 */
	@Override
	@Cacheable(value = "ProductDetailList")
	public List<Product> gethotsale(final String shopName) {
		if (shopName == null) {
			return null;
		}
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.gethotsale"), 0, 6, shopName, date, date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDao#getProdDetail(com.legendshop
	 * .core.dao.support.CriteriaQuery)
	 */
	@Override
	@Cacheable(value = "ProductDetailList", condition = "T(Integer).parseInt(#curPageNO) < 3")
	public PageSupport getProdDetail(String curPageNO, Long sortId, Long nsortId, Long subNsortId, Boolean isGlobal) {
		// Qbc查找方式
		CriteriaQuery cq = new CriteriaQuery(Product.class, curPageNO, PageProviderEnum.SIMPLE_PAGE_PROVIDER);
		cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.FRONT_PAGE_SIZE, Integer.class));
		cq.addOrder("desc", "buys");
		cq.addOrder("desc", "views");
		cq.addOrder("desc", "modifyDate");
		if(isGlobal){
			cq.eq("globalSort", sortId);
			cq.eq("globalNsort", nsortId);
			cq.eq("globalSubSort", subNsortId);
		}else{
			cq.eq("sortId", sortId);
			cq.eq("nsortId", nsortId);
			cq.eq("subNsortId", subNsortId);
		}
		PageSupport ps = find(cq);
		return ps;
	}

	/**
	 * 找到商品分类下的商品
	 */
	@Override
	public PageSupport getProdDetail(String curPageNO, Long sortId) {
		// HQL查找方式
		HqlQuery hql = new HqlQuery(PropertiesUtil.getObject(SysParameterEnum.FRONT_PAGE_SIZE, Integer.class), curPageNO,
				PageProviderEnum.SIMPLE_PAGE_PROVIDER);
		String QueryNsortCount = ConfigCode.getInstance().getCode("prod.getSortProdCount");
		String QueryNsort = ConfigCode.getInstance().getCode("prod.getSortProd");
		hql.setAllCountString(QueryNsortCount);
		hql.setQueryString(QueryNsort);
		Date date = new Date();
		hql.setParam(new Object[] { sortId, date, date });
		PageSupport ps = find(hql);
		return ps;
	}
	
/**
 * 全局商城分类下的商品
 */
	public  PageSupport getGlobalProdDetail(String curPageNO, Long sortId){
		// HQL查找方式
		HqlQuery hql = new HqlQuery(PropertiesUtil.getObject(SysParameterEnum.FRONT_PAGE_SIZE, Integer.class), curPageNO,
				PageProviderEnum.SIMPLE_PAGE_PROVIDER);
		String QueryNsortCount = ConfigCode.getInstance().getCode("prod.getGlobalSortProdCount");
		String QueryNsort = ConfigCode.getInstance().getCode("prod.getGlobalSortProd");
		hql.setAllCountString(QueryNsortCount);
		hql.setQueryString(QueryNsort);
		Date date = new Date();
		hql.setParam(new Object[] { sortId, date, date });
		PageSupport ps = find(hql);
		return ps;
	}
	

	/**
	 * 有排序功能.
	 * 
	 * @param curPageNO
	 *            the cur page no
	 * @param sortId
	 *            the sort id
	 * @param args
	 *            the args
	 * @return the prod detail
	 */
	public PageSupport getProdDetail(String curPageNO, Long sortId, Long nsortId, Long subNsortId, ProdSearchArgs args) {
		// HQL查找方式
		HqlQuery hql = new HqlQuery(PropertiesUtil.getObject(SysParameterEnum.FRONT_PAGE_SIZE, Integer.class), curPageNO,
				PageProviderEnum.SIMPLE_PAGE_PROVIDER);
		QueryMap map = new QueryMap();
		map.put("sortId", sortId);
		map.put("nsortId", nsortId);
		map.put("subNsortId", subNsortId);
		if (args.isHasProd()) {
			map.put("hasProd", 0); // prod.actualStocks > $hasProd$ hasProd = 0
		}
		if (AppUtils.isNotBlank(args.getOrderBy())) {
			if ("asc".equals(args.getOrderDir())) {
				map.put("orderByAndDir", "order by " + args.getOrderBy() + " asc");
			} else {
				// by default
				map.put("orderByAndDir", "order by " + args.getOrderBy() + " desc");
			}

		} else {
			map.put("orderByAndDir", "order by prod.buys desc ");
		}

		String QueryNsortCount = ConfigCode.getInstance().getCode("prod.getOrderSortProdCount", map);
		String QueryNsort = ConfigCode.getInstance().getCode("prod.getOrderSortProd", map);
		hql.setAllCountString(QueryNsortCount);
		hql.setQueryString(QueryNsort);
		Date date = new Date();
		map.put("startDate", date);
		map.put("endDate", date);
		map.remove("orderByAndDir");
		hql.setParam(map.toArray());
		PageSupport ps = find(hql);
		return ps;
	}

	// 热门商品
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDao#getHotOn(java.lang.String)
	 */
	@Override
	@Cacheable(value = "ProductDetailList")
	public List<Product> getHotOn(String shopName, Long sortId) {
		if (AppUtils.isBlank(sortId)) {
			return Collections.EMPTY_LIST;
		}
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.getHotOnProd"), 0, 21, shopName, sortId, date, date);
	}

	/**
	 * 查看某个商城的热门商品 ->查看整个商城的热门商品
	 * 
	 */
	@Override
	@Cacheable(value = "ProductDetailList")
	public List<Product> getHotViewProd(String shopName, Integer number) {
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.getHotViewProd"), 0, number, date, date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.spi.dao.ProductDao#getHotComment(java.lang.String,
	 * java.lang.Long, int)
	 */
	@Override
	@Cacheable(value = "ProductDetailList")
	public List<Product> getHotComment(String shopName, Long sortId, int maxNum) {
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.getHotComment"), 0, maxNum, shopName, sortId, date, date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDao#queryProduct(java.lang.Long)
	 */
	@Override
	public Product getProduct(Long id) {
		return get(Product.class, id);
	}

	// 更新商品查看数
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDao#updateProdViews(java.lang
	 * .Long)
	 */
	@Override
	public void updateProdViews(Long prodId) {
		exeByHQL("update Product set views = views+1 where prodId = ?", prodId);
	}

	/**
	 *更新产品并发布产品更新事件，通知Lucene等更新Index
	 */
	@Override
	public void updateProduct(Product product) {
		EventHome.publishEvent(new ProductUpdateEvent(product));
		updateOnly(product);
	}
	
	/**
	 * 更新产品并更新缓存
	 */
	@Override
	@ProductUpdate
	public  void updateOnly(Product product){
		update(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.ProductDao#saveProduct(com.legendshop
	 * .model.entity.Product)
	 */
	@Override
	public Long saveProduct(Product product) {
		return (Long) save(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductDao#getProdDetail(com.legendshop.core
	 * .dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getProdDetail(CriteriaQuery cq) {
		return find(cq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductDao#deleteProdById(java.lang.Long)
	 */
	@Override
	public String deleteProd(String loginedUser, Long prodId) {
		if (prodId == null) {
			return "商品ID不能为空";
		}
		// 删除商品
		Product product = getProduct(prodId);
		if (product == null) {
			log.warn("Product with Id {} does not exists ", prodId);
			return "找不到该商品";
		}
		// 检查权限
		if (!product.getUserName().equals(loginedUser)) {
			log.warn("can not delete Id {} does not belongs to you", prodId);
			return "您不是该商品的主人，不能删除该商品";
		}
		
		if (product != null) {
			//删除产品收藏
			exeByHQL("delete from Myfavorite where prodId = ?", prodId);
			
			//删除产品咨询
			exeByHQL("delete from ProductConsult where prodId = ?", prodId);
			
			
			// 删除相关产品
						List<RelProduct> list = getReleationProd(product.getProdId(), product.getUserName());
						if (AppUtils.isNotBlank(list)) {
							deleteAll(list);
						}

						// 删除相关图片
						List<ImgFile> imgFileList = imgFileDao.getAllProductPics(product.getProdId());
						if (AppUtils.isNotBlank(imgFileList)) {
							for (ImgFile imgFile : imgFileList) {
								imgFileDao.deleteImgFile(imgFile);
								String imgFileUrl = RealPathUtil.getBigPicRealPath() + "/" + imgFile.getFilePath();
								// 删除文件
								log.debug("delete Big imgFileUrl file {}", imgFileUrl);
								FileProcessor.deleteFile(imgFileUrl);
								
							}
						}
						
						// 删除商品评论
						productCommentDao.deleteProductComment(product.getProdId(), product.getUserName());
						// 删除全文索引
						shopDetailDao.updateShopDetail(product.getUserName());
						//删除文件，并触发异步事件
						deleteProd(product);
						//删除产品图片
						 deleteProdImgFile(product);

		}
		return  Constants.SUCCESS;
	}
	
	//删除产品图片
	private void deleteProdImgFile(Product product){
		//1.删除产品的主图片
		FileProcessor.deleteFile(RealPathUtil.getBigPicRealPath() + "/" + product.getPic());
		//2.删除产品主图片的缩略图
		for (String sacle : scaleList.keySet()) {
			StringBuilder sb = new StringBuilder(RealPathUtil.getSmallPicRealPath());
			sb.append("/").append(sacle).append("/").append(product.getPic());
			FileProcessor.deleteFile(sb.toString(),false);
		}
		//3.删除产品缩略图
		if(AppUtils.isNotBlank(product.getSmallPic())){
			FileProcessor.deleteFile(RealPathUtil.getBigPicRealPath() + "/" + product.getSmallPic(),false);
		}
	
	}

	/**
	 * 删除文件，并触发异步事件
	 */
	@ProductUpdate
	public void deleteProd(Product product){
		delete(product);
		EventHome.publishEvent(new ProductDeleteEvent(product));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductDao#loadDynamicAttribute(java.lang
	 * .Long, java.lang.String)
	 */
	@Override
	public List<Model> loadDynamicAttribute(Long prodId, String userName) {
		List<Model> list = new ArrayList<Model>();
		Product product = getProd(prodId, userName);
		if (AppUtils.isNotBlank(product) && AppUtils.isNotBlank(product.getAttribute())) {
				list = JSONUtil.getArray(product.getAttribute(), Model.class);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.business.dao.ProductDao#getProd(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public Product getProd(Long prodId, String userName) {
		return findUniqueBy("from Product prod where prod.prodId = ? and prod.userName = ?", Product.class, prodId, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.ProductDao#getDynamicTemp(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public DynamicTemp getDynamicTemp(Long tempId) {
		return get(DynamicTemp.class, tempId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.dao.ProductDao#getDynamicTemp(com.legendshop.core.
	 * dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getDynamicTemp(CriteriaQuery cq) {
		return find(cq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.dao.ProductDao#saveDynamicTemp(com.legendshop.model
	 * .entity.DynamicTemp)
	 */
	@Override
	public Long saveDynamicTemp(DynamicTemp dynamicTemp) {
		// String tempName, String userName, Short type, String content
		if (AppUtils.isBlank(dynamicTemp.getName()) || AppUtils.isBlank(dynamicTemp.getUserName())) {
			return -1l;
		}
		// 检查是否已经存在同名的商品规格
		List<DynamicTemp> temps = findByHQL("from DynamicTemp where type = ? and name = ? and userName = ?", dynamicTemp.getType(),
				dynamicTemp.getName(), dynamicTemp.getUserName());
		if (AppUtils.isNotBlank(temps)) {
			return -1l;
		}
		return (Long) save(dynamicTemp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.dao.ProductDao#updateDynamicTemp(com.legendshop.model
	 * .entity.DynamicTemp)
	 */
	@Override
	public boolean updateDynamicTemp(DynamicTemp dynamicTemp) {
		if (AppUtils.isBlank(dynamicTemp.getId()) || AppUtils.isBlank(dynamicTemp.getUserName())) {
			return false;
		}
		update(dynamicTemp);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.dao.ProductDao#deleteDynamicTemp(com.legendshop.model
	 * .entity.DynamicTemp)
	 */
	@Override
	public boolean deleteDynamicTemp(DynamicTemp temp) {
		if (temp != null) {
			delete(temp);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.spi.dao.ProductDao#saveProdItem(java.util.List,
	 * java.util.List, java.lang.Long, java.lang.String)
	 */
	@Override
	public String saveProdItem(List<String> idList, List<String> nameList, Long prodId, String userName) {
		List<RelProduct> list = findByHQL("from RelProduct n where n.prodId = ? and userName = ?", prodId, userName);
		// delete all
		deleteAll(list);
		if (AppUtils.isNotBlank(idList)) {
			for (int i = 0; i < idList.size(); i++) {
				RelProduct rprod = new RelProduct();
				rprod.setRecDate(new Date());
				rprod.setProdId(prodId);
				rprod.setRelProdId(Long.valueOf(idList.get(i)));
				rprod.setRelProdName(nameList.get(i));
				rprod.setUserName(userName);
				save(rprod);
			}

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.spi.dao.ProductDao#getProdParameter(java.lang.Long)
	 */
	@Override
	public String getProdParameter(Long prodId) {
		return findUniqueBy("select prod.parameter from Product prod where prod.prodId = ?", String.class, prodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.spi.dao.ProductDao#getReleationProd(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public List<RelProduct> getReleationProd(Long prodId, String userName) {
		return findByHQL("from RelProduct n where n.prodId = ? and userName = ?", prodId, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.spi.dao.ProductDao#getHotRecommend(java.lang.String,
	 * java.lang.Long, int)
	 */
	public List<Product> getHotRecommend(String shopName, Long sortId, int maxNum) {
		if (AppUtils.isBlank(sortId)) {
			return Collections.EMPTY_LIST;
		}
		Date date = new Date();
		return findByHQLLimit(ConfigCode.getInstance().getCode("prod.getHotRecommend"), 0, maxNum, shopName, sortId, date, date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.spi.dao.ProductDao#getDynamicTemp(java.lang.Integer,
	 * java.lang.String)
	 */
	public List<DynamicTemp> getDynamicTemp(Integer type, String userName) {
		return findByHQL("from DynamicTemp t where  t.status = 1 and t.type =? and t.userName = ?  ", type, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.spi.dao.ProductDao#getDynamicTemp(java.lang.Integer,
	 * java.lang.Long, java.lang.String)
	 */
	public List<DynamicTemp> getDynamicTemp(Integer type, Long sortId, String userName) {
		return findByHQL("from DynamicTemp t where  t.status = 1 and t.type =?  and t.sortId =? and t.userName = ? ", type, sortId,
				userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.dao.ProductDao#getDynamicTemp(com.legendshop.core.
	 * dao.support.SimpleHqlQuery)
	 */
	public PageSupport getDynamicTemp(SimpleHqlQuery hql) {
		hql.initSQL("biz.QueryDynamicTemp", "biz.QueryDynamicTempCount");
		return find(hql);
	}

	/**
	 * @param imgFileDao the imgFileDao to set
	 */
	public void setImgFileDao(ImgFileDao imgFileDao) {
		this.imgFileDao = imgFileDao;
	}

	/**
	 * @param productCommentDao the productCommentDao to set
	 */
	public void setProductCommentDao(ProductCommentDao productCommentDao) {
		this.productCommentDao = productCommentDao;
	}

	/**
	 * @param shopDetailDao the shopDetailDao to set
	 */
	public void setShopDetailDao(ShopDetailDao shopDetailDao) {
		this.shopDetailDao = shopDetailDao;
	}
	
	@Override
	public  List<Item> getUsableProductItemByName(Long prodId, String userName, String prodName){
		return findByHQLLimit(
				"select new com.legendshop.model.dynamic.Item(b.prodId , b.name) from Product b where name like ? and b.prodId <> ? and b.userName = ? and not exists ( select n.userName from RelProduct n where b.prodId = n.relProdId and  n.prodId = ?)",
				0, 30, prodName, prodId, userName, prodId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BrandDao#getUsableProductItem(java.lang
	 * .Long, java.lang.String)
	 */
	@Override
	public List<Item> getUsableProductItem(Long prodId, String userName) {
		return findByHQLLimit(
				"select new com.legendshop.model.dynamic.Item(b.prodId , b.name) from Product b where  b.prodId <> ? and not exists ( select n.userName from RelProduct n where b.prodId = n.relProdId and  n.prodId = ?  ) and b.userName = ?",
				0, 30, new Object[] {prodId, prodId, userName });
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.BrandDao#getUsedProd(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public List<Item> getUsedProd(Long prodId, String userName) {
		return findByHQLLimit(
				"select new com.legendshop.model.dynamic.Item(b.prodId , b.name) from Product b where b.userName = ? and exists ( select n.userName from RelProduct n where b.prodId = n.relProdId and  n.prodId = ? )",
				0, 30, userName, prodId);
	}

	public void setScaleList(Map<String, List<Integer>> scaleList) {
		this.scaleList = scaleList;
	}

	

}
