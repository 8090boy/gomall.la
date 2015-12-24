<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<c:if test="${requestScope.hotRecommendList != null}">
	<div class="i-right">
		<div class="hotsale">
			<div class="mc list-h">
				<c:forEach items="${requestScope.hotRecommendList}" var="prod"
					varStatus="status" end="2">
					<dl>
						<dt>
							<img width="100px" height="100px" alt=" "
								src="<ls:images item='${prod.pic}' scale='2'/>" />
						</dt>
						<dd>
							<div class="p-name">${prod.name}</div>
							<div class="p-price">
								特价：<span><fmt:formatNumber type="currency"
										value="${prod.cash}" pattern="${CURRENCY_PATTERN}" /></span>
							</div>
							<div class="btns">
								<a
									href="${pageContext.request.contextPath}/views/${prod.prodId}"
									target="_blank">立即抢购</a>
							</div>
						</dd>
					</dl>
				</c:forEach>
			</div>
		</div>
	</div>
</c:if>
