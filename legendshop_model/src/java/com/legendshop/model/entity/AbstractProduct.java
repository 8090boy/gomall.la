/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.model.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * The Class AbstractProduct.
 */
public abstract class AbstractProduct extends UploadFile implements BaseEntity{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 639772483681855956L;

	/** The prod id. */
	protected Long prodId;
	
	/** The sort id. */
	protected Long sortId;
	
	/** The nsort id. */
	protected Long nsortId;
	
	/** 全局商城一级分类. */
	protected Long globalSort;
	
	/** 全局商城二级分类. */
	protected Long globalNsort;
	
	/** 全局商城三级分类. */
	protected Long globalSubSort;
	
	/** The sub nsort id. */
	protected Long subNsortId;
	
	/** The model id. */
	protected String modelId;
	
	/** The name. */
	protected String name;
	
	/** 产品原价，市场价. */
	protected Double price;
	
	/** 产品现价，客户最终价格. */
	protected Double cash;
	
	/** The proxy price. */
	protected Double proxyPrice;
	
	protected Double carriage;
	
	protected int points;
	
	/** The brief. */
	protected String brief;
	
	/** The content. */
	protected String content;
	
	/** The views. */
	protected Integer views;
	
	/** The buys. */
	protected Integer buys;
	
	/** 评论数. */
	protected Integer comments;
	
	/** The rec date. */
	protected Date recDate;
	
	/** The small pic. */
	protected String smallPic;
	
	/** 是否使用小图片. */
	protected String useSmallPic;
	
	/** The pic. */
	protected String pic;
	
	/** 是否推荐产品. */
	protected String commend;
	
	/** 是否热门产品. */
	protected String hot;
	
	/** The status. */
	protected Integer status;
	
	/** The modify date. */
	protected Date modifyDate;
	
	/** The user id. */
	protected String userId;
	
	/** The user name. */
	protected String userName;
	
	/** The start date. */
	protected Date startDate;
	
	/** The end date. */
	protected Date endDate;
	
	/** The stocks. */
	protected Integer stocks;
	
	/** The prod type. */
	protected String prodType;
	
	/** The key word. */
	protected String keyWord;
	
	/** The attribute. */
	protected String attribute;
	
	/** The parameter. */
	protected String parameter;
	
	/** The brand id. */
	protected Long brandId;
	
	/** 全局商城品牌 **/
	protected Long globalBrand;
	
	/** The actual stocks. */
	protected Integer actualStocks;
	
	
	/** 产品缩略图. */
	protected MultipartFile smallPicFile;
	
	/** The provinceid. */
	protected Integer provinceid;

	/** The cityid. */
	protected Integer cityid;

	/** The areaid. */
	protected Integer areaid;
	
	/** The comment num. 评论数*/
	protected Integer commentNum;


