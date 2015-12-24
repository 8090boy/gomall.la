package com.legendshop.payment.model;

import java.io.Serializable;
import java.util.Map;

import com.legendshop.model.entity.Sub;

/**
 * 
 * 支付接口返回的对象
 *
 */
public class PaymentResponse implements Serializable{

	private static final long serialVersionUID = -6359520578113476646L;
	
	/**
	 * 订单
	 */
	private Sub sub;

	private String outOrderNo; // 外部订单号码

	private String key; // 获取支付宝GET过来反馈信息
	
	private Map params; //参数列表
	
	private String tradeNo; //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
	
	private String totalFee; //总金额
	
	private String subject; //商品订单名称

	private String body; //商品描述
	
	private String buyerEmail; //买家支付宝帐号

	private String receiveName; //收货人姓名
	
	private String receiveAddress;//收货人地址
	
	private String receiveZip; // 收货人邮编
	
	private String receivePhone;//收货人电话
	
	private String receiveMobile;//收货人手机
	
	private String tradeStatus ;	//交易状态
	
	String verifyStatus = ""; //验证状态

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getReceiveZip() {
		return receiveZip;
	}

	public void setReceiveZip(String receiveZip) {
		this.receiveZip = receiveZip;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getReceiveMobile() {
		return receiveMobile;
	}

	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public Sub getSub() {
		return sub;
	}

	public void setSub(Sub sub) {
		this.sub = sub;
	}

}
