<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.legendshop.core.helper.PropertiesUtil"%>
<%@ include file="/WEB-INF/pages/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/tld/auth.tld" prefix="auth"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.Timestamp"%>

<html>
<head>
<title>文件下载</title>

</head>
<body>
	<div align="center">
		<span class="title1">上传的文件列表</span><br>
	</div>
	<center>
		<table width="567" class="tableBorder">
			<tr>
				<td width="180">文件名</td>
				<td width="180">时间</td>
				<td width="30">下载</td>
			</tr>
			<%
                 	        String filePath = PropertiesUtil.getDownloadFilePath();
                 	    	 
			                // System.out.println("filePath = "+filePath);
			                 File dir = new File(filePath);
			                 File[] files=null;
			                 if(dir.isDirectory()){
			                     files=dir.listFiles();
			                 }
			                 if(files!=null){
			                 for(int i=0;i<files.length;i++){
			                 
			                 
                  %>
			<tr>
				<td><%=files[i].getName()%></td>
				<td><%=new Timestamp(files[i].lastModified())%></td>
				<td><a
					href="<%=request.getContextPath()%>/servlet/downloadfileservlet/<%=java.net.URLEncoder.encode(files[i].getName(),"UTF-8") %>">下载</a>
				</td>
			</tr>
			<%} }%>
		</table>

	</center>
</body>
</html>
