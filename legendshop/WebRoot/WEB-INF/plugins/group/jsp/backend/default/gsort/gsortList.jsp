<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="/WEB-INF/tld/auth.tld" prefix="auth"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/tld/options.tld" prefix="option"%>
<html>
<head>
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
<script
	src="<ls:templateResource item='/common/default/js/alternative.js'/>"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<ls:templateResource item='/plugins/artDialog/artDialog.js'/>"></script>
<script type="text/javascript"
	src="<ls:templateResource item='/plugins/artDialog/plugins/iframeTools.js'/>"></script>
<link
	href="<ls:templateResource item='/plugins/artDialog/skins/aero.css'/>"
	rel="stylesheet" type="text/css" />
<title>分类列表</title>
</head>
<body>
	<%
			Integer offset = (Integer)request.getAttribute("offset");
	%>
	<table class="${tableclass}" style="width: 100%">
		<thead>
			<tr>
				<td><a href="<ls:url address='/admin/index'/>" target="_parent">首页</a>
					&raquo; 团购管理 &raquo; <a
					href="${pageContext.request.contextPath}/admin/sort/query">类型管理</a></td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<form id="form1"
						action="${pageContext.request.contextPath}/admin/gsort/query">
						<input type="hidden" id="curPageNO" name="curPageNO"
							value="${curPageNO}">
						<div align="left" style="padding: 3px">
							&nbsp; 类型 <input type="text" name="sortName" id="sortName"
								maxlength="50" value="${sort.sortName}" />
							<auth:auth ifAnyGranted="F_VIEW_ALL_DATA">
			&nbsp; 商城
			<input type="text" name="userName" id="userName" maxlength="50"
									value="${sort.userName}" />
							</auth:auth>
						</div>
						<div align="left" style="padding: 3px">
							&nbsp; Header菜单 <select id="headerMenu" name="headerMenu">
								<option:optionGroup type="select" required="false" cache="true"
									beanName="YES_NO" selectedValue="${sort.headerMenu}" />
							</select> &nbsp; 导航菜单 <select id="navigationMenu" name="navigationMenu">
								<option:optionGroup type="select" required="false" cache="true"
									beanName="YES_NO" selectedValue="${sort.navigationMenu}" />
							</select> <input type="submit" value="搜索" /> <input type="button"
								value="创建商品类型"
								onclick='window.location="${pageContext.request.contextPath}/admin/gsort/load"' />
							<input type="button" value="返回商品列表"
								onclick='window.location="<ls:url address='/admin/group/product/query'/>"' />
						</div>
					</form>
				</td>
			</tr>
		</tbody>
	</table>

	<div align="center">
		<%@ include file="/WEB-INF/pages/common/messages.jsp"%>
		<display:table name="list" requestURI="/admin/gsort/query" id="item"
			export="true" class="${tableclass}" style="width:100%"
			sort="external">
			<display:column title="顺序" class="orderwidth"><%=offset++%></display:column>
			<auth:auth ifAnyGranted="F_VIEW_ALL_DATA">
				<display:column title="商城" property="userName" sortable="true"
					sortName="userName" />
			</auth:auth>
			<display:column title="名称" property="sortName" sortable="true"
				sortName="sortName"></display:column>
			<display:column title="次序" property="seq" sortable="true"
				sortName="seq"></display:column>
			<display:column title="Header菜单">
				<option:optionGroup type="label" required="false" cache="true"
					beanName="YES_NO" selectedValue="${item.headerMenu}"
					defaultDisp="否" />
			</display:column>
			<display:column title="导航菜单">
				<option:optionGroup type="label" required="false" cache="true"
					beanName="YES_NO" selectedValue="${item.navigationMenu}"
					defaultDisp="否" />
			</display:column>
			<display:column title="操作" media="html" style="width:100px">
				<c:choose>
					<c:when test="${item.status == 1}">
						<img name="statusImg" item_id="${item.sortId}"
							itemName="${item.sortName}" status="${item.status}"
							class="cursor_pointer"
							src="<ls:templateResource item='/common/default/images/blue_down.png'/> ">
					</c:when>
					<c:otherwise>
						<img name="statusImg" item_id="${item.sortId}"
							itemName="${item.sortName}" status="${item.status}"
							class="cursor_pointer"
							src="<ls:templateResource item='/common/default/images/yellow_up.png'/> ">
					</c:otherwise>
				</c:choose>
				<a
					href="${pageContext.request.contextPath}/admin/gsort/update/${item.sortId}"
					title="修改"><img alt="修改"
					src="<ls:templateResource item='/common/default/images/grid_edit.png'/> "></a>
				<auth:auth ifAnyGranted="F_OPERATOR">
					<a href='javascript:deleteSort("${item.sortId}")' title="删除"><img
						alt="删除"
						src="<ls:templateResource item='/common/default/images/grid_delete.png'/> "></a>
				</auth:auth>
			</display:column>
		</display:table>
		<c:if test="${not empty toolBar}">
			<c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
		</c:if>
	</div>
	<table style="width: 100%; border: 0px">
		<tr>
			<td align="left">说明：<br> 1.
				商品类型分为三级，一级商品类型带有一个说明图片，用于页面广告介绍，三级分类下可以挑选对应的品牌<br> 2.
				商品可以挂在一级，二级或者三级之上，三个级别有关联关系<br> 3. <img alt="创建商品类型"
				src="${pageContext.request.contextPath}/common/default/images/grid_add.png">&nbsp;创建商品类型，在不同列表中创建不同级别的商品类型<br>
				4. <img alt="修改"
				src="<ls:templateResource item='/common/default/images/grid_edit.png'/> ">
				修改按钮，在一级商品类型列表中编辑一级商品类型，其他级别一样<br> 5. <img alt="删除"
				src="<ls:templateResource item='/common/default/images/grid_delete.png'/> ">
				删除按钮，在一级商品类型列表中编辑删除一级商品类型，其他级别一样，删除前确保其下的元素已经删除<br>
			</td>
		<tr>
	</table>

	<script language="JavaScript" type="text/javascript">
