package com.legendshop.business.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import la.gomall.web.util.UtilRest;
import la.gomall.web.util.UtilSMS;
import la.gomall.web.util.UtilVerify;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.legendshop.core.UserManager;
import com.legendshop.core.base.BaseController;
import com.legendshop.core.constant.FunctionEnum;
import com.legendshop.core.constant.PathResolver;
import com.legendshop.core.constant.SysParameterEnum;
import com.legendshop.core.exception.BusinessException;
import com.legendshop.core.exception.NotFoundException;
import com.legendshop.core.helper.FileProcessor;
import com.legendshop.core.helper.PropertiesUtil;
import com.legendshop.core.helper.ThreadLocalContext;
import com.legendshop.core.randing.CaptchaServiceSingleton;
import com.legendshop.event.EventContext;
import com.legendshop.event.EventHome;
import com.legendshop.event.GenericEvent;
import com.legendshop.model.ValidationMessage;
import com.legendshop.model.entity.ShopDetail;
import com.legendshop.model.entity.UserDetail;
import com.legendshop.spi.constants.Constants;
import com.legendshop.spi.event.EventId;
import com.legendshop.spi.form.UserForm;
import com.legendshop.spi.page.FrontPage;
import com.legendshop.spi.page.TilesPage;
import com.legendshop.spi.service.BasketService;
import com.legendshop.spi.service.LoginService;
import com.legendshop.spi.service.UserDetailService;
import com.legendshop.spi.service.impl.DefaultLoginServiceImpl;
import com.legendshop.spi.service.timer.SubService;
import com.legendshop.util.AppUtils;
import com.legendshop.util.ContextServiceLocator;
import com.legendshop.util.MD5Util;

/**
 * 用户控制器
 * 
 */
@Controller
public class UserController extends BaseController {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private SubService subService;

	@Autowired
	private BasketService basketService;

	@Autowired
	private LoginService loginService;
	
	
	/**
	 * 是否需要设置账户，0不显示填写，1显示填写
	 * @param request
	 * @param response
	 * @param iiiiii
	 */
	@RequestMapping("/clubRs")
	public void mySN(HttpServletRequest request, HttpServletResponse response , String i) {
		String userName = UserManager.getUserName(request);
		String inNoA = (String) request.getSession().getAttribute("isNo");
		if (userName == null) {
			UtilRest.restJson("0", response);
			return ;
		}
		
		//是下次
		if( inNoA != null ){
			System.out.println("我已经设置了下次完善iAlipay帐号");
			UtilRest.restJson("0", response);
			return ;
		}else{
			//本次
			if( i != null ){
				System.out.println("下次完善iAlipay帐号");
				request.getSession().setAttribute("isNo", "next");
				UtilRest.restJson("0", response);
				return ;
			}
			
			UserDetail userDetail = userDetailService.getUserDetail(userName);
			if (userDetail == null) {
				System.out.println("----系统不存在此账户------");
				UtilRest.restJson("0", response);
				return ;
			}

		//	System.out.println(  "userDetail.getScore()===" + userDetail.getScore()  );
			if ( userDetail.getScore() == null ) {
				System.out.println("----积分为空，不提示设置帐号------");
				userDetail.setScore(0l);
				userDetailService.updateUser(userDetail);
				UtilRest.restJson("0", response);
				return ;
			}
			//积分是否达到可兑换基点
			if ( userDetail.getScore() > 0 ) {
				// 查看我们的支付宝信息是否存在，纯在就返回"0"
				if( userDetail.getiAlipay() == null ||  userDetail.getiAlipay() == "" ||  userDetail.getiAlipay().getBytes().length < 10   ){
					UtilRest.restJson("1", response);
					return ;
				}else{
					//String iAlipay = payName + "|" + payId + "|" + note;
					String[] iAlipay = userDetail.getiAlipay().split("\\|");
//					System.out.println( "---- iAlipay[0]--payName--" +  iAlipay[0]  );
//					System.out.println( "---- iAlipay[1]--payId-------" +  iAlipay[1]  );
//					System.out.println( "---- iAlipay[2]--note--------" +  iAlipay[2]  );
					myClub(request,response, iAlipay[0],iAlipay[1],iAlipay[2] );
					return ;
				}
			}
		}
	}
	
	

