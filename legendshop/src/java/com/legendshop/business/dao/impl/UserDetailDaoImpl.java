/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.oro.text.regex.MalformedPatternException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.legendshop.business.dao.BasketDao;
import com.legendshop.business.dao.DeliveryCorpDao;
import com.legendshop.business.dao.DeliveryTypeDao;
import com.legendshop.business.dao.SubDao;
import com.legendshop.business.dao.UserDetailDao;
import com.legendshop.core.OperationTypeEnum;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.core.dao.jdbc.BaseJdbcDao;
import com.legendshop.core.dao.support.HqlQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.dao.support.SqlQuery;
import com.legendshop.core.event.FireEvent;
import com.legendshop.core.helper.FileProcessor;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.RealPathUtil;
import com.legendshop.core.randing.RandomStringUtils;
import com.legendshop.event.EventHome;
import com.legendshop.model.entity.ShopDetail;
import com.legendshop.model.entity.User;
import com.legendshop.model.entity.UserDetail;
import com.legendshop.model.entity.UserRole;
import com.legendshop.spi.constants.RegisterEnum;
import com.legendshop.spi.constants.RoleEnum;
import com.legendshop.spi.event.SendMailEvent;
import com.legendshop.spi.service.CommonUtil;
import com.legendshop.util.AppUtils;
import com.legendshop.util.MD5Util;
import com.legendshop.util.constant.ShopStatusEnum;
import com.legendshop.util.ip.IPSeeker;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * 
 * 官方网站：http://www.legendesign.net
 */
public class UserDetailDaoImpl extends BaseDaoImpl implements UserDetailDao {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(UserDetailDaoImpl.class);

	/** The common util. */
	private CommonUtil commonUtil;

	private BaseJdbcDao baseJdbcDao;
	
	private SubDao subDao;
	
	//购物车Dao
	private BasketDao basketDao;
	
	private DeliveryTypeDao deliveryTypeDao;
	
	private DeliveryCorpDao deliveryCorpDao;

	/** The REGISTE d_ tag. */
	private final String REGISTED_TAG = "REGISTED";
	
	//缩略图列表
	private Map<String, List<Integer>> scaleList;

	/**
	 * 普通用户注册
	 * 
	 */
	@Override
	public String saveUser(User user, UserDetail userDetail, ShopDetail shopDetail, boolean isOpenShop) {
		// 1如果需要邮箱验证，则使客户失效先
		if (PropertiesUtil.getObject(SysParameterEnum.VALIDATION_FROM_MAIL, Boolean.class)) {
			user.setEnabled("0");// 不可用
			userDetail.setRegisterCode(MD5Util.Md5Password(user.getName(), user.getPassword())); // 将RegisterCode发送到邮件，点击开通用户
		}
		String userId = (String) save(user);
		userDetail.setUserId(userId);
		userDetail.setUserName(user.getName());
		//2保存用户详细信息和商家信息
		saveUerDetail(userDetail, shopDetail, isOpenShop);
		return userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#saveUerDetail(com.legendshop
	 * .model.entity.UserDetail, com.legendshop.model.entity.ShopDetail,
	 * boolean)
	 */
	@Override
	public void saveUerDetail(UserDetail userDetail, ShopDetail shopDetail, boolean isOpenShop) {
		if (isOpenShop) {
			saveShopDetailAndRole(userDetail, shopDetail);
		} else {
			// 保存用户角色
			commonUtil.saveUserRight(userDetail.getUserId());
		}
		save(userDetail);
	}

