<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="/WEB-INF/tld/auth.tld" prefix="auth"%>
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/common/js/jquery.validate.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/common/default/css/errorform.css" />
<script language="javascript">
    $.validator.setDefaults({
    });

    $(document).ready(function() {
    jQuery("#form1").validate({
        rules: {
            name: {
                required: true
            },
            protectFunction: {
		        required: true
		    }
        },
        messages: {

          	 name: {
                required: '<fmt:message key="name.required"/>'
            },
            protectFunction: {
                required: '<fmt:message key="function.required"/>'
            }
        }
    });
		//斑马条纹
     	 $("#col1 tr:nth-child(even)").addClass("even");
});
</script>
</head>


<body>
	<div align="center">
		<form
			action="${pageContext.request.contextPath}/admin/member/right/save"
			id="form1" method="post">
			<table class="${tableclass}" style="width: 100%">
				<thead>
					<tr>
						<th><a href="<ls:url address='/admin/index'/>"
							target="_parent">首页</a> &raquo; 用户管理 &raquo; 权限管理 &raquo; 修改权限</th>
					</tr>
				</thead>
			</table>


			<table align="center" class="${tableclass}" id="col1">
				<thead>
					<tr class="sortable">
						<th colspan="2">
							<div align="center">修改权限</div>
						</th>
					</tr>
				</thead>


				<tr style="display: none">
					<td width="163" height="29" align="center" class="forumRow">
						主键：</td>
					<td width="211" align="center" class="forumRow"><input
						type="text" name="id" value="${bean.id }" readonly="true">
					</td>
				</tr>
				<tr>
					<td height="27" align="center" class="forumRow">
						<div align="right">
							名称 <font color="ff0000">*</font>
						</div>
					</td>
					<td align="center" class="forumRow"><input type="text"
						name="name" value="${bean.name}"></td>
				</tr>
				<tr>
					<td height="27" align="center" class="forumRow"><div
							align="right">
							权限名称 <font color="#ff0000">*</font>
						</div></td>
					<td align="center" class="forumRow"><input type="text"
						name="protectFunction" value="${bean.protectFunction }" /></td>
				</tr>
				<tr>
					<td height="27" align="center" class="forumRow"><div
							align="right">备注：</div></td>
					<td align="center" class="forumRow"><input type="text"
						name="note" value="${bean.note}"></td>
				</tr>
				<tr>
					<td height="42" colspan="2" class="forumRow">
						<div align="center">
							<input type="submit" name="Submit" value="提交"> <input
								type="reset" name="cancel" value="重置"> <input
								type="button" value="返回"
								onclick="window.location='${pageContext.request.contextPath}/admin/member/right/query'" />
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	&nbsp;
</body>
</html>

