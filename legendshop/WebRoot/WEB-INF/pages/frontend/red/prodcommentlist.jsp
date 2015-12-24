<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<!----------------评论------------------------->
<div id="i-comment">
	<div class="rate">
		<strong><fmt:formatNumber type="percent"
				value="${prodCommCategory.highRate}" /></strong> <br> 好评度
	</div>
	<div class="percent">
		<dl>
			<dt>好评</dt>
			<dd class="d1">
				<div style="width: ${prodCommCategory.highRate * 150}px;"></div>
			</dd>
			<dd class="d2">
				<fmt:formatNumber type="percent"
					value="${prodCommCategory.highRate}" />
			</dd>
		</dl>
		<dl>
			<dt>中评</dt>
			<dd class="d1">
				<div style="width: ${prodCommCategory.mediumRate * 150}px;"></div>
			</dd>
			<dd class="d2">
				<fmt:formatNumber type="percent"
					value="${prodCommCategory.mediumRate}" />
			</dd>
		</dl>
		<dl>
			<dt>差评</dt>
			<dd class="d1">
				<div style="width: ${prodCommCategory.lowRate * 150}px;"></div>
			</dd>
			<dd class="d2">
				<fmt:formatNumber type="percent" value="${prodCommCategory.lowRate}" />
			</dd>
		</dl>
	</div>
	<div class="btns">
		<div>我购买过此商品</div>
		<a class="btn-comment"
			href="<ls:url address='/productcomment/update/${prodCommCategory.prodId}'/>">我要评价</a>
		<div>发评价拿积分</div>
	</div>
	<div class="clear"></div>
</div>
<c:forEach items="${requestScope.list}" var="comment" varStatus="status">
	<div class="item">
		<div class="user">
			<div class="uinfo-icon">
				<a target="_blank" href="#"> <img width="50" height="50"
					src="../images/60.gif">
				</a>
			</div>
			<div class="u-name">
				<a target="_blank" href="#">${comment.userName }</a>
			</div>
			<div class="date-buy">
				购买日期<br>
				<fmt:formatDate value="${comment.buyTime }" pattern="yyyy-MM-dd" />
			</div>
		</div>
		<div class="i-item">
			<div class="o-topic">
				<strong class="topic"><a target="_blank" href="#">${comment.title }</a></strong>
				<c:choose>
					<c:when test="${comment.score!=null }">
						<span class="star sa${comment.score }"></span>
					</c:when>
					<c:otherwise>
						<span class="star sa3"></span>
					</c:otherwise>
				</c:choose>
				<span class="date-comment"><fmt:formatDate
						value="${comment.addtime }" pattern="yyyy-MM-dd HH:mm" /></span>
			</div>
			<div class="comment-content">
				<dl>
					<dt>优点：</dt>
					<dd>${comment.advtge }</dd>
				</dl>
				<dl>
					<dt>不足：</dt>
					<dd>${comment.defect }</dd>
				</dl>
				<dl>
					<dt>使用心得：</dt>
					<dd>${comment.content }</dd>
				</dl>
			</div>
			<div class="btns">
				<a href="#" class="btn-reply">有用(0)</a>
			</div>
		</div>
	</div>
</c:forEach>

<!--item end-->
<div class="fanye">
	<c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
</div>
