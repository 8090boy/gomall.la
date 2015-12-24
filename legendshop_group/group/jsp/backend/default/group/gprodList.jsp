<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.legendshop.core.helper.PropertiesUtil"%>
<%@page import="com.legendshop.core.constant.SysParameterEnum"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="/WEB-INF/tld/auth.tld" prefix="auth"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<html>
<head>
    <script src="<ls:templateResource item='/common/js/jquery.js'/>" type="text/javascript"></script>
     <script type="text/javascript" src="<ls:templateResource item='/common/js/infinite-linkage.js'/>"></script>
    <script src="<ls:templateResource item='/common/default/js/alternative.js'/>" type="text/javascript"></script>
 	<%@ include file="/WEB-INF/pages/common/dialog.jsp"%>
<title>商品列表</title>
<%
	Integer offset = (Integer)request.getAttribute("offset");
%>
</head>
<body>
    <table class="${tableclass}" style="width: 100%">
    <thead>
    	<tr><th><a href="<ls:url address='/admin/index'/>" target="_parent">首页</a> &raquo; 团购管理 &raquo; <a href="<ls:url address='/admin/group/product/query'/>">团购管理</a></th></tr>
    </thead>
    <tbody>
    	<tr><td>
    	    <input type="hidden" id="curPageNO" name="curPageNO" value="${curPageNO}"/>
			<div align="left" style="padding: 5px;">
		    <auth:auth ifAnyGranted="F_VIEW_ALL_DATA">
               商城&nbsp; <input type="text" name="userName" maxlength="50" value="${prod.userName}" size="15"/>
            </auth:auth>
			  商品类型&nbsp;
          		<select class="combox"  id="sortId" name="sortId"  requiredTitle="-- 一级分类 -- "   selectedValue="${prod.sortId}"  retUrl="${pageContext.request.contextPath}/sort/loadGroupSorts/${prod.sortId}">
				</select>
        </div>
        <div align="left" style="padding: 3px">
			商品&nbsp;
			<input type="text" name="name" id="name" maxlength="50" value="${prod.name}" size="32"/>
			状态
			<select id="status" name="status">
					<option value="">请选择</option>	
			    	<option value="1">上线</option>
	      			<option value="0" >下线</option>
			</select>		
			<input type="submit" value="搜索" onclick="javascript:sendData()"/>
		   <input type="button" value="创建团购" onclick='window.location="<ls:url address='/admin/group/product/load'/>"'/>
			</div>
    	</td>
    	</tr>
    </tbody>
    </table>
	<div align="center"  id="prodContentList" name="prodContentList">
    </div>
	<table style="width: 100%; border: 0px"><tr><td align="left">说明：<br>
   1. 建立商品的五个步骤：1.商品详细信息 2.商品相关说明图片 3.动态属性 4.动态参数  5.相关商品<br>
   1. 商品可以挂在一级，二级，三级类型上<br>
   2. 商品有一个对应的品牌，品牌对应三级类型<br>
   3. 商品状态，在开始时间和结束时间之内有效，失效后包括下线状态将不会在前台显示，推荐状态为是则在首页精品推荐中出现<br>
   4. 价格、运费、库存量为正数，不填写则不在前台显示，如果填写了库存量为0则无法订购<br>
   5. 库存量在用户下单时会减少，实际库存量在订单成交时减少<br>
   6. <img alt="修改" src="<ls:templateResource item='/common/default/images/grid_edit.png'/> "> 修改按钮<br>
   7. <img alt="删除" src="<ls:templateResource item='/common/default/images/grid_delete.png'/> "> 删除按钮<br>
   </td><tr></table> 
   
   <script language="JavaScript" type="text/javascript">
<!--

 function deleteAction()
{
	//获取选择的记录集合
	selAry = document.getElementsByName("strArray");
	if(!checkSelect(selAry)){
	 window.alert('删除时至少选中一条记录！');
	 return false;
	}
	if(confirm("确定要删除吗？")){
	for(i=0;i<selAry.length;i++){
	  if(selAry[i].checked){
		var prodId = selAry[i].value;
		var name = selAry[i].getAttribute("arg");
			deleteProduct(prodId,true);
		 }
		}
	}
	return true;
}

  function confirmDelete(prodId,name)
	{
	    if(confirm("确定要删除 "+name+" 吗？")){
	   		deleteProduct(prodId,false);
	   }
	}
	

	function deleteProduct(prodId, multiDel) {
    			jQuery.ajax({
				url:"${pageContext.request.contextPath}/admin/group/product/delete/" + prodId , 
				type:'post', 
				async : true, //默认为true 异步   
			    dataType : 'html', 
				error: function(jqXHR, textStatus, errorThrown) {
			 		// alert(textStatus, errorThrown);
				},
				success:function(retData){
				if(!multiDel){
						 if(retData == null ||  retData == ''){
					 		 window.location = '${pageContext.request.contextPath}/admin/group/product/query';
				       }else{
					 		 alert(retData.substring(6, retData.length)) ;
				       }
					}
				}
				});
	}
			
		function pager(curPageNO){
			sendData(curPageNO);
		}
		
		    
		   jQuery(document).ready(function(){
		       //三级联动
              $("select.combox").initSelect();
		 		highlightTableRows("item");   
       		  //query  content
       		   sendData($("#curPageNO").val());
          });
          
      function sendData(curPageNO){
	    var data = {
                	"curPageNO": curPageNO,
                	"status": $("#status").val(),
                	"sortId": $("#sortId").val(),
                	"name" :$("#name").val()
                };
    			$.ajax({
				url:"${pageContext.request.contextPath}/admin/group/product/queryprodcontent", 
				data: data,
				type:'post', 
				async : true, //默认为true 异步   
				error: function(jqXHR, textStatus, errorThrown) {
			 		// alert(textStatus, errorThrown);
				},
				success:function(result){
				   $("#prodContentList").html(result);
				}
				});
	}
 
//-->
</script>
</body>
</html>

