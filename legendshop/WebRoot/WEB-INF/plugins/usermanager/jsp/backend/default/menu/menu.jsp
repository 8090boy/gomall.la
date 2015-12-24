<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<html>
<head>
<title>创建后台菜单定义表</title>
<script type='text/javascript'
	src="<ls:templateResource item='/common/js/jquery.js'/>"></script>
<script type='text/javascript'
	src="<ls:templateResource item='/common/js/jquery.validate.js'/>" /></script>
<script
	src="<ls:templateResource item='/common/default/js/alternative.js'/>"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/common/default/css/errorform.css" />
</head>
<body>
	<table class="${tableclass}" style="width: 100%">
		<thead>
			<tr>
				<th><a href="<ls:url address='/admin/index'/>" target="_parent">首页</a>
					&raquo; 权限管理 &raquo; 资源管理 &raquo; 创建菜单</th>
			</tr>
		</thead>
	</table>
	<form action="<ls:url address='/system/menu/save'/>" method="post"
		id="form1">
		<input id="menuId" name="menuId" value="${menu.menuId}" type="hidden">
		<div align="center">
			<table border="0" align="center" class="${tableclass}" id="col1">
				<thead>
					<tr class="sortable">
						<th colspan="2">
							<div align="center">
								<c:if test="${allParentMenu !=null }">
									<c:forEach items="${allParentMenu }" var="menu">
						    		${menu.value } &raquo; 
						    	</c:forEach>
								</c:if>
								创建
								<c:choose>
									<c:when test="${parentMenu !=null}">${parentMenu.level +1}级</c:when>
									<c:otherwise>1级</c:otherwise>
								</c:choose>
								菜单
							</div>
						</th>
					</tr>
				</thead>

				<tr>
					<td width="30%">
						<div align="center">
							名称: <font color="ff0000">*</font>
						</div>
					</td>
					<td><input type="text" name="name" id="name"
						value="${menu.name}" size="30" /></td>
				</tr>
				<tr>
					<td>
						<div align="center">国际化标签:</div>
					</td>
					<td><input type="text" name="label" id="label"
						value="${menu.label}" size="30" /></td>
				</tr>
				<tr>
					<td>
						<div align="center">
							标题: <font color="ff0000">*</font>
						</div>
					</td>
					<td><input type="text" name="title" id="title"
						value="${menu.title}" size="30" /></td>
				</tr>
				<c:if test="${parentMenu != null}">
					<tr>
						<td>
							<div align="center">父节点:</div>
						</td>
						<td><select id="parentId" name="parentId">
								<ls:optionGroup type="select" required="true" cache="fasle"
									defaultDisp="-- 父节点 --"
									hql="select m.menuId, m.name from Menu m where m.level = ?"
									param="${parentMenu.level}"
									selectedValue="${parentMenu.menuId}" />
						</select></td>
					</tr>
				</c:if>
				<tr>
					<td>
						<div align="center">连接地址:</div>
					</td>
					<td><input type="text" name="action" id="action"
						value="${menu.action}" size="50" /></td>
				</tr>
				<tr>
					<td>
						<div align="center">由那个插件提供的功能:</div>
					</td>
					<td><input type="text" name="providedPlugin"
						id="providedPlugin" value="${menu.providedPlugin}" /></td>
				</tr>
				<tr>
					<td>
						<div align="center">顺序:</div>
					</td>
					<td><input type="text" name="seq" id="seq" value="${menu.seq}"
						size="5" maxlength="5" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="center">
							<input type="submit" value="添加" /> <input type="reset"
								value="重置" />
							<c:choose>
								<c:when test="${parentMenu != null }">
									<input type="button" value="返回"
										onclick="window.location='${pageContext.request.contextPath}/system/menu/queryChildMenu/${parentMenu.menuId}'" />
								</c:when>
								<c:otherwise>
									<input type="button" value="返回"
										onclick="window.location='<ls:url address="/system/menu/query"/>'" />
								</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
	<script language="javascript">
		    $.validator.setDefaults({
		    });

    $(document).ready(function() {
    jQuery("#form1").validate({
            rules: {
            name: {
                required: true
            },
            title: "required",
            seq: {
             	required: true,
             	number: true
            }
        },
        messages: {
            name: {
                required: '<fmt:message key="simple.errors.required"/>'
            },
            title: {
                required: '<fmt:message key="simple.errors.required"/>'
            },
            seq: {
                required: '<fmt:message key="simple.errors.required"/>',
                number: '<fmt:message key="errors.number"><fmt:param value=""/></fmt:message>'
            },           
            
        }
    });
 		highlightTableRows("col1");
		//斑马条纹
     	 $("#col1 tr:nth-child(even)").addClass("even");
});
</script>
</body>
</html>