	@RequestMapping("/myClub")
	public void myClub(HttpServletRequest request, HttpServletResponse response, String payName, String payId, String note) {
	//	System.out.println("----------myClub----------");
		String userName = UserManager.getUserName(request);
		if (userName == null) {
			UtilRest.restJson("0", response);
			return;
		}
		UserDetail userDetail = userDetailService.getUserDetail(userName);

		if (userDetail.getiAlipay() == null || userDetail.getiAlipay() == "" || userDetail.getiAlipay().getBytes().length < 10) {

			if (payName == null || payId == null || note == null) {
				UtilRest.restJson("0", response);
				return;
			}
			if (UtilVerify.isCnName(payName) == true && UtilVerify.isMobilesOrEmail(payId) == true && UtilVerify.isNotEmpty(note) == true) {
				note = UtilVerify.replaceStr(note);
				note = new MD5Util().getMD5ofStr(note);
				String iAlipay = payName + "|" + payId + "|" + note;
				System.out.println("iAlipay==" + iAlipay);
				userDetail.setiAlipay(iAlipay);
				userDetailService.updateUser(userDetail);
			} else {
				System.out.println("---------iAlipay 数据格式错误--------");
				UtilRest.restJson("0", response);
				return;
			}
		}

		addSn(userDetail, request, response);
	}

	/**
	 * 加入用户序列号
	 * 
	 * @param userDetail
	 * @param request
	 * @param response
	 * @return
	 */
	public String addSn(UserDetail userDetail, HttpServletRequest request, HttpServletResponse response) {

		String myAlipay = userDetail.getiAlipay();
	 
		//String uri = "http://localhost:88/RedSystem/au.go";
 	String uri = "http://localhost:88/au.go";
		// 手机|支付宝姓名|支付宝账户|用户加密问题|商城密码|用户注册IP地址
		String myRegSnData = userDetail.getUserName() + "|" 
		+ myAlipay + "|" 
		+ userDetail.getPassword() + "|" 
		+ userDetail.getUserRegip() + "|" 
		+ userDetail.getScore() + 2000;
		System.out.println("myRegSnData==="+ myRegSnData  +"，"+ uri );
		HttpClient hClient = new HttpClient();
		PostMethod post = new PostMethod(uri);
		post.setParameter("str", myRegSnData);
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		String result = "0";
		try {
			hClient.executeMethod(post);
			// 得到返回的字符串
			result = post.getResponseBodyAsString();
			if( result.getBytes().length != 1 ){
				System.out.println("返回异常UserController 205 行");
				return result;
			}
		} catch (HttpException e) {
			System.out.println("Http 发送数据失败！HttpException");
			return result;
		} catch (IOException e) {
			System.out.println("IO异常！IOException");
			return result;
		} finally {
			// 是否正确执行，这里要关闭连接
			post.releaseConnection();
		}
		int resultInt = 0;
		try {
			resultInt = Integer.parseInt(result);
			if (resultInt > 0) {
				System.out.println("获取序列号成功！");
				return "1";
			}
		} catch (Exception e) {
			System.out.println("返回数据转换异常！");
		}
		return result;
	}

