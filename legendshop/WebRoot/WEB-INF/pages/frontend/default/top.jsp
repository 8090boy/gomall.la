<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<%@ taglib uri="/WEB-INF/tld/auth.tld" prefix="auth"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
<script src="<ls:templateResource item='/common/js/top.js'/>"
	type="text/javascript"></script>
<script src="<ls:templateResource item='/common/js/club.js'/>"
	type="text/javascript"></script>
<%@include file='/WEB-INF/pages/common/common.jsp'%>
<script>
 	 function form1_onsubmit() {
      var words = document.getElementById("headerkeywordp");
      if( words.value == null || words.value == '' || words.value == '关键字'){
      	alert('<fmt:message key="errors.keyword.required"/>');
      	words.focus();
      	return false;
      }
      return true;
 	 }

	function addMyLeague() {
	    jQuery.post("${pageContext.request.contextPath}/admin/myleague/addMyLeague", {siteName : '${shopDetail.siteName}'},  function(retData){
		       switch(retData){
		       case 0:
		    	   alert('<fmt:message key="addLeagueSuccess"/>') ;
		    	   break;
		       default:
		    	   alert('<fmt:message key="addLeagueError"/>') ;	    	   	    	   		    	   	       
		       }	      
	    });
	}
	
jQuery(document).ready(function(){
				$.get("${pageContext.request.contextPath}/topuserinfo", function(data){
				  $('#user_info').html(data);
				  	$.get("${pageContext.request.contextPath}/clubRs", function(data){
				  	//	alert( data );
						 if(data) club(); //大于0才显示
				 	});
				   });
});


</script>
<lb:shopDetail var="shopDetail" />
<link
	href="<ls:templateResource item='/common/style/style_${shopDetail.frontEndStyle}.css'/>"
	rel="stylesheet" type="text/css" />
<link
	href="<ls:templateResource item='/common/style/global_${shopDetail.frontEndStyle}.css'/>"
	rel="stylesheet" type="text/css" />

<title>${shopDetail.siteName}</title>
<link rel="icon" href="<ls:templateResource item='/favicon.ico'/>"
	type="image/x-icon" />
<link href="favicon.ico" rel="icon" type="image/x-icon" />
<link href="favicon.ico" rel="shortcut icon" type="image/x-icon" />
<link href="favicon.png" rel="apple-touch-icon" />
<meta name="msapplication-TileImage" content="favicon.png" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="description"
	content="${shopDetail.userName},${shopDetail.briefDesc}, ${prod.keyWord}" />
<meta name="keywords"
	content="${shopDetail.userName},${shopDetail.briefDesc}, ${prod.keyWord}" />
<meta name="keywords" content="购买啦商城" />
</head>
<body>
	<div id='mask'></div>
	<div id="wrap"></div>

	<%@ include file="header.jsp"%>
	<div id="header">
		<div id="logo">
			<a href="/"> <c:choose>
					<c:when test="${shopDetail.logoPic == null}">
						<img style="Clear: Both; Border: 0px"
							src="${pageContext.request.contextPath}/common/images/logo.png"
							height="65px" />
					</c:when>
					<c:otherwise>
						<img style="Clear: Both; Border: 0px"
							src="<ls:photo item='${shopDetail.logoPic}'/>" height="65px" />
					</c:otherwise>
				</c:choose>
			</a>
		</div>
		<div id="user_info"></div>
	</div>
	<div id="headernav">
		<ul>
			<c:if test="${prod.userName != null}">
				<li class="n2"><a
					href="<ls:url address='/shop/${prod.userName}'/>"><fmt:message
							key="shop.index" /> </a></li>
			</c:if>
			<!-- 团购	 -->
			<ls:plugin pluginId="group">
				<li><a href="<ls:url address='/group/index'/>" target="_blank"><fmt:message
							key="group.on" /> </a></li>
			</ls:plugin>
			<li class="n2"><a href="<ls:url address='/league'/>"><fmt:message
						key="leagueShop" /> </a></li>
			<c:forEach items="${newsSortList}" var="newsSort">
				<li><a href="<ls:url address='/news/${newsSort.newsId}'/>">${newsSort.newsTitle}</a>
				</li>
			</c:forEach>
		</ul>
		<ul class="nright">
			<c:forEach items="${requestScope.sortList}" var="sort" end="5">
				<li><a href="<ls:url address='/sort/${sort.sortId}'/>">${sort.sortName}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<form method="post" onsubmit="return form1_onsubmit();"
		action="<ls:url address='/search'/>" id="searchForm" name="searchForm"
		style="margin: 0px; padding: 0px">
		<div id="searchwrapper">
			<div id="qchannel">
				<div id="headersearch">
					<input type="hidden" id="curPageNOTop" name="curPageNOTop"
						value="${searchForm.curPageNOTop}" /> <select name="sortId"
						id="headersearchcategory" style="margin-right: 3px">
						<option id="allProduct" value="0">
							<fmt:message key="all.product" />
						</option>
						<c:forEach items="${requestScope.sortList}" var="sort">
							<c:choose>
								<c:when test="${CurrentSortId == sort.sortId}">
									<option id="${sort.sortId}" value="${sort.sortId}" class="c"
										selected="selected">${sort.sortName}</option>
								</c:when>
								<c:otherwise>
									<option id="${sort.sortId}" value="${sort.sortId}" class="c">${sort.sortName}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select> <input type="text" value="${searchForm.keyword}" name="keyword"
						id="headerkeywordp" class="input2" onMouseOver="this.focus()"
						onBlur="if (this.value =='') this.value='关键字'"
						onFocus="this.select()"
						onClick="if (this.value=='关键字') this.value=''" /> <input
						type="submit" value='<fmt:message key="search"/>' class="input3"
						onmouseover="this.style.background='#99CC00'"
						onmouseout="this.style.background='#FFCC00'" /> <a
						href="javascript:advanceSearch('${pageContext.request.contextPath}')"
						style="margin: 6px"><font color="white"><fmt:message
								key="advance.search" /> </font> </a>

				</div>
				<div id="topcatalog">
					<h2>
						<a href='#'
							onclick="javascript:bookmark('${shopDetail.userName} - ${shopDetail.siteName}','<ls:domain shopName="${shopDetail.userName}" />');">
							<fmt:message key="favorite" />
						</a>
					</h2>
				</div>
				<div>
					<ul id="favourite">
						<li><center>
								<a
									href="<ls:url address='/shopcontact'/>?shop=<lb:currentShop />"
									title="<lb:currentShop /><fmt:message key='online.customer'/>"><fmt:message
										key="online.customer" /> </a>
							</center></li>
					</ul>
				</div>
			</div>
		</div>
	</form>