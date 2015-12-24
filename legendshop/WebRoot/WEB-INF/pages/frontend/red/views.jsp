<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<link rel="stylesheet" type="text/css" media='screen'
	href="<ls:templateResource item='/common/red/css/productTab.css'/>" />
<script type="text/javascript">
	jQuery(document).ready(function($) {
	    
	  $("#recommend ul li").click(function(){
    	 $("#recommend ul li").each(function(i){
    	 	$(this).removeClass("on");
    	 });
    	 $(this).addClass("on");
  	  });
  	  
  	   $("#productTab ul li").click(function(){
    	 $("#productTab ul li").each(function(i){
    	 	$(this).removeClass("on");
    	 	var id = $(this).attr("id");
    	 	 //alert($(id + "Pane"));
    	 	$("#"+ id + "Pane").hide();
    	 });
    	 $(this).addClass("on");
    	 var id = $(this).attr("id");
    	  $("#"+id + "Pane").show();
         changeProductTab(id);
  	  });
  	  
  	  //产品评论
  	  queryProdComment('${prod.prodId}',0);
  	  
  	  //产品咨询
  	  queryProdConsult('${prod.prodId}');
  	  //call ajax
	  $.post("${pageContext.request.contextPath}/visitedprod", 
		 function(retData) {
					$("#visitedprod").html(retData);
		},'html');
	});
	
	function changeProductTab(id){
	   if("prodSpec" == id){
	   		queryParameter('${prod.prodId}');
	   }else if("prodComment" == id){
	         queryProdComment('${prod.prodId}', 0);
	   }
	}
	
function queryParameter(prodId){
    	$.ajax({
							url: "${pageContext.request.contextPath}/dynamic/queryDynamicParameter", 
							data: {"prodId":prodId},
							type:'post', 
							async : true, //默认为true 异步   
							dataType : 'html', 
							error:function(data){
							},   
							success:function(data){
								document.getElementById("prodSpecPane").innerHTML =data;
							}   
			});         
		 }
		 
function queryProdComment(prodId, score){
		  var data = {
                	"curPageNO": jQuery("#prodCommentCurPageNO").val(),
                	"score" :score
                };
			jQuery.ajax({
				url:"${pageContext.request.contextPath}/productcomment/list/" + prodId,
				data: data,
				type:'post', 
				async : true, //默认为true 异步   
				error: function(jqXHR, textStatus, errorThrown) {
			 		 //alert(textStatus, errorThrown);
				},
				success:function(result){
				   jQuery("#prodCommentPagenationPane").html(result);
				}
				});
		 }
		 
		 function queryProdConsult(prodId){
		 		  var data = {
                	"curPageNO": jQuery("#prodCousultCurPageNO").val()
                };
			jQuery.ajax({
				url:"${pageContext.request.contextPath}/productConsult/list/" + prodId,
				data: data,
				type:'post', 
				async : true, //默认为true 异步   
				error: function(jqXHR, textStatus, errorThrown) {
			 	//	 alert(textStatus, errorThrown);
				},
				success:function(result){
				   jQuery("#prodCousultPagenationPane").html(result);
				}
				});
		 }
		 
		 function saveConsult(){
		 var content =  jQuery("#consultationContent").val();
		   if(content == null || content.length < 5){
		  		 jQuery("#consult_refer_result").html("请认真填写，咨询内容必须大于5个字。");
		  		 jQuery("#column_refer_result").show();
		  	 	return;
		   }
		 	 var data = {
                	"content": content,
                	prodId : '${prodId}',
                	pointType :jQuery('input[type="radio"][name="pointType"]:checked').val()
                };
			jQuery.ajax({
				url:"${pageContext.request.contextPath}/productConsult/save",
				data: data,
				type:'post', 
				async : true, //默认为true 异步   
				error: function(jqXHR, textStatus, errorThrown) {
			 		 //alert(textStatus, errorThrown);
				},
				success:function(result){
					if(result == 0){
						jQuery("#consultationContent").val("");
						jQuery("#consult_refer_result").html("提交咨询成功");
						//刷新数据
						queryProdConsultList('${prodId}',  1);
					}else if(result == -1){
						jQuery("#consult_refer_result").html("请登录后再提交咨询");
					}else if(result == -2){
						jQuery("#consult_refer_result").html("请认真填写，咨询内容必须大于5个字。");
					}else if(result == -3){
						jQuery("#consult_refer_result").html("您刚才已经提交了咨询，请5分钟后再提交咨询");
					}else{
						jQuery("#consult_refer_result").html("提交咨询失败");
					}
				   jQuery("#column_refer_result").show();
				}
				});
		 }
