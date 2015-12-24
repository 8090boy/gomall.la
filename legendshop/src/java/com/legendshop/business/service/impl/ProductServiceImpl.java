/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.annotation.Cacheable;

import com.legendshop.business.dao.ImgFileDao;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SimpleHqlQuery;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.core.helper.VisitHistoryHelper;
import com.legendshop.core.model.UserMessages;
import com.legendshop.model.dynamic.Item;
import com.legendshop.model.entity.DynamicTemp;
import com.legendshop.model.entity.ImgFile;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ProductDetail;
import com.legendshop.model.entity.RelProduct;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.dao.ProductDao;
import com.legendshop.spi.dao.ShopDetailDao;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.service.ProductService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.JSONUtil;
import com.legendshop.util.constant.ProductStatusEnum;

/**
 * 产品服务.
 */
public class ProductServiceImpl extends BaseServiceImpl implements ProductService {
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	/** 产品Dao. */
	private ProductDao productDao;

	/** The img file dao. */
	private ImgFileDao imgFileDao;

	/**
	 * Sets the shop detail dao.
	 * 
	 * @param shopDetailDao
	 *            the new shop detail dao
	 */
	@Override
	@Required
	public void setShopDetailDao(ShopDetailDao shopDetailDao) {
		this.shopDetailDao = shopDetailDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductService#findProduct(com.legendshop
	 * .core.dao.support.HqlQuery)
	 */
	@Override
	public PageSupport getProductList(HqlQuery hql) {
		return productDao.find(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductService#findProduct(com.legendshop
	 * .core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getProductList(CriteriaQuery cq) {
		return productDao.find(cq);
	}

	/**
	 * Sets the product dao.
	 * 
	 * @param productDao
	 *            the new product dao
	 */
	@Required
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductService#queryProduct(java.lang
	 * .Long)
	 */
	@Override
	public Product getProductById(Long prodId) {
		if (prodId == null) {
			return null;
		}
		return productDao.getProduct(prodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductService#updateProduct(com.legendshop
	 * .model.entity.Product)
	 */
	@Override
	public void updateProduct(Product product, Product origin) {
		// update
		Date date = new Date();
		origin.setModifyDate(date);
		if (product.getStocks() != null && !product.getStocks().equals(origin.getStocks())) {
			origin.setActualStocks(product.getStocks());
		}
		origin.setName(product.getName());
		origin.setSortId(product.getSortId());
		origin.setSubNsortId(product.getSubNsortId());
		origin.setNsortId(product.getNsortId());
		origin.setModelId(product.getModelId());
		origin.setKeyWord(product.getKeyWord());
		origin.setPrice(product.getPrice());
		origin.setCash(product.getCash());
		origin.setCarriage(product.getCarriage());
		origin.setStocks(product.getStocks());
		origin.setBrandId(product.getBrandId());
		origin.setBrief(product.getBrief());
		origin.setContent(product.getContent());
		origin.setProdType(product.getProdType());
		origin.setStartDate(product.getStartDate());
		origin.setEndDate(product.getEndDate());
		origin.setCommend(product.getCommend());
		origin.setHot(product.getHot());
		origin.setStatus(product.getStatus());
		productDao.updateProduct(origin);
		shopDetailDao.updateShopDetailWhenProductChange(origin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.service.ProductService#saveProduct(com.legendshop
	 * .model.entity.Product)
	 */
	@Override
	public Long saveProduct(Product product, String prodType) {
		Date date = new Date();
		product.setStatus(ProductStatusEnum.PROD_ONLINE.value());
		product.setRecDate(date);
		product.setModifyDate(date);
		product.setViews(0);
		product.setComments(0);
		product.setBuys(0);
		product.setProdType(prodType);
		System.out.println("product.getPoints()===" + product.getPoints() );
		try{
			if( product.getPoints() < 1  ){
				System.out.println("1111" );
				product.setPoints(0);
			}
		}catch (Exception e) {
			System.out.println("22222" );
			product.setPoints(0);
		}
		if (product.getStocks() != null) {
			product.setActualStocks(product.getStocks());
		}
		Long prodId = productDao.saveProduct(product);
		product.setProdId(prodId);
		shopDetailDao.updateShopDetailWhenProductChange(product);
		return prodId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getProductGallery(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Long)
	 */
	@Override
	public String getProductGallery(HttpServletRequest request, HttpServletResponse response, Long prodId) {
		ProductDetail prod = productDao.getProdDetail(prodId);
		if (prod != null) {
			if (!Constants.ONLINE.equals(prod.getStatus())) {
				throw new NotFoundException("Product does not online.");
			}
			request.setAttribute("prod", prod);
			// 查看商品的说明图片
			List<ImgFile> prodPics = imgFileDao.getProductPics(prod.getUserName(), prodId);
			if (AppUtils.isNotBlank(prodPics)) {
				request.setAttribute("prodPics", prodPics);
			}
			return PathResolver.getPath(request, response, FrontPage.PROD_PIC_GALLERY);
		} else {
			UserMessages uem = new UserMessages();
			uem.setTitle(ResourceBundleHelper.getString("product.not.found"));
			uem.setDesc(ResourceBundleHelper.getString("product.status.check"));
			uem.setCode(ErrorCodes.ENTITY_NO_FOUND);
			request.setAttribute(UserMessages.MESSAGE_KEY, uem);
			return PathResolver.getPath(request, response, FrontPage.FAIL);
		}
	}

	// 商品动态属性
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getAttributeprodAttribute(java
	 * .lang.Long)
	 */
	@Override
	public String getAttributeprodAttribute(Long prodId) {
		return productDao.findUniqueBy("select prod.attribute from Product prod where prod.prodId = ?", String.class, prodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getHotSale(java.lang.String)
	 */
	@Override
	public List<Product> getHotSale(String shopName) {
		return productDao.gethotsale(shopName);
	}

	/**
	 * 确保该产品属于指定用户.
	 * 
	 * @param prodId
	 *            the prod id
	 * @param userName
	 *            the user name
	 * @return the prod
	 */
	@Override
	public Product getProd(Long prodId, String userName) {
		return productDao.getProd(prodId, userName);
	}

	/**
	 * 商品动态参数.
	 * 
	 * @param prodId
	 *            the prod id
	 * @return the prod parameter
	 */
	@Override
	public String getProdParameter(Long prodId) {
		return productDao.getProdParameter(prodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#saveDynamicTemp(java.lang.String
	 * , java.lang.String, java.lang.Short, java.lang.String)
	 */
	@Override
	public Long saveDynamicTemp(DynamicTemp dynamicTemp) {
		return productDao.saveDynamicTemp(dynamicTemp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#updateDynamicTemp(java.lang
	 * .Long, java.lang.String, java.lang.Short, java.lang.String)
	 */
	@Override
	public boolean updateDynamicTemp(DynamicTemp dynamicTemp) {
		return productDao.updateDynamicTemp(dynamicTemp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getDynamicTemp(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public DynamicTemp getDynamicTemp(Long tempId) {
		return productDao.getDynamicTemp(tempId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#deleteDynamicTemp(java.lang
	 * .Long, java.lang.String)
	 */
	@Override
	public int deleteDynamicTemp(Long tempId, String userName) {
		if (AppUtils.isNotBlank(userName)) {
			DynamicTemp temp = productDao.getDynamicTemp(tempId);
			if (temp != null) {
				if (userName.equals(temp.getUserName())) {
					productDao.deleteDynamicTemp(temp);
				}
				return temp.getType();
			}
		}
		return 1; // default
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#saveProdItem(java.util.List,
	 * java.util.List, java.lang.Long, java.lang.String)
	 */
	@Override
	public String saveProdItem(List<String> idList, List<String> nameList, Long prodId, String userName) {
		return productDao.saveProdItem(idList, nameList, prodId, userName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#updateProd(com.legendshop.model
	 * .entity.Product)
	 */
	@Override
	public void updateProd(Product product) {
		productDao.updateProduct(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getProdDetail(java.lang.Long)
	 */
	@Override
	public ProductDetail getProdDetail(Long prodId) {
		if (prodId == null) {
			return null;
		}
		return productDao.getProdDetail(prodId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getReleationProd(java.lang.
	 * String, java.lang.Long, int)
	 */
	@Override
	public List<Product> getReleationProd(String shopName, Long prodId, int total) {
		return productDao.getReleationProd(shopName, prodId, total);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getReleationProd(java.lang.
	 * Long, java.lang.String)
	 */
	@Override
	public List<RelProduct> getReleationProd(Long prodId, String userName) {
		return productDao.getReleationProd(prodId, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#updateProdViews(java.lang.Long)
	 */
	@Override
	public void updateProdViews(Long prodId) {
		productDao.updateProdViews(prodId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.legendshop.spi.service.ProductService#getHotOn(java.lang.String,
	 * java.lang.Long)
	 */
	@Override
	public List<Product> getHotOn(String shopName, Long sortId) {
		return productDao.getHotOn(shopName, sortId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getHotViewProd(java.lang.String
	 * , int)
	 */
	@Override
	public List<Product> getHotViewProd(String shopName, int maxNum) {
		return productDao.getHotViewProd(shopName, maxNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getProdDetail(java.lang.String,
	 * java.lang.Long)
	 */
	@Override
	@Cacheable(value = "ProductDetailList", condition = "T(Integer).parseInt(#curPageNO) < 3")
	public PageSupport getProdDetail(String curPageNO, Long sortId) {
		return productDao.getProdDetail(curPageNO, sortId);
	}

	/**
	 * Sets the img file dao.
	 * 
	 * @param imgFileDao
	 *            the new img file dao
	 */
	public void setImgFileDao(ImgFileDao imgFileDao) {
		this.imgFileDao = imgFileDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getHotComment(java.lang.String,
	 * java.lang.Long, int)
	 */
	@Override
	@Cacheable(value = "ProductDetailList")
	public List<Product> getHotComment(String shopName, Long sortId, int maxNum) {
		return productDao.getHotComment(shopName, sortId, maxNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getHotRecommend(java.lang.String
	 * , java.lang.Long, int)
	 */
	@Override
	@Cacheable(value = "ProductDetailList")
	public List<Product> getHotRecommend(String shopName, Long sortId, int maxNum) {
		return productDao.getHotRecommend(shopName, sortId, maxNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getDynamicTemp(com.legendshop
	 * .core.dao.support.CriteriaQuery)
	 */
	@Override
	public PageSupport getDynamicTemp(CriteriaQuery cq) {
		return productDao.getDynamicTemp(cq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getDynamicTemp(com.legendshop
	 * .core.dao.support.SimpleHqlQuery)
	 */
	public PageSupport getDynamicTemp(SimpleHqlQuery hql) {
		return productDao.getDynamicTemp(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getDynamicTemp(java.lang.Integer
	 * , java.lang.String)
	 */
	@Override
	public List<DynamicTemp> getDynamicTemp(Integer type, String userName) {
		return productDao.getDynamicTemp(type, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.spi.service.ProductService#getDynamicTemp(java.lang.Integer
	 * , java.lang.Long, java.lang.String)
	 */
	@Override
	public List<DynamicTemp> getDynamicTemp(Integer type, Long sortId, String userName) {
		return productDao.getDynamicTemp(type, sortId, userName);
	}

	/**
	 * 产品查看历史
	 */
	@Override
	public List<ProductDetail> getVisitedProd(HttpServletRequest request) {
		List<Object> prodIds = VisitHistoryHelper.getVisitedProd(request);
		return productDao.getProdDetail(prodIds);
	}

	/**
	 * 
	 * 得到推荐商品，跟以下因素相关 1. 商品访问历史，访问次数 2. 商品订购记录 3. 商品的收藏 暂时用商品浏览历史代替 TODO
	 */
	@Override
	public List<ProductDetail> getRecommendProd(Long prodId) {
		return getVisitedProd(ThreadLocalContext.getRequest());
	}

	@Override
	public boolean deleteDynamicTemp(DynamicTemp temp) {
		return productDao.deleteDynamicTemp(temp);
	}

	@Override
	public String delete(String loginedUser, Long prodId) {
		try {
			// 检查是否有订单，如果已经订购了就不可以删除
			if (productDao.hasOrder(prodId)) {
				return "该产品已经有订单,不能删除，请将该产品下线";
			}
			return productDao.deleteProd(loginedUser, prodId);
		} catch (Exception e) {
			log.error("delete product fail with id " + prodId, e);
			return Constants.FAIL;
		}
	}

	@Override
	public String saveRelProd(String idJson, String nameJson, Long prodId, String userName) {
		List<String> idList = JSONUtil.getArray(idJson, String.class);
		List<String> nameList = JSONUtil.getArray(nameJson, String.class);
		return productDao.saveProdItem(idList, nameList, prodId, userName);
	}

	@Override
	public List<Item> getUsableProductItemByName(Long prodId, String userName, String prodName) {
		return productDao.getUsableProductItemByName(prodId, userName, prodName);
	}

	@Override
	public List<Item> getUsableProductItem(Long prodId,String userName) {
		return productDao.getUsableProductItem(prodId, userName);
	}

	@Override
	public List<Item> getUsedProductItem(Long prodId, String userName) {
		return productDao.getUsedProd(prodId, userName);
	}

}
