<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<!----------------咨询------------------------->
<c:forEach items="${requestScope.prodCousultList}" var="consult"
	varStatus="status">
	<div class="item <c:if test='${status.count % 2 eq 0 }'>odd</c:if>">
		<div class="user">
			<span class="u-name">网友：${consult.askUserName}</span> <span
				class="u-level"><font style="color: #999999">
					${consult.gradeName}</font></span> <span class="date-ask"><fmt:formatDate
					value="${consult.recDate}" pattern="yyyy-MM-dd HH:mm" /></span>
		</div>
		<dl class="ask">
			<dt>
				<b></b>咨询：
			</dt>
			<dd>${consult.content}</dd>
		</dl>
		<c:if test="${consult.answer != null}">
			<dl class="answer">
				<dt>
					<b></b>回复：
				</dt>
				<dd>
					<div class="content" style="color: #FF7604;">${consult.answer}</div>
					<div class="date-answer">
						<fmt:formatDate value="${consult.answertime}"
							pattern="yyyy-MM-dd HH:mm" />
					</div>
				</dd>
			</dl>
		</c:if>
	</div>
</c:forEach>

<div class="extra clearfix">
	<div class="total">
		共<strong>${prodCousultTotal}</strong>条&nbsp;&nbsp;
	</div>
	<div class="join">
		购买之前，如有问题，请先咨询。&nbsp;&nbsp; <a href="#writeConsult" id="consultation">
			[发表咨询]</a>
	</div>
</div>

<div class="fanye">
	<c:out value="${prodCousultToolBar}" escapeXml="${prodCousultToolBar}"></c:out>
</div>