</script>
<meta name="keywords" content="${prod.keyWord}" />
<meta name="description" content="${prod.keyWord}" />
<!----地址---->
<div class="w addr">
	<span><a href='<ls:url address="/index"/>'>首页</a></span>&gt; <span><a
		href="${pageContext.request.contextPath}/sort/${prod.sortId}">${prod.sortName}</a></span>&gt;
	<span><a
		href="${pageContext.request.contextPath}/nsort?sortId=${prod.sortId}&nsortId=${prod.nsortId}">${prod.nsortName}</a></span>&gt;
	<span><a
		href="${pageContext.request.contextPath}/nsort?sortId=${prod.sortId}&nsortId=${prod.nsortId}&subNsortId=${prod.subNsortId}">${prod.subNsortName}</a></span>
</div>
<!----地址end---->


<!----两栏---->
<div class="w" style="padding-top: 10px;">
	<!-----------leftm-------------->
	<div class="leftm">
		<!-- 商品分类 start -->
		<jsp:include flush="true" page="/sortlist/${prod.sortId}" />
		<!--商品分类 end -->
		<!--热门产品 start-->
		<jsp:include flush="true" page="/hoton/${prod.sortId}" />
		<!--热门产品 end -->
		<!--热门评论 start -->
		<jsp:include flush="true" page="/hotcomments/${prod.sortId}" />
		<!--热门评论 end -->
		<!--浏览历史 start-->
		<div id="visitedprod" name="visitedprod"></div>
		<!--浏览历史 end-->
	</div>
	<!-----------leftm end-------------->

	<!-----------right_con-------------->
	<div class="right_con">

		<div class="infoname">
			<h1>${prod.name}<br> <font style="color: #ff0000">${prod.brief}</font>
			</h1>
		</div>

		<!---------------产品图片------------->
		<%@include file="prodpics.jsp"%>
		<!---------------产品图片end------------->

		<!---------------产品信息------------------------>
		<ul id="summary">
			<li><span>商品编号：${prod.modelId}</span></li>
			<li>
				<div class="fl">
					<fmt:message key="price.hint" />
					：<strong class="price"> <fmt:formatNumber type="currency"
							value="${prod.price}" pattern="${CURRENCY_PATTERN}" />
					</strong>
				</div> <c:choose>
					<c:when test="${prod.price - prod.cash > 0}"> (已优惠<fmt:formatNumber
							type="currency" value="${prod.price - prod.cash}"
							pattern="${CURRENCY_PATTERN}" />）</c:when>
					<c:otherwise>&nbsp;</c:otherwise>
				</c:choose>
			</li>

			<li class="clearfix"><span class="fl">商品评分：</span>
				<div class="fl">
					<div class="star sa5"></div>
					<a href="#">(已有68人评价)</a>
				</div></li>
			<li class="hide" id="cx"><table cellspacing="0" cellpadding="0"
					border="0">
					<tbody>
						<tr>
							<td valign="top">促销信息：</td>
							<td><font color="#ef0000">已优惠￥3000.00</font></td>
						</tr>
					</tbody>
				</table></li>
			<!--促销-->
			<li class="clearfix "><span class="fl"><font color="red">赠&nbsp;&nbsp;&nbsp;&nbsp;品</font>：</span>
			<div class="fl">
					<div>
						宏碁笔记本包 <font color="red">×1</font>
					</div>
				</div></li>
		</ul>
		<!---------------产品信息end------------------------>
		<!----------------购买信息---------------------------->
		<div id="choose" class="m">
			<dl class="amount">
				<dt>&#12288;我要买：</dt>
				<dd>
					<a href="javascript:void(0)" class="reduce">-</a><input type="text"
						id="pamount" value="1"><a href="javascript:void(0)"
						class="add">+</a>
				</dd>
			</dl>
			<div class="btns">
				<a href="#" class="btn-append">添加到购物车</a> <input type="button"
					value=" " class="btn-coll">
			</div>
		</div>
		<!----------------购买信息 end---------------------------->

		<!-----------------推荐组合------------------------------>
		<div class="clear"></div>
		<div class="m10 recommend" style="display: block;">
			<div class="pagetab m10" id="recommend" name="recommend">
				<ul>
					<li class="on"><span>推荐配件</span></li>
					<li><span>人气组合</span></li>
					<li><span>推荐服务</span></li>
				</ul>
			</div>

			<div id="group" class="tabcon">
				<div class="master">
					<div class="p-img">
						<a href="#"><img width="100" height="100"
							src="../images/sample100.jpg"></a>
					</div>
					<div class="p-name">
						<a target="_blank" href="#">宏碁（acer）AS4739-382G32Mnkk
							14英寸笔记本电脑（i3-380M 2G 320G D刻 LED背光丽镜宽屏)黑灰色</a>
					</div>
					<div class="icon-add"></div>
				</div>
				<div class="suits">
					<ul class="list">
						<li>
							<div class="p-img">
								<a target="_blank" href="#"><img width="100" height="100"
									src="../images/sample100.jpg"></a>
							</div>
							<div class="p-name">
								<a target="_blank" href="#">现代（HYUNDAI）HY-N11 2.4G无线鼠标</a>
							</div>
							<div class="choose">
								<input type="checkbox" checked="true" value="326551"><span
									class="p-price"><strong>￥39.00</strong></span>
							</div>
						</li>
						<li>
							<div class="p-img">
								<a target="_blank" href="#"><img width="100" height="100"
									src="../images/sample100.jpg"></a>
							</div>
							<div class="p-name">
								<a target="_blank" href="#">现代（HYUNDAI）HY-N11 2.4G无线鼠标</a>
							</div>
							<div class="choose">
								<input type="checkbox" checked="true" value="326551"><span
									class="p-price"><strong>￥39.00</strong></span>
							</div>
						</li>
						<li>
							<div class="p-img">
								<a target="_blank" href="#"><img width="100" height="100"
									src="../images/sample100.jpg"></a>
							</div>
							<div class="p-name">
								<a target="_blank" href="#">现代（HYUNDAI）HY-N11 2.4G无线鼠标</a>
							</div>
							<div class="choose">
								<input type="checkbox" checked="true" value="326551"><span
									class="p-price"><strong>￥39.00</strong></span>
							</div>
						</li>
						<li>
							<div class="p-img">
								<a target="_blank" href="#"><img width="100" height="100"
									src="../images/sample100.jpg"></a>
							</div>
							<div class="p-name">
								<a target="_blank" href="#">现代（HYUNDAI）HY-N11 2.4G无线鼠标</a>
							</div>
							<div class="choose">
								<input type="checkbox" checked="true" value="326551"><span
									class="p-price"><strong>￥39.00</strong></span>
							</div>
						</li>
					</ul>
				</div>
				<div class="infos">
					<div class="p-name" style="text-align: left;">
						<a href="#">购买人气组合</a>
					</div>
					<div value="2950.00" id="buy-wmeprice" class="p-price">
						总京东价：<strong style="color: red;">￥2950.00</strong>
					</div>
					<div value="3771.00" id="buy-wmaprice" class="p-market">
						总定价：
						<del>￥3771.00</del>
					</div>
					<div class="btns">
						<input name="" type="image" src="../images/buyy.jpg"
							class="btn-buy" />
					</div>
				</div>

				<div class="clear"></div>
			</div>
			<!--group end-->


		</div>
		<!-----------------推荐组合 end------------------------------>

		<!-----------------detai------------------------------>
		<div id="detail" class="m10">
			<div class="pagetab" id="productTab" name="productTab">
				<ul>
					<li class="on" id="prodContent"><span>商品介绍</span></li>
					<li id="prodSpec"><span>规格参数</span></li>
					<li id="prodComment"><span>商品评论</span></li>
					<li id="prodRel"><span>相关商品</span></li>
				</ul>
			</div>
			<div class="mc fore tabcon" id="prodContentPane">
				<ul id="i-detail">
					<li title="宏碁AS4739-382G32Mnkk">商品：宏碁AS4739-382G32Mnkk</li>
					<li>生产厂家：<a href="http://www.360buy.com/brand/1000.html"
						target="_blank">宏碁</a></li>
					<li>商品产地：中国大陆</li>
					<li>商品毛重：4</li>
					<li>上架时间：2012-2-16 18:13:32</li>
					<li>价格举报：如果您发现有比京东价格更低的，<a
						href="http://myjd.360buy.com/pricetip/report/priceReport.action?id=583051"
						target="_blank">欢迎举报</a></li>
					<li>纠错信息：如果您发现商品信息不准确，<a
						href="http://club.360buy.com/jdvote/skucheck.aspx?skuid=583051&amp;cid1=670&amp;cid2=671&amp;cid3=672"
						target="_blank">欢迎纠错</a></li>
				</ul>
				<div class="content">
					<!-- 商品详细说明 -->
					${prod.content}
				</div>
			</div>
			<div class="mc fore tabcon" id="prodSpecPane" style="display: none">
			</div>
			<div class="mc fore tabcon" id="prodRelPane" style="display: none">
				<table width=100% " cellpadding="0" cellspacing="10" align="left">
					<tr>
						<c:forEach items="${requestScope.productList}" var="prod"
							varStatus="status">
							<td width="25%" align="left"><a
								href="<ls:url address='/views/${prod.prodId}'/>"> <img
									src="<ls:images item='${prod.pic}' scale='1'/>" width="150px"
									height="150px" id="pic"></a> </span><br>${prod.name}<br> <c:if
									test="${not empty prod.cash}">
									<fmt:message key="prod_cash" />
									<font color="red"><fmt:formatNumber type="currency"
											value="${prod.cash}" pattern="${CURRENCY_PATTERN}" /></font>
								</c:if></td>
							<c:if test="${(status.index+1)%4==0&&(status.index+1)>=4}">
					</tr>
					<tr>
						</c:if>
						</c:forEach>
					</tr>
				</table>

				<table>
					<tr>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
		<!-----------------detai end------------------------------>

		<!----------------评论------------------------->
		<input type="hidden" id="prodCommentCurPageNO"
			name="prodCommentCurPageNO" value="${curPageNO}" />
		<div class="m10" id="prodCommentPagenationPane"></div>
		<!----------------评论end------------------------->


		<!---------------咨询--------------------->
		<input type="hidden" id="prodCousultCurPageNO"
			name="prodCousultCurPageNO" value="${prodCousultCurPageNO}" />
		<div class="m10" id="prodCousultPagenationPane"></div>
		<!---------------咨询-end-------------------->
		<div class="clear"></div>

		<!--发表咨询-->
		<div class="Review_Form">
			<h5 id="writeConsult" name="writeConsult">发表咨询：</h5>
			<div class="Re_Explain">声明：您可在购买前对产品包装、颜色、运输、库存等方面进行咨询，我们有专人进行回复！因厂家随时会更改一些产品的包装、颜色、产地等参数，所以该回复仅在当时对提问者有效，其他网友仅供参考！咨询回复的工作时间为：周一至周五，9:00至18:00，请耐心等待工作人员回复。</div>
			<ul>
				<li id="pointType"><span style="display: inline;">咨询类型：</span>
					<input type="radio" name="pointType" value="1" checked="checked">
					商品咨询 <input type="radio" name="pointType" value="2"> 库存配送 <input
					type="radio" name="pointType" value="3"> 售后咨询 <label
					for="pointType" style="display: none;" class="error">
						请选择咨询类型</label></li>
				<li><span>咨询内容：</span> <textarea class="area1"
						name="consultationContent" id="consultationContent"
						style="font-size: 14px;"></textarea></li>
				<li style="display: none;" id="column_refer_result"
					name="column_refer_result">
					<div class="column_refer_result" id="consult_refer_result"></div>
				</li>
				<li class="buttons"><img src="../images/button_08.jpg"
					id="saveConsultation" onclick="javascript:saveConsult();"> <strong
					class="text1">请登录后再提交咨询</strong></li>
			</ul>
		</div>
		<!--发表咨询结束-->

		<!-------------------similar------------------------->
		<c:if test="${requestScope.recommendProds != null}">
			<div id="similar" class="similar">
				<div id="desgoods">

					<div class="mt">
						<h2>浏览了该商品的用户还浏览了</h2>
					</div>

					<div class="mc">
						<ul class="list-h">
							<c:forEach items="${requestScope.recommendProds}" var="prod"
								varStatus="status">
								<li>
									<div class="p-img">
										<a target="_blank" title="${prod.name }"
											href="${pageContext.request.contextPath}/views/${prod.id}">
											<img width="100px" height="100px"
											src="<ls:images item='${prod.pic}' scale='2'/>"
											alt="${prod.name}">
										</a>
									</div>
									<div class="p-name">
										<a target="_blank"
											href="${pageContext.request.contextPath}/views/${prod.id}">${prod.name }</a>
									</div>
									<div class="p-price">
										<fmt:formatNumber type="currency" value="${prod.cash}"
											pattern="${CURRENCY_PATTERN}" />
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>

				</div>
			</div>
		</c:if>
		<!-------------------similar- end------------------------>

	</div>
	<!-----------right_con end-------------->


	<div class="clear"></div>
</div>