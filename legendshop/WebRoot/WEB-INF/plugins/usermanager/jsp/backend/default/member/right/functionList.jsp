<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<html>
<head>
<script
	src="<ls:templateResource item='/common/default/js/alternative.js'/>"
	type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
<!--
	 function deleteById(id) {
			      if(confirm("  确定删除 ?")){
			            window.location = "<ls:url address='/admin/member/right/delete/" + id + "'/>";
			        }
			    }
	
   function pager(curPageNO){
			document.getElementById("curPageNO").value=curPageNO;
			document.getElementById("form1").submit();
		}  
//-->
</script>
<title>权限列表</title>
</head>
<body>

	<%
			Integer offset=((Integer)request.getAttribute("offset"));
	%>
	<form id="form1"
		action="${pageContext.request.contextPath}/admin/member/right/query"
		method="post" id="form1">
		<table class="${tableclass}" style="width: 100%">
			<thead>
				<tr>
					<th><a href="<ls:url address='/admin/index'/>"
						target="_parent">首页</a> &raquo; 用户管理 &raquo; <a
						href="${pageContext.request.contextPath}/admin/member/right/query">权限管理</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<div align="left" style="padding: 3px">
							<input type="hidden" id="curPageNO" name="curPageNO"
								value="<%=request.getAttribute("curPageNO")%>"> &nbsp;
							名称 <input type="text" name="name" maxlength="50"
								value="${bean.name }" /> <input type="submit" value="搜索" /> <input
								type="button" value="创建权限"
								onclick='window.location="${pageContext.request.contextPath}/admin/member/right/load"' />
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<div align="center">
		<%@ include file="/WEB-INF/pages/common/messages.jsp"%>
		<display:table name="list"
			requestURI="${pageContext.request.contextPath}/admin/member/right/query"
			id="item" export="true" class="${tableclass}" style="width:100%">
			<display:column title="顺序" class="orderwidth"><%=offset++%></display:column>
			<display:column title="名称 " property="name" sortable="true"></display:column>
			<display:column title="权限名称 " property="protectFunction"></display:column>
			<display:column title="备注" property="note"></display:column>
			<display:column title="对应角色">
				<a
					href="${pageContext.request.contextPath}/admin/member/right/roles/${item.id}">角色</a>
			</display:column>
			<display:column title="操作" media="html" style="width:75px">
				<a
					href="${pageContext.request.contextPath}/admin/member/right/update/${item.id}"><img
					alt="修改"
					src="<ls:templateResource item='/common/default/images/grid_edit.png'/> "></a>
				<a href='javascript:deleteById("${item.id}")' title="删除"><img
					alt="删除"
					src="<ls:templateResource item='/common/default/images/grid_delete.png'/> "></a>
			</display:column>
		</display:table>
		<c:if test="${not empty toolBar}">
			<c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
		</c:if>
	</div>

</body>
</html>

