<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<link type="text/css"
	href="<ls:templateResource item='/common/red/css/legend.css'/>"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/common/red/js/menu.js"></script>
<%@ include file="header.jsp"%>
<script>
	function submitSearchAllTopform(){
		if(document.getElementById("priceStart") && document.getElementById("priceEnd")){
		var priceStart = document.getElementById("priceStart").value;
		var pricarEnd = document.getElementById("priceEnd").value;
		if(isNaN(priceStart) || isNaN(pricarEnd)){
			alert('<fmt:message key="errors.number"><fmt:param value=""/></fmt:message>');
			return;
		}
		document.getElementById("priceStartValue").value = priceStart;
		document.getElementById("priceEndValue").value = pricarEnd;	
		}
		
		if(document.getElementById("provinceid")){
			document.getElementById("provinceidValue").value = document.getElementById("provinceid").value;
		}
		if(document.getElementById("cityid")){
			document.getElementById("cityidValue").value = document.getElementById("cityid").value;
		}
		if(document.getElementById("areaid")){
			document.getElementById("areaidValue").value = document.getElementById("areaid").value;
		}
		var entityType = document.getElementById("entityType").value;
		var keyword = document.getElementById("keyword").value;
		
        document.getElementById("searchAllform").action = "<ls:url address='/searchall'/>/" + entityType +"/"+keyword;
		document.getElementById("searchAllform").submit();
	}
	
		function pager(curPageNO){
		//alert(curPageNO);
			document.getElementById("curPageNO").value=curPageNO;
			submitSearchAllTopform();
		}
</script>
<!--logo+search-->
<div id="logo" class="w">
	<p class="logopic">
		<a href="<ls:url address='/index'/>"> <img
			src="${pageContext.request.contextPath}/img/group/logo.png"
			width="257" height="42" /></a>
	</p>
	<div class="searchwrap">
		<form action="${pageContext.request.contextPath}/searchall"
			method="post" id="searchAllform" name="searchAllform">
			<div class="search">
				<div class="sleft">
					<input type="hidden" id="curPageNO" name="curPageNO"
						value="${curPageNO}" /> <input type="hidden" id="priceStartValue"
						name="priceStartValue" /> <input type="hidden" id="priceEndValue"
						name="priceEndValue" /> <input type="hidden" id="provinceidValue"
						name="provinceidValue" /> <input type="hidden" id="cityidValue"
						name="cityidValue" /> <input type="hidden" id="areaidValue"
						name="areaidValue" /> <input type="hidden" id="orderByValue"
						name="orderByValue" /> <input type="hidden" id="orderDirValue"
						name="orderDirValue" /> <select name="entityType" id="entityType">
						<c:choose>
							<c:when test="${entityType == 1 }">
								<option value="0">商品</option>
								<option value="1" selected="selected">商城</option>
							</c:when>
							<c:otherwise>
								<option value="0" selected="selected">商品</option>
								<option value="1">商城</option>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
				<input name="keyword" id="keyword" type="text" value="${keyword}" />
			</div>
			<p class="searchpic">
				<a href="javascript:submitSearchAllTopform();"><img
					src="${pageContext.request.contextPath}/img/group/search.gif"
					width="104" height="40" /></a>
			</p>

		</form>
		<div class="clear"></div>
		<p class="hotword">
			热门搜索：
			<c:forEach items="${hotProdList }" var="hot">
				<a href="<ls:url address='/searchall'/>/0/${hot.msg }">${hot.title }</a>&nbsp;
   			</c:forEach>
		</p>
	</div>
	<p class="shoplist">
		<a href="<ls:url address='/p/usercenter'/>"> <img
			src="${pageContext.request.contextPath}/img/group/geren.gif"
			width="142" height="46" />
		</a> &nbsp; <a href="<ls:url address='/p/buy'/>"> <img
			src="${pageContext.request.contextPath}/img/group/shoplist.gif"
			width="156" height="47" />
		</a>
	</p>
	<div class="clear"></div>
</div>
<!--logo+search-->
<%@ include file="nav.jsp"%>