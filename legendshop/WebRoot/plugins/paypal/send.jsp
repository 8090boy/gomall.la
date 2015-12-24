<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.capinfo.crypt.*"%>
<%
/**
	此页面是向首信易支付提交页面。接口文档的第一部分。表单一共13项。有些参数可以用常量代替。首信不提取消费者的敏感信息。
*/
  String v_mid = "250";		//商户编号，签约由易支付分配。250是测试商户编号。
  String ddate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
  String ddate1= new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
  String v_oid = ddate1+"-"+v_mid+"-"+ddate;  //订单编号，订单编号的格式:yyyymmdd-v_mid-流水号。流水号可以按照自己的订单规则生成，但是要保证每一次提交，订单号是唯一值，否则会出错
  String v_rcvname = request.getParameter("v_rcvname"); //收货人姓名，建议用商户编号代替。
  String v_rcvaddr = request.getParameter("v_rcvaddr"); //收货人姓名，可以用常量
  String v_rcvtel = "010-82652626";  	//收货人电话，可以用常量
  String v_rcvpost = "100080";					//收货人邮编，可以用常量
  String v_amount = request.getParameter("v_amount"); //订单金额
  String v_ymd = ddate1;        //订单日期
  String v_orderstatus = "1";		//配货状态，0-未配齐，1-已配齐。
  String v_ordername = "易支付科技有限公司";  //订货人姓名，可以用常量
  String v_moneytype = "0";  //币种。0-人民币，1-美元。
  String v_url="http://www.sina.com.cn";  //支付完成后返回地址。此地址是支付完成后，订单信息实时的向这个地址做返回。返回参数详见接口文档第二部分。
  String MD5Key="testtest"; //MD5Key,签约后由商户自定义一个16位的数字字母组合作为密钥，发到huangyi@payeasenet.com.说明商户编号，公司名称和密钥。
  //MD5算法
  Md5 md5 = new Md5 ("") ;
  md5.hmac_Md5(v_moneytype+v_ymd+v_amount+v_rcvname+v_oid+v_mid+v_url,MD5Key) ;
  byte b[]= md5.getDigest();
  String digestString = md5.stringify(b) ;
%>

<html>
<title>自定义支付页面</title>
<body>
	<form action="http://pay.beijing.com.cn/customer/gb/pay_bank.jsp"
		method="POST" name="E_FORM">
		<input type="hidden" name="v_md5info" size="100"
			value="<%=digestString%>"> <input type="hidden" name="v_mid"
			value="<%=v_mid%>"> <input type="hidden" name="v_oid"
			value="<%=v_oid%>"> <input type="hidden" name="v_rcvname"
			value="<%=v_rcvname%>"> <input type="hidden" name="v_rcvaddr"
			value="<%=v_rcvaddr%>"> <input type="hidden" name="v_rcvtel"
			value="<%=v_rcvtel%>"> <input type="hidden" name="v_rcvpost"
			value="<%=v_rcvpost%>"> <input type="hidden" name="v_amount"
			value="<%=v_amount%>"> <input type="hidden" name="v_ymd"
			value="<%=v_ymd%>"> <input type="hidden" name="v_orderstatus"
			value="<%=v_orderstatus%>"> <input type="hidden"
			name="v_ordername" value="<%=v_ordername%>"> <input
			type="hidden" name="v_moneytype" value="<%=v_moneytype%>"> <input
			type="hidden" name="v_url" value="<%=v_url%>"> <font>请选择银行</font>
		<table width="1000" border="3" align="left" bordercolor="#9966FF"
			bgcolor="#99CC99">
			<tr>
				<td width="200"><div align="center">
						<input type="radio" name="v_pmode" value="3" />招商银行
					</div></td>
				<td width="200"><div align="center">
						<input type="radio" name="v_pmode" value="4" />建设银行
					</div></td>
				<td width="200"><div align="center">
						<input type="radio" name="v_pmode" value="9" />工商银行
					</div></td>
				<td width="200"><div align="center">
						<input type="radio" name="v_pmode" value="14" />平安银行
					</div></td>
				<td width="200"><div align="center">
						<input type="radio" name="v_pmode" value="22" />首信会员
					</div></td>
			</tr>
			<tr>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="28" />民生银行
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="33" />兴业银行
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="40" />深圳发展
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="43" />农业银行
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="44" />广东发展
					</div></td>
			</tr>
			<tr>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="50" />北京银行
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="59" />中国邮政
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="60" />华夏银行
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="67" />交通银行
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="69" />浦发银行
					</div></td>
			</tr>
			<tr>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="72" />网汇通
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="74" />光大银行
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="75" />北京农村信用社
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="83" />渤海银行
					</div></td>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="84" />中信银行
					</div></td>
			</tr>
			<tr>
				<td><div align="center">
						<input type="radio" name="v_pmode" value="85" />中国银行
					</div></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><div align="center">
						<input type="submit" name="submit" value="提交">
					</div></td>
			</tr>

		</table>
	</form>
</body>
</html>