<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<c:if test="${requestScope.hotonList != null}">
	<div class="m10 rank">
		<div class="mt">
			<h2>分类销量排行榜</h2>
		</div>
		<div class="mc">
			<ul class="tabcon">
				<li class="fore"><span>1</span>
					<div class="p-img">
						<a
							href="${pageContext.request.contextPath}/views/${hotonList[0].prodId}"
							target="_blank"> <img
							src="<ls:images item='${hotonList[0].pic }' scale='3'/>"
							alt="${hotonList[0].name }" height="50" width="50"></a>
					</div>
					<div class="p-name">
						<a
							href="${pageContext.request.contextPath}/views/${hotonList[0].prodId}"
							target="_blank">${hotonList[0].name }<font color="#ff6600"></font></a>
					</div>
					<div class="p-price">
						<strong></strong>
					</div></li>
				<c:forEach items="${requestScope.hotonList}" var="hoton"
					varStatus="status" begin="2" end="10">
					<li><span>${status.index }</span>
					<div class="p-name">
							<a
								href="${pageContext.request.contextPath}/views/${hoton.prodId}"
								target="_blank">${hoton.name }</a>
						</div></li>
				</c:forEach>

			</ul>
			<div class="clear"></div>
		</div>
	</div>
</c:if>