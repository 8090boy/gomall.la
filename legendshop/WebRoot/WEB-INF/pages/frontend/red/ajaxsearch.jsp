<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<div class="shop_slist">
	<c:choose>
		<c:when
			test="${requestScope.searchResult != null && searchArgs.entityType == 0}">
			<c:forEach items="${requestScope.searchResult}" var="record"
				varStatus="status">
				<div
					class='alley-item  <c:if test="${status.index % 2 == 1}">odd</c:if>'>
					<div class="lp">
						<a
							href="${pageContext.request.contextPath}/views/${record.prodId}"
							target="_blank"> <img alt=" "
							src="<ls:images item='${record.pic}' scale='1'/>" width="150px"
							height="150px">
						</a>
					</div>
					<div class="rp">
						<h3>
							<a
								href="${pageContext.request.contextPath}/views/${record.prodId}"
								target="_blank">${record.name}</a>
						</h3>
						<c:if test="${record.price != null}">
							<p class="yprice">
								<fmt:message key="prod_price" />
								<fmt:formatNumber type="currency" value="${record.price}"
									pattern="${CURRENCY_PATTERN}" />
							</p>
						</c:if>
						<c:if test="${record.cash != null}">
							<p class="price">
								<fmt:message key="prod_cash" />
								<fmt:formatNumber type="currency" value="${record.cash}"
									pattern="${CURRENCY_PATTERN}" />
							</p>
						</c:if>

						<p class="section">${record.content}</p>
						<span class="p_class"> <c:if
								test="${'C2C' == applicationScope.BUSINESS_MODE}">
								<span style="color: green"> <fmt:message key="shop.name" />
									- <a href="<ls:domain shopName='${record.userName}' />"
									style="color: green" target="_blank">${record.userName}</a>,
								</span>
							</c:if> <fmt:message key="product.sort" /> - <a
							href="${pageContext.request.contextPath}/sort/${record.sortId}"
							target="_blank">${record.sortName}</a> <a
							href="${pageContext.request.contextPath}/nsort/${record.sortId}-${record.nsortId}"
							target="_blank">${record.nsortName}</a> <a
							href="${pageContext.request.contextPath}/nsort/${record.sortId}-${record.nsortId}?subNsortId=${record.subNsortId}"
							target="_blank">${record.subNsortName}</a>
						</span>
					</div>
				</div>
			</c:forEach>
		</c:when>
		<c:when
			test="${requestScope.searchResult != null && searchArgs.entityType == 1}">
			<c:forEach items="${requestScope.searchResult}" var="record"
				varStatus="status">
				<div
					class='alley-item  <c:if test="${status.index % 2 == 1}">odd</c:if>'>
					<div class="lp">
						<a href="#" target="_blank"> <img alt=" "
							src="${pageContext.request.contextPath}/images/shopicon.jpg">
						</a>
					</div>
					<div class="rp">
						<h3>
							<a href="<ls:domain shopName='${record.userName}' />">${record.siteName}
							</a>
						</h3>
						<p class="section">${record.detailDesc}</p>
						<span class="p_class"> <fmt:message
								key="shop.contact.method" /> - <fmt:message key="Phone" /> <c:if
								test="${record.qq !=null}"> QQ:<span style="color: green">${record.qq}</span>
							</c:if> <br /> <c:if test="${record.province != null}">
								<fmt:message key="address" /> -  ${record.province}
								 <c:if test="${record.city != null}">&raquo; ${record.city}
								 	<c:if test="${record.area != null}">&raquo; ${record.area}</c:if>
								</c:if>
							</c:if><br /> <c:if test="${record.postAddr != null}">
								<fmt:message key="detail.address" /> - ${record.postAddr}
							</c:if>
						</span>
					</div>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div style="font-size: 13pt; margin: 30px">
				<fmt:message key="can.not.found">
					<fmt:param value="${searchArgs.keyword}" />
				</fmt:message>
			</div>
		</c:otherwise>
	</c:choose>
	<c:if test="${toolBar!=null}">
		<div class="fanye">
			<c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
		</div>
	</c:if>

</div>
