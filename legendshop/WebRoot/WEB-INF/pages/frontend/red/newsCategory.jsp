<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<!----右边---->
<div class="index_right">
	<c:forEach items="${requestScope.USER_REG_ADV_740}" var="adv"
		varStatus="status">
		<div
			style='<c:if test="${status.index>1 }">margin-top:10px; margin-bottom:10px;</c:if>'>
			<a href="${adv.linkUrl}"><img
				src="<ls:photo item='${adv.picUrl}'/>" width="320px" height="105px" /></a>
		</div>
	</c:forEach>
	<c:forEach items="${newsCatList}" var="newsCat">
		<div class="side2" style="margin-top: 10px;">
			<h3>
				<a
					href="${pageContext.request.contextPath}/allnews?newsCategoryId=${newsCat.key.key}">${newsCat.key.value}</a>
			</h3>
			<ul class="lista font12 blue_a listimg1" style="margin-left: 10px;">
				<c:forEach var="news" items="${newsCat.value}">
					<li><a href='<ls:url address="/news/${news.newsId }"/>'>${news.newsTitle }</a></li>
				</c:forEach>
			</ul>
		</div>
	</c:forEach>
</div>