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

import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.exception.ErrorCodes;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ResourceBundleHelper;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.core.helper.VisitHistoryHelper;
import com.legendshop.core.model.UserMessages;
import com.legendshop.event.EventHome;
import com.legendshop.model.entity.ImgFile;
import com.legendshop.model.entity.Product;
import com.legendshop.model.entity.ProductDetail;
import com.legendshop.model.entity.ShopDetailView;
import com.legendshop.model.visit.VisitHistory;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.constants.PageADV;
import com.legendshop.spi.constants.VisitTypeEnum;
import com.legendshop.spi.event.VisitLogEvent;
import com.legendshop.spi.page.FowardPage;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.ImgFileService;
import com.legendshop.spi.service.ProductService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.constant.ProductTypeEnum;

/**
 * 产品分类控制器。.
 */
@Controller
public class ProductController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ImgFileService imgFileService;


	@RequestMapping("/views/{prodId}")
	public String views(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId) {
		if (prodId == null) {
			return PathResolver.getPath(request, response, FowardPage.INDEX_QUERY);
		}
		ProductDetail prod = productService.getProdDetail(prodId);

		if (prod != null) {
			if (ProductTypeEnum.GROUP.value().equals(prod.getProdType())) {
				return PathResolver.getPath(request, response, "/group/view/" + prodId, FowardPage.VARIABLE);
			}
			if (!Constants.ONLINE.equals(prod.getStatus())) {
				throw new NotFoundException("产品 " + prod.getName() + " 已经下线.");
			}
			// 查看商品的说明图片
			List<ImgFile> prodPics = imgFileService.getProductPics(prod.getUserName(), prodId);
			if (AppUtils.isNotBlank(prodPics)) {
				request.setAttribute("prodPics", prodPics);
			}
			// 商家详细说明
			ShopDetailView shopDetail = ThreadLocalContext.getShopDetailView(request, response, prod.getUserName());
			if (shopDetail == null) {
				return PathResolver.getPath(request, response, FrontPage.TOPALL);
			}

			//ThreadLocalContext.setCurrentShopName(request, response, prod.getUserName());
			// 相关商品
			List<Product> releationProds = productService.getReleationProd(prod.getUserName(), prod.getProdId(), 30);
			if (!AppUtils.isBlank(releationProds)) {
				request.setAttribute("productList", releationProds);
			}

			request.setAttribute("prod", prod);

			// 商品评论

			// 广告
			productService.getAndSetAdvertisement(request, response, prod.getUserName(), PageADV.PROD.name());

			// 更新查看次数
			if (PropertiesUtil.getObject(SysParameterEnum.VISIT_HW_LOG_ENABLE, Boolean.class)) {
				productService.updateProdViews(prodId);
			}

			String userName = UserManager.getUserName(request.getSession());

			if (log.isInfoEnabled()) {
				log.info("{},{},{},{},viewsprod", new Object[] { request.getRemoteAddr(), userName == null ? "" : userName,
						ThreadLocalContext.getCurrentShopName(request, response), prod.getName() });
			}

			// 记录商品访问历史
			VisitHistoryHelper.visit(prod, request, response);

			// 商品推荐
			List<ProductDetail> recommendProds = productService.getRecommendProd(prod.getProdId());
			if (AppUtils.isNotBlank(recommendProds)) {
				request.setAttribute("recommendProds", recommendProds);
			}

			// 多线程记录访问历史
			if (PropertiesUtil.getObject(SysParameterEnum.VISIT_LOG_ENABLE, Boolean.class)) {
				EventHome.publishEvent(new VisitLogEvent(request.getRemoteAddr(), prod.getUserName(), userName, prod.getProdId(),
						prod.getName(), VisitTypeEnum.PROD.value()));
			}
			return PathResolver.getPath(request, response, TilesPage.VIEWS);
		} else {
			UserMessages uem = new UserMessages();
			uem.setTitle(ResourceBundleHelper.getString("product.not.found"));
			uem.setDesc(ResourceBundleHelper.getString("product.status.check"));
			uem.setCode(ErrorCodes.ENTITY_NO_FOUND);
			request.setAttribute(UserMessages.MESSAGE_KEY, uem);
			return PathResolver.getPath(request, response, FrontPage.FAIL);
		}
	}

	/**
	 * Visit.
	 * 
	 * @param prod
	 *            the prod
	 * @param request
	 *            the request
	 */
	@Deprecated
	private void visit(ProductDetail prod, HttpServletRequest request) {
		VisitHistory visitHistory = (VisitHistory) request.getSession().getAttribute(Constants.VISIT_HISTORY);
		if (visitHistory == null) {
			visitHistory = new VisitHistory();
		}
		visitHistory.visit(prod);
		request.getSession().setAttribute(Constants.VISIT_HISTORY, visitHistory);
	}

	/**
	 * Hotsale.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/hotsale")
	public String hotsale(HttpServletRequest request, HttpServletResponse response) {
		String shopName = ThreadLocalContext.getCurrentShopName(request, response);
		List<Product> hotsaleList = productService.getHotSale(shopName);
		if (AppUtils.isNotBlank(hotsaleList)) {
			request.setAttribute("hotsaleList", hotsaleList);
		}
		return PathResolver.getPath(request, response, FrontPage.HOT_SALE);
	}

	/**
	 * Hoton.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param sortId
	 *            the sort id
	 * @return the string
	 */
	@RequestMapping("/hoton/{sortId}")
	public String hoton(HttpServletRequest request, HttpServletResponse response, @PathVariable Long sortId) {
		List<Product> hotonList = productService.getHotOn(ThreadLocalContext.getCurrentShopName(request, response), sortId);
		if (AppUtils.isNotBlank(hotonList)) {
			request.setAttribute("hotonList", hotonList);
		}

		return PathResolver.getPath(request, response, FrontPage.HOT_ON);
	}

	/**
	 * 热门产品.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/hotview")
	public String hotView(HttpServletRequest request, HttpServletResponse response) {
		String shopName = ThreadLocalContext.getCurrentShopName(request, response);
		List<Product> hotViewList = productService.getHotViewProd(shopName, 10);
		if (AppUtils.isNotBlank(hotViewList)) {
			request.setAttribute("hotViewList", hotViewList);
		}
		return PathResolver.getPath(request, response, FrontPage.HOT_VIEW);
	}

	/**
	 * Hot comments. 热门评论
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param sortId
	 *            the sort id
	 * @return the string
	 */
	@RequestMapping("/hotcomments/{sortId}")
	public String hotComments(HttpServletRequest request, HttpServletResponse response, @PathVariable Long sortId) {
		String shopName = ThreadLocalContext.getCurrentShopName(request, response);
		List<Product> hotCommentList = productService.getHotComment(shopName, sortId, 10);
		if (AppUtils.isNotBlank(hotCommentList)) {
			request.setAttribute("hotCommentList", hotCommentList);
		}
		return PathResolver.getPath(request, response, FrontPage.HOT_COMMENT);
	}

	/**
	 * Hot recommend. 热门推荐
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param sortId
	 *            the sort id
	 * @return the string
	 */
	@RequestMapping("/hotrecommends/{sortId}")
	public String hotRecommend(HttpServletRequest request, HttpServletResponse response, @PathVariable Long sortId) {
		String shopName = ThreadLocalContext.getCurrentShopName(request, response);
		List<Product> hotRecommendList = productService.getHotRecommend(shopName, sortId, 3);
		if (AppUtils.isNotBlank(hotRecommendList)) {
			request.setAttribute("hotRecommendList", hotRecommendList);
		}
		return PathResolver.getPath(request, response, FrontPage.HOT_RECOMMEND);
	}

	/**
	 * Product gallery.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param prodId
	 *            the prod id
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping("/productGallery/{prodId}")
	public String productGallery(HttpServletRequest request, HttpServletResponse response, @PathVariable Long prodId)
			throws Exception {
		if (AppUtils.isBlank(prodId)) {
			return PathResolver.getPath(request, response, FowardPage.INDEX_QUERY);
		}
		return productService.getProductGallery(request, response, prodId);
	}

	/**
	 * All prods.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping("/allprods")
	public String allProds(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return PathResolver.getPath(request, response, TilesPage.ALL_PRODS);
	}

	/**
	 * Visited history. 商品查看历史
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/visitedprod")
	public String visitedProd(HttpServletRequest request, HttpServletResponse response) {
		List<ProductDetail> products = productService.getVisitedProd(request);
		request.setAttribute("visitedProd", products);
		return PathResolver.getPath(request, response, FrontPage.VISITED_PROD);
	}

}
