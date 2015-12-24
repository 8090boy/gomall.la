<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<lb:shopDetail var="shopDetail">
	<title>${prod.name}${shopDetail.siteName}</title>
</lb:shopDetail>
<link type="text/css"
	href="<ls:templateResource item='/common/red/css/legend.css'/>"
	rel="stylesheet" />
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
</head>
<body>
	<jsp:include flush="true" page="/top">
		<jsp:param name="sortId" value="-1" />
	</jsp:include>
	<tiles2:insertAttribute name="main" ignore="true" />
	<jsp:include page="/bottom" />