	@RequestMapping("/p/myaccount")
	public String myaccount(HttpServletRequest request, HttpServletResponse response) {
		String userName = UserManager.getUserName(request);
		if (userName == null) {
			return PathResolver.getPath(request, response, TilesPage.LOGIN);
		}
		String viewName = request.getParameter("userName");
		if (AppUtils.isNotBlank(viewName)) {
			if (UserManager.hasFunction(request.getSession(), FunctionEnum.FUNCTION_VIEW_ALL_DATA.value())) { // 保留，只能超级管理员可以看
				userName = viewName;
				request.setAttribute("isAdmin", true); // 管理员不可操作
			}
		}
		UserDetail userDetail = userDetailService.getUserDetail(userName);
		if (userDetail == null) {
			log.error("userDetail not found, userName = " + userName);
			throw new NotFoundException("user not found");
		}
		// 如果加入即会返回当前用户的当铺
		ShopDetail shopDetail = userDetail.getShopDetail();
		if (shopDetail != null) {
			request.setAttribute("myShopDetail", shopDetail);
		}

		if (userDetail.getBirthDate() != null) {
			setBirthDate(userDetail.getBirthDate(), request);
		}
		if (userDetail.getScore() == null) {
			userDetail.setScore(0l);// 默认
		}
		request.setAttribute("user", userDetail);
		EventContext eventContext = new EventContext(request);
		EventHome.publishEvent(new GenericEvent(eventContext, EventId.CAN_ADD_SHOPDETAIL_EVENT));
		request.setAttribute("supportOpenShop", eventContext.getBooleanResponse());
		request.setAttribute("totalProcessingOrder", subService.getTotalProcessingOrder(userName));
		request.setAttribute("totalBasketByuserName", basketService.getTotalBasketByUserName(userName));
		userDetailService.getAndSetOneAdvertisement(request, response, ThreadLocalContext.getCurrentShopName(request, response), Constants.USER_REG_ADV_740);
		return PathResolver.getPath(request, response, TilesPage.MYACCOUNT);
	}

	/**
	 * Shopcontact.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/shopcontact")
	public String shopcontact(HttpServletRequest request, HttpServletResponse response) {
		String shopName = request.getParameter("shop");
		if (shopName == null) {
			shopName = ThreadLocalContext.getCurrentShopName(request, response);
		}
		if (shopName == null) {
			return PathResolver.getPath(request, response, TilesPage.SEARCHALL);
		}
		UserDetail userDetail = userDetailService.getUserDetail(shopName);
		// 如果加入即会返回当前用户的当铺
		request.setAttribute("user", userDetail);
		return PathResolver.getPath(request, response, TilesPage.SHOPCONTACT);
	}

	/**
	 * Login.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		return PathResolver.getPath(request, response, TilesPage.LOGIN);
	}

	/**
	 * Update account.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param userForm
	 *            the user form
	 * @return the string
	 */
	@RequestMapping("/updateAccount")
	public String updateAccount(HttpServletRequest request, HttpServletResponse response, UserForm userForm) {
		return userDetailService.updateAccount(request, response, userForm);
	}

	/**
	 * 用户注册动作
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param userForm
	 *            the user form
	 * @return the string
	 */
	@RequestMapping("/userReg")
	public String userReg(HttpServletRequest request, HttpServletResponse response, UserForm userForm) {

		userForm.setUserMobile(userForm.getName());
		ValidationMessage message = userForm.validate();
		if (message.isFailed()) {
			log.error("register failed: " + message);
			throw new BusinessException("UserForm validation failed");
		}
		String result = userDetailService.saveUserReg(request, response, userForm);
		if (!PropertiesUtil.getObject(SysParameterEnum.VALIDATION_FROM_MAIL, Boolean.class)) {
			// 用户注册即登录
			getLoginService().onAuthentication(request, response, userForm.getName(), userForm.getPassword());
		}
		return result;
	}

	private LoginService getLoginService() {
		if (loginService == null) {
			if (ContextServiceLocator.getInstance().containsBean("loginService")) {
				loginService = (LoginService) ContextServiceLocator.getInstance().getBean("loginService");
			}
			if (loginService == null) {
				loginService = new DefaultLoginServiceImpl();
			}
		}
		return loginService;
	}

