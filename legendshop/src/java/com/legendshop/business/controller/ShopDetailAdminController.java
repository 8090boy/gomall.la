/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.business.common.CommonServiceUtil;
import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.exception.BusinessException;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.core.helper.FileProcessor;
import com.legendshop.core.helper.FoundationUtil;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.RealPathUtil;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.core.page.TempletManager;
import com.legendshop.model.StatusKeyValueEntity;
import com.legendshop.model.entity.ShopDetail;
import com.legendshop.model.entity.UserDetail;
import com.legendshop.model.template.Templet;
import com.legendshop.model.template.TempletEntity;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.constants.ShopTypeEnum;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.ShopDetailService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.ContextServiceLocator;
import com.legendshop.util.JSONUtil;
import com.legendshop.util.SafeHtml;
import com.legendshop.util.constant.ShopStatusEnum;

/**
 * 商城详细信息控制器.
 */
@Controller
@RequestMapping("/admin/shopDetail")
public class ShopDetailAdminController extends BaseController {

	/** The shop detail service. */
	@Autowired
	private ShopDetailService shopDetailService;
	
	/** The location service. */
	@Autowired
	private TempletManager templetManager;

	/**
	 * Query.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param shopDetail
	 *            the shop detail
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping("query")
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, ShopDetail shopDetail)
			throws Exception {
		if(FoundationUtil.haveViewAllDataFunction(request)){
			CriteriaQuery cq = new CriteriaQuery(ShopDetail.class, curPageNO, "javascript:pager");
			hasAllDataFunction(cq, request, "userName", StringUtils.trim(shopDetail.getUserName()));

			if (!CommonServiceUtil.isDataForExport(cq, request)) {// 非导出情况
				cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
			}
			if (!CommonServiceUtil.isDataSortByExternal(cq, request)) {
				cq.addOrder("desc", "modifyDate");
			}
			cq.eq("type", shopDetail.getType());
			cq.eq("status", shopDetail.getStatus());

			PageSupport ps = shopDetailService.getShopDetail(cq);
			ps.savePage(request);
			request.setAttribute("shopDetail", shopDetail);
			return PathResolver.getPath(request, response, BackPage.SHOP_DETAIL_LIST_PAGE);
		}else{
			//goto shopSetail page
			String userName = UserManager.getUserName(request);
			Long shopId = shopDetailService.getShopIdByName(userName);
			return PathResolver.getPath(request, response, FowardPage.SHOP_DETAIL_LOAD) + shopId;
		}
		
	}

	/**
	 * Save.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopDetail
	 *            the shop detail
	 * @return the string
	 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpServletResponse response, ShopDetail shopDetail) {
		if ((shopDetail != null) && (!AppUtils.isBlank(shopDetail.getShopId()))) {
			return update(request, response, shopDetail);
		} else {
			// 检查用户权限，只有超级用户可以使用
			String userName = shopDetail.getUserName();

			String shopPic = null;
			String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), userName);
			if (result != null) {
				return result;
			}
			if (AppUtils.isBlank(userName)) {
				throw new NotFoundException("user name can not be empty");
			}
			UserDetail userDetail = shopDetailService.getShopDetailByName(userName);
			if (AppUtils.isBlank(userDetail)) {
				throw new NotFoundException("userDetail can not beempty");
			}
			try {
				// SafeHtml safeHtml = new SafeHtml();
				shopDetail.setUserId(userDetail.getUserId());
				Date date = new Date();
				shopDetail.setModifyDate(date);
				shopDetail.setRecDate(date);
				shopDetail.setVisitTimes(0);
				shopDetail.setOffProductNum(0);
				shopDetail.setCommNum(0);
				shopDetail.setProductNum(0);
				shopDetail.setType(ShopTypeEnum.BUSINESS.value());
				shopDetail.setStatus(ShopStatusEnum.ONLINE.value());
				shopDetail.setCapital(0d);
				shopDetail.setCredit(0);
				String subPath = userName + "/shop/";
				if (shopDetail.getFile().getSize() > 0) {
					shopPic = FileProcessor.uploadFileAndCallback(shopDetail.getFile(), subPath, "");
					shopDetail.setShopPic(shopPic);
				}
				shopDetailService.save(shopDetail);
				saveMessage(request, ResourceBundleHelper.getSucessfulString());
			} catch (Exception e) {
				if (shopPic != null) {
					FileProcessor.deleteFile(shopPic);
				}
				throw new BusinessException(e, "Error happened when save shop", ErrorCodes.SAVE_ERROR);
			}

			return PathResolver.getPath(request, response, FowardPage.SHOP_DETAIL_LIST_QUERY);
		}
	}

	/**
	 * Delete.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param id
	 *            the id
	 * @return the string
	 */
	@RequestMapping(value = "/delete/{id}")
	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		ShopDetail shopDetail = shopDetailService.getShopDetailById(id);
		if (shopDetail != null) {
			shopDetailService.delete(shopDetail);
		}

