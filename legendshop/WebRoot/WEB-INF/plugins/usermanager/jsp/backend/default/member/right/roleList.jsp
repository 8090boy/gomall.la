<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<html>
<head>
<script
	src="<ls:templateResource item='/common/default/js/alternative.js'/>"
	type="text/javascript"></script>
<title>角色列表</title>
</head>
<body>
	<%
			String curPageNO = (String) request.getParameter("curPageNO");
			int curpage = 1;
			if ("".equals(curPageNO) || curPageNO == null)
				curpage = 1;
			else {
				curpage = Integer.parseInt(curPageNO);
			}
			int offset=((Integer)request.getAttribute("offset")).intValue();
	%>
	<form id="form1"
		action="${pageContext.request.contextPath}/admin/member/role/query">
		<table class="${tableclass}" style="width: 100%">
			<thead>
				<tr>
					<th><a href="<ls:url address='/admin/index'/>"
						target="_parent">首页</a> &raquo; 用户管理 &raquo; <a
						href="${pageContext.request.contextPath}/admin/member/role/query">角色管理</a>
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
								value="${bean.name }" /> &nbsp;状态 <select id="enabled"
								name="enabled">
								<ls:optionGroup type="select" required="true" cache="true"
									beanName="ENABLED" selectedValue="${bean.enabled}" />
							</select> <input type="submit" value="搜索" /> <input type="button"
								value="创建角色"
								onclick='window.location="${pageContext.request.contextPath}/admin/member/role/load"' />
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>

	<div align="center">
		<%@ include file="/WEB-INF/pages/common/messages.jsp"%>
		<display:table name="list"
			requestURI="${pageContext.request.contextPath}/admin/member/role/query"
			id="item" export="true" class="${tableclass}" style="width:100%">
			<display:column title="顺序" class="orderwidth"><%=offset++%></display:column>
			<display:column title="名称 " property="name" sortable="true"></display:column>
			<display:column title="角色名称 " property="roleType"></display:column>
			<display:column title="状态">
				<ls:optionGroup type="label" required="true" cache="true"
					beanName="ENABLED" selectedValue="${item.enabled}" defaultDisp="" />
			</display:column>
			<display:column title="备注" property="note"></display:column>
			<display:column title="对应权限">
				<a
					href="${pageContext.request.contextPath}/admin/member/role/functions/${item.id}">权限</a>
			</display:column>
			<display:column title="操作" media="html" style="width:75px">
				<a
					href="${pageContext.request.contextPath}/admin/member/role/update/${item.id}"><img
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
	<script language="JavaScript" type="text/javascript">
<!--
	  function deleteById(id) {
			      if(confirm("  确定删除 ?")){
			            window.location = "<ls:url address='/admin/member/role/delete/" + id + "'/>";
			        }
			    }
	highlightTableRows("item");
//-->
</script>
</body>
</html>

