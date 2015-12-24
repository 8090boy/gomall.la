<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="com.legendshop.core.helper.PropertiesUtil"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/common/js/top.js"></script>
<script type=text/javascript>
$(document).ready(function() {
	$(".menu").mouseover(function(){
		var _this = $(this);
		 _this.addClass("on");
	});
	$(".menu").mouseout(function(){
		var _this = $(this);
		 _this.removeClass("on");
	});
});
</script>
<!--顶部menu-->
<div id="shortcut">
	<div class="w">
		<ul class="fl lh">
			<ls:plugin pluginId="advanceSearch">
				<li class="fore1"><a
					href="${pageContext.request.contextPath}/all" target="_blank"><b><ls:i18n
								key="search.total.index" /></b></a></li>
			</ls:plugin>
			<c:if test="${'C2C' == applicationScope.BUSINESS_MODE}">
				<li><a href=" ${pageContext.request.contextPath}/home"><ls:i18n
							key="shop.total.index" /></a></li>
			</c:if>
			<li><ls:i18n key="nows.location" /> <c:if
					test="${shopDetail.province != null}">
				  		 ${shopDetail.province}/${shopDetail.city}/${shopDetail.area}/
				  </c:if> <b><a href="${pageContext.request.contextPath}/shopcontact"><lb:currentShop /></a>
			</b></li>
			<c:if test="${'C2C' == applicationScope.BUSINESS_MODE}">
				<li><a href="${pageContext.request.contextPath}/reg?openshop=1"
					target="_blank"><ls:i18n key="register.shop" /> </a></li>
			</c:if>
			<li class="favorite"><a href='#'
				onclick="javascript:bookmark('<ls:i18n key="welcome.to.legendshop"/><%=PropertiesUtil.getDefaultShopName()%> - LegendShop购物商城','<ls:domain shopName="<%=PropertiesUtil.getDefaultShopName()%>" />');">
					<ls:i18n key="shop.favorite" />
			</a></li>
		</ul>
		<ul class="fr lh">
			<c:choose>
				<c:when test="${sessionScope.SPRING_SECURITY_LAST_USERNAME != null}">

					<li id="loginbar" class="ld"><font color="#FF3300"
						style="font-weight: bold;">${sessionScope.SPRING_SECURITY_LAST_USERNAME}</font>
						<span> <a href="<ls:url address='/p/usercenter'/>")>[<ls:i18n
									key="myaccount" />]
						</a> <a href="${pageContext.request.contextPath}/p/logout"
							target="_parent">[<ls:i18n key="logout" />]
						</a>
					</span></li>

				</c:when>
				<c:otherwise>
					<li id="loginbar" class="ld"><span><a
							href="<ls:url address='/login'/>">[<ls:i18n key="login" />]
						</a> <a class="link-regist" href="<ls:url address='/reg'/>">[<ls:i18n
									key="regFree" />]
						</a></span></li>
				</c:otherwise>
			</c:choose>
			<c:if test="${sessionScope.SPRING_SECURITY_LAST_USERNAME != null}">
				<ls:auth ifAnyGranted="FE_BACKEND">
					<li><a href="<ls:url address='/admin/index'/>"><b><fmt:message
									key="system.management" /></b></a></li>
				</ls:auth>
				<li><a href="<ls:url address='/p/order'/>">我的订单</a></li>
			</c:if>
			<li><a href="<ls:url address='/allnews'/>"><ls:i18n
						key="newsCenter" /></a></li>
			<%-- 	        <li class="menu">
				<dl>
					<dt class="ld">常见问题<b></b></dt>
					<dd>
					   <c:forEach items="${newsTopList}" var="newsTop">
					       <div><a href="<ls:url address='/news/${newsTop.newsId}'/>">${newsTop.newsTitle}</a></div>
					   </c:forEach>
					</dd>
				</dl>
			</li> --%>

		</ul>
		<span class="clr"></span>
	</div>
</div>
<!--顶部menu end-->