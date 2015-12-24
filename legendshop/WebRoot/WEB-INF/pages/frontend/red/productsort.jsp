<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<link rel="stylesheet" type="text/css" media='screen'
	href="${pageContext.request.contextPath}/common/default/css/overlay.css" />
<script type="text/javascript">
		jQuery(document).ready(function() {
	    sendData("buys", "desc", "1");
	  $("#searchBy ul li").click(function(){
	     var id = $(this).attr("id");
	     var orderDir = "desc";
    	 $("#searchBy ul li").each(function(i){
    	 if(id != $(this).attr("id")){
    	 	$(this).removeClass("on upa downa");
    	 }
    	 });
    	 $(this).addClass("on");
    	 if(id == 'cash'){
    	     	 if($(this).hasClass("upa")){
		    	   	 $(this).removeClass("upa");
		    	 	 $(this).addClass("downa");
		    	 	 orderDir = "asc";
		    	 }else{
		    		 $(this).removeClass("downa");
		    	 	 $(this).addClass("upa");
		    	 	 orderDir = "desc";
		    	 }
    	 }

    	$("#orderBy").val(id);
		$("#orderDir").val(orderDir);
		$("#curPageNO").val(1);
		//always search start with 1 when switch tab
       sendData(id,orderDir,1);
  
  	  });
	});
	
	function sendData(orderBy, orderDir, curPageNO){
	    var data = {
                	"orderBy": orderBy,
                	"orderDir": orderDir,
                	"curPageNO": curPageNO,
                	"hasProd": document.getElementById("hasProd").checked
                };
    			$.ajax({
				url:"${pageContext.request.contextPath}/prodsortlist/" + $("#sortId").val() , 
				data: data,
				type:'post', 
				async : true, //默认为true 异步   
				error: function(jqXHR, textStatus, errorThrown) {
			 		// alert(textStatus, errorThrown);
				},
				success:function(result){
				   $("#prodsortlist").html(result);
				}
				});
	}
		
			function pager(curPageNO){
			$("#curPageNO").val(curPageNO);
			sendData($("#orderBy").val(), $("#orderDir").val(), curPageNO);
		}	
		
		function hasProdSort(){
			sendData($("#orderBy").val(), $("#orderDir").val(), $("#curPageNO").val());
		}
	</script>
<!-----------right_con-------------->
<div class="right_con">
	<!--热门评论 start -->
	<jsp:include flush="true" page="/hotrecommends/${sortId}" />
	<!--热门评论 end -->

	<!--------------筛选---------------->
	<div class="m10 select">
		<div class="mt">
			<h1>平板电视</h1>
			<div class="extra">
				<a href="http://www.360buy.com/products/737-794-798.html">重置筛选条件</a>
			</div>
		</div>
		<dl class="fore">
			<dt>品牌：</dt>
			<dd>
				<div class="content">
					<div rel="0">
						<a class="curr" href="#" id="0">不限</a>
					</div>
					<div rel="8">
						<a href="#" id="3789">夏普</a>
					</div>
					<div rel="6">
						<a href="#" id="4085">索尼</a>
					</div>
					<div rel="6">
						<a href="#" id="1279">三星</a>
					</div>
					<div rel="3">
						<a href="#" id="3999">海信</a>
					</div>
					<div rel="6">
						<a href="#" id="5674">TCL</a>
					</div>
					<div rel="1">
						<a href="#" id="3781">创维</a>
					</div>
					<div rel="6">
						<a href="#" id="4678">松下</a>
					</div>
					<div rel="2">
						<a href="#" id="4012">飞利浦</a>
					</div>
					<div rel="3">
						<a href="#" id="3889">康佳</a>
					</div>
					<div rel="1">
						<a href="#" id="3888">长虹</a>
					</div>
					<div rel="5">
						<a href="#" id="54846">清华同方</a>
					</div>
					<div rel="1">
						<a href="#">东芝</a>
					</div>
					<div rel="8">
						<a href="#" id="70352">熊猫</a>
					</div>
					<div rel="4">
						<a href="#" id="9675">乐华</a>
					</div>
					<div rel="4">
						<a href="#" id="1280">LG</a>
					</div>
					<div rel="4">
						<a href="#" id="113066">联想</a>
					</div>
					<div rel="1">
						<a href="#" id="64110">BOE/京东方</a>
					</div>
					<div rel="5">
						<a href="#" id="62990">Pangoo/盘古</a>
					</div>
				</div>
			</dd>
		</dl>
		<dl>
			<dt>品类：</dt>
			<dd>
				<div>
					<a class="curr" href="#">不限</a>
				</div>
				<div>
					<a href="#">LED背光电视</a>
				</div>
				<div>
					<a href="#">LCD背光电视</a>
				</div>
				<div>
					<a href="#">等离子电视</a>
				</div>
			</dd>
		</dl>
		<dl>
			<dt>功能：</dt>
			<dd>
				<div>
					<a class="curr" href="#">不限</a>
				</div>
				<div>
					<a href="#">3D电视</a>
				</div>
				<div>
					<a href="#">非3D电视</a>
				</div>
				<div>
					<a href="#">智能电视</a>
				</div>
				<div>
					<a href="#">非智能电视</a>
				</div>
				<div>
					<a href="#">网络电视</a>
				</div>
				<div>
					<a href="#">非网络电视</a>
				</div>
			</dd>
		</dl>

	</div>
	<!---------------筛选end------------------->


	<div class="pagetab m10" id="searchBy" name="searchBy">
		<h3>排序</h3>
		<ul>
			<li id="buys" class="on"><span>销量</span></li>
			<li id="cash"><span>价格</span></li>
			<li id="comments"><span>评论数</span></li>
			<li id="rec_date"><span>上架时间</span></li>
		</ul>
		<h3>
			<input type="checkbox" id="hasProd" name="hasProd"
				onchange="javascript:hasProdSort()" /> 仅显示有货
		</h3>
	</div>
	<!--产品列表 start -->
	<!--产品列表end -->
	<div id="prodsortlist" name="prodsortlist"></div>

</div>
<!-----------right_con end-------------->
<input type="hidden" id="curPageNO" name="curPageNO" value="1" />
<input type="hidden" id="sortId" name="sortId" value="${sortId}" />
<input type="hidden" id="orderBy" name="orderBy" value="buys" />
<input type="hidden" id="orderDir" name="orderDir" value="desc" />





