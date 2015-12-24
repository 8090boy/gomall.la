<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<c:if test="${requestScope.hotCommentList != null}">
	<div class="m10 m0">
		<div class="mt">
			<h2>热门评论</h2>
		</div>
		<div class="mc">
			<ul>
				<c:forEach items="${requestScope.hotCommentList}" var="prod"
					varStatus="status" end="5">
					<li>
						<div class="m0-img">
							<a href="${pageContext.request.contextPath}/views/${prod.prodId}"
								target="_blank"><img
								src="<ls:images item='${prod.pic}' scale='2'/>" alt=""
								height="100px" width="100px"></a>
						</div>
						<div class="rate">
							<a href="${pageContext.request.contextPath}/views/${prod.prodId}"
								target="_blank">${prod.name}</a>
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
</c:if>
