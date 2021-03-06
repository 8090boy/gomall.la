<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="/WEB-INF/tld/auth.tld" prefix="auth"%>

<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<html>
<head>
<script
	src="<ls:templateResource item='/common/default/js/alternative.js'/>"
	type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
<!--
  function confirmDelete()
	{
	    return con = confirm("确定要删除吗？");
	}
	
  function selAll(){
    chk = document.getElementById("checkbox");
	allSel = document.getElementsByName("strArray");
	
	if(!chk.checked)
		for(i=0;i<allSel.length;i++)
		{
			   allSel[i].checked=false;
		}
	else
		for(i=0;i<allSel.length;i++)
		{
			   allSel[i].checked=true;
		}
	}
	
 function deleteAction()
{
	//获取选择的记录集合
	selAry = document.getElementsByName("strArray");
	count = 0;
	selValue = "";
	for(i=0;i<selAry.length;i++)
	{
		if(selAry[i].checked)
		{
			count++;
			selValue = selAry[i].value;
		}
	}
	if(count<1)
	{
		window.alert('删除时至少选中一条记录！');
	}
	else
	{
	 if( confirmDelete()){
     document.forms[0].action="${pageContext.request.contextPath}/admin/member/role/deletePermissionByRoleId";  
     document.forms[0].submit(); 
     return true ;
     }else{
       return false ;
     } 
	}
	return false;
}
	
//-->
</script>
<title>权限列表</title>
</head>
<body>
	<%
			int offset=1;
	%>

	<form
		action="${pageContext.request.contextPath}/admin/member/role/deletePermissionByRoleId"
		id="form1" method="post">

		<table class="${tableclass}" style="width: 100%">
			<thead>
				<tr>
					<th><a href="<ls:url address='/admin/index'/>"
						target="_parent">首页</a> &raquo; 用户管理 &raquo; <a
						href="<ls:url address="/admin/member/role/query"/>">角色管理</a>&raquo;角色[${bean.name }]对应的权限列表
					</th>
				</tr>
			</thead>
		</table>
		<input type="hidden" name="roleId" value="${bean.id }">
		<div align="center">
			<%@ include file="/WEB-INF/pages/common/messages.jsp"%>

			<display:table name="list" id="item" export="false"
				class="${tableclass}" style="width:100%">
				<display:column style="width:70px"
					title="<input type='checkbox'  id='checkbox' name='checkbox' onClick='javascript:selAll()' />顺序">
					<input type="checkbox" name="strArray" value="${item.id}" /><%=offset++%></display:column>
				<display:column title="名称 " property="name"></display:column>
				<display:column title="权限名称 " property="protectFunction"></display:column>
				<display:column title="备注" property="note"></display:column>
			</display:table>
			<input type="button" value="增加权限"
				onclick="window.location='<ls:url address="/admin/member/role/otherFunctions/${bean.id}"/>'" />
			&nbsp; <input type="submit" value="删除"
				onclick="return deleteAction();" />&nbsp; <input type="button"
				value="返回"
				onclick="window.location='${pageContext.request.contextPath}/admin/member/role/query'" />
		</div>
	</form>
</body>
</html>

