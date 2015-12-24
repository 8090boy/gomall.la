/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.payment.tenpay.util;

import java.util.HashMap;
import java.util.Map;

import com.legendshop.util.AppUtils;

/* *
 *类名：TenPaySubmit
 *功能：PayPal各接口请求提交类
 *详细：构造PayPal各接口表单HTML文本，获取远程HTTP数据
 *版本：3.2
 *日期：2011-03-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究PayPal接口使用，只是提供一个参考。
 */

/**
 * The Class TenPaySubmit.
 */
public class TenPaySubmit {

	
	
	/**
	 * 生成要请求给PayPal的参数数组.
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @param validateKey
	 *            the validate key
	 * @return 要请求的参数数组
	 */
	private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String validateKey) {
		
		Map<String, String> sPara = paraFilter(sParaTemp);
		StringBuffer sb = new StringBuffer();
		for(String key : TenPayConfig.payKeys){
			String value =  sPara.get(key);
			if(AppUtils.isNotBlank(value)){
				  sb.append(key + "=" + value + "&");
			}
		}
		//组织签名
		sb.append("key=" + validateKey);
		//System.out.println("Sign String == " + sb.toString());
		//算出摘要
		String sign = MD5Util.MD5Encode(sb.toString(), TenPayConfig.input_charset).toLowerCase();
		sPara.put("sign", sign);
		
		return sPara;
	}
	
	/**
	 * 除去数组中的空值和签名参数.
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
//			if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
//				continue;
//			}
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	 /**
	 * 构造模拟远程HTTP的POST请求，获取PayPal的返回XML处理结果.
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param gateway
	 *            网关地址
	 * @param validateKey
	 *            the validate key
	 * @return PayPal返回XML处理结果
	 * @throws Exception
	 *             the exception
	 *//*
	public static String sendPostInfo(Map<String, String> sParaTemp, String gateway, String validateKey) throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp, validateKey);

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(AlipayConfig.input_charset);

		request.setParameters(generatNameValuePair(sPara));
		request.setUrl(gateway + "_input_charset=" + AlipayConfig.input_charset);

		HttpResponse response = httpProtocolHandler.execute(request);
		if (response == null) {
			return null;
		}

		String strResult = response.getStringResult();

		return strResult;
	}*/

	/**
	 * 构造提交表单HTML数据 直接支付接口.
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param gateway
	 *            网关地址
	 * @param strMethod
	 *            提交方式。两个值可选：post、get
	 * @param strButtonName
	 *            确认按钮显示文字
	 * @param validateKey
	 *            the validate key
	 * @return 提交表单HTML文本
	 */
	public static String buildForm(Map<String, String> sParaTemp, String gateway, String strMethod, String strButtonName,
			String validateKey) {
		
		Map<String, String> sPara = buildRequestPara(sParaTemp, validateKey);
		// 待请求参数数组
		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form id=\"TenPaySubmit\" name=\"TenPaySubmit\" action=\"" + gateway + "\" method=\"" + strMethod + "\">");

		for(Map.Entry<String, String> entry : sPara.entrySet()){
			
			String name = entry.getKey();
			String value = entry.getValue();

			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['TenPaySubmit'].submit();</script>");
		return sbHtml.toString();
	}

}
