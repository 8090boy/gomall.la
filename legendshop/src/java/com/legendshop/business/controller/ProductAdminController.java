/**
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
import com.legendshop.model.dynamic.Item;
import com.legendshop.model.entity.Brand;
import com.legendshop.model.entity.Nsort;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.Sort;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.page.BackPage;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.service.BrandService;
import com.legendshop.spi.service.ProductService;
import com.legendshop.spi.service.SortService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.SafeHtml;
import com.legendshop.util.constant.ProductTypeEnum;

/**
 * 产品控制器.
 */
@Controller
@RequestMapping("/admin/product")
public class ProductAdminController extends BaseController {

	/** The product service. */
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SortService sortService;
	
	@Autowired
	private BrandService brandService;

	
	@RequestMapping("/query")
	public String query(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.PROD_LIST_PAGE);
	}

	@RequestMapping("/queryprodcontent")
	public String queryProdContent(HttpServletRequest request, HttpServletResponse response, String curPageNO, Product product) {
		// Qbc查找方式
		CriteriaQuery cq = new CriteriaQuery(Product.class, curPageNO);

		if (!AppUtils.isBlank(product.getName())) {
			cq.like("name", "%" + product.getName().trim() + "%");
		}

		if (product.getStatus() == null) {
			product.setStatus(Constants.ONLINE);
		} else if (product.getStatus() == -1) {
			product.setStatus(null);// 清空查询条件
		}

		cq.eq("commend", product.getCommend());
		cq.eq("status", product.getStatus());
		cq.eq("hot", product.getHot());
		cq.eq("prodType", ProductTypeEnum.PRODUCT.value());
		if(!PropertiesUtil.isDefaultShopName(UserManager.getUserName(request))){
			cq.eq("sortId", product.getSortId());
			cq.eq("nsortId", product.getNsortId());
			cq.eq("subNsortId", product.getSubNsortId());
			cq.eq("brandId", product.getBrandId());
			cq = hasAllDataFunction(cq, request, StringUtils.trim(product.getUserName()));
		}else{
			//默认商城
			cq.eq("globalSort", product.getSortId());
			cq.eq("globalNsort", product.getNsortId());
			cq.eq("globalSubSort", product.getSubNsortId());
			cq.eq("globalBrand", product.getBrandId());
		}

		if (!CommonServiceUtil.isDataForExport(cq, request)) {// 非导出情况
			cq.setPageSize(PropertiesUtil.getObject(SysParameterEnum.PAGE_SIZE, Integer.class));
		}
		if (!CommonServiceUtil.isDataSortByExternal(cq, request)) {
			cq.addOrder("desc", "modifyDate");
		}

		PageSupport ps = productService.getProductList(cq);
		ps.savePage(request);
		request.setAttribute("prod", product);
		return PathResolver.getPath(request, response, BackPage.PROD_CONTENT_LIST_PAGE);
	}

	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response, String curPageNO, Product product) {
		return PathResolver.getPath(request, response, BackPage.PROD_LIST_PAGE_TEMP);
	}


	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Product product) {
		String name = UserManager.getUserName(request);
		// product.validate();//验证
		String result = checkLogin(request, response, name);
		if (result != null) {
			return result;
		}
		// 过滤特殊字符
		SafeHtml safeHtml = new SafeHtml();
		product.setName(safeHtml.makeSafe(product.getName()));
		product.setModelId(safeHtml.makeSafe(product.getModelId()));
		product.setKeyWord(safeHtml.makeSafe(product.getKeyWord()));
		product.setBrief(safeHtml.makeSafe(product.getBrief()));
		MultipartFile formFile = product.getFile();// 取得上传的文件
		MultipartFile smallPicFile = product.getSmallPicFile();
		String subPath = name + "/prod/";
		String filename = null;
		try {
			// 判断是否上传了大图片和小图片
			boolean uploadFile = fileUploaded(formFile);
			boolean uploadSmallPicFile = fileUploaded(smallPicFile);
			if (product.getProdId() != null) {
				// update product
				String orginProdPic = null; // 记住当前路径用来删除
				String orginSmallProdPic = null;
				Product origin = productService.getProductById(product.getProdId());
				if (origin == null) {
					throw new NotFoundException("can not found product by id " + product.getProdId());
				}
				String checkPrivilegeResult = checkPrivilege(request, name, origin.getUserName());
				if (checkPrivilegeResult != null) {
					return checkPrivilegeResult;
				}

				if (uploadFile) {
					orginProdPic = RealPathUtil.getBigPicRealPath() + "/" + origin.getPic();
					// upload file
					filename = FileProcessor.uploadFileAndCallback(formFile, subPath, "");
					origin.setPic(filename);
				}

				// 处理产品小图片
				if (product.getUseSmallPic() != null && uploadSmallPicFile) {
					// 使用小图片
					// upload file
					orginSmallProdPic = RealPathUtil.getBigPicRealPath() + "/" + origin.getSmallPic();
					String smallPicName = FileProcessor.uploadFileAndCallback(smallPicFile, subPath, "");
					origin.setSmallPic(smallPicName);
					origin.setUseSmallPic(Constants.TRUE_INDICATOR);
				} else {
					// 自动生成缩略图
					orginSmallProdPic = RealPathUtil.getSmallPicRealPath() + "/" + origin.getSmallPic();
					origin.setSmallPic(null);
					origin.setUseSmallPic(Constants.FALSE_INDICATOR);
				}

				// call service
				productService.updateProduct(product, origin);

				// ------------------ 以下删除大图片 --------------------
				if (uploadFile) {
					FileProcessor.deleteFile(orginProdPic);
					if (product.getUseSmallPic() == null) {
						FileProcessor.deleteFile(orginSmallProdPic);
					}

				}
				// 删除小图片
				if (uploadSmallPicFile) {
					FileProcessor.deleteFile(orginSmallProdPic);
				}

			} else {
				//上传大图片
				if (uploadFile) {
					filename = FileProcessor.uploadFileAndCallback(formFile, subPath, "" + "");
					product.setPic(filename);
				}

				// 上传产品小图片
				if (product.getUseSmallPic() != null && uploadSmallPicFile) {
					// 使用小图片
					String smallPicName = FileProcessor.uploadFileAndCallback(smallPicFile, subPath, "");
					product.setSmallPic(smallPicName);
					product.setUseSmallPic(Constants.TRUE_INDICATOR);
				} else {
					// 自动生成缩略图
					product.setSmallPic(null);
					product.setUseSmallPic(Constants.FALSE_INDICATOR);
				}
				
				// 保存到数据库
				product.setUserId(UserManager.getUserId(request));
				product.setUserName(name);
				productService.saveProduct(product, ProductTypeEnum.PRODUCT.value());
			}
		} catch (Exception e) {
			// delete file uploaded
			if ((formFile != null) && (formFile.getSize() > 0)) {
				FileProcessor.deleteFile(RealPathUtil.getBigPicRealPath() + "/" + subPath + filename);
			}
			throw new BusinessException(e, "save product error",  ErrorCodes.BUSINESS_ERROR);
		}

		String nextAction = request.getParameter("nextAction");
		if (nextAction != null && nextAction.equals("next")) {
			request.setAttribute("productId", product.getProdId());
			return PathResolver.getPath(request, response, FowardPage.IMG_LIST_QUERY);
		} else {
			saveMessage(request, ResourceBundleHelper.getSucessfulString());
			return PathResolver.getPath(request, response, FowardPage.PROD_LIST_QUERY);
		}
	}

	/**
	 * File uploaded. 是否上传了文件
	 * 
	 * @param formFile
	 *            the form file
	 * @return true, if successful
	 */
	private boolean fileUploaded(MultipartFile formFile) {
		return formFile != null && formFile.getSize() > 0;
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
	@RequestMapping(value = "/delete/{prodId}")
	public  @ResponseBody String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId) {
		String loginedUser = UserManager.getUserName(request);
		String result = productService.delete(loginedUser, prodId);
		return result;
	}

	/**
	 * 通过控制器到页面.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/load")
	public String load(HttpServletRequest request, HttpServletResponse response,Long sortId, Long nsortId, Long subsortId, Long brandId) {
		parseSort(request,sortId,nsortId,subsortId,brandId);
		
		return PathResolver.getPath(request, response, BackPage.PROD_EDIT_PAGE);
	}
	
	private void parseSort(HttpServletRequest request,Long sortId, Long nsortId, Long subsortId, Long brandId){
		if(AppUtils.isBlank(sortId) || AppUtils.isBlank( nsortId) || AppUtils.isBlank(subsortId)){
			throw new BusinessException("product category can not be null",ErrorCodes.BUSINESS_ERROR);
		}
		List<Sort> sortList = sortService.getSort(PropertiesUtil.getDefaultShopName(), ProductTypeEnum.PRODUCT.value(), true);
		Sort sort1 = null; //一级商品分类
		Nsort nsort2 = null;//二级商品分类
		Nsort nsort3 = null;//三级商品分类
		for (Sort sort : sortList) {
			if(sort.getSortId().equals(sortId)){
				sort1 = sort;
				break;
			}
		}
		if(sort1 != null){
			Set<Nsort> nsortList = sort1.getNsort();
			if(nsortList!=null){
				for (Nsort nsort : nsortList) {
					if(nsort.getNsortId().equals(nsortId)){
						nsort2 = nsort;
						break;
					}
				}
			}
		}
		
		if(nsort2 != null){
			Set<Nsort> subSortList = nsort2.getSubSort();
			if(subSortList != null){
				for (Nsort nsort : subSortList) {
					if(nsort.getNsortId().equals(subsortId)){
						nsort3 = nsort;
						break;
					}
				}
			}
		}
		
		request.setAttribute("sort1", sort1);
		request.setAttribute("nsort2", nsort2);
		request.setAttribute("nsort3", nsort3);
		
		if(brandId != null){
			Brand brand = brandService.getBrand(brandId); //品牌
			request.setAttribute("brand", brand);
		}
	}

	/**
	 * 通过控制器到页面.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param prodId
	 *            the prod id
	 * @return the string
	 */
	@RequestMapping(value = "/append/{prodId}")
	public String append(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId) {
		request.setAttribute("prodId", prodId);
		return PathResolver.getPath(request, response, BackPage.APPEND_PRODUCT);
	}

	/**
	 * Createsetp.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/createsetp")
	public String createsetp(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.CREATEP_RODUCT_STEP);
	}

	/**
	 * Update.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param prodId
	 *            the prod id
	 * @return the string
	 */
	@RequestMapping(value = "/update/{prodId}")
	public String update(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId) {
		checkNullable("prodId", prodId);
		Product product = productService.getProductById(prodId);
		String result = checkPrivilege(request, UserManager.getUserName(request), product.getUserName());
		if (result != null) {
			return result;
		}
		request.setAttribute("prod", product);
		String sortId = request.getParameter("sortId");
		if(sortId != null){
			String nsortId = request.getParameter("nsortId");
			String subsortId = request.getParameter("subsortId");
			String brandId = request.getParameter("brandId");
			parseSort(request,parstLong(sortId), parstLong(nsortId), parstLong(subsortId), parstLong(brandId));
		}else{
			parseSort(request,product.getGlobalSort(),product.getGlobalNsort(),product.getGlobalSubSort(),product.getBrandId());
		}
		return PathResolver.getPath(request, response, BackPage.PROD_EDIT_PAGE);
	}
	
	private Long parstLong(String id){
		if(AppUtils.isBlank(id)){
			return null;
		}
		return Long.parseLong(id);
	}

	/**
	 * Update status.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param prodId
	 *            the prod id
	 * @param status
	 *            the status
	 * @return the integer
	 */
	@RequestMapping(value = "/updatestatus/{prodId}/{status}", method = RequestMethod.GET)
	public @ResponseBody
	Integer updateStatus(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId,
			@PathVariable Integer status) {
		Product product = productService.getProductById(prodId);
		if (product == null) {
			return -1;
		}
		if (!status.equals(product.getStatus())) {
			if (!FoundationUtil.haveViewAllDataFunction(request)) {
				String loginName = UserManager.getUserName(request.getSession());
				// user
				if (!loginName.equals(product.getUserName())) {
					return -1;
				}
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status)) {
					product.setStatus(status);
					product.setModifyDate(new Date());
					productService.updateProd(product);
				}
			} else {
				// admin
				if (Constants.ONLINE.equals(status) || Constants.OFFLINE.equals(status) || Constants.STOPUSE.equals(status)) {
					product.setStatus(status);
					product.setModifyDate(new Date());
					productService.updateProd(product);
				}
			}
		}
		return product.getStatus();
	}
	
	
	
	/**
	 * 通过控制器到页面.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/loadnew")
	public String loadnew(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.PROD_EDIT_NEW_PAGE);
	}
	
	@RequestMapping(value = "/category2")
	public String category2(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.PROD_CATEGORY_2_PAGE);
	}
	
	@RequestMapping(value = "/category3")
	public String category3(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.PROD_CATEGORY_3_PAGE);
	}
	
	/**
	 * 通过控制器到页面.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/productProperty")
	public String productProperty(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.PROD_EDIT_PROPERTY_PAGE);
	}
	
	/**
	 * 通过控制器到页面.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/productDetails")
	public String productDetails(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.PROD_EDIT_DETAILS_PAGE);
	}
	
	
	/**
	 * 通过控制器到页面.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/saleProperty")
	public String saleProperty(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, BackPage.PROD_EDIT_SALE_PROP_PAGE);
	}
	
	@RequestMapping(value = "/saveRelProd/{prodId}")
	public  @ResponseBody String saveRelProd(HttpServletRequest request, HttpServletResponse response, String idJson, String nameJson,@PathVariable Long prodId) {
		return productService.saveRelProd(idJson, nameJson, prodId, UserManager.getUserName(request));
	}
	
	@RequestMapping(value = "/getUsableProductItemByName/{prodId}")
	public @ResponseBody List<Item> getUsableProductItemByName(HttpServletRequest request,@PathVariable Long prodId,String prodName){
		return productService.getUsableProductItemByName(prodId, UserManager.getUserName(request),prodName);
	}
	
	@RequestMapping(value = "/getUsableProductItem/{prodId}")
	public @ResponseBody List<Item> getUsableProductItem(HttpServletRequest request,@PathVariable Long prodId){
		return productService.getUsableProductItem(prodId, UserManager.getUserName(request));
	}
	
	@RequestMapping(value = "/getUsedProductItem/{prodId}")
	public @ResponseBody  List<Item> getUsedProductItem(HttpServletRequest request, @PathVariable Long prodId){
		return productService.getUsedProductItem(prodId,UserManager.getUserName(request));
	}

}
