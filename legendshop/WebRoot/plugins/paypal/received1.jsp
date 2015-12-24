<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.capinfo.crypt.*"%>

<%
//接收参数
  request.setCharacterEncoding("gb2312");
  String v_oid = request.getParameter("v_oid");//订单编号
  String v_pmode = request.getParameter("v_pmode");//支付方式
  String v_pstatus = request.getParameter("v_pstatus");//支付结果 20支付成功 30 支付失败
  String v_pstring = request.getParameter("v_pstring");//支付结果信息说明
  String v_amount = request.getParameter("v_amount");//订单金额
  String v_moneytype = request.getParameter("v_moneytype");//币种
  String v_md5money = request.getParameter("v_md5money");//数字指纹
  String v_md5info = request.getParameter("v_md5info");//数字指纹
  String v_sign = request.getParameter("v_sign");//RSA数字指纹

 
//中文解析
  v_pstring = new String(v_pstring.getBytes("8859_1"));
  v_pmode = new String(v_pmode.getBytes("8859_1"));

  String source1 = v_oid + v_pstatus + v_pstring + v_pmode;
  String source2 = v_amount +v_moneytype; 

  out.println("v_pmode:");
  out.print(v_pmode);  
  out.print("<br>");  
  
  out.println("v_pstring:");
  out.print(v_pstring);  
  out.print("<br>");  
  
  
  out.println("v_md5info指纹结果");
  out.print("<br>");  
  request.setCharacterEncoding("gb2312");

  Md5 md5 = new Md5 ("") ;
  md5.hmac_Md5(source1 , "test" ) ;
  byte b[]= md5.getDigest();
  String digestString = md5.stringify(b) ;
  
  out.println (digestString) ;
  out.print("<br>"); 
  

  out.println("v_md5money指纹结果");
  out.print("<br>");  
  request.setCharacterEncoding("gb2312");

  Md5 m = new Md5 ("") ;
  md5.hmac_Md5(source2 , "test" ) ;
  byte a[]= md5.getDigest();
  String digestString2 = md5.stringify(a) ;
  
  out.println (digestString2) ;
  out.print("<br>");


	out.println("输出md5info对比结果");
	out.print("<br>");
	if(digestString.equals(v_md5info) )
	{
	out.println("success");
	}
	else
	{
	out.println("error");	
	out.print("<br>");
	}
	out.print("<br>");
	out.println("输出md5money对比结果");
	out.print("<br>");
	if(digestString2.equals(v_md5money) )
	{
	out.println("success");
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
	String publicKey = "D:/JspTest/Public1024.key";   //public1024.key的路径
	String SignString = v_sign;
	String strSource = v_oid + v_pstatus + v_amount + v_moneytype ;
	
	
	RSA_MD5 rsaMD5 = new RSA_MD5();
	int k = rsaMD5.PublicVerifyMD5(publicKey,SignString,strSource);
	if(k==0){
		out.println("验证成功");
  }
  else
  {
		out.println("验证失败");
  }
//数据库操作略
%>