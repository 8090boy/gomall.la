<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<c:if test="${visitedProd != null && fn:length(visitedProd) > 0}">
	<div class="m10 m0">
		<div class="mt">
			<h2>浏览历史</h2>
		</div>
		<div class="mc">
			<ul>

				<c:forEach items="${visitedProd}" var="prod" varStatus="status">
					<li>
						<div class="m0-img">
							<a href="${pageContext.request.contextPath}/views/${prod.id}"
								target="_blank"> <img
								src="<ls:images item='${prod.pic}' sacle='2'/>"
								alt="${prod.name}" height="85" width="100" /></a>
						</div>
						<div class="rate">
							<a href="${pageContext.request.contextPath}/views/${prod.id}"
								target="_blank">${prod.name }</a>
						</div>
						<div class="m0-price">
							<fmt:formatNumber type="currency" value="${prod.cash}"
								pattern="${CURRENCY_PATTERN}" />
						</div>
					</li>
				</c:forEach>
			</ul>
			<div class="more">
				<a target="_blank" title="查看更多" href="#">查看更多推荐</a>
			</div>
		</div>
	</div>
	<!--浏览历史-->
</c:if>

