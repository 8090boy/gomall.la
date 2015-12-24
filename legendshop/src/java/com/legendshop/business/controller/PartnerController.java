/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legendshop.core.UserManager;
import com.legendshop.core.base.AdminController;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.dao.support.CriteriaQuery;
import com.legendshop.core.dao.support.PageSupport;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.core.helper.FileProcessor;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.RealPathUtil;
import com.legendshop.model.entity.Partner;
import com.legendshop.model.entity.ShopDetail;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.PartnerService;
import com.legendshop.spi.service.ShopDetailService;
import com.legendshop.util.AppUtils;

/**
 * The Class PartnerController
 * 
 */
@Controller
@RequestMapping("/admin/partner")
public class PartnerController extends BaseController implements AdminController<Partner, Long> {
	@Autowired
	private PartnerService partnerService;

	@Autowired
	private ShopDetailService shopDetailService;

	@RequestMapping("/query")
	public String query(HttpServletRequest request, HttpServletResponse response, String curPageNO, Partner partner) {
		CriteriaQuery cq = new CriteriaQuery(Partner.class, curPageNO);
		cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
		cq = hasAllDataFunction(cq, request, StringUtils.trim(partner.getUserName()));
		/*
		 * //TODO add your condition
		 */

		PageSupport ps = partnerService.getPartner(cq);
		ps.savePage(request);
		request.setAttribute("partner", partner);
		return PathResolver.getPath(request, response, BackPage.PARTNER_LIST_PAGE);
	}

	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Partner partner) {
		Partner p = null;

		String username = UserManager.getUserName(request.getSession());
		String subPath = username + "/partner/";
		if (AppUtils.isNotBlank(partner.getPartnerId())) {// update
			p = partnerService.getPartner(partner.getPartnerId());
			// set some value
			partner.setCreateTime(p.getCreateTime());
			partner.setPassword(p.getPassword());
			partner.setShopId(p.getShopId());

			// private String image;
			// private String image1;
			// private String image2;

			partner.setCommentGood(p.getCommentGood());
			partner.setCommentNone(p.getCommentNone());
			partner.setCommentBad(p.getCommentBad());

			if (partner.getImageFile().getSize() > 0) {
				String image = FileProcessor.uploadFileAndCallback(partner.getImageFile(), subPath, "");
				String originImage = p.getImage();
				if (StringUtils.isNotEmpty(originImage)) {
					FileProcessor.deleteFile(RealPathUtil.getBigPicRealPath() + "/" + originImage);
				}
				partner.setImage(image);
			}
			if (partner.getImageFile1().getSize() > 0) {
				String image1 = FileProcessor.uploadFileAndCallback(partner.getImageFile1(), subPath, "");
				String originImage1 = p.getImage1();
				if (StringUtils.isNotEmpty(originImage1)) {
					FileProcessor.deleteFile(RealPathUtil.getBigPicRealPath() + "/" + originImage1);
				}
				partner.setImage1(image1);
			}
			if (partner.getImageFile2().getSize() > 0) {
				String image2 = FileProcessor.uploadFileAndCallback(partner.getImageFile2(), subPath, "");
				String originImage2 = p.getImage2();
				if (StringUtils.isNotEmpty(originImage2)) {
					FileProcessor.deleteFile(RealPathUtil.getBigPicRealPath() + "/" + originImage2);
				}
				partner.setImage2(image2);
			}

			p = partner;
		} else {// add
			p = partner;
			p.setCreateTime(new Date());
			p.setCommentBad(0);
			p.setCommentGood(0);
			p.setCommentNone(0);

			ShopDetail shopDetail = shopDetailService.getShopDetailByUserId(UserManager.getUserId(request));

			if (shopDetail == null) {
				throw new NotFoundException("Can't find shopDetail by user id");
			}
			p.setShopId(shopDetail.getShopId());

			if (partner.getImageFile().getSize() > 0) {
				String image = FileProcessor.uploadFileAndCallback(partner.getImageFile(), subPath, "" );
				partner.setImage(image);
			}

			if (partner.getImageFile1().getSize() > 0) {
				String image1 = FileProcessor.uploadFileAndCallback(partner.getImageFile1(), subPath, "");
				partner.setImage1(image1);
			}
			if (partner.getImageFile2().getSize() > 0) {
				String image2 = FileProcessor.uploadFileAndCallback(partner.getImageFile2(), subPath, "");
				partner.setImage2(image2);
			}

		}

		p.setUserId(UserManager.getUserId(request));
		p.setUserName(username);
		p.setModifyTime(new Date());

		partnerService.savePartner(p);
		saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
		return PathResolver.getPath(request, response, FowardPage.PARTNER_LIST_QUERY);
	}

	@RequestMapping(value = "/delete/{id}")
	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		Partner partner = partnerService.getPartner(id);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), partner.getUserName());
		if (result != null) {
			return result;
		}
		partnerService.deletePartner(partner);
		saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("entity.deleted"));
		return PathResolver.getPath(request, response, FowardPage.PARTNER_LIST_QUERY);
	}

	@RequestMapping(value = "/load/{id}")
	public String load(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		Partner partner = partnerService.getPartner(id);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), partner.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("partner", partner);
		return PathResolver.getPath(request, response, BackPage.PARTNER_EDIT_PAGE);
	}

	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.PARTNER_EDIT_PAGE);
	}

	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		Partner partner = partnerService.getPartner(id);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), partner.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("partner", partner);
		return PathResolver.getPath(request, response, FowardPage.PARTNER_LIST_QUERY);
	}

	@RequestMapping(value = "/changePassword/{id}")
	public String changePassword(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
		Partner partner = partnerService.getPartner(id);
		String result = checkPrivilege(request, UserManager.getUserName(request.getSession()), partner.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("partner", partner);
		return PathResolver.getPath(request, response, BackPage.PARTNER_CHANGE_PASSWORD_PAGE);
	}

	@RequestMapping(value = "/savePassword")
	public String savePassword(HttpServletRequest request, HttpServletResponse response, Partner partner) {
		Partner p = null;
		if (AppUtils.isNotBlank(partner.getPartnerId()) && AppUtils.isNotBlank(partner.getPassword())) {// update
			p = partnerService.getPartner(partner.getPartnerId());
			p.setPassword(partner.getPassword());
		}

		p.setModifyTime(new Date());

		partnerService.savePartner(p);
		saveMessage(request, ResourceBundle.getBundle("i18n/ApplicationResources").getString("operation.successful"));
		return PathResolver.getPath(request, response, FowardPage.PARTNER_LIST_QUERY);
	}

}