	/**
	 * 加载用户注册页面.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping("/reg")
	public String reg(HttpServletRequest request, HttpServletResponse response) {
		userDetailService.getAndSetOneAdvertisement(request, response, ThreadLocalContext.getCurrentShopName(request, response), Constants.USER_REG_ADV_950);
		EventContext eventContext = new EventContext(request);
		EventHome.publishEvent(new GenericEvent(eventContext, EventId.CAN_ADD_SHOPDETAIL_EVENT));
		request.setAttribute("supportOpenShop", eventContext.getBooleanResponse());
		request.setAttribute("validationOnOpenShop", PropertiesUtil.getObject(SysParameterEnum.VALIDATION_ON_OPEN_SHOP, Boolean.class));
		String content = null;
		try {
			File file = new File(PropertiesUtil.getDownloadFilePath() + "/register/regItem.html");
			content = FileProcessor.readFile(file, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		request.setAttribute("regItem", content);
		return PathResolver.getPath(request, response, TilesPage.REG);
	}

	/**
	 * Adds the shop.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param shopDetail
	 *            the shop detail
	 * @return the string
	 */
	@RequestMapping("/addShop")
	public String addShop(HttpServletRequest request, HttpServletResponse response, ShopDetail shopDetail) {
		// 用户需要登录
		String userName = UserManager.getUserName(request);
		if (AppUtils.isBlank(userName)) {
			return PathResolver.getPath(request, response, TilesPage.NO_LOGIN);
		}
		return userDetailService.saveShop(request, response, shopDetail);
	}

	@RequestMapping("/isUserExist")
	public @ResponseBody
	Boolean isUserExist(String userName) {
		return userDetailService.isUserExist(userName);
	}

	@RequestMapping("/getCheckNo")
	public @ResponseBody
	Boolean getCheckNo(String mob) {
		if (!userDetailService.isUserExist(mob)) {
			if (UtilVerify.isMobileNO(mob)) {
				if (UtilSMS.SendRandomForMob(mob, "1") == true) {
					return true;
				}
			}
		}
		return false;
	}

	@RequestMapping("/isEmailExist")
	public @ResponseBody
	Boolean isEmailExist(String email) {
		return userDetailService.isEmailExist(email);
	}

	/**
	 * User reg success.
	 * 
	 * 用户用registerCode激活帐号
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param userName
	 *            the user name
	 * @param registerCode
	 *            the register code
	 * @return the string
	 */
	@RequestMapping("/userRegSuccess")
	public String userRegSuccess(HttpServletRequest request, HttpServletResponse response, String userName, String registerCode) {
		return userDetailService.updateUserReg(request, response, userName, registerCode);
	}

	/**
	 * Resetpassword.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping("/resetpassword")
	public String resetpassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return PathResolver.getPath(request, response, FrontPage.RESETPASSWORD);
	}

	@RequestMapping("/openShop")
	public String openShop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return PathResolver.getPath(request, response, TilesPage.OPEN_SHOP);
	}

	/**
	 * 重置用户名密码
	 * 
	 * @param userName
	 * @param mail
	 * @return
	 */
	@RequestMapping("/resetPassword")
	public @ResponseBody
	String resetPassword(String userName, String userMail) {
		if (AppUtils.isBlank(userName) || AppUtils.isBlank(userMail)) {
			return "fail";
		}
		try {
			String templateFilePath = PropertiesUtil.getDownloadFilePath() + "/mail/resetpassmail.jsp";
			if (userDetailService.updatePassword(userName, userMail, templateFilePath)) {
				return null;
			} else {
				return "fail";
			}
		} catch (Exception e) {
			log.error("", e);
			return "fail";
		}

	}

	/**
	 * 验证码
	 * 
	 * @param validateCodeParameter
	 * @return
	 */
	@RequestMapping("/validateRandImg")
	public @ResponseBody
	boolean validateRandImg(HttpServletRequest request, HttpServletResponse response, String randNum) {
		HttpSession session = request.getSession();
		if (session == null) {
			return true;
		}
		return CaptchaServiceSingleton.getInstance().validateResponseForID(session.getId(), randNum);
	}

	/**
	 * 设置生日日期
	 * 
	 * @param birthDate
	 *            the birth date
	 * @param request
	 *            the request
	 */
	private void setBirthDate(String birthDate, HttpServletRequest request) {
		try {
			String year = birthDate.substring(0, 4);
			String month = birthDate.substring(4, 6);
			String day = birthDate.substring(6, 8);
			request.setAttribute("userBirthYear", year);
			request.setAttribute("userBirthMonth", month);
			request.setAttribute("userBirthDay", day);
		} catch (Exception e) {

		}

	}

}