	// 返回商城开通状态
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#saveShopDetailAndRole(
	 * com.legendshop.model.entity.UserDetail,
	 * com.legendshop.model.entity.ShopDetail)
	 */
	@Override
	public Integer saveShopDetailAndRole(UserDetail userDetail, ShopDetail shopDetail) {
		// 保存管理员角色
		return saveShopDetail(userDetail, shopDetail);
	}

	@Override
	public void updateShopDetail(UserDetail userDetail, ShopDetail shopDetail, boolean isAdmin) {
		if (isAdmin) {
			saveShopDetail(userDetail, shopDetail);
		}
	}

	// UserId 一定不能为空，返回当前商城开通状态
	@Override
	public Integer saveShopDetail(UserDetail userDetail, ShopDetail shopDetail) {
		Date date = new Date();
		shopDetail.setUserName(userDetail.getUserName());
		shopDetail.setShopAddr(userDetail.getUserAdds());
		shopDetail.setModifyDate(date);
		shopDetail.setUserId(userDetail.getUserId());
		shopDetail.setRecDate(date);
		shopDetail.setVisitTimes(0);
		shopDetail.setOffProductNum(0);
		shopDetail.setProductNum(0);
		shopDetail.setCommNum(0);
		shopDetail.setCapital(0d);
		shopDetail.setCredit(0);
		if (PropertiesUtil.getObject(SysParameterEnum.VALIDATION_ON_OPEN_SHOP, Boolean.class)) {
			shopDetail.setStatus(ShopStatusEnum.AUDITING.value());
			commonUtil.saveUserRight(userDetail.getUserId());
		} else {
			shopDetail.setStatus(ShopStatusEnum.ONLINE.value());
			commonUtil.saveAdminRight(userDetail.getUserId());
		}

		// 得到创建时的国家和地区
		if (shopDetail.getIp() != null) {
			shopDetail.setCreateAreaCode(IPSeeker.getInstance().getArea(shopDetail.getIp()));
			shopDetail.setCreateCountryCode(IPSeeker.getInstance().getCountry(shopDetail.getIp()));
		}
		String userName = userDetail.getUserName();
		String subPath = userName + "/shop/";
		String filename = null;
		// 取得上传的文件
		MultipartFile idCardPicFile = shopDetail.getIdCardPicFile();
		MultipartFile trafficPicFile = shopDetail.getTrafficPicFile();

		if ((idCardPicFile != null) && (idCardPicFile.getSize() > 0)) {
			filename = FileProcessor.uploadFileAndCallback(idCardPicFile, subPath, "");
			log.info("{} 上传身份证照片文件 {} ", userDetail.getUserName(), filename);
			shopDetail.setIdCardPic(filename);

		}

		if ((trafficPicFile != null) && (trafficPicFile.getSize() > 0)) {
			filename = FileProcessor.uploadFileAndCallback(trafficPicFile, subPath, "");
			log.info("{} 营业执照照片文件 {} ", userDetail.getUserName(), filename);
			shopDetail.setTrafficPic(filename);
		}
		this.save(shopDetail);
		return shopDetail.getStatus();
	}

	// 普通用户信息修改
	@Override
	public void updateUser(UserDetail userDetail) {
		EventHome.publishEvent(new FireEvent(userDetail, OperationTypeEnum.UPDATE));
		update(userDetail);
		updatePassword(userDetail);
	}

	// 修改用户密码
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#changePassword(com.legendshop
	 * .model.entity.UserDetail)
	 */
	@Override
	public void updatePassword(UserDetail userDetail) {
		if (!AppUtils.isBlank(userDetail.getPassword())) {
			User user = this.get(User.class, userDetail.getUserId());
			user.setPassword(MD5Util.Md5Password(userDetail.getUserName(), userDetail.getPassword()));
			EventHome.publishEvent(new FireEvent(user, OperationTypeEnum.UPDATE_PASSWORD));
			this.update(user);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#isUserExist(java.lang.
	 * String)
	 */
	@Override
	public boolean isUserExist(String userName) {
		List list = this.findByHQL("select 1 from User where name = ?", userName);
		return AppUtils.isNotBlank(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#isEmailExist(java.lang
	 * .String)
	 */
	@Override
	public boolean isEmailExist(String email) {
		List list = this.findByHQL("select 1  from UserDetail where userMail = ?", email);
		return AppUtils.isNotBlank(list);
	}

	// 店名是否存在
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#isShopExist(java.lang.
	 * String)
	 */
	@Override
	public boolean isShopExist(String shopName) {
		List list = this.findByHQL("from ShopDetail where userName = ?", new Object[] { shopName });
		if (AppUtils.isBlank(list)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#findUser(java.lang.String)
	 */
	@Override
	public User getUser(String userId) {
		return this.get(User.class, userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#findUserByName(java.lang
	 * .String)
	 */
	@Override
	public User getUserByName(String userName) {
		return findUniqueBy("from User where name = ?", User.class, userName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#findUserDetailByName(java
	 * .lang.String)
	 */
	@Override
	public UserDetail getUserDetailByName(String userName) {
		return findUniqueBy("from UserDetail where userName = ?", UserDetail.class, userName);
	}

	/**
	 * Sets the common util.
	 * 
	 * @param commonUtil
	 *            the new common util
	 */
	public void setCommonUtil(CommonUtil commonUtilImpl) {
		this.commonUtil = commonUtilImpl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#userRegSuccess(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public RegisterEnum getUserRegStatus(String userName, String registerCode) {
		UserDetail userDetail = getUserDetailByName(userName);
		if (userDetail == null) {
			return RegisterEnum.REGISTER_NO_USER_FOUND;
		}
		if (registerCode != null && registerCode.equals(userDetail.getRegisterCode())) {
			// update user
			User user = getUser(userDetail.getUserId());
			user.setEnabled("1");
			update(user);
			// update userDetail
			userDetail.setRegisterCode(REGISTED_TAG);
			update(userDetail);
			return RegisterEnum.REGISTER_SUCCESS;
		}
		if (REGISTED_TAG.equals(userDetail.getRegisterCode())) {
			return RegisterEnum.REGISTER_SUCCESS;
		}
		return RegisterEnum.REGISTER_FAIL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#findUserDetail(java.lang
	 * .String)
	 */
	@Override
	public UserDetail getUserDetail(String userName) {
		UserDetail userDetail = findUniqueBy("from UserDetail where userName= ?", UserDetail.class, userName);
		if (userDetail != null) {
			String qq = userDetail.getQq();
			if (AppUtils.isNotBlank(qq)) {
				String[] qqs = qq.split(",");
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < qqs.length; i++) {
					if (AppUtils.isNotBlank(qqs[i])) {
						list.add(qqs[i]);
					}
				}
				userDetail.setQqList(list);
			}
			ShopDetail shopDetail = findUniqueBy("from ShopDetail where userId = ?", ShopDetail.class, userDetail.getUserId());
			userDetail.setShopDetail(shopDetail);
		}

		return userDetail;
	}

 
	@Override
	public Long getUserScore(String userName) {
		UserDetail userDetail = findUniqueBy("from UserDetail where userName= ?", UserDetail.class, userName);
		if (userDetail != null && userDetail.getScore() != null) {
			return userDetail.getScore();
		}
		return 0l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.legendshop.business.dao.impl.UserDetailDao#findUserDetailList(com
	 * .legendshop.core.dao.support.HqlQuery)
	 */
	@Override
	public PageSupport getUserDetailList(HqlQuery hqlQuery) {
		return find(hqlQuery);
	}

	@Override
	public PageSupport getUserDetailList(SqlQuery sqlQuery) {
		return find(sqlQuery);
	}

	/**
	 * 删除用户所有信息，如果该用户是一个商家也同时干掉商家部分的所有信息
	 */
	@Override
	public String deleteUserDetail(String userId, String userName) {
		// 检测是否默认商城
		if (PropertiesUtil.getDefaultShopName().equals(userName)) {
			return "不能删除默认商城" + userName + "， 请在系统管理中修改默认商城";
		}
		
		//检查是否超级管理员等不能删除的角色，出于对系统的保护，敏感数据删除不支持UI操作
		List<UserRole> list = findByHQL("from UserRole where id.userId = ?", userId);
		boolean isAdmin = false; 
		for (UserRole role : list) {
			// ||role.getId().getRoleId().equals(RoleEnum.ROLE_ADMIN.value())
			if (role.getId().getRoleId().equals(RoleEnum.ROLE_SUPERVISOR.value())) {
				isAdmin = true;
				break;
			}

		}
		if (isAdmin) {
			return "不能删除商家用户或者管理员用户，请先备份好数据和去掉该用户的权限再试！";
		}
		///////////删除用户部分//////////////
		//用户的购物车
		basketDao.deleteUserBasket(userName);

      //用户所有订单
		subDao.deleteSub(userName);
		//用户的商品收藏
		deleteUserItem("ls_favorite", userName);
		//用户积分
		deleteUserItem("ls_score", userName);
		
		///////////删除商城部分//////////////
		deleteShopDetail(userId, userName,false);

		// 删除用户角色
		deleteAll(list);
		// 删除用户详细信息
		deleteById(UserDetail.class, userId);
		// 删除用户基本信息
		User user = getUser(userId);
		if (user != null) {
			delete(user);
			EventHome.publishEvent(new FireEvent(user, OperationTypeEnum.DELETE));
		} else {
			log.debug("删除用户{}不存在", userName);
			return "删除用户" + userName + "不存在";
		}

		log.debug("删除用户 {},  {} 成功", userId, userName);
		return null;
	}
	
	/**
	 * 删除商家的所有信息
	 * @param userName
	 * @param deleteShopOnly 是否只是删除商城，如果是要删除商城对应的权限
	 */
	public void deleteShopDetail(String userId, String userName, boolean deleteShopOnly) {
		// 检测是否默认商城
		if (PropertiesUtil.getDefaultShopName().equals(userName)) {
			return;
		}
		if(deleteShopOnly){//删除商家的权限,只是剩下用户权限
			commonUtil.removeAdminRight(userId);
		}
		
		//商城的购物车
		basketDao.deleteBasket(userName);

		//物流方式
		deliveryTypeDao.deleteDeliveryType(userName);

		//	物流公司
		deliveryCorpDao.deleteDeliveryCorp(userName);

		// 商城表
		deleteUserItem("ls_shop_detail", userName);

		// 用户商品的介绍图片
		deleteUserItem("ls_img_file", userName);

		// 用户商品的相关商品
		deleteUserItem("ls_prod_rel", userName);
		
		// 短消息
		deleteUserItem("ls_usr_comm", userName);

		//首页标签
		baseJdbcDao.update(
				"delete from ls_tag_map where exists (select 1 from ls_tag where ls_tag_map.tag_id = ls_tag.tag_id and ls_tag.user_name = ?)",
				userName);
		deleteUserItem("ls_tag", userName);

		// 用户商家联盟
		baseJdbcDao.update("delete from ls_league where user_id = ?", userName);
		baseJdbcDao.update("delete from ls_league where friend_id = ?", userName);

		// 商品评论
		baseJdbcDao.update("delete from ls_prod_comm where owner_name = ?", userName);
		//产品咨询
		baseJdbcDao.update("delete from ls_prod_cons where ask_user_name = ?", userName);

		// 商品动态属性
		deleteUserItem("ls_dyn_temp", userName);

		//团购产品
		baseJdbcDao.update(
				"delete from ls_group_prod where exists (select 1 from ls_prod where ls_group_prod.prod_id = ls_prod.prod_id and ls_prod.user_name = ?)",
				userName);
		//商户（产品供应商）
		deleteUserItem("ls_partner", userName);
		
		// 用户商品
		deleteUserItem("ls_prod", userName);

		// 商品分类品牌对应表
		deleteUserItem("ls_ns_brand", userName);

		// 商品品牌
		deleteUserItem("ls_brand", userName);

		// 商品首页广告图片
		deleteUserItem("ls_index_jpg", userName);

		// 广告
		deleteUserItem("ls_adv", userName);

		// 热点商品
		deleteUserItem("ls_hsearch", userName);

		// 新闻
		deleteUserItem("ls_news", userName);

		// 新闻分类
		deleteUserItem("ls_news_cat", userName);

		// 二级和三级商品分类
		baseJdbcDao.update(
				"delete from ls_nsort where exists (select 1 from ls_sort sort1_ where ls_nsort.sort_id=sort1_.sort_id and sort1_.user_name= ?)",
				userName);

		// 商品分类
		deleteUserItem("ls_sort", userName);

		// 支付方式
		deleteUserItem("ls_pay_type", userName);

		// 公告
		deleteUserItem("ls_pub", userName);

		// 友情链接
		deleteUserItem("ls_extl_link", userName);
		
		// 删除用户商品图片目录
		if (AppUtils.isNotBlank(userName)) {
			FileProcessor.deleteDirectory(new File(RealPathUtil.getBigPicRealPath() + "/" + userName));
			deleteSmallImage(userName);
		}
	}
	
	/**
	 * 删除各个等级的缩略图
	 * @param userName
	 */
	private void deleteSmallImage(String userName){
		for (String sacle : scaleList.keySet()) {
			StringBuilder sb = new StringBuilder(RealPathUtil.getSmallPicRealPath());
			sb.append("/").append(sacle).append("/").append(userName);
			FileProcessor.deleteDirectory(new File(sb.toString()));
		}
	
	}
	
	private void deleteUserItem(String tableName, String userName) {
		baseJdbcDao.update("delete from " + tableName + " where user_name = ?", userName);
	}

	@Override
	public boolean updatePassword(String userName, String mail, String templateFilePath) throws MalformedPatternException,
			MessagingException {
		UserDetail userDetail = findUniqueBy("from UserDetail n where n.userName = ? and n.userMail = ?", UserDetail.class,
				userName, mail);
		if (userDetail == null) {
			return false;
		}
		// update password
		User user = get(User.class, userDetail.getUserId());
		String newPassword = RandomStringUtils.randomNumeric(10, 6);
		user.setPassword(MD5Util.Md5Password(user.getName(), newPassword));
		update(user);
		// send mail
		log.info("{} 修改密码，发送通知邮件到 {}", userName, userDetail.getUserMail());
		// String text = FileProcessor.readFile(new File(filePath));
		Map<String, String> values = new HashMap<String, String>();
		
		values.put("#domainName#", PropertiesUtil.getDomainName());
		values.put("#nickName#", userDetail.getNickName());
		values.put("#userName#", userDetail.getUserName());
		values.put("#password#", newPassword);
		String text = AppUtils.convertTemplate(templateFilePath, values);
		if (PropertiesUtil.sendMail()) {
			EventHome.publishEvent(new SendMailEvent(userDetail.getUserMail(), "恭喜您，修改密码成功！", text));
		}
		return true;
	}

	@Override
	public Long getAllUserCount() {
		return findUniqueBy("select count(*) from UserDetail", Long.class);
	}

	/**
	 * @param baseJdbcDao
	 *            the baseJdbcDao to set
	 */
	public void setBaseJdbcDao(BaseJdbcDao jdbcDao) {
		this.baseJdbcDao = jdbcDao;
	}

	@Override
	public void updateUser(User user) {
		update(user);

	}

	/**
	 * @param subDao the subDao to set
	 */
	public void setSubDao(SubDao subDao) {
		this.subDao = subDao;
	}

	/**
	 * @param deliveryTypeDao the deliveryTypeDao to set
	 */
	public void setDeliveryTypeDao(DeliveryTypeDao deliveryTypeDao) {
		this.deliveryTypeDao = deliveryTypeDao;
	}

	/**
	 * @param deliveryCorpDao the deliveryCorpDao to set
	 */
	public void setDeliveryCorpDao(DeliveryCorpDao deliveryCorpDao) {
		this.deliveryCorpDao = deliveryCorpDao;
	}

	public void setBasketDao(BasketDao basketDao) {
		this.basketDao = basketDao;
	}

	public void setScaleList(Map<String, List<Integer>> scaleList) {
		this.scaleList = scaleList;
	}

}
