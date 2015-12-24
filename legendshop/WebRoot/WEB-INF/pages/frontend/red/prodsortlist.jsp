<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<c:if test="${toolBar!=null}">
	<div class="fanye">
		<c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
	</div>
	<div class="clear"></div>
</c:if>
<!--------------产品列表--------------------->
<div class="plist" class="m">
	<ul class="list-h">
		<c:forEach items="${requestScope.list}" var="prodDetail"
			varStatus="status">
			<li>
				<div class="p-img">
					<a href="<ls:url address='/views/${prodDetail.prodId}'/>"
						target="_blank"><img width="150" height="150"
						src="<ls:images item='${prodDetail.pic}' scale='1'/>"></a>
					<div class="pi3"></div>
				</div>
				<div class="p-name">
					<a href="<ls:url address='/views/${prodDetail.prodId}'/>"
						target="_blank">${prodDetail.name}</a>
				</div>
				<div class="p-price">
					<c:if test="${not empty prodDetail.cash}">
						<fmt:message key="prod_cash" />
						<fmt:formatNumber type="currency" value="${prodDetail.cash}"
							pattern="${CURRENCY_PATTERN}" />

					</c:if>
				</div>
				<div class="extra">
					<span class="evaluate"><a href="#" target="_blank">已有68人评价</a></span><span
						class="reputation">(91%好评)</span><span id="p533471"></span>
				</div>
				<div class="btns">
					<input type="button" value="购买"><input type="button"
						value="关注"><input type="button" value="对比">
				</div>
			</li>
		</c:forEach>

	</ul>

</div>

<!--------产品列表end---------->

<div class="clear"></div>
<div class="fanye">
	<c:if test="${toolBar!=null}">
		<p align="right">
			<c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
		</p>
	</c:if>
</div>





