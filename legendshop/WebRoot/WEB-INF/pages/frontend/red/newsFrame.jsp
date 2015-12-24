<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<lb:shopDetail var="shopDetail">
	<title>新闻中心 - ${shopDetail.siteName}</title>
</lb:shopDetail>
<link
	href="${pageContext.request.contextPath}/common/default/css/pager.css"
	rel="stylesheet" type="text/css" />
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
</head>
<body>
	<jsp:include flush="true" page="/top">
		<jsp:param name="sortId" value="-1" />
	</jsp:include>
	<!----地址---->
	<div class="w addr">
		<span><a href="#">首页</a></span>&gt;<span><a
			href="<ls:url address='/allnews'/>">新闻中心</a></span>
		<c:if test="${ not empty requestScope.newsCategoryName }">
   		 &gt;<span><a
				href="${pageContext.request.contextPath}/allnews?newsCategoryId=${newsCategoryId}">${ requestScope.newsCategoryName }</a></span>
		</c:if>
	</div>
	<!----地址end---->


	<!----两栏---->
	<div class="w" style="padding-top: 10px;">
		<!-----------leftm-------------->
		<!-----------leftm end-------------->
		<!-- 左边的内容 -->
		<tiles2:insertAttribute name="main" ignore="true" />
		<div class="clear"></div>

	</div>

	<!----两栏end---->
	<jsp:include page="/bottom" />