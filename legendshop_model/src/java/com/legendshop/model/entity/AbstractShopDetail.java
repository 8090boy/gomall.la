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
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 商城实体抽象类.
 */
public abstract class AbstractShopDetail extends UploadFile implements BaseEntity{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9153182932362623738L;

	/** The shop id. */
	protected Long shopId;

    /** The user id. */
    protected String userId;

    /** 商城名称. */
    protected String userName;

    /** The siteName. */
    protected String siteName;

    /** 网店地址. */
    protected String shopAddr;

    /** 银行汇款帐号. */
    protected String bankCard;

    /** 收款人姓名. */
    protected String payee;

    /** 邮政编码. */
    protected String code;

    /**  邮寄地址. */
    protected String postAddr;

    /** 邮递接收人. */
    protected String recipient;

    /** The status. */
    protected Integer status;

    /** The visit times. */
    protected Integer visitTimes;
    
    //今天的访问次数
    /** The visit times today. */
    protected Integer visitTimesToday;
    //产品数量
    /** The product num. */
    protected Integer productNum;
    //评论数量
    /** The comm num. */
    protected Integer commNum;
    //下线产品数量
    /** The off product num. */
    protected Integer offProductNum;
    
    /** The modify time. */
    protected Date modifyDate;

    /** The recDate. */
    protected Date recDate;

    /** The brief desc. */
    protected String briefDesc;

    /** The detail desc. */
    protected String detailDesc;

    /** The shop pic. */
    protected String shopPic;
    
    /** The logo pic. */
    protected String logoPic;

    //从UserDetail拷贝过来的属性
    /** The user mail. */
    protected String userMail;

    /** The user adds. */
    protected String userAdds;

    /** The user tel. */
    protected String userTel;

    /** The user postcode. */
    protected String userPostcode;
    
    /** The grade id. */
    protected Integer gradeId;
    
    /** The type. */
    protected Integer type;
    
    /** The id card pic. */
    protected String idCardPic;
    
    /** The traffic pic. */
    protected String trafficPic;
    
	/** The id card pic file. */
	protected MultipartFile idCardPicFile;
	
	/** The traffic pic file. */
	protected MultipartFile trafficPicFile;
    
    
    /** The id card num. */
    protected String idCardNum;
    
    /** The real path. */
    protected String realPath;
    
    /** The create area code. */
    protected String createAreaCode;
    
    /** The create country code. */
    protected String createCountryCode;
    
    /** The ip. */
    protected String ip;
    
    /** The provinceid. */
    protected Integer provinceid;
    
    /** The cityid. */
    protected Integer cityid;
    
    /** The areaid. */
    protected Integer areaid;
    
    //前台模版
    /** The front end templet. */
    protected String frontEndTemplet;
    
    //前台模版
    /** The back end templet. */
    protected String backEndTemplet;
    
    /** 前台语言选项. */
    protected String frontEndLanguage;
    
    /** 前台语言选项. */
    protected String backEndLanguage;

    /** 前台风格. */
    protected String frontEndStyle;
	
	/** 后台风格. */
	protected String backEndStyle;
	
	/** 独立域名. */
	protected String domainName;
	
	/** 备案信息. */
	protected String icpInfo;
	
	/** The qq list. */
	protected List<String> qqList; //copy from userDetail qq
	
	/** 商家的现金. */
	protected Double capital;
	
	/** 商家信誉度. */
	protected Integer credit;

	/**
	 * Gets the shop id.
	 * 
	 * @return the shop id
	 */
	public Long getShopId() {
		return shopId;
	}

	/**
	 * Sets the shop id.
	 * 
	 * @param shopId
	 *            the new shop id
	 */
	public void setShopId(Long shopId) {
		this.shopId = shopId;
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
	 * 商城名称
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
	 * Gets the site name.
	 * 
	 * @return the site name
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * Sets the site name.
	 * 
	 * @param siteName
	 *            the new site name
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * Gets the shop addr.
	 * 
	 * @return the shop addr
	 */
	public String getShopAddr() {
		return shopAddr;
	}

	/**
	 * Sets the shop addr.
	 * 
	 * @param shopAddr
	 *            the new shop addr
	 */
	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}

	/**
	 * Gets the bank card.
	 * 
	 * @return the bank card
	 */
	public String getBankCard() {
		return bankCard;
	}

	/**
	 * Sets the bank card.
	 * 
	 * @param bankCard
	 *            the new bank card
	 */
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	/**
	 * Gets the payee.
	 * 
	 * @return the payee
	 */
	public String getPayee() {
		return payee;
	}

