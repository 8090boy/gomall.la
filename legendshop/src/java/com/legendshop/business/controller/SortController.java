/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.business.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legendshop.business.service.locator.SortServiceLocator;
import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.model.KeyValueEntity;
import com.legendshop.model.ProdSearchArgs;
import com.legendshop.model.entity.Sort;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.SortService;
import com.legendshop.util.constant.ProductTypeEnum;

/**
 * 产品分类控制器。.
 */
@Controller
public class SortController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SortController.class);

	/** The sort service locator. */
	@Autowired
	private SortServiceLocator sortServiceLocator;

	/**
	 * Topsort.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/topsort")
	public String topsort(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("sortList",
				sortServiceLocator.getSortService().getSort(ThreadLocalContext.getCurrentShopName(request, response), true));
		return PathResolver.getPath(request, response, FrontPage.TOP_SORT);
	}

	/**
	 * 
	 * 查找二级商品分类下的商品
	 * 
	 * 以中横线为分割
	 */
	@RequestMapping("/nsort/{sortId}-{nsortId}")
	public String nsort(HttpServletRequest request, HttpServletResponse response, @PathVariable Long sortId,
			@PathVariable Long nsortId) {
		if (nsortId == null || sortId == null) {
			log.error("sortId or nsortId is null! ");
			return PathResolver.getPath(request, response, FowardPage.INDEX_QUERY);
		}
		Sort sort = sortServiceLocator.getSort(sortId);
		return sortServiceLocator.getConcreteService(request, response, TilesPage.NSORT).getSecSort(request, response, sort,
				nsortId, null);
	}

	/**
	 * 
	 * 查找三级商品分类下的商品 以中横线为分割
	 */
	@RequestMapping("/snsort/{sortId}-{nsortId}-{subnsortId}")
	public String snsort(HttpServletRequest request, HttpServletResponse response, @PathVariable Long sortId,
			@PathVariable Long nsortId, @PathVariable Long subnsortId) {
		if (nsortId == null || sortId == null) {
			log.error("sortId or nsortId is null! ");
			return PathResolver.getPath(request, response, FowardPage.INDEX_QUERY);
		}
		Sort sort = sortServiceLocator.getSort(sortId);
		return sortServiceLocator.getConcreteService(request, response, TilesPage.NSORT).getSecSort(request, response, sort,
				nsortId, subnsortId);
	}

	// 二级分类查询
	@RequestMapping("/nsortlist/{sortId}-{nsortId}")
	public String nsortList(HttpServletRequest request, HttpServletResponse response, String curPageNO, @PathVariable Long sortId,
			@PathVariable Long nsortId, ProdSearchArgs args) {
		Sort sort = sortServiceLocator.getSort(sortId);
		return sortServiceLocator.getConcreteService(request, response, TilesPage.NSORT, sort).parseSort(request, response,
				curPageNO, sortId, args);
	}

	/**
	 * Nsort.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param sortId
	 *            the sort id
	 * @param nsortId
	 *            the nsort id
	 * @param subNsortId
	 *            the sub nsort id
	 * @return the string
	 */
	@RequestMapping("/nsort")
	public String nsort(HttpServletRequest request, HttpServletResponse response, Long sortId, Long nsortId, Long subNsortId) {
		if (nsortId == null || sortId == null) {
			log.error("sortId or nsortId is null! ");
			return PathResolver.getPath(request, response, FowardPage.INDEX_QUERY);
		}
		Sort sort = sortServiceLocator.getSort(sortId);
		return sortServiceLocator.getConcreteService(request, response, TilesPage.NSORT).getSecSort(request, response, sort,
				nsortId, subNsortId);
	}

	/**
	 * Sort.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param sortId
	 *            the sort id
	 * @param order
	 *            the order
	 * @param seq
	 *            the seq
	 * @return the string
	 */
	@RequestMapping("/sort/{sortId}")
	public String sort(HttpServletRequest request, HttpServletResponse response, String curPageNO, @PathVariable Long sortId,
			String order, String seq) {
		if (sortId == null) {
			return PathResolver.getPath(request, response, FowardPage.INDEX_QUERY);
		}
		try {
			Sort sort = sortServiceLocator.getSort(sortId);
			return sortServiceLocator.getConcreteService(request, response, TilesPage.PRODUCT_SORT, sort).parseSort(request,
					response, curPageNO, sort);
		} catch (Exception e) {
			log.error("query sort error",e);
			return PathResolver.getPath(request, response, FowardPage.INDEX_QUERY);
		}

	}
	
	/**
	 * Sort list.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param sortId
	 *            the sort id
	 * @param order
	 *            the order
	 * @param seq
	 *            the seq
	 * @return the string
	 */
	@RequestMapping("/sortlist/{sortId}")
	public String sortList(HttpServletRequest request, HttpServletResponse response, String curPageNO, @PathVariable Long sortId,
			String order, String seq) {
		Sort sort = sortServiceLocator.getSort(sortId);
		return sortServiceLocator.getConcreteService(request, response, TilesPage.PRODUCT_SORT, sort).sortList(request, response,
				sort);
	}

	/**
	 * Prod sort list. 在商品分类页面中异步取得商品数据 refer to productsort.jsp
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param sortId
	 *            the sort id
	 * @param args
	 *            the args
	 * @return the string
	 */
	@RequestMapping("/prodsortlist/{sortId}")
	public String prodSortList(HttpServletRequest request, HttpServletResponse response, String curPageNO,
			@PathVariable Long sortId, ProdSearchArgs args) {
		Sort sort = sortServiceLocator.getSort(sortId);
		return sortServiceLocator.getConcreteService(request, response, TilesPage.PRODUCT_SORT, sort).parseSort(request, response,
				curPageNO, sortId, args);
	}

	@RequestMapping("/prodnsortlist/{sortId}-{nsortId}-{subnsortId}")
	public String prodNSortList(HttpServletRequest request, HttpServletResponse response, String curPageNO,
			@PathVariable Long sortId, @PathVariable Long nsortId, @PathVariable Long subnsortId, ProdSearchArgs args) {
		Sort sort = sortServiceLocator.getSort(sortId);
		return sortServiceLocator.getConcreteService(request, response, TilesPage.NSORT, sort).parseSecSort(request, response,
				curPageNO, sortId, nsortId, subnsortId, args);
	}

	/**
	 * Sort by id.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param sortId
	 *            the sort id
	 * @param order
	 *            the order
	 * @param seq
	 *            the seq
	 * @return the string
	 */
	@RequestMapping("/sort")
	public String sortById(HttpServletRequest request, HttpServletResponse response, String curPageNO, Long sortId, String order,
			String seq) {
		if (sortId == null) {
			return PathResolver.getPath(request, response, FowardPage.INDEX_QUERY);
		}
		Sort sort = sortServiceLocator.getSort(sortId);
		return sortServiceLocator.getConcreteService(request, response, TilesPage.PRODUCT_SORT, sort).parseSort(request, response,
				curPageNO, sort);
	}

	/**
	 * All sorts.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param curPageNO
	 *            the cur page no
	 * @param sortId
	 *            the sort id
	 * @return the string
	 */
	@RequestMapping("/allsorts")
	public String allSorts(HttpServletRequest request, HttpServletResponse response, String curPageNO, Long sortId) {
		List<Sort> sortList = sortServiceLocator.getSortService().getSort(ThreadLocalContext.getCurrentShopName(request, response),
				true);
		request.setAttribute("sortList", sortList);
		request.setAttribute("halfSize", sortList.size() / 2);
		return PathResolver.getPath(request, response, TilesPage.ALL_SORTS);
	}
	
	/**
	 * 用于填充一级商品分类的select options
	 * @param request
	 * @param response
	 * @param sortType
	 * @return
	 */
	@RequestMapping("/sort/loadSorts/{sortId}")
	public @ResponseBody
	List<KeyValueEntity> loadSorts(HttpServletRequest request, HttpServletResponse response,@PathVariable Long sortId) {
		SortService sortService = sortServiceLocator.getSortService();
		Sort sort = sortService.getSortById(sortId);
		if(sort == null){
			throw new NotFoundException("Can not found product category");
		}
		return sortService.loadSorts(sort.getUserName(), ProductTypeEnum.PRODUCT.value());
	}
	
	/**
	 * 用于填充团购一级商品分类的select options
	 * @param request
	 * @param response
	 * @param sortType
	 * @return
	 */
	@RequestMapping("/sort/loadGroupSorts/{sortId}")
	public @ResponseBody
	List<KeyValueEntity> loadGroupSorts(HttpServletRequest request, HttpServletResponse response,@PathVariable Long sortId) {
		SortService sortService = sortServiceLocator.getSortService();
		Sort sort = sortService.getSortById(sortId);
		if(sort == null){
			throw new NotFoundException("Can not found product category");
		}
		return sortService.loadSorts(sort.getUserName(), ProductTypeEnum.GROUP.value());
	}
	
	/**
	 * 用于填充一级商品分类的select options
	 * @param request
	 * @param response
	 * @param sortType
	 * @return
	 */
	@RequestMapping("/sort/loadSorts")
	public @ResponseBody
	List<KeyValueEntity> loadSorts(HttpServletRequest request, HttpServletResponse response) {
		String userNmae = UserManager.getUserName(request);
		SortService sortService = sortServiceLocator.getSortService();
		return sortService.loadSorts(userNmae, ProductTypeEnum.PRODUCT.value());
	}
	
	/**
	 * 
	 *  用于填充团购一级商品分类的select options
	 */
	@RequestMapping("/sort/loadGroupSorts")
	public @ResponseBody
	List<KeyValueEntity> loadGroupSorts(HttpServletRequest request, HttpServletResponse response) {
		String userNmae = UserManager.getUserName(request);
		SortService sortService = sortServiceLocator.getSortService();
		return sortService.loadSorts(userNmae, ProductTypeEnum.GROUP.value());
	}
	
	/**
	 * 用于填充二级商品分类的select options
	 * @param request
	 * @param response
	 * @param sortId
	 * @return
	 */
	@RequestMapping("/sort/loadNSorts/{sortId}")
	public @ResponseBody
	List<KeyValueEntity> loadNSorts(HttpServletRequest request, HttpServletResponse response, @PathVariable Long sortId) {
		return sortServiceLocator.getSortService().loadNSorts(sortId);
	}
	
	/**
	 * 用于填充三级商品分类的select options
	 * @param request
	 * @param response
	 * @param nsortId
	 * @return
	 */
	@RequestMapping("/sort/loadSubNSorts/{nsortId}")
	public @ResponseBody
	List<KeyValueEntity> loadSubNSorts(HttpServletRequest request, HttpServletResponse response, @PathVariable Long nsortId) {
		return sortServiceLocator.getSortService().loadSubNSorts(nsortId);
	}

	/**
	 * Sets the sort service locator.
	 * 
	 * @param sortServiceLocator
	 *            the sortServiceLocator to set
	 */
	public void setSortServiceLocator(SortServiceLocator sortServiceLocator) {
		this.sortServiceLocator = sortServiceLocator;
	}

}
