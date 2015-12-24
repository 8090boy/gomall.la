<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>

<div class="w pagetab " style="padding-top: 20px;">
	<ul>
		<li class="on"><span><a
				href="<ls:url address='/allsorts'/>">全部商品分类</a></span></li>
		<li><span><a href="<ls:url address='/allbrands'/>">全部品牌</a></span></li>
		<li><span><a href="<ls:url address='/allprods'/>">全部商品</a></span></li>
	</ul>
</div>


<div class="w" style="padding-top: 10px;">
	<div class="i-w">
		<div class="text">更多特价产品，请进入以下频道页面</div>
		<ul class="tab">
			<c:forEach items="${sortList }" var="sort">
				<li class="l1"><a
					href='<ls:url address="/sort/${sort.sortId }"/>'>${sort.sortName }</a></li>
			</c:forEach>
		</ul>
		<div class="clear"></div>
	</div>
</div>

<!----两栏---->
<div class="w" id="allsort" style="padding-top: 10px;">
	<!----左手边---->
	<div class='fl'>
		<c:forEach items="${sortList }" var="sort" begin="0"
			end="${halfSize - 1}">
			<div class="m">
				<div class="mt">
					<h2>
						<a href='<ls:url address="/sort/${sort.sortId }"/>'>${sort.sortName }${status.index}</a>
					</h2>
				</div>
				<div class="mc">
					<c:forEach items="${sort.nsort}" var="nsort">
						<dl class="fore">
							<dt>${nsort.nsortName }</dt>
							<dd>
								<c:forEach items="${nsort.subSort }" var="subSort">
									<em><a
										href='<ls:url address="/nsort/${nsort.sortId}-${subSort.nsortId}'" />'>${subSort.nsortName}</a></em>
								</c:forEach>
							</dd>
						</dl>
					</c:forEach>

				</div>
			</div>
		</c:forEach>
	</div>

	<!----右手边---->
	<div class='fr'>
		<c:forEach items="${sortList }" var="sort" begin="${halfSize}">
			<div class="m">
				<div class="mt">
					<h2>
						<a href='<ls:url address="/sort/${sort.sortId }"/>'>${sort.sortName }${status.index}</a>
					</h2>
				</div>
				<div class="mc">
					<c:forEach items="${sort.nsort}" var="nsort">
						<dl class="fore">
							<dt>${nsort.nsortName }</dt>
							<dd>
								<c:forEach items="${nsort.subSort }" var="subSort">
									<em><a
										href='<ls:url address="/nsort/${nsort.sortId}-${subSort.nsortId}'" />'>${subSort.nsortName}</a></em>
								</c:forEach>
							</dd>
						</dl>
					</c:forEach>

				</div>
			</div>
		</c:forEach>
	</div>
	<div class="clear"></div>
</div>
<!----两栏end---->
