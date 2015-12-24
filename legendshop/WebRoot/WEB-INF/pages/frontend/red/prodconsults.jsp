<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<!----------------评论------------------------->
<div class="pagetab" id="questionTab" name="questionTab">
	<ul>
		<li class="on" id="saleConsultion" type="0"><span>全部咨询</span></li>
		<li id="saleConsultion1" type="1"><span>商品咨询</span></li>
		<li id="saleConsultion2" type="2"><span>库存配送</span></li>
		<li id="saleConsultion3" type="3"><span>售后咨询</span></li>
		<li><span>常见问题</span></li>
	</ul>
</div>
<input type="hidden" id="pointType" name="pointType" />
<div id="consult" class="mc tabcon fore" style="display: block;">
	<%@include file='prodconsultlist.jsp'%>
</div>
<!--tabcon end-->
<script language="javascript">
 jQuery(document).ready(function() {
	  $("#questionTab ul li").click(function(){
    	 $("#questionTab ul li").each(function(i){
    	 	$(this).removeClass("on");
    	 });
    	 $(this).addClass("on");
    	  var pointType = $(this).attr("type");
    	  if(pointType != null ){
    	 	 queryProdConsultList('${prodId}', pointType, 1);
    	  }else{
    	  	//其他问题
    	  	
    	  }
    	 
  	  });
  });
  
  function queryProdConsultList(prodId,  pointType, curPageNO){
         jQuery("#prodCousultCurPageNO").val(curPageNO);
          jQuery("#pointType").val(pointType);
		  var data = {
                	"curPageNO": curPageNO,
                	"pointType":pointType
                };
			jQuery.ajax({
				url:"${pageContext.request.contextPath}/productConsult/listcontent/" + prodId,
				data: data,
				type:'post', 
				async : true, //默认为true 异步   
				error: function(jqXHR, textStatus, errorThrown) {
			 		// alert(textStatus, errorThrown);
				},
				success:function(result){
				   jQuery("#consult").html(result);
				}
				});
		 }
  
		 function consultPager(curPageNO){
		 var pointType =  jQuery("#pointType").val();
            queryProdConsultList('${prodId}', pointType,curPageNO);
        }
 </script>