	/**
	 * Sets the payee.
	 * 
	 * @param payee
	 *            the new payee
	 */
	public void setPayee(String payee) {
		this.payee = payee;
	}

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code
	 *            the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the post addr.
	 * 
	 * @return the post addr
	 */
	public String getPostAddr() {
		return postAddr;
	}

	/**
	 * Sets the post addr.
	 * 
	 * @param postAddr
	 *            the new post addr
	 */
	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}

	/**
	 * Gets the recipient.
	 * 
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * Sets the recipient.
	 * 
	 * @param recipient
	 *            the new recipient
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
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
	 * Gets the visit times.
	 * 
	 * @return the visit times
	 */
	public Integer getVisitTimes() {
		return visitTimes;
	}

	/**
	 * Sets the visit times.
	 * 
	 * @param visitTimes
	 *            the new visit times
	 */
	public void setVisitTimes(Integer visitTimes) {
		this.visitTimes = visitTimes;
	}

	/**
	 * Gets the visit times today.
	 * 
	 * @return the visit times today
	 */
	public Integer getVisitTimesToday() {
		return visitTimesToday;
	}

	/**
	 * Sets the visit times today.
	 * 
	 * @param visitTimesToday
	 *            the new visit times today
	 */
	public void setVisitTimesToday(Integer visitTimesToday) {
		this.visitTimesToday = visitTimesToday;
	}

	/**
	 * Gets the product num.
	 * 
	 * @return the product num
	 */
	public Integer getProductNum() {
		return productNum;
	}

	/**
	 * Sets the product num.
	 * 
	 * @param productNum
	 *            the new product num
	 */
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	/**
	 * Gets the comm num.
	 * 
	 * @return the comm num
	 */
	public Integer getCommNum() {
		return commNum;
	}

	/**
	 * Sets the comm num.
	 * 
	 * @param commNum
	 *            the new comm num
	 */
	public void setCommNum(Integer commNum) {
		this.commNum = commNum;
	}

	/**
	 * Gets the off product num.
	 * 
	 * @return the off product num
	 */
	public Integer getOffProductNum() {
		return offProductNum;
	}

	/**
	 * Sets the off product num.
	 * 
	 * @param offProductNum
	 *            the new off product num
	 */
	public void setOffProductNum(Integer offProductNum) {
		this.offProductNum = offProductNum;
	}


	/**
	 * Gets the modify time.
	 * 
	 * @return the modify time
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * Sets the modify time.
	 *
	 * @param modifyTime the new modify date
	 */
	public void setModifyDate(Date modifyTime) {
		this.modifyDate = modifyTime;
	}

	/**
	 * Gets the recDate.
	 * 
	 * @return the recDate
	 */
	public Date getRecDate() {
		return recDate;
	}

	/**
	 * Sets the recDate.
	 * 
	 * @param recDate
	 *            the new recDate
	 */
	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}

	/**
	 * Gets the brief desc.
	 * 
	 * @return the brief desc
	 */
	public String getBriefDesc() {
		return briefDesc;
	}

	/**
	 * Sets the brief desc.
	 * 
	 * @param briefDesc
	 *            the new brief desc
	 */
	public void setBriefDesc(String briefDesc) {
		this.briefDesc = briefDesc;
	}

	/**
	 * Gets the detail desc.
	 * 
	 * @return the detail desc
	 */
	public String getDetailDesc() {
		return detailDesc;
	}

	/**
	 * Sets the detail desc.
	 * 
	 * @param detailDesc
	 *            the new detail desc
	 */
	public void setDetailDesc(String detailDesc) {
		this.detailDesc = detailDesc;
	}

	/**
	 * Gets the shop pic.
	 * 
	 * @return the shop pic
	 */
	public String getShopPic() {
		return shopPic;
	}

	/**
	 * Sets the shop pic.
	 * 
	 * @param shopPic
	 *            the new shop pic
	 */
	public void setShopPic(String shopPic) {
		this.shopPic = shopPic;
	}

	/**
	 * Gets the user mail.
	 * 
	 * @return the user mail
	 */
	public String getUserMail() {
		return userMail;
	}

	/**
	 * Sets the user mail.
	 * 
	 * @param userMail
	 *            the new user mail
	 */
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	/**
	 * Gets the user adds.
	 * 
	 * @return the user adds
	 */
	public String getUserAdds() {
		return userAdds;
	}

	/**
	 * Sets the user adds.
	 * 
	 * @param userAdds
	 *            the new user adds
	 */
	public void setUserAdds(String userAdds) {
		this.userAdds = userAdds;
	}

	/**
	 * Gets the user tel.
	 * 
	 * @return the user tel
	 */
	public String getUserTel() {
		return userTel;
	}

	/**
	 * Sets the user tel.
	 * 
	 * @param userTel
	 *            the new user tel
	 */
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	/**
	 * Gets the user postcode.
	 * 
	 * @return the user postcode
	 */
	public String getUserPostcode() {
		return userPostcode;
	}

	/**
	 * Sets the user postcode.
	 * 
	 * @param userPostcode
	 *            the new user postcode
	 */
	public void setUserPostcode(String userPostcode) {
		this.userPostcode = userPostcode;
	}

	/**
	 * Gets the grade id.
	 * 
	 * @return the grade id
	 */
	public Integer getGradeId() {
		return gradeId;
	}

	/**
	 * Sets the grade id.
	 * 
	 * @param gradeId
	 *            the new grade id
	 */
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the id card pic.
	 * 
	 * @return the id card pic
	 */
	public String getIdCardPic() {
		return idCardPic;
	}

	/**
	 * Sets the id card pic.
	 * 
	 * @param idCardPic
	 *            the new id card pic
	 */
	public void setIdCardPic(String idCardPic) {
		this.idCardPic = idCardPic;
	}

	/**
	 * Gets the traffic pic.
	 * 
	 * @return the traffic pic
	 */
	public String getTrafficPic() {
		return trafficPic;
	}

	/**
	 * Sets the traffic pic.
	 * 
	 * @param trafficPic
	 *            the new traffic pic
	 */
	public void setTrafficPic(String trafficPic) {
		this.trafficPic = trafficPic;
	}

	/**
	 * Gets the id card pic file.
	 * 
	 * @return the id card pic file
	 */
	public MultipartFile getIdCardPicFile() {
		return idCardPicFile;
	}

	/**
	 * Sets the id card pic file.
	 * 
	 * @param idCardPicFile
	 *            the new id card pic file
	 */
	public void setIdCardPicFile(MultipartFile idCardPicFile) {
		this.idCardPicFile = idCardPicFile;
	}

	/**
	 * Gets the traffic pic file.
	 * 
	 * @return the traffic pic file
	 */
	public MultipartFile getTrafficPicFile() {
		return trafficPicFile;
	}

	/**
	 * Sets the traffic pic file.
	 * 
	 * @param trafficPicFile
	 *            the new traffic pic file
	 */
	public void setTrafficPicFile(MultipartFile trafficPicFile) {
		this.trafficPicFile = trafficPicFile;
	}

	/**
	 * Gets the id card num.
	 * 
	 * @return the id card num
	 */
	public String getIdCardNum() {
		return idCardNum;
	}

	/**
	 * Sets the id card num.
	 * 
	 * @param idCardNum
	 *            the new id card num
	 */
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	/**
	 * Gets the real path.
	 * 
	 * @return the real path
	 */
	public String getRealPath() {
		return realPath;
	}

	/**
	 * Sets the real path.
	 * 
	 * @param realPath
	 *            the new real path
	 */
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	/**
	 * Gets the creates the area code.
	 * 
	 * @return the creates the area code
	 */
	public String getCreateAreaCode() {
		return createAreaCode;
	}

	/**
	 * Sets the creates the area code.
	 * 
	 * @param createAreaCode
	 *            the new creates the area code
	 */
	public void setCreateAreaCode(String createAreaCode) {
		this.createAreaCode = createAreaCode;
	}

	/**
	 * Gets the creates the country code.
	 * 
	 * @return the creates the country code
	 */
	public String getCreateCountryCode() {
		return createCountryCode;
	}

	/**
	 * Sets the creates the country code.
	 * 
	 * @param createCountryCode
	 *            the new creates the country code
	 */
	public void setCreateCountryCode(String createCountryCode) {
		this.createCountryCode = createCountryCode;
	}

	/**
	 * Gets the ip.
	 * 
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 * 
	 * @param ip
	 *            the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
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
	 * Gets the front type.
	 * 
	 * @return the front type
	 */
	public String getFrontEndStyle() {
		return frontEndStyle;
	}


	/**
	 * Sets the front end style.
	 *
	 * @param frontEndStyle the new front end style
	 */
	public void setFrontEndStyle(String frontEndStyle) {
		this.frontEndStyle = frontEndStyle;
	}

	/**
	 * Gets the end type.
	 * 
	 * @return the end type
	 */
	public String getBackEndStyle() {
		return backEndStyle;
	}


	/**
	 * Sets the back end style.
	 *
	 * @param backEndStyle the new back end style
	 */
	public void setBackEndStyle(String backEndStyle) {
		this.backEndStyle = backEndStyle;
	}

	/**
	 * Gets the qq list.
	 * 
	 * @return the qq list
	 */
	public List<String> getQqList() {
		return qqList;
	}

	/**
	 * Sets the qq list.
	 * 
	 * @param qqList
	 *            the new qq list
	 */
	public void setQqList(List<String> qqList) {
		this.qqList = qqList;
	}
	

	/* (non-Javadoc)
	 * @see com.legendshop.model.entity.BaseEntity#getId()
	 */
	public Serializable getId() {
		return shopId;
	}

	/**
	 * Gets the domain name.
	 *
	 * @return the domain name
	 */
	public String getDomainName() {
		return domainName;
	}

	/**
	 * Sets the domain name.
	 *
	 * @param domainName the new domain name
	 */
	public void setDomainName(String domainName) {
		if(domainName != null){
			String domain = domainName.trim();
			if(domain.toLowerCase().startsWith("http://")){
				domain = domain.substring(7);
			}
			
			if(domain.startsWith("www.")){
				this.domainName = domain.substring(4).trim();
				return;
			}
			
			this.domainName = domain.trim();
		}
	
	}

	/**
	 * Gets the icp info.
	 *
	 * @return the icp info
	 */
	public String getIcpInfo() {
		return icpInfo;
	}

	/**
	 * Sets the icp info.
	 *
	 * @param icpInfo the new icp info
	 */
	public void setIcpInfo(String icpInfo) {
		this.icpInfo = icpInfo;
	}

	/**
	 * Gets the logo pic.
	 *
	 * @return the logo pic
	 */
	public String getLogoPic() {
		return logoPic;
	}

	/**
	 * Sets the logo pic.
	 *
	 * @param logoPic the new logo pic
	 */
	public void setLogoPic(String logoPic) {
		this.logoPic = logoPic;
	}

	/**
	 * Gets the capital.
	 *
	 * @return the capital
	 */
	public Double getCapital() {
		return capital;
	}

	/**
	 * Sets the capital.
	 *
	 * @param capital the capital to set
	 */
	public void setCapital(Double capital) {
		this.capital = capital;
	}

	/**
	 * Gets the credit.
	 *
	 * @return the credit
	 */
	public Integer getCredit() {
		return credit;
	}

	/**
	 * Sets the credit.
	 *
	 * @param credit the credit to set
	 */
	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	/**
	 * Gets the front end templet.
	 *
	 * @return the frontEndTemplet
	 */
	public String getFrontEndTemplet() {
		return frontEndTemplet;
	}

	/**
	 * Sets the front end templet.
	 *
	 * @param frontEndTemplet the frontEndTemplet to set
	 */
	public void setFrontEndTemplet(String frontEndTemplet) {
		this.frontEndTemplet = frontEndTemplet;
	}

	/**
	 * Gets the back end templet.
	 *
	 * @return the backEndTemplet
	 */
	public String getBackEndTemplet() {
		return backEndTemplet;
	}

	/**
	 * Sets the back end templet.
	 *
	 * @param backEndTemplet the backEndTemplet to set
	 */
	public void setBackEndTemplet(String backEndTemplet) {
		this.backEndTemplet = backEndTemplet;
	}

	/**
	 * Gets the front end language.
	 *
	 * @return the frontEndLanguage
	 */
	public String getFrontEndLanguage() {
		return frontEndLanguage;
	}

	/**
	 * Sets the front end language.
	 *
	 * @param frontEndLanguage the frontEndLanguage to set
	 */
	public void setFrontEndLanguage(String frontEndLanguage) {
		this.frontEndLanguage = frontEndLanguage;
	}

	/**
	 * Gets the back end language.
	 *
	 * @return the backEndLanguage
	 */
	public String getBackEndLanguage() {
		return backEndLanguage;
	}

	/**
	 * Sets the back end language.
	 *
	 * @param backEndLanguage the backEndLanguage to set
	 */
	public void setBackEndLanguage(String backEndLanguage) {
		this.backEndLanguage = backEndLanguage;
	}


}
