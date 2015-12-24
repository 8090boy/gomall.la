<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tld/auth.tld" prefix="auth" %>
<html>
<head>
<title>创建团购商品类型</title>
		<script src="<ls:templateResource item='/common/js/jquery.js'/>" type="text/javascript"></script>
        <script src="<ls:templateResource item='/common/default/js/alternative.js'/>" type="text/javascript"></script>
		<script src="<ls:templateResource item='/plugins/My97DatePicker/WdatePicker.js'/>" type="text/javascript"></script>
		<script type="text/javascript" src="<ls:templateResource item='/common/js/infinite-linkage.js'/>"></script>
<script language="javascript">
	function checkform() {
	var content = document.getElementById("content");
      var sortId = document.getElementById("sortId");
	  if(sortId.value == null || sortId.value == '')
		{
			alert("请选择类型!");
			sortId.focus();
			return false;
		}
		var name = document.getElementById("name");
		if(name.value==null||name.value=='')
		{
			alert("请输入商品!");
			name.focus();
			return false;
		}

		if(!isNumber("salesMin")){
			return false;
		}
	   if(!isNumber("salesMax")){
			return false;
		}
		if(!isNumber("maxBuys")){
			return false;
		}
	  if(!isNumber("price")){
			return false;
		}
	   if(!isNumber("cash")){
			return false;
		}
		if(!isNumber("visualBuys")){
			return false;
		}
		if(!isNumber("stocks")){
			return false;
		}
								
	  var file = document.getElementById("file");
	  if(file.value==null||file.value==''){
		var pic = document.getElementById("pic");
		if(pic.value==null||pic.value==''){
			alert("请上传商品图片!");
			file.focus();
			return false;
			}
		}			
		return true ;
  }
  
  function isNumber(name){	
  		var element = $("#"+name);
  		var value = element.val();
        if((value == null || value=='') || isNaN(value)){
			alert(element.attr("msg"));
			element.focus();
			return false;
		}
		return true;
  }
  
  function submitFinish(){
    	var saveProdForm = document.getElementById("saveProdForm");
    	document.getElementById("nextAction").value = "success";
  		  	if(checkform()){
  		saveProdForm.submit();
  	}
  }
  
   jQuery(document).ready(function() {
            //三级联动
           $("select.combox").initSelect();
           highlightTableRows("col1");   
          //斑马条纹
     	 $("#col1 tr:nth-child(even)").addClass("even");
	});
	
</script>
</head>
<body>
    <table class="${tableclass}" style="width: 100%">
    <thead>
    	<tr><th><a href="<ls:url address='/admin/index'/>" target="_parent">首页</a> &raquo; 商品管理  &raquo; <a href="<ls:url address='/admin/group/product/query'/>">团购管理</a>
    	 &raquo; <a href="<ls:url address='/admin/group/product/load'/>">创建团购</a>
    		<c:if test="${prod.name != null}">&raquo; <a href= "<ls:url address='/views/${prod.prodId}'/>" target="_blank">${prod.name}</a></c:if>
    	</th></tr>
    </thead>
    </table>
