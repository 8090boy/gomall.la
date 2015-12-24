/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.payment.processor.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legendshop.model.entity.PayType;
import com.legendshop.payment.kq.KQService;
import com.legendshop.payment.kq.util.KQConfig;
import com.legendshop.spi.processor.PaymentProcessor;

/**
 * 快钱支付
 * 
 * 官方网站：http://www.legendesign.net
 */
public class KQPayProcessorImpl implements PaymentProcessor {
	/** The log. */
	private static Logger log = LoggerFactory.getLogger(KQPayProcessorImpl.class);

	@Override
	public String getName() {
		return "快钱支付";
	}

	@Override
	public String payto(String shopName, String userName, String payTypeId,
			String out_trade_no, String subject, String body, String price,
			String ip) {
		// TODO Auto-generated method stub

		PayType payType = KQConfig.getPayType(shopName, payTypeId);
		String seller_email = payType.getSellerEmail();
		String partner = payType.getPartner();
		String show_url = KQConfig.show_url;

		//人民币网关账户号
		///请登录快钱系统获取用户编号，用户编号后加01即为人民币网关账户号。
		String merchantAcctId=payType.getSellerEmail();

		//人民币网关密钥
		///区分大小写.请与快钱联系索取
		String key=payType.getValidateKey();

		//字符集.固定选择值。可为空。
		///只能选择1、2、3.
		///1代表UTF-8; 2代表GBK; 3代表gb2312
		///默认值为1
		String inputCharset=KQConfig.inputCharset;

		//接受支付结果的页面地址.与[bgUrl]不能同时为空。必须是绝对地址。
		///如果[bgUrl]为空，快钱将支付结果Post到[pageUrl]对应的地址。
		///如果[bgUrl]不为空，并且[bgUrl]页面指定的<redirecturl>地址不为空，则转向到<redirecturl>对应的地址
		String pageUrl= KQConfig.return_url;

		//服务器接受支付结果的后台地址.与[pageUrl]不能同时为空。必须是绝对地址。
		///快钱通过服务器连接的方式将交易结果发送到[bgUrl]对应的页面地址，在商户处理完成后输出的<result>如果为1，页面会转向到<redirecturl>对应的地址。
		///如果快钱未接收到<redirecturl>对应的地址，快钱将把支付结果post到[pageUrl]对应的页面。
		String bgUrl=KQConfig.notify_url;
			
		//网关版本.固定值
		///快钱会根据版本号来调用对应的接口处理程序。
		///本代码版本号固定为v2.0
		String version=KQConfig.version;

		//语言种类.固定选择值。
		///只能选择1、2、3
		///1代表中文；2代表英文
		///默认值为1
		String language=KQConfig.language;

		//签名类型.固定值
		///1代表MD5签名
		///当前版本固定为1
		String signType="1";
		   
		//支付人姓名
		///可为中文或英文字符
		String payerName= subject;

		//支付人联系方式类型.固定选择值
		///只能选择1
		///1代表Email
		String payerContactType=KQConfig.payerContactType;

		//支付人联系方式
		///只能选择Email或手机号
		String payerContact="";

		//商户订单号
		///由字母、数字、或[-][_]组成
		String orderId= out_trade_no;

		//订单金额
		///以分为单位，必须是整型数字
		///比方2，代表0.02元
		String orderAmount= String.valueOf(Double.parseDouble(price) * 100) ;
			
		//订单提交时间
		///14位数字。年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
		///如；20080101010101
		String orderTime=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

		//商品名称
		///可为中文或英文字符
		String productName=subject;

		//商品数量
		///可为空，非空时必须为数字
		String productNum="1";

		//商品代码
		///可为字符或者数字
		String productId="";

		//商品描述
		String productDesc="";
			
		//扩展字段1
		///在支付结束后原样返回给商户
		String ext1="";

		//扩展字段2
		///在支付结束后原样返回给商户
		String ext2="";
			
		//支付方式.固定选择值
		///只能选择00、10、11、12、13、14
		///00：组合支付（网关支付页面显示快钱支持的各种支付方式，推荐使用）10：银行卡支付（网关支付页面只显示银行卡支付）.11：电话银行支付（网关支付页面只显示电话支付）.12：快钱账户支付（网关支付页面只显示快钱账户支付）.13：线下支付（网关支付页面只显示线下支付方式）.14：B2B支付（网关支付页面只显示B2B支付，但需要向快钱申请开通才能使用）
		String KQPayType= KQConfig.payType;

		//银行代码
		///实现直接跳转到银行页面去支付,只在payType=10时才需设置参数
		///具体代码参见 接口文档银行代码列表
		String bankId="";

		//同一订单禁止重复提交标志
		///固定选择值： 1、0
		///1代表同一订单号只允许提交1次；0表示同一订单号在没有支付成功的前提下可重复提交多次。默认为0建议实物购物车结算类商户采用0；虚拟产品类商户采用1
		String redoFlag="0";

		//快钱的合作伙伴的账户号
		///如未和快钱签订代理合作协议，不需要填写本参数
		String pid="";

		//生成加密签名串
		///请务必按照如下顺序和规则组成加密串！
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new LinkedHashMap<String, String>(); //有序MAP
		sParaTemp.put("inputCharset",inputCharset);
		sParaTemp.put("pageUrl",pageUrl);
		sParaTemp.put("bgUrl",bgUrl);
		sParaTemp.put("version",version);
		sParaTemp.put("language",language);
		sParaTemp.put("signType",signType);
		sParaTemp.put("merchantAcctId",merchantAcctId);
		sParaTemp.put("payerName",payerName);
		sParaTemp.put("payerContactType",payerContactType);
		sParaTemp.put("payerContact",payerContact);
		sParaTemp.put("orderId",orderId);
		sParaTemp.put("orderAmount",orderAmount);
		sParaTemp.put("orderTime",orderTime);
		sParaTemp.put("productName",productName);
		sParaTemp.put("productNum",productNum);
		sParaTemp.put("productId",productId);
		sParaTemp.put("productDesc",productDesc);
		sParaTemp.put("ext1",ext1);
		sParaTemp.put("ext2",ext2);
		sParaTemp.put("payType",KQPayType);
		sParaTemp.put("bankId",bankId);
		sParaTemp.put("redoFlag",redoFlag);
		sParaTemp.put("pid",pid);
		sParaTemp.put("key",key);
		 
		
		return KQService.create_direct_pay_by_user(sParaTemp, payType.getValidateKey());
	}
 
}
