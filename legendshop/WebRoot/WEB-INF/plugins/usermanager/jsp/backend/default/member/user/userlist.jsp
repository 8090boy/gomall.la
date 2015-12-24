<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<html>
<head>
<title>用户列表</title>
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
<script
	src="<ls:templateResource item='/common/default/js/alternative.js'/>"
	type="text/javascript"></script>
<script src="<ls:templateResource item='/common/js/recordstatus.js'/>"
	type="text/javascript"></script>
<script
	src="<ls:templateResource item='/common/default/js/alternative.js'/>"
	type="text/javascript"></script>
<%@ include file="/WEB-INF/pages/common/dialog.jsp"%>
</head>
<body>
	<%
			int offset=((Integer)request.getAttribute("offset")).intValue();
	%>
	<form action="<ls:url address='/admin/member/user/query'/>" id="form1"
		method="post">
		<table class="${tableclass}" style="width: 100%">
			<thead>
				<tr>
					<th><a href="<ls:url address='/admin/index'/>"
						target="_parent">首页</a> &raquo; 用户管理 &raquo; <a
						href="<ls:url address='/admin/member/user/query'/>">用户管理</a></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<div align="left" style="padding: 3px">
							<input type="hidden" id="curPageNO" name="curPageNO"
								value="<%=request.getAttribute("curPageNO")%>"> &nbsp;
							用户名 <input type="text" name="name" maxlength="50"
								value="${bean.name }" /> &nbsp;状态 <select id="enabled"
								name="enabled">
								<ls:optionGroup type="select" required="false" cache="true"
									beanName="ENABLED" selectedValue="${bean.enabled}" />
							</select> <input type="submit" value="搜索" />
							<!--
							<input type="button" value="创建用户" onclick='window.location="<ls:url address='/admin/member/user/load'/>"'/>
							  -->
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<div align="center">
		<%@ include file="/WEB-INF/pages/common/messages.jsp"%>
		<display:table name="list"
			requestURI="${pageContext.request.contextPath}/admin/member/user/query"
			id="item" export="true" class="${tableclass}" style="width:100%">
			<display:column title="顺序" class="orderwidth"><%=offset++%></display:column>
			<display:column title="用户名 " property="name" sortable="true"></display:column>
			<display:column title="状态">
				<ls:optionGroup type="label" required="true" cache="true"
					beanName="ENABLED" selectedValue="${item.enabled}" defaultDisp="" />
			</display:column>
			<display:column title="备注" property="note"></display:column>
			<display:column title="用户角色">
				<a href="<ls:url address='/admin/member/user/roles/${item.id}'/>">用户角色</a>
			</display:column>
			<display:column title="用户权限">
				<a
					href="<ls:url address='/admin/member/user/functions/${item.id}'/>">用户权限</a>
			</display:column>
			<display:column title="修改密码">
				<a href="<ls:url address='/admin/member/user/update/${item.id}'/>">修改密码</a>
			</display:column>
			<display:column title="状态">
				<c:choose>
					<c:when test="${item.enabled == 1}">
						<img name="statusImg" itemId="${item.id}" itemName="${item.name}"
							status="${item.enabled}" class="cursor_pointer"
							src="<ls:templateResource item='/common/default/images/blue_down.png'/> ">
					</c:when>
					<c:otherwise>
						<img name="statusImg" itemId="${item.id}" itemName="${item.name}"
							status="${item.enabled}" class="cursor_pointer"
							src="<ls:templateResource item='/common/default/images/yellow_up.png'/> ">
					</c:otherwise>
				</c:choose>
			</display:column>
		</display:table>
		<c:if test="${not empty toolBar}">
			<c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
		</c:if>
	</div>
	<div align="center">

		<script language="JavaScript" type="text/javascript">
<!--
		  $(document).ready(function(){
          	       $("img[name='statusImg']").click(function(event){
	          		$this = $(this);
	          		initStatus($this.attr("itemId"), $this.attr("itemName"),  $this.attr("status"), "${pageContext.request.contextPath}/admin/system/userDetail/updatestatus/", event.target,"${pageContext.request.contextPath}");
	       			 }
	       		);
				highlightTableRows("item");
          });
        
//-->
</script>
</body>
</html>