<!--
  function confirmDelete()
	{
	    return con = confirm("确定要删除吗？");
	}
	
  function deleteSort(sortId){
  if(confirmDelete()){
  	 window.location.href = "${pageContext.request.contextPath}/admin/gsort/delete/"+sortId ;
  	}
  }
  
   		function pager(curPageNO){
			document.getElementById("curPageNO").value=curPageNO;
			document.getElementById("form1").submit();
		}
		
		  jQuery(document).ready(function(){
          	jQuery("img[name='statusImg']").click(function(event){
          	var item_id = $(this).attr("item_id");
          	var status = $(this).attr("status");
          	var desc;
          	var toStatus;
          	   if(status == 1){
          	   		toStatus  = 0;
          	   		desc = $(this).attr("itemName")+' 下线?';
          	   }else{
          	       toStatus = 1;
          	       desc = $(this).attr("itemName")+' 上线?';
          	   }

           	 art.dialog({
			    content: desc,
			    lock:false,
			    ok: function () {
				       jQuery.ajax({
							url:"${pageContext.request.contextPath}/admin/sort/updatestatus/" + item_id + "/" + toStatus, 
							type:'get', 
							async : false, //默认为true 异步   
							dataType : 'json', 
							error:function(data){
							alert(data);   
							},   
							success:function(data){
								if(data == 1){
									$(event.target).attr("src","<ls:templateResource item='/common/default/images/blue_down.png'/>");
								}else if(data == 0){
									$(event.target).attr("src","<ls:templateResource item='/common/default/images/yellow_up.png'/>");
								}
							   	$(event.target).attr("status",data);
							}   
							});  
				    
			        return true;
			    },
			    cancelVal: '关闭',
			    cancel: true //为true等价于function(){}
			});
			
       			 }
       		 );
              	
       		 		highlightTableRows("item");  
          });


//-->
</script>
</body>
</html>

