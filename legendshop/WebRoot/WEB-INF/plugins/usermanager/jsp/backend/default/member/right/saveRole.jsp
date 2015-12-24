﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="/WEB-INF/tld/auth.tld" prefix="auth"%>
<html>
<head>
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/common/js/jquery.validate.js"
	type="text/javascript"></script>
<script
	src="<ls:templateResource item='/common/default/js/alternative.js'/>"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/common/default/css/errorform.css" />
<title>创建角色</title>
</head>
<body>
	<form
		action="${pageContext.request.contextPath}/admin/member/role/save"
		id="form1" method="post">
		<table class="${tableclass}" style="width: 100%">
			<thead>
				<tr>
					<th><a href="<ls:url address='/admin/index'/>"
						target="_parent">首页</a> &raquo; 用户管理 &raquo; <a
						href="${pageContext.request.contextPath}/admin/member/role/query">角色管理</a>
						&raquo; <c:choose>
							<c:when test="${not empty bean}">修改角色</c:when>
							<c:otherwise>创建角色</c:otherwise>
						</c:choose></th>
				</tr>
			</thead>
		</table>

		<input type="hidden" name="id" value="${bean.id }">


		<div align="center">
			<table align="center" class="${tableclass}" id="col1">
				<thead>
					<tr class="sortable">
						<th colspan="2">
							<div align="center">
								<c:choose>
									<c:when test="${not empty bean}">修改角色</c:when>
									<c:otherwise>创建角色</c:otherwise>
								</c:choose>
							</div>
						</th>
					</tr>
				</thead>
				<tr>
					<td>
						<div align="center">
							名称 <font color="ff0000">*</font>
						</div>
					</td>
					<td><input type="text" name="name" id="name"
						value="${bean.name}" /></td>
				</tr>
				<tr>
					<td>
						<div align="center">
							角色名称 <font color="ff0000">*</font>
						</div>
					</td>
					<td><input type="text" name="roleType" id="roleType"
						value="${bean.roleType}" /></td>
				</tr>
				<tr>
					<td>
						<div align="center">状态</div>
					</td>
					<td><label> <input type="radio" name="enabled"
							value="1" checked="checked" /> 有效
					</label> &nbsp; &nbsp; <label> <input type="radio" name="enabled"
							value="0"
							<c:if test="${bean.enabled eq 0}">
            checked="checked"
        </c:if> />
							无效
					</label></td>
				</tr>
				<tr>
					<td>
						<div align="center">备注</div>
					</td>
					<td><input name="note" type="text" value="${bean.note}">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="center">
							<auth:auth ifAnyGranted="F_SYSTEM">
								<input type="submit" value="提交" />
							</auth:auth>
							<input type="reset" value="重置" /> <input type="button"
								value="返回"
								onclick="window.location='${pageContext.request.contextPath}/admin/member/role/query'" />
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
            roleType: {
		        required: true
		    }
        },
        messages: {
       	 name: {
             required: '<fmt:message key="name.required"/>'
         },
         roleType: {
             required: '<fmt:message key="roletype.required"/>'
         }
        }
    });
 		   highlightTableRows("col1");
         //斑马条纹
     	 $("#col1 tr:nth-child(even)").addClass("even");
});
</script>
</body>
</html>