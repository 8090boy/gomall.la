<%@ page language="java" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/common/default/css/errorform.css" />
<title>创建用户</title>

<script language="javascript">
    $.validator.setDefaults({
    });

    $(document).ready(function() {
    jQuery("#form1").validate({
        rules: {
            name: {
                required: true
            },
            password: {
		        required: true
		    },
		    passwordag: {
		        required: true,
		        equalTo:"#password" 
		    }
        },
        messages: {
        	 name: {
                 required: '<fmt:message key="username.required"/>',
                 minlength: '<fmt:message key="username.minlength"/>'
             },
             password: {
                 required: '<fmt:message key="password.required"/>',
                 minlength: '<fmt:message key="password.minlength"/>'
             },
             passwordag: {
                 required: '<fmt:message key="password.required"/>',
                 minlength: '<fmt:message key="password.minlength"/>',
                 equalTo: '<fmt:message key="password.equalTo"/>'
             }
        }

    });
 
		//斑马条纹
     	 $("#col1 tr:nth-child(even)").addClass("even");
});

	// check if confirm password is still valid after password changed
	jQuery("#password").blur(function() {
		jQuery("#passwordag").valid();
	})
</script>
</head>


<form action="${pageContext.request.contextPath}/admin/member/user/save"
	id="form1" method="post">

	<table class="${tableclass}" style="width: 100%">
		<thead>
			<tr>
				<th><a href="<ls:url address='/admin/index'/>" target="_parent">首页</a>
					&raquo; 用户管理 &raquo; <a
					href="${pageContext.request.contextPath}/admin/member/user/query">权限用户管理</a>
					&raquo; 创建用户</th>
			</tr>
		</thead>
	</table>
	<div align="center">
		<table align="center" class="${tableclass}" id="col1">
			<thead>
				<tr class="sortable">
					<th colspan="2">
						<div align="center">创建用户</div>
					</th>
				</tr>
			</thead>
			<tr>
				<td>
					<div align="center">
						名称 <font color="ff0000">*</font>
					</div>
				</td>
				<td><input type="text" name="name" id="name" value=""
					maxlength="30" /></td>
			</tr>
			<tr>
				<td>
					<div align="center">
						密码 <font color="ff0000">*</font>
					</div>
				</td>
				<td><input type="password" name="password" id="password"
					value="" maxlength="30" /></td>
			</tr>
			<tr>
				<td>
					<div align="center">
						确认密码 <font color="ff0000">*</font>
					</div>
				</td>
				<td><input type="password" name="passwordag" id="passwordag"
					value="" maxlength="30" /></td>
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
					<div align="center">注释</div>
				</td>
				<td><input type="text" name="note" id="note" value="" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="center">
						<auth:auth ifAnyGranted="F_SYSTEM">
							<input type="submit" value="提交" />
						</auth:auth>
						<input type="reset" value="重置" /> <input type="button" value="返回"
							onclick="window.location='${pageContext.request.contextPath}/admin/member/user/query'" />
					</div>
				</td>
			</tr>
		</table>
	</div>

</form>


</html>