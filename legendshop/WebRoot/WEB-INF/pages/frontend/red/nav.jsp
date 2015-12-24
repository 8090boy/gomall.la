<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/common/red/js/menu.js"></script>
<SCRIPT type=text/javascript> 
jQuery(document).ready(function($) {
 //console.debug(jQuery(".item"));
	$(".item").hoverForIE6({delay:100});
	$(".nav_vbox").hoverForIE6({delay:150});
});
</SCRIPT>
<!--nav-->
<div class="nav">
	<div class="nav">
		<div class="w wnav">
			<div class="nav_vbox">
				<div class="navbtn">
					<a href="<ls:url address='/allsorts'/>">全部商品分类</a>
				</div>

				<!-- mc start -->

				<div class="mc"
					style='<c:if test="${showMenu  eq true}">display: block</c:if>'>
					<c:forEach items="${navigationSortList }" var="sort"
						varStatus="status">
						<div class="item fore1">
							<span><h3>
									<c:choose>
										<c:when test="${ not empty deputyMap[sort.sortId] }">
											<c:forEach items="${deputyMap[sort.sortId]}" var="dnsort"
												varStatus="vs">
												<a
													href="<ls:url address='/nsort/${dnsort.sortId}-${dnsort.nsortId}'/>">${dnsort.nsortName }</a>
												<c:if
													test="${vs.count lt fn:length(deputyMap[sort.sortId]) }">、</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<a href="<ls:url address='/sort/${sort.sortId}'/>">${sort.sortName }</a>
										</c:otherwise>
									</c:choose>
								</h3>
								<s></s></span>
							<!-- 子分类  -->
							<div class="i-mc" style="top: ${status.index * 28 + 3 }px;">
								<div class="subitem">
									<c:forEach items="${sTreeMap[sort.sortId] }" var="snsort">
										<dl class="fore1">
											<dt>
												<a
													href="<ls:url address='/nsort/${snsort.sortId}-${snsort.nsortId}'/>">${snsort.nsortName }</a>
											</dt>
											<dd>
												<c:forEach items="${tTreeMap[snsort.nsortId] }" var="tnsort">
													<em><a
														href="<ls:url address='/nsort/${tnsort.sortId}-${tnsort.nsortId}'/>">${tnsort.nsortName }</a></em>
												</c:forEach>
											</dd>
										</dl>
									</c:forEach>
								</div>
							</div>
							<!-- i-mc end -->
						</div>
						<!-- item fore1 end  -->
					</c:forEach>
					<div class="extra">
						<a href="<ls:url address='/allsorts'/>">全部商品分类</a>
					</div>
				</div>
				<!-- mc end -->
			</div>
			<ul>
				<c:forEach items="${headerSortList}" var="item">
					<li style="overflow: hidden"
						class='<c:if test="${currentSortId eq item.sortId}">focus</c:if>'><a
						href="<ls:url address='/sort/${item.sortId}'/>">${item.sortName }</a></li>
				</c:forEach>
				<ls:plugin pluginId="group">
					<li class='<c:if test="${currentSortId eq -1}">focus</c:if>'><a
						href="<ls:url address='/group/index'/>">团购</a></li>
				</ls:plugin>
			</ul>
		</div>
	</div>
</div>
<!--nav end-->