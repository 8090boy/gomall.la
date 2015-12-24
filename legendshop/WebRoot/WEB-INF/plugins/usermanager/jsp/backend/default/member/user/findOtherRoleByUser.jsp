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
	function  saveLeastOne()
{
	 //获取选择的记录集合
	selAry = document.getElementsByName("strArray");
	count = 0;
	selValue = "";
	//判断是否只有一条记录被选中
	for(i=0;i<selAry.length;i++)
	{
		if(selAry[i].checked)
		{
			count++;
			selValue = selAry[i].value;
		}
	}
	if(count==0)
	{
		window.alert('至少选中一条记录！');
	}
	else
	{
      return true;
	}
	  return false;
} 
//-->
</script>
<title>角色列表</title>
</head>
<body>
	<%int offset=((Integer)request.getAttribute("offset")).intValue();%>

	<table class="${tableclass}" style="width: 100%">
		<thead>
			<tr>
				<th><a href="<ls:url address='/admin/index'/>" target="_parent">首页</a>
					&raquo; 用户管理 &raquo; <a
					href="${pageContext.request.contextPath}/admin/member/user/query">权限用户管理</a>&raquo;增加用户[${bean.name }]角色</th>
			</tr>
		</thead>
	</table>
	<form action="<ls:url address='/admin/member/user/saveRoleToUser'/>"
		id="form1" name="form1">
		<input type="hidden" id="curPageNO" name="curPageNO"
			value="${curPageNO}"> <input type="hidden" name="userId"
			value="${bean.id }">
		<div align="center">
			<%@ include file="/WEB-INF/pages/common/messages.jsp"%>
			<display:table name="list" id="item" export="true"
				class="${tableclass}" style="width:100%">
				<display:column style="width:70px"
					title="<input type='checkbox'  id='checkbox' name='checkbox' onClick='javascript:selAll()' />顺序">
					<input type="checkbox" name="strArray" value="${item.id}" /><%=offset++%></display:column>
				<display:column title="名称 " property="name" sortable="true"></display:column>
				<display:column title="角色名称 " property="roleType" sortable="true"></display:column>
				<display:column title="状态">
					<ls:optionGroup type="label" required="true" cache="true"
						beanName="ENABLED" selectedValue="${item.enabled}" defaultDisp="" />
				</display:column>
				<display:column title="备注" property="note"></display:column>
			</display:table>
			<input type="submit" value="添加" onclick="return saveLeastOne();" /> <input
				type="button" value="返回"
				onclick="window.location='${pageContext.request.contextPath}/admin/member/user/roles/${bean.id }'" />
		</div>
	</form>
	<div align="center">
		<c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
	</div>
</body>
</html>

