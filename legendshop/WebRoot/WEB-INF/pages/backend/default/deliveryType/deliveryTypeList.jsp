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
<title>配送方式列表</title>
</head>
<body>
	<form action="<ls:url address='/admin/deliveryType/query'/>" id="form1"
		method="post">
		<table class="${tableclass}" style="width: 100%">
			<thead>
				<tr>
					<th><a href="<ls:url address='/admin/index'/>"
						target="_parent">首页</a> &raquo; 商城管理 &raquo; <a
						href="<ls:url address='/admin/deliveryType/query'/>">配送方式</a></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<div align="left" style="padding: 3px">
							<input type="hidden" id="curPageNO" name="curPageNO"
								value="${curPageNO}" />
							<auth:auth ifAnyGranted="F_VIEW_ALL_DATA">
            	商城&nbsp;<input type="text" name="userName" maxlength="50"
									value="${deliveryType.userName}" />
							</auth:auth>
							配送方式&nbsp;<input type="text" name="name" maxlength="50"
								value="${deliveryType.name}" /> <input type="submit" value="搜索" />
							<auth:auth ifAnyGranted="FA_SHOP">
								<input type="button" value="创建配送方式"
									onclick='window.location="<ls:url address='/admin/deliveryType/load'/>"' />
							</auth:auth>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<div align="center">
		<%@ include file="/WEB-INF/pages/common/messages.jsp"%>
		<display:table name="list" requestURI="/admin/deliveryType/query"
			id="item" export="true" class="${tableclass}" style="width:100%">
			<display:column title="顺序" class="orderwidth">${item_rowNum}</display:column>
			<auth:auth ifAnyGranted="F_VIEW_ALL_DATA">
				<display:column title="商城" property="userName"></display:column>
			</auth:auth>
			<display:column title="配送方式" property="name"></display:column>
			<auth:auth ifAnyGranted="FA_SHOP">
				<display:column title="操作" media="html">
					<a
						href="<ls:url address='/admin/deliveryType/load/${item.dvyTypeId}'/>"
						title="修改"> <img alt="修改"
						src="<ls:templateResource item='/common/default/images/grid_edit.png'/>">
					</a>
					<a href='javascript:deleteById("${item.dvyTypeId}")' title="删除">
						<img alt="删除"
						src="<ls:templateResource item='/common/default/images/grid_delete.png'/>">
					</a>
				</display:column>
			</auth:auth>
		</display:table>
		<c:if test="${not empty toolBar}">
			<c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
		</c:if>
	</div>
	<script language="JavaScript" type="text/javascript">
			<!--
			  function deleteById(id) {
			      if(confirm("  确定删除 ?")){
			            window.location = "<ls:url address='/admin/deliveryType/delete/" + id + "'/>";
			        }
			    }
			highlightTableRows("item");
			//-->
		</script>
</body>
</html>


