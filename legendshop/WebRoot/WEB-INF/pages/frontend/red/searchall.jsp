<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/common.jsp'%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<script type="text/javascript"
	src="<ls:templateResource item='/common/js/infinite-linkage.js'/>"></script>
<link
	href="${pageContext.request.contextPath}/common/default/css/pager.css"
	rel="stylesheet" type="text/css" />
<script>
	function clearCritria(){
		  $("#provinceid").val("");
		  $("#cityid").val("");
		  $("#areaid").val("");
		 $("#priceStart").val("");
		 $("#priceEnd").val("");
	}	
		
	jQuery(document).ready(function() {
	   //jQuery("b").css("color","red");
	  // jQuery("#apple img[rel]").overlay({effect: 'apple'}); 
	  sendData("buys", "desc", "1");
	  $("select.combox").initSelect();
	  $("#searchBy ul li").click(function(){
	     var id = $(this).attr("id");
	     var orderDir;
    	 $("#searchBy ul li").each(function(i){
    	 if(id != $(this).attr("id")){
    	 	$(this).removeClass("on upa downa");
    	 }
    	 });
    	 $(this).addClass("on");
    	 if(id == 'price'){
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


		//alert(id + ", " + orderDir);
    	//submitSearchAllTopform();
       sendData(id,orderDir,$("#curPageNO").val());
  
  	  });
	});
	
	
	function sendData(orderBy, orderDir, curPageNO){
 /*
		if($("#orderBy").val() == orderBy ){
		if(orderBy == 'price' ){
		   if(orderDir != undefined && $("#orderDir").val() == orderDir){
		   return;
		   }
		}else{
		  return;
		}
	}
	*/
		$("#orderBy").val(orderBy);
		$("#orderDir").val(orderDir);
		$("#curPageNO").val(curPageNO);
		
	    var data = {
      			    provinceid:$("#provinceid").val(), 
					cityid:$("#cityid").val(), 
					areaid:$("#areaid").val(), 
					entityType:$("#entityType").val(), 
					keyword:$("#keyword").val(), 
                	priceStart:$("#priceStart").val(), 
                	priceEnd: $("#priceEnd").val(),
                	orderBy: $("#orderBy").val(),
                	orderDir: $("#orderDir").val(),
                	curPageNO: $("#curPageNO").val()
                };
    			$.ajax({
				url:"${pageContext.request.contextPath}/ajaxsearch", 
				data: data,
				type:'post', 
				async : true, //默认为true 异步   
				error: function(jqXHR, textStatus, errorThrown) {
			 		// console.log(textStatus, errorThrown);
				},
				success:function(result){
				   $("#ajaxSearch").html(result);
				}
				});
	}
		
			function pager(curPageNO){
			//alert(curPageNO);
			$("#curPageNO").val(curPageNO);
			//submitSearchAllTopform();
			sendData($("#orderBy").val(), $("#orderDir").val(), $("#curPageNO").val());
		}	
  </script>
<!--------------筛选---------------->
<div class="w">
	<div class="m10 select selectfull">
		<div class="mt">
			<h1>高级查询</h1>
			<div class="extra">
				<a href="javascript:clearCritria();">重置筛选条件</a>
			</div>
		</div>
		<c:if test="${searchArgs.entityType == 0}">
			<dl>
				<dt>
					<fmt:message key="search.by.price" />
					：
				</dt>
				<dd>
					<div class="content">
						<div>
							<input type="hidden" id="orderBy" name="orderBy"
								value="${searchArgs.orderBy}" /> <input type="hidden"
								id="orderDir" name="orderDir" value="${searchArgs.orderDir}" />
							<fmt:message key="from.hint" />
							<input type="text" id="priceStart" name="priceStart"
								height="28px" value="${searchArgs.priceStart}" maxlength="8"
								size="8" />
						</div>
						<div>
							<fmt:message key="to.hint" />
							<input type="text" id="priceEnd" name="priceEnd" height="28px"
								maxlength="8" value="${searchArgs.priceEnd}" size="8"
								onkeydown='if(event.keyCode==13)submitSearchAllTopform(0,document.getElementById("keyword").value);' />
						</div>
						<div>
							<input type="checkbox" id="hasProd" name="hasProd" /> 仅显示有货
						</div>
					</div>
				</dd>
			</dl>
		</c:if>
		<dl>
			<dt>
				<fmt:message key="search.shop.by.address" />
				：
			</dt>
			<dd>
				<div>
					<select class="combox" id="provinceid" name="provinceid"
						requiredTitle="true" childNode="cityid"
						selectedValue="${searchArgs.provinceid}"
						retUrl="${pageContext.request.contextPath}/common/loadProvinces">
					</select>&nbsp;
				</div>
				<div>
					<select class="combox" id="cityid" name="cityid"
						requiredTitle="true" selectedValue="${searchArgs.cityid}"
						showNone="false" parentValue="${searchArgs.provinceid}"
						childNode="areaid"
						retUrl="${pageContext.request.contextPath}/common/loadCities/{value}">
					</select>&nbsp;
				</div>
				<div>
					<select class="combox" id="areaid" name="areaid"
						requiredTitle="true" selectedValue="${searchArgs.areaid}"
						showNone="false" parentValue="${searchArgs.cityid}"
						retUrl="${pageContext.request.contextPath}/common/loadAreas/{value}">
					</select>
				</div>
				<div>
					<input type="button" value="确定"
						onclick='javascript:submitSearchAllTopform(0,document.getElementById("keyword").value);'>
				</div>
			</dd>
		</dl>


	</div>
</div>
<!---------------筛选end------------------->

<div class="w">

	<div class="pagetab m10" id="searchBy" name="searchBy">
		<h3>搜索结果</h3>
		<ul>
			<li class="on" id="buys"><span>销量</span></li>
			<li id="price"><span>价格</span></li>
			<li id="commentNum"><span>评论数</span></li>
			<li id="date"><span>上架时间</span></li>
		</ul>
	</div>
</div>



<!----两栏---->
<div class="w" id="allsort">


	<div class="shop_slist" id="ajaxSearch" name="ajaxSearch">


		<div class="clear"></div>
	</div>
	<!----两栏end---->