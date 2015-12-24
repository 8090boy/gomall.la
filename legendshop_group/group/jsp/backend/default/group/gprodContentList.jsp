<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.legendshop.core.helper.PropertiesUtil"%>
<%@page import="com.legendshop.core.constant.SysParameterEnum"%>
<%@ include file="/WEB-INF/pages/common/back-common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<%@ taglib uri="/WEB-INF/tld/auth.tld" prefix="auth"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%
	Integer offset = (Integer)request.getAttribute("offset");
%>
        <%@ include file="/WEB-INF/pages/common/messages.jsp"%>
    <display:table name="list" requestURI="/admin/group/product/query" id="item"
         export="true"  class="${tableclass}" style="width:100%" sort="external">
      <display:column style="width:70px" title="<input type='checkbox'  id='checkbox' name='checkbox' onClick='javascript:selAll()' />顺序">
            <input type="checkbox" name="strArray" value="${item.prodId}" arg="${item.name}"/><%=offset++%>
       </display:column>
       <auth:auth ifAnyGranted="F_VIEW_ALL_DATA">
     			<display:column title="商城" property="userName"></display:column>
     </auth:auth>
      <display:column title="商品" sortable="true" sortName="name">
      		<a href= "${pageContext.request.contextPath}/views/${item.prodId}"  target="_blank">${item.name}</a>
      </display:column>
      <display:column title="原价" property="price" sortable="true" sortName="p.price"></display:column>
      <display:column title="现价" property="cash" sortable="true" sortName="p.cash"></display:column>
      <%
      	if(PropertiesUtil.getObject(SysParameterEnum.VISIT_HW_LOG_ENABLE, Boolean.class)){
      %>
      <display:column title="查看数" property="views" sortable="true" sortName="p.views"></display:column>
      <%
      	}
      %>
      <display:column title="订购数" property="buys" sortable="true" sortName="p.buys"></display:column>
      <display:column title="库存" property="stocks" sortable="true" sortName="p.stocks"></display:column>
      <display:column title="实际库存" property="actualStocks" sortable="true" sortName="p.actualStocks"></display:column>
      <auth:auth ifAnyGranted="FA_PROD">
	      <display:column title="操作" media="html" style="width: 80px;">
		  	<c:choose>
		  		<c:when test="${item.status == 1}">
		  			<img name="statusImg"  item_id="${item.prodId}"  itemName="${item.name}"  status="${item.status}"  class="cursor_pointer"   src="<ls:templateResource item='/common/default/images/blue_down.png'/> ">
		  		</c:when>
		  		<c:otherwise>
		  			<img  name="statusImg"   item_id="${item.prodId}"  itemName="${item.name}"  status="${item.status}"   class="cursor_pointer"  src="<ls:templateResource item='/common/default/images/yellow_up.png'/> ">
		  		</c:otherwise>
		  	</c:choose>
		  	 <a href="<ls:url address='/admin/group/product/load/${item.prodId}'/>" title="修改"><img alt="修改" src="<ls:templateResource item='/common/default/images/grid_edit.png'/> "></a>
		     <a href='javascript:confirmDelete("${item.prodId}","${item.name}")' title="删除"><img alt="删除" src="<ls:templateResource item='/common/default/images/grid_delete.png'/> "></a>
	      </display:column>
      </auth:auth>
    </display:table>
    <input type="button" value="刪除" style="float: left" onclick="return deleteAction();"/> 
        <c:if test="${not empty toolBar}">
            <c:out value="${toolBar}" escapeXml="${toolBar}"></c:out>
        </c:if>
   <script language="JavaScript" type="text/javascript">
<!--
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
							url:"${pageContext.request.contextPath}/admin/product/updatestatus/" + item_id + "/" + toStatus, 
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