	/**
	 * Gets the prod id.
	 * 
	 * @return the prod id
	 */
	public Long getProdId() {
		return prodId;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * Sets the prod id.
	 * 
	 * @param prodId
	 *            the new prod id
	 */
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	/**
	 * Gets the sort id.
	 * 
	 * @return the sort id
	 */
	public Long getSortId() {
		return sortId;
	}

	/**
	 * Sets the sort id.
	 * 
	 * @param sortId
	 *            the new sort id
	 */
	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

	/**
	 * Gets the nsort id.
	 * 
	 * @return the nsort id
	 */
	public Long getNsortId() {
		return nsortId;
	}

	/**
	 * Sets the nsort id.
	 * 
	 * @param nsortId
	 *            the new nsort id
	 */
	public void setNsortId(Long nsortId) {
		this.nsortId = nsortId;
	}

	/**
	 * Gets the sub nsort id.
	 * 
	 * @return the sub nsort id
	 */
	public Long getSubNsortId() {
		return subNsortId;
	}

	/**
	 * Sets the sub nsort id.
	 * 
	 * @param subNsortId
	 *            the new sub nsort id
	 */
	public void setSubNsortId(Long subNsortId) {
		this.subNsortId = subNsortId;
	}

	/**
	 * Gets the model id.
	 * 
	 * @return the model id
	 */
	public String getModelId() {
		return modelId;
	}

	/**
	 * Sets the model id.
	 * 
	 * @param modelId
	 *            the new model id
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the price.
	 * 
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 * 
	 * @param price
	 *            the new price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Gets the cash.
	 * 
	 * @return the cash
	 */
	public Double getCash() {
		return cash;
	}

	/**
	 * Sets the cash.
	 * 
	 * @param cash
	 *            the new cash
	 */
	public void setCash(Double cash) {
		this.cash = cash;
	}

	/**
	 * Gets the proxy price.
	 * 
	 * @return the proxy price
	 */
	public Double getProxyPrice() {
		return proxyPrice;
	}

	/**
	 * Sets the proxy price.
	 * 
	 * @param proxyPrice
	 *            the new proxy price
	 */
	public void setProxyPrice(Double proxyPrice) {
		this.proxyPrice = proxyPrice;
	}

	/**
	 * Gets the carriage.
	 * 
	 * @return the carriage
	 */
	public Double getCarriage() {
		return carriage;
	}

	/**
	 * Sets the carriage.
	 * 
	 * @param carriage
	 *            the new carriage
	 */
	public void setCarriage(Double carriage) {
		this.carriage = carriage;
	}

	/**
	 * Gets the brief.
	 * 
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}

	/**
	 * Sets the brief.
	 * 
	 * @param brief
	 *            the new brief
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}

	/**
	 * Gets the content.
	 * 
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 * 
	 * @param content
	 *            the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the views.
	 * 
	 * @return the views
	 */
	public Integer getViews() {
		return views;
	}

	/**
	 * Sets the views.
	 * 
	 * @param views
	 *            the new views
	 */
	public void setViews(Integer views) {
		this.views = views;
	}

	/**
	 * Gets the buys.
	 * 
	 * @return the buys
	 */
	public Integer getBuys() {
		return buys;
	}

	/**
	 * Sets the buys.
	 * 
	 * @param buys
	 *            the new buys
	 */
	public void setBuys(Integer buys) {
		this.buys = buys;
	}

	/**
	 * Gets the rec date.
	 * 
	 * @return the rec date
	 */
	public Date getRecDate() {
		return recDate;
	}

	/**
	 * Sets the rec date.
	 * 
	 * @param recDate
	 *            the new rec date
	 */
	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	/**
	 * Gets the pic.
	 * 
	 * @return the pic
	 */
	public String getPic() {
		return pic;
	}

	/**
	 * Sets the pic.
	 * 
	 * @param pic
	 *            the new pic
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}

	/**
	 * Gets the commend.
	 * 
	 * @return the commend
	 */
	public String getCommend() {
		return commend;
	}

	/**
	 * Sets the commend.
	 * 
	 * @param commend
	 *            the new commend
	 */
	public void setCommend(String commend) {
		this.commend = commend;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the modify date.
	 * 
	 * @return the modify date
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * Sets the modify date.
	 * 
	 * @param modifyDate
	 *            the new modify date
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId
	 *            the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the start date.
	 * 
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 * 
	 * @param startDate
	 *            the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 * 
	 * @return the end date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date.
	 * 
	 * @param endDate
	 *            the new end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the stocks.
	 * 
	 * @return the stocks
	 */
	public Integer getStocks() {
		return stocks;
	}

	/**
	 * Sets the stocks.
	 * 
	 * @param stocks
	 *            the new stocks
	 */
	public void setStocks(Integer stocks) {
		this.stocks = stocks;
	}

	/**
	 * Gets the prod type.
	 * 
	 * @return the prod type
	 */
	public String getProdType() {
		return prodType;
	}

	/**
	 * Sets the prod type.
	 * 
	 * @param prodType
	 *            the new prod type
	 */
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	/**
	 * Gets the key word.
	 * 
	 * @return the key word
	 */
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * Sets the key word.
	 * 
	 * @param keyWord
	 *            the new key word
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * Gets the attribute.
	 * 
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * Sets the attribute.
	 * 
	 * @param attribute
	 *            the new attribute
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	/**
	 * Gets the parameter.
	 * 
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * Sets the parameter.
	 * 
	 * @param parameter
	 *            the new parameter
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * Gets the brand id.
	 * 
	 * @return the brand id
	 */
	public Long getBrandId() {
		return brandId;
	}

	/**
	 * Sets the brand id.
	 * 
	 * @param brandId
	 *            the new brand id
	 */
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	/**
	 * Gets the actual stocks.
	 * 
	 * @return the actual stocks
	 */
	public Integer getActualStocks() {
		return actualStocks;
	}

	/**
	 * Sets the actual stocks.
	 * 
	 * @param actualStocks
	 *            the new actual stocks
	 */
	public void setActualStocks(Integer actualStocks) {
		this.actualStocks = actualStocks;
	}
	
	/* (non-Javadoc)
	 * @see com.legendshop.model.entity.BaseEntity#getId()
	 */
	public Serializable getId() {
		return prodId;
	}

	/**
	 * Gets the small pic.
	 *
	 * @return the small pic
	 */
	public String getSmallPic() {
		return smallPic;
	}

	/**
	 * Sets the small pic.
	 *
	 * @param smallPic the new small pic
	 */
	public void setSmallPic(String smallPic) {
		this.smallPic = smallPic;
	}

	/**
	 * Gets the hot.
	 *
	 * @return the hot
	 */
	public String getHot() {
		return hot;
	}

	/**
	 * Sets the hot.
	 *
	 * @param hot the new hot
	 */
	public void setHot(String hot) {
		this.hot = hot;
	}

	/**
	 * Gets the small pic file.
	 *
	 * @return the small pic file
	 */
	public MultipartFile getSmallPicFile() {
		return smallPicFile;
	}

	/**
	 * Sets the small pic file.
	 *
	 * @param smallPicFile the new small pic file
	 */
	public void setSmallPicFile(MultipartFile smallPicFile) {
		this.smallPicFile = smallPicFile;
	}

	/**
	 * Gets the use small pic.
	 *
	 * @return the use small pic
	 */
	public String getUseSmallPic() {
		return useSmallPic;
	}

	/**
	 * Sets the use small pic.
	 *
	 * @param useSmallPic the new use small pic
	 */
	public void setUseSmallPic(String useSmallPic) {
		this.useSmallPic = useSmallPic;
	}

	
	/**
	 * Gets the provinceid.
	 * 
	 * @return the provinceid
	 */
	public Integer getProvinceid() {
		return provinceid;
	}

	/**
	 * Sets the provinceid.
	 * 
	 * @param provinceid
	 *            the new provinceid
	 */
	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	/**
	 * Gets the cityid.
	 * 
	 * @return the cityid
	 */
	public Integer getCityid() {
		return cityid;
	}

	/**
	 * Sets the cityid.
	 * 
	 * @param cityid
	 *            the new cityid
	 */
	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	/**
	 * Gets the areaid.
	 * 
	 * @return the areaid
	 */
	public Integer getAreaid() {
		return areaid;
	}

	/**
	 * Sets the areaid.
	 * 
	 * @param areaid
	 *            the new areaid
	 */
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}

	/**
	 * Gets the comment num.
	 *
	 * @return the comment num
	 */
	public Integer getCommentNum() {
		return commentNum;
	}

	/**
	 * Sets the comment num.
	 *
	 * @param commentNum the new comment num
	 */
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public Integer getComments() {
		return comments;
	}

	/**
	 * Sets the comments.
	 *
	 * @param comments the new comments
	 */
	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Long getGlobalSort() {
		return globalSort;
	}

	public void setGlobalSort(Long globalSort) {
		this.globalSort = globalSort;
	}

	public Long getGlobalNsort() {
		return globalNsort;
	}

	public void setGlobalNsort(Long globalNsort) {
		this.globalNsort = globalNsort;
	}

	public Long getGlobalSubSort() {
		return globalSubSort;
	}

	public void setGlobalSubSort(Long globalSubSort) {
		this.globalSubSort = globalSubSort;
	}

	public Long getGlobalBrand() {
		return globalBrand;
	}

	public void setGlobalBrand(Long globalBrand) {
		this.globalBrand = globalBrand;
	}

	
}
