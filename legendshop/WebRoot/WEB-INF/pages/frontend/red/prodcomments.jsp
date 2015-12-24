<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<!----------------评论------------------------->
<div class="pagetab" id="commentTab" name="commentTab">
	<ul>
		<li class="on" score="0"><span>全部评价<strong id="cnum0">${prodCommCategory.total}</strong></span></li>
		<li score="3"><span>好评<strong id="cnum1">(${prodCommCategory.high})</strong></span></li>
		<li score="2"><span>中评<strong id="cnum2">(${prodCommCategory.medium})</strong></span></li>
		<li score="1"><span>差评<strong id="cnum3">(${prodCommCategory.low})</strong></span></li>
	</ul>
</div>
<input type="hidden" id="prodScore" name="prodScore" />
<div id="comment" class="mc tabcon" style="display: block;">
	<%@include file='prodcommentlist.jsp'%>
</div>
<!--tabcon end-->
<div class="clear"></div>
<script language="javascript">
 jQuery(document).ready(function() {
	  $("#commentTab ul li").click(function(){
    	 $("#commentTab ul li").each(function(i){
    	 	$(this).removeClass("on");
    	 });
    	 $(this).addClass("on");
    	  var score = $(this).attr("score");
    	  queryProdCommentList('${prodCommCategory.prodId}',  score, 1);
  	  });
  });
  
  function queryProdCommentList(prodId, score, curPageNO){
         jQuery("#prodCommentCurPageNO").val(curPageNO);
 		 jQuery("#prodScore").val(score);
		  var data = {
                	"curPageNO": curPageNO,
                	"score" :score
                };
			jQuery.ajax({
				url:"${pageContext.request.contextPath}/productcomment/listcontent/" + prodId,
				data: data,
				type:'post', 
				async : true, //默认为true 异步   
				error: function(jqXHR, textStatus, errorThrown) {
			 		// alert(textStatus, errorThrown);
				},
				success:function(result){
				   jQuery("#comment").html(result);
				}
				});
		 }
  
		 function commentPager(curPageNO){
           var score =  jQuery("#prodScore").val();
            queryProdCommentList('${prodCommCategory.prodId}',  score, curPageNO);
        }
 </script>
