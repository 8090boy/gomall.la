<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<lb:shopDetail var="shopDetail">
	<title>${sort.sortName}- ${shopDetail.siteName}</title>
</lb:shopDetail>
<link
	href="${pageContext.request.contextPath}/common/default/css/pager.css"
	rel="stylesheet" type="text/css" />
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
<script type="text/javascript">
					$(document).ready(function(e) {
								//call ajax
							   $.post("${pageContext.request.contextPath}/visitedprod", 
							        function(retData) {
							              	$("#visitedprod").html(retData);
									},'html');
						});
		</script>
</head>
<body>
	<jsp:include flush="true" page="/top">
		<jsp:param name="sortId" value="${sort.sortId}" />
	</jsp:include>
	<!----地址---->
	<div class="w addr">
		<span><a href='<ls:url address="/index"/>'>首页</a></span>&gt;<span>${sort.sortName}
		</span>
	</div>
	<!----地址end---->
	<!----两栏---->
	<div class="w" style="padding-top: 10px;">
		<!-----------leftm-------------->
		<div class="leftm">
			<!-- 商品分类 start -->
			<jsp:include flush="true" page="/sortlist/${sort.sortId}" />
			<!--商品分类 end -->
			<!--热门产品 start-->
			<jsp:include flush="true" page="/hoton/${sort.sortId}" />
			<!--热门产品 end -->
			<!--热门评论 start -->
			<jsp:include flush="true" page="/hotcomments/${sort.sortId}" />
			<!--热门评论 end -->
			<!--浏览历史 start-->
			<div id="visitedprod" name="visitedprod"></div>
			<!--浏览历史 end-->
		</div>
		<!-----------leftm end-------------->
		<!-- 右边的内容 -->
		<tiles2:insertAttribute name="main" ignore="true" />
		<div class="clear"></div>
	</div>
	<!----两栏end---->
	<jsp:include page="/bottom" />