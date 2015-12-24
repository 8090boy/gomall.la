<%@ page contentType="text/html;charset=gb2312"%>
<%request.setCharacterEncoding("GB2312");%>
<%@ page pageEncoding="GB2312"%>
<%@ page import="com.capinfo.crypt.*"%>
<%@ page import="java.lang.String.*"%>
<%
  request.setCharacterEncoding("8859_1");
//获取参数
  String v_oid = request.getParameter("v_oid");//订单编号
  String v_pmode = request.getParameter("v_pmode");//支付方式
  String v_pstatus = request.getParameter("v_pstatus");//支付结果 1支付成功 3 支付失败
  String v_pstring = request.getParameter("v_pstring");//支付结果信息说明
  String v_count = request.getParameter("v_count");//订单个数
  String v_amount = request.getParameter("v_amount");//订单金额
  String v_moneytype = request.getParameter("v_moneytype");//币种
  String v_md5money = request.getParameter("v_md5money");//数字指纹
  String v_mac = request.getParameter("v_mac");//数字指纹
  String v_sign = request.getParameter("v_sign");//RSA数字指纹
//中文转换

  v_pstring = new String(v_pstring.getBytes("8859_1"));
  v_pmode = new String(v_pmode.getBytes("8859_1"));

//拆分参数
  String[] resultoid = v_oid.split("[|][_][|]");
  String[] resultpmode = v_pmode.split("[|][_][|]");
  String[] resultstatus = v_pstatus.split("[|][_][|]");
  String[] resultpstring = v_pstring.split("[|][_][|]");
  String[] resultamount = v_amount.split("[|][_][|]");
  String[] resultmoneytype = v_moneytype.split("[|][_][|]");


//web.xml编写UTF-8处理方法
//v_pstring = new String(v_pstring.getBytes("ISO8859-1"), "GB2312");
//v_pmode = new String(v_pmode.getBytes("ISO8859-1"), "GB2312"); 

	String source1 = v_oid + v_pmode + v_pstatus + v_pstring + v_count;
	String source2 = v_amount +v_moneytype;


//md5加密1
  
  Md5 md5 = new Md5 ("") ;
  md5.hmac_Md5(source1 , "testtest" ) ;
  byte b[]= md5.getDigest();
  String digestString = md5.stringify(b) ;
  

  
  //md5加密2
  
  Md5 m = new Md5 ("") ;
  md5.hmac_Md5(source2 , "testtest" ) ;
  byte a[]= md5.getDigest();
  String digestString2 = md5.stringify(a) ;

	if(digestString2.equals(v_md5money) && digestString.equals(v_mac))
	{
		out.println("sent");
	}
	else
	{
		out.println("error");	
		out.print("<br>");
	}

	//RSA验证
	out.print("<br>");
	out.println ("RSA验证结果");
	out.print("<br>");
	String publicKey = "D:/JspTest/Public1024.key";
	String SignString = v_sign;
	String strSource = v_oid + v_pstatus + v_amount + v_moneytype + v_count;
	RSA_MD5 rsaMD5 = new RSA_MD5();
	int k = rsaMD5.PublicVerifyMD5(publicKey,SignString,strSource);
	if(k==0)
	{
		out.println("验证成功");
	}
	else
	{
			out.println("验证失败");
	}
	//数据库操作略
%>