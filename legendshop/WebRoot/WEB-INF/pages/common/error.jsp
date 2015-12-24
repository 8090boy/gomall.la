<%@ page language="java" isErrorPage="true"
	contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/common.jsp"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<%@include file='localeBundle.jsp'%>
<html>
<head>
<title><c:choose>
		<c:when test="${User_Messages.code != null}">${User_Messages.code}</c:when>
		<c:otherwise>500</c:otherwise>
	</c:choose> Error - LegendShop</title>
</head>
<body>
	<center>
		<TABLE cellSpacing="0" cellPadding="0" width="955px">
			<TBODY>
				<tr>
					<TD noWrap width="1%" rowSpan=3><a
						href="${pageContext.request.contextPath}/index"><img
							src="${pageContext.request.contextPath}/common/images/logo.png"
							width="200px" title="LegendShop" /></a>
					<TD>&nbsp;</TD>
				</tr>
				<tr bgColor=#3366cc>
					<TD><FONT face=arial,sans-serif color=#ffffff><B> <c:choose>
									<c:when test="${User_Messages.code != null}">${User_Messages.code}</c:when>
									<c:otherwise>500</c:otherwise>
								</c:choose> Error
						</B></FONT></TD>
				</tr>
				<tr>
					<TD>&nbsp;</TD>
				</tr>
			</TBODY>
		</TABLE>

		<br>
		<table cellSpacing=0 cellPadding=0 width="955px">
			<tr style="display: none" id="errorMessages" name="errorMessages">
				<TD>${ERROR_MESSAGE}</TD>
			</tr>
			<tr>
				<td>
					<BLOCKQUOTE>
						<c:choose>
							<c:when test="${User_Messages.title != null}">
								<H2>${User_Messages.title}</H2>
							  			 ${User_Messages.desc} 
							</c:when>
							<c:otherwise>
								<H2>系统错误</H2>
							 			 Internal error found on this server. 
							</c:otherwise>
						</c:choose>
						<fmt:message key="error.page.message" />
					</BLOCKQUOTE>
				</td>
			</tr>
		</table>
		<jsp:include page="moreoption.jsp"></jsp:include>
	</center>


	<br>
</body>
</html>