		saveMessage(request, ResourceBundleHelper.getDeleteString());
		return PathResolver.getPath(request, response, FowardPage.SHOP_DETAIL_LIST_QUERY);
	}

	/**
	 * Load.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param id
	 *            the id
	 * @return the string
	 */
	@RequestMapping(value = "/load/{id}")
	public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		ShopDetail shopDetail = shopDetailService.getShopDetailById(id);
		if (shopDetail != null) {
			String result = checkPrivilege(request, UserManager.getUserName(request), shopDetail.getUserName());
			if (result != null) {
				return result;
			}
		}

		request.setAttribute("shopDetail", shopDetail);
		request.setAttribute("id", request.getParameter("userId"));
		request.setAttribute("templetEntity", JSONUtil.getJson(getTempletsEntity(shopDetail)));
		return PathResolver.getPath(request, response, BackPage.SHOP_DETAIL_EDIT_PAGE);
	}
	
	public TempletEntity getTempletsEntity(ShopDetail shopDetail) {
		TempletEntity templet =  new TempletEntity();
		templet.setFrontEndTempletList(CommonServiceUtil.convertKeyValueEntity(templetManager.getFrontEndTempletList(), shopDetail.getFrontEndTemplet()));
		templet.setBackEndTempletList(CommonServiceUtil.convertKeyValueEntity(templetManager.getBackEndTempletList(), shopDetail.getBackEndTemplet()));

		//front end
		Map<String,String> frontEndTempletMap = templetManager.getFrontEndtTempletMap();
		for (String templetId : frontEndTempletMap.keySet()) {
			Templet newTemplet = getFrontEndTemplet(templetId);
			convertTemplet(templetId,newTemplet,shopDetail.getFrontEndLanguage(),shopDetail.getFrontEndStyle() );
			templet.addFrontEndTempletMap(newTemplet);
		}
		//back end
		Map<String,String> backEndTempletMap = templetManager.getBackEndTempletMap();
		for (String templetId : backEndTempletMap.keySet()) {
			Templet newTemplet = getBackEndTemplet(templetId);
			convertTemplet(templetId,newTemplet, shopDetail.getBackEndLanguage(), shopDetail.getBackEndStyle());
			templet.addBackEndTempletMap(newTemplet);
		}
		return templet;
	}
	
	private void convertTemplet(String templetId, Templet newTemplet, String selectLanague,String selectStyle){
		newTemplet.setId(templetId);
		newTemplet.setLanguages(CommonServiceUtil.convertKeyValueEntity(newTemplet.getLanguages(), selectLanague));
		newTemplet.setStyles(CommonServiceUtil.convertKeyValueEntity(newTemplet.getStyles(),selectStyle));
	}
	
	private Templet getFrontEndTemplet(String templetId){
		return (Templet)ContextServiceLocator.getInstance().getBean(templetManager.getFrontEndTempletById(templetId));
	}
	
	private Templet getBackEndTemplet(String templetId){
		return (Templet)ContextServiceLocator.getInstance().getBean(templetManager.getBackEndTempletById(templetId));
	}

	/**
	 * Load.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("templetEntity", JSONUtil.getJson(getTempletsEntity(new ShopDetail())));
		return PathResolver.getPath(request, response, BackPage.SHOP_DETAIL_EDIT_PAGE);
	}

	/**
	 * Update.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopDetail
	 *            the shop detail
	 * @return the string
	 */
	private String update(HttpServletRequest request, HttpServletResponse response, ShopDetail shopDetail) {
		ShopDetail shop = shopDetailService.getShopDetailById(shopDetail.getShopId());
		if (shop == null) {
			throw new NotFoundException("shop can not be null, getShopId = " + shopDetail.getShopId());
		}
		String originShopPic = shop.getShopPic();
		String shopPic = null;
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), shopDetail.getUserName());
		if (result != null) {
			return result;
		}
		try {
			String subPath = shopDetail.getUserName() + "/shop/";
			SafeHtml safeHtml = new SafeHtml();
			shop.setSiteName(safeHtml.makeSafe(shopDetail.getSiteName()));
			shop.setShopAddr(safeHtml.makeSafe(shopDetail.getShopAddr()));
			shop.setBankCard(safeHtml.makeSafe(shopDetail.getBankCard()));
			shop.setPayee(safeHtml.makeSafe(shopDetail.getPayee()));
			shop.setCode(safeHtml.makeSafe(shopDetail.getCode()));
			shop.setPostAddr(safeHtml.makeSafe(shopDetail.getPostAddr()));
			shop.setRecipient(safeHtml.makeSafe(shopDetail.getRecipient()));
			shop.setBriefDesc(safeHtml.makeSafe(shopDetail.getBriefDesc()));
			shop.setDetailDesc(safeHtml.makeSafe(shopDetail.getDetailDesc()));
			shop.setModifyDate(new Date());
			shop.setProvinceid(shopDetail.getProvinceid());
			shop.setCityid(shopDetail.getCityid());
			shop.setAreaid(shopDetail.getAreaid());

			shop.setFrontEndTemplet(shopDetail.getFrontEndTemplet());
			shop.setBackEndTemplet(shopDetail.getBackEndTemplet());
			
			//parse style and language
			Templet frontEndTemplet = getFrontEndTemplet(shopDetail.getFrontEndTemplet());
			Templet backEndTemplet = getBackEndTemplet(shopDetail.getBackEndTemplet());
			shop.setFrontEndStyle(checkTemplet(frontEndTemplet.getStyles(),  shopDetail.getFrontEndStyle()));
			shop.setBackEndStyle(checkTemplet(backEndTemplet.getStyles(), shopDetail.getBackEndStyle()));
			shop.setFrontEndLanguage(checkTemplet(frontEndTemplet.getLanguages(), shopDetail.getFrontEndLanguage()));
			shop.setBackEndLanguage(checkTemplet(frontEndTemplet.getLanguages(), shopDetail.getBackEndLanguage()));
			
			if (CommonServiceUtil.haveViewAllDataFunction(request) || !ShopStatusEnum.AUDITING.value().equals(shop.getStatus())) {
				if (shopDetail.getStatus() != null) {
					shop.setStatus(shopDetail.getStatus());
				}
			}
			if (shopDetail.getFile().getSize() > 0) {
				shopPic = FileProcessor.uploadFileAndCallback(shopDetail.getFile(), subPath, "");
				shop.setShopPic(shopPic);
			}
			if (FoundationUtil.haveViewAllDataFunction(request)) {
				shop.setDomainName(shopDetail.getDomainName());
				shop.setIcpInfo(shopDetail.getIcpInfo());
			}

			shopDetailService.update(shop);
		} catch (Exception e) {
			if (shopPic != null) {
				FileProcessor.deleteFile(shopPic);
			}
			throw new BusinessException(e, "Error happened when update shop", ErrorCodes.UPDATE_ERROR);
		} finally {
			if (shopPic != null && originShopPic != null) {
				FileProcessor.deleteFile(RealPathUtil.getBigPicRealPath() + "/" + originShopPic);
			}
		}
		return PathResolver.getPath(request, response, FowardPage.SHOP_DETAIL_LIST_QUERY);
	}
	
	//检查如果该模版没有特定配置则用模版的第一个作为默认值
	private String checkTemplet(List<StatusKeyValueEntity> entityList, String keyValue){
		boolean matched = false;
		if(entityList != null){
			for (StatusKeyValueEntity entity : entityList) {
				if(entity.getKey().equals(keyValue)){
					matched = true;
					break;
				}
			}
		}else{
			return null;
		}

		if(matched){
			return keyValue;
		}else{
			return entityList.get(0).getKey();
		}
		
	}
	
	/**
	 * 审核商城状态
	 * @param userId
	 * @param shopId
	 * @param status
	 * @return
	 */
	@RequestMapping("audit")
	public @ResponseBody String auditShop(HttpServletRequest request, String userId, Long shopId, Integer status) {
		if (userId == null || shopId == null) {
			return Constants.FAIL;
		}
		
		ShopDetail shopDetail = shopDetailService.getShopDetailByShopId(shopId);
		if (shopDetail != null) {
			//check privilege
			String loginName = UserManager.getUserName();
			checkPrivilege(request, loginName, shopDetail.getUserName());
			boolean result = shopDetailService.updateShop(userId, shopDetail, status);
			if (result) {
				return null;
			} else {
				return Constants.FAIL;
			}
		}
		return Constants.FAIL;

	}

}
