<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<link
	href="${pageContext.request.contextPath}/common/default/css/pager.css"
	rel="stylesheet" type="text/css" />
<!----advtisement---->
<!----advtisementend---->
<!----blue两栏---->
<div class="w">
	<!----右边---->
	<!----右边end---->

	<!----左边---->
	<!----左边end---->

	<div class="clear"></div>
</div>
<!----blue两栏end---->


<!----advtisementend---->
<!----red两栏---->
<div class="w">
	<!----右边---->
	<%@ include file="newsCategory.jsp"%>
	<!----右边end---->

	<!----左边---->
	<div class="index_left">
		<div class="news_wrap">

			<div class="news_bor">

				<c:forEach items="${requestScope.list}" var="news"
					varStatus="status">
					<p class="new_title">

						<c:choose>
							<c:when test="${news.highLine == 1}">
								<span class="ntitleHighLine">
							</c:when>
							<c:otherwise>
								<span class="ntitle">
							</c:otherwise>
						</c:choose>

						<a href="${pageContext.request.contextPath}/news/${news.newsId}">${news.newsTitle}</a></span>
						<span class="ntime">作者：${news.userName} <span class="ndate"><fmt:formatDate
									value="${news.newsDate}" pattern="yyyy-MM-dd" /></span></span>
					</p>
					<p class="nabs">${news.newsBrief}</p>
				</c:forEach>

				<div class="clear"></div>
				<div class="fanye">
					<c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
				</div>

				<div class="clear"></div>
			</div>




			<div class="clear"></div>
		</div>




	</div>
	<!----左边end---->

	<div class="clear"></div>
</div>
<!----red两栏end---->



<!----advtisementend---->
<!----green两栏---->
<div class="w">

	<div class="clear"></div>
</div>
<!----green两栏end---->




<!----down两栏---->
<div class="w">
	<div class="clear"></div>
</div>
<!----down两栏end---->
<script type="text/javascript">
<!--
		function pager(curPageNO){
			window.location.href="${pageContext.request.contextPath}/allnews?curPageNO="+curPageNO;
		}
		
		function changePager(curPageNO){
			document.getElementById("curPageNO").value=curPageNO;
			document.getElementById("form1").submit();
		}
//-->
</script>