<form action="<ls:url address='/admin/group/product/save'/>"  id="saveProdForm" name="saveProdForm" method="post" enctype="multipart/form-data" onsubmit="return checkform()">
    <input type="hidden" value="next" id="nextAction" name="nextAction"/>
    <input type="hidden" id="prodId" name="product.prodId" value="${prod.prodId}">
  <table class="${tableclass}" style="width: 100%;" id="col1">
   <thead>
    <tr>
      <td colspan="3">
      <div align="center">
		<b>团购详细信息</b>   
      </div></td>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td width="120px"><div align="center">选择团购类型<font color="#ff0000">*</font></div></td>
      <td width="48%">
      		  <select class="combox"  id="sortId" name="product.sortId"  requiredTitle="-- 团购类型 -- "  selectedValue="${prod.sortId}"  retUrl="${pageContext.request.contextPath}/sort/loadGroupSorts/${prod.sortId}">
			</select>
           还没有商品类型？请先&nbsp;<a href="${pageContext.request.contextPath}/admin/gsort/load">创建类型</a>
      </td>
      <td width="25%" rowspan="9" align="center" valign="middle" style="margin: 0px">
      	<c:choose>
      		<c:when test="${prod.pic!=null}"><a href="<ls:photo item='${prod.pic}'/>" target="_blank">
      		<img src="<ls:photo item='${prod.pic}'/>"  height="235px" width="320px"/></a>
      		</c:when>
      		<c:otherwise>请上传商品图片</c:otherwise>
      	</c:choose>
      	
      	</td>
    </tr>
    
    <tr>
      <td width="120px"><div align="center">商品<font color="#ff0000">*</font></div></td>
      <td>
			<input type="text" name="product.name" id="name" size="50" value="${prod.name}" maxlength="120"/></td>
    </tr>
    <tr>
      <td width="120px"><div align="center">型号</div></td>
      <td>
			<input type="text" name="product.modelId" id="modelId" size="50" value="${prod.modelId}" maxlength="50"/></td>
    </tr>
    <tr>
      <td width="120px"><div align="center">关键字</div></td>
      <td>
			<input type="text" name="product.keyWord" id="keyWord" size="50" value="${prod.keyWord}" maxlength="100"/></td>
    </tr> 
     <tr>
      <td width="120px"><div align="center">数量限制(<font color="#ff0000">*</font>必须是数字)</div></td>
      <td>
			最低数量&nbsp;<input type="text" name="salesMin" id="salesMin" size="10" value="${prod.groupProduct.salesMin}" maxlength="15"  msg="最低数量必须为数字!"/>&nbsp;
            最高数量&nbsp;<input type="text" name="salesMax" id="salesMax" size="10" value="${prod.groupProduct.salesMax}" maxlength="15"  msg="最高数量必须为数字!"/>&nbsp;
            每人限购&nbsp;<input type="text" name="maxBuys" id="maxBuys" size="10" value="${prod.groupProduct.maxBuys}" maxlength="15" msg="每人限购必须为数字!"/>
		</td>
    </tr> 
    <tr>
      <td width="120px"><div align="center">价格(<font color="#ff0000">*</font>必须是数字)</div></td>
      <td>
			原价&nbsp;<input type="text" name="product.price" id="price" size="10" value="${prod.groupProduct.product.price}" maxlength="15"  msg="原价必须为数字!"/>&nbsp;
            现价&nbsp;<input type="text" name="product.cash" id="cash" size="10" value="${prod.groupProduct.product.cash}" maxlength="15" msg="现价必须为数字!"/>&nbsp;
           虚拟购买&nbsp;<input type="text" name="visualBuys" id="visualBuys" size="10" value="${prod.groupProduct.visualBuys}" maxlength="15" msg="虚拟购买必须为数字!"/>
		</td>
    </tr>   
   <tr>
      <td width="120px"><div align="center">库存量(<font color="#ff0000">*</font>必须是数字)</div></td>
      <td>
			<input type="text" name="product.stocks" id="stocks" size="15" value="${prod.stocks}" maxlength="15" msg="库存量必须为数字!"/>      </td>
    </tr>
    <tr>
      <td width="120px"><div align="center">类型</div></td>
      <td>
		       <select id="prodType" name="product.prodType" style="display: none">
				  <ls:optionGroup type="select" required="true" cache="true"
	                beanName="PRODUCT_TYPE" selectedValue="${prod.prodType}"/>
	            </select>&nbsp;

	        是否推荐到首页<select id="commend" name="product.commend">
      		<c:choose>
      			<c:when test="${prod.commend == 'Y'}">
      				<option value="Y" selected="selected">是</option>	
	      			<option value="N" >否</option>
      			</c:when>
      			<c:otherwise>
      			 	<option value="N" selected="selected">否</option>
      				<option value="Y">是</option>	
      			</c:otherwise>
      		</c:choose>
      		</select>       
       </td>
    </tr> 
    <tr>
      <td width="120px"><div align="center">有效时间</div></td>
      <td>
			开始时间
			<input readonly="readonly" name="product.startDate" id="startDate" class="Wdate" type="text" onClick="WdatePicker()" value='<fmt:formatDate value="${prod.startDate}" pattern="yyyy-MM-dd"/>' />
			&nbsp;结束时间
			<input readonly="readonly"  name="product.endDate" id="endDate" class="Wdate" type="text" onClick="WdatePicker()" value='<fmt:formatDate value="${prod.endDate}" pattern="yyyy-MM-dd"/>' />
			&nbsp;券有效期
			<input readonly="readonly"  name="couponEndTime" id="couponEndTime" class="Wdate" type="text" onClick="WdatePicker()" value='<fmt:formatDate value="${prod.groupProduct.couponEndTime}" pattern="yyyy-MM-dd"/>' />
	  </td>
    </tr> 
    <tr>
      <td width="120px"><div align="center">商品图片<font color="#ff0000">*</font></div></td>
      <td><input type="file" name="product.file" id="file" size="30"/>
      	<input type="hidden" name="product.pic" id="pic" size="30" value="${prod.pic}"/>图片分辨率640×470,大小不能超过1M</td>
    </tr>
   <tr>
      <td width="120px"><div align="center">限制条件</div></td>
      <td colspan="2">
			    <select id="successCause" name="successCause">
				  <ls:optionGroup type="select" required="true" cache="true"
	                beanName="GROUP_SUCCESS_CAUSE" selectedValue="${prod.groupProduct.successCause}"/>
		        </select>
		        <select id="buyCondition" name="buyCondition">
				  <ls:optionGroup type="select" required="true" cache="true"
	                beanName="GROUP_BUY_CONDITION" selectedValue="${prod.groupProduct.buyCondition}"/>
		        </select>
	 </td>
    </tr>  
     <tr>
      <td width="120px"><div align="center">商户</div></td>
      <td colspan="2">
			    <select id="partnerId" name="partnerId">	                
	                <ls:optionGroup type="select" required="false" cache="fasle"
                     defaultDisp="--请选择商户--"  
                    hql="select t.partnerId, t.partnerName from Partner t where t.userName = ?" param="${sessionScope.SPRING_SECURITY_LAST_USERNAME}" selectedValue="${prod.groupProduct.partnerId}"/>
		        </select>
	 </td>
    </tr> 
     <tr>
      <td width="120px"><div align="center">简要描述</div></td>
      <td colspan="2"><textarea style="width:100%;height:70" name="product.brief" id="brief" >${prod.brief}</textarea></td>
    </tr>    
    <auth:auth ifAnyGranted="FA_PROD">
      <tr>
      <td width="14%"><div align="center">详细描述</div></td>
      <td colspan="2">
			<FCK:editor instanceName="product.content" height="600px" basePath="/plugins/fckeditor">
                <jsp:attribute name="value">${prod.content}</jsp:attribute>
            </FCK:editor>  
	 </td>
    </tr>
    </auth:auth>
    <tr>
      <td colspan="3"><div align="center">
        <auth:auth ifAnyGranted="FA_PROD">
          <input type="button" value="完成" onclick="javascript:submitFinish()"/>
        </auth:auth>  
        <input type="reset" value="重置"/>
		<input type="button" value="返回" onclick='window.location="<ls:url address='/admin/group/product/query'/>"'/>
      </div></td>
    </tr>
    </tbody>
  </table>
   <table style="width: 100%; border: 0px"><tr><td align="left">说明：<br>
   1. 带星号<font color="#ff0000">*</font>的属性务必要填写，如果不填写则不在页面展示。如价格和库存量等。<br>
   2. 商品类型：首先选择了一级类型，自动关联对应的二级类型，选择了二级类型，自动关联对应的三级类型<br>
   3. 是否推荐到首页如果选择是，则会在首页的精品推荐中出现<br>
   4. 商品在开始时间和时间结束之内有效，如果不填写则长期有效<br>
   5. 详细描述可以上传图片，编辑html元素
   6. 最低数量必须大于0，最高数量/每日限购：0 表示没最高上限 （商品数|人数 由成团条件决定）
   </td><tr></table> 
</form>
		</body>
</html>