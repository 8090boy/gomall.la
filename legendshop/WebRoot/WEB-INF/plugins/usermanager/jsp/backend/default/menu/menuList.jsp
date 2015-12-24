<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="/WEB-INF/tld/auth.tld" prefix="auth"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<html>
<head>
<%
        Integer offset = (Integer) request.getAttribute("offset");
    %>
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
<script
	src="<ls:templateResource item='/common/default/js/alternative.js'/>"
	type="text/javascript"></script>
<%@ include file="/WEB-INF/pages/common/dialog.jsp"%>
<title>后台菜单定义列表</title>
</head>
<body>
	<form action="<ls:url address='/system/menu/query'/>" id="form1"
		method="post">
		<table class="${tableclass}" style="width: 100%">
			<thead>
				<tr>
					<th><a href="<ls:url address='/admin/index'/>"
						target="_parent">首页</a> &raquo; 用户管理 &raquo; 资源管理 &raquo; <a
						href="<ls:url address='/system/menu/query'/>">后台${menuLevel}级菜单定义</a>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<div align="left" style="padding: 3px">
							<input type="hidden" id="curPageNO" name="curPageNO"
								value="${curPageNO}" />
							<auth:auth ifAnyGranted="F_VIEW_ALL_DATA">
					            	&nbsp;名称  
					            	 <input type="text" name="name" maxlength="50"
									value="${menu.name}" />
							</auth:auth>
							<input type="submit" value="搜索" />
							<c:if test="${allParentMenu !=null }">
								<c:forEach items="${allParentMenu }" var="menu">
									<a
										href="<ls:url address='/system/menu/queryChildMenu/${menu.key}'/>">
										${menu.value }</a>&raquo; 
											    	</c:forEach>
							</c:if>
							<input type="button" value="创建${menuLevel}级菜单"
								onclick='window.location="<ls:url address='/system/menu/create/${parentId}'/>"' />
						</div>
					</td>
				</tr>
			</tbody>
		</table>

	</form>
	<div align="center">
		<%@ include file="/WEB-INF/pages/common/messages.jsp"%>
		<display:table name="list" requestURI="/system/menu/query" id="item"
			export="true" sort="external" class="${tableclass}"
			style="width:100%">
			<display:column title="顺序" class="orderwidth"><%=offset++%></display:column>
			<display:column title="名称" property="name"></display:column>
			<display:column title="标题" property="title"></display:column>
			<display:column title="动作" property="action"></display:column>
			<display:column title="角色" style="width:220px">
				<c:forEach items="${item.roleNameList }" var="roleName"
					varStatus="status">${status.count }. ${roleName } <br />
				</c:forEach>
			</display:column>
			<display:column title="插件" property="providedPlugin"></display:column>
			<display:column title="顺序" property="seq" sortable="true"
				sortName="seq"></display:column>
			<display:column title="操作" media="html" style="width:120px">
				<a id="menuRole"
					href="javascript:menuRole.appendMenuRole(${item.menuId})"> 编辑角色</a>
				<c:if
					test="${parentMenu == null || (parentMenu != null && parentMenu.level < 2)}">
					<a
						href="${pageContext.request.contextPath}/system/menu/queryChildMenu/${item.menuId }"
						title="创建子菜单"> <img
						src="<ls:templateResource item='/common/default/images/grid_add.png'/> ">
					</a>
				</c:if>
				<a href="<ls:url address='/system/menu/load/${item.menuId}'/>"
					title="修改"> <img alt="修改"
					src="<ls:templateResource item='/common/default/images/grid_edit.png'/>">
				</a>
				<a href='javascript:deleteById("${item.menuId}")' title="删除"> <img
					alt="删除"
					src="<ls:templateResource item='/common/default/images/grid_delete.png'/>">
				</a>
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
			            window.location = "<ls:url address='/system/menu/delete/" + id + "'/>";
			        }
			    }
			    
		var menuRole={
          appendMenuRole:function(id){
           var url="${pageContext.request.contextPath}/system/menu/appendMenuRole/"+id ;
		   var options={id:" appendMenuRole",title:"增加角色",
		  // follow: document.getElementById('relProdSetp'),
		   width:500,height:450,lock:false,closeFn: function(){},close: function(){window.location.reload()} };
		    art.dialog.open(url,options);
      		 }
         };	
         
         highlightTableRows("item");
			//-->
		</script>
</body>
</html>

