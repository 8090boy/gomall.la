<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
	    <link rel="stylesheet" type="text/css" media='screen' href="<ls:templateResource item='/common/default/css/addtocart.css'/>" />
<script type="text/javascript">
function lxfEndtime(){
    $(".lxftime").each(function() {
        var lxfday = $(this).attr("lxfday"); //用来判断是否显示天数的变量
        
        var endtime = new Date($(this).attr("endtime")).getTime(); //取结束日期(毫秒值)
        var nowtime = new Date().getTime();        //今天的日期(毫秒值)
        var youtime = endtime - nowtime; //还有多久(毫秒值)
        var seconds = youtime / 1000;
        var minutes = Math.floor(seconds / 60);
        var hours = Math.floor(minutes / 60);
        var days = Math.floor(hours / 24);
        var CDay = days;
        var CHour = hours % 24;
        var CMinute = minutes % 60;
        var CSecond = Math.floor(seconds % 60); //"%"是取余运算，可以理解为60进一后取余数，然后只要余数。
        if (endtime <= nowtime) {
            $(this).html("<strong>已过期</strong>");//如果结束日期小于当前日期就提示过期啦
        } else {
            if ($(this).attr("lxfday") == "no") {
                $(this).html("<span>剩余</span><strong>" + CHour + "</strong>时<strong>" + CMinute + "</strong>分<strong>" + CSecond + "</strong>秒");          //输出没有天数的数据
            }
            else
            {
                $(this).html("<span>剩余</span><strong>" + days + "</strong>天<strong>" + CHour + "</strong>时<strong>" + CMinute + "</strong>分<strong>" + CSecond + "</strong>秒");          //输出有天数的数据
            }
        }
    });
 setTimeout("lxfEndtime()",1000);
};
$(function(){
    lxfEndtime();
 });



//加入购物车
 function showcart(show){
	var facebox1 = document.getElementById("facebox");
	if(show == 0){
		facebox1.style.display = "none";
	}else{
		   var count = 1;			
			var carriage = '${product.carriage}';
			if(carriage == null){
				carriage = 0;
			}
		var prodAttr = "";
		    jQuery.ajax({
							url: "${pageContext.request.contextPath}/addtocart", 
							data: {"prodId":${product.prodId}, "count": count, "attribute": prodAttr},
							type:'post', 
							async : false, //默认为true 异步   
							dataType : 'json', 
							error:function(data){
							},   
							success:function(retData){
							       document.getElementById("basket_total_cash").innerHTML = "<b>" + retData.BASKET_TOTAL_CASH + "</b>";
							       document.getElementById("basket_count").innerHTML = retData.BASKET_COUNT;
							       if(retData.BASKET_COUNT > 0){
							       	document.getElementById("go_ToCash").style.display = "";
							       }
									    if(retData.status == 'LESS'){
												 document.getElementById("can_AddToCart").innerHTML = '<fmt:message key="product.less" />';
												  document.getElementById("product_Less").style.display = "";
									       }else if(retData.status == 'OWNER'){
												 document.getElementById("can_AddToCart").innerHTML = '<fmt:message key="failed.product.owner" />';
									       }else if(retData.status == 'MAX'){
												 document.getElementById("can_AddToCart").innerHTML = '<fmt:message key="failed.product.basket.max" />';
									       }else if(retData.status == 'ERR'){
												 document.getElementById("can_AddToCart").innerHTML = '<fmt:message key="failed.product.basket.error" />';
									       }
									      
							}   
			});  
		    facebox1.style.display = "block";
	}
}

 function gotoCash(){
		document.getElementById("form1").submit();
}


 </script>
<!----地址---->
 <div class="w addr">
   <span><a href="<ls:url address='/group/index'/>">首页</a></span>&gt;<span>团购</span>&gt;<span>${product.name}</span> 
 </div>
<!----地址end-
 <div class="w banner1"><img src="${pageContext.request.contextPath}/img/group/banner1.gif" width="1216" height="128" /></div>
<c:forEach items="${requestScope.INDEX_ADV_1}" var="adv">
 <div class="w banner1"><a href="${adv.linkUrl}"><img src="<ls:photo item='${adv.picUrl}'/>" width="1216" height="128" /></a></div>
</c:forEach>
--->
<!----两栏---->
 <div class="w"> 
    <!----右边---->
    <div class="t_right">   
      <jsp:include page="/group/clientServicePanel" />
      <jsp:include page="/group/questionPanel" />  
       
    </div>
<!----右边end---->
    
<!----左边---->
    <div class="t_left">    
       
       <!----up---->
       <div class="t_info_box">
                <h1>
                <c:choose>
		          	<c:when test="${not empty product.brief }">${product.brief }</c:when>
		          	<c:otherwise>${product.name}</c:otherwise>
		          </c:choose>
          </h1>
                <div class="main">
                    <table class="deal_discount">
                        <tbody><tr>
                            <td>原价</td>
                            <td>折扣</td>
                            <td>节省</td>
                        </tr>
                        <tr>
                            <th><span><del>¥<fmt:formatNumber value="${product.price}" pattern="#.0#"/></del></span></th>
                            <th>
                            
	                        <c:if test="${product.cash > 0 and product.price > 0 }">
	                        <span><fmt:formatNumber  value="${product.cash*10 / product.price}" pattern="#.#"/>折</span>
	                        </c:if>
                        	</th>
                            <th><span>¥<fmt:formatNumber value="${product.price-product.cash}" pattern="#.0#"/></span></th>
                        </tr>
                    </tbody></table>
                    <div class="deal-buy">                        
                        <p class="deal-price">
                            <strong>¥<fmt:formatNumber value="${product.cash}" pattern="#.0#"/></strong><span><a href="javascript:showcart(1)">抢购</a></span></p>
                    </div>
                    
             <div class="downinfo" style="margin-top:75px;"><p><span class="text_red">${product.buys+visualBuys }</span> 人已购买</p></div>                  
                    
            <div class="downinfo" ><p><label>本团购剩余</label>${product.actualStocks  }<br />
            <c:if test="${not empty  product.endDate }">
            <div class="lxftime" endtime="<fmt:formatDate value="${product.endDate }" pattern="MM/dd/yyyy hh:mm:ss" />"></div>            
            </c:if>
            <!-- <span class="text_gray">1</span>天<span class="text_gray">20</span>小时<span class="text_gray">10</span>分</p> -->
            </div>
            
            <!-- <div class="downinfo"><p  class="mailfree">网上支付满69元免运费</p></div>     -->         
             
            </div>
            
            <div class="side">
                <div class="deal-buy-cover-img"><img src="<ls:photo item='${product.pic}'/>" width="440"></div>
            </div>
            <div class="clear"></div>
            
            
        </div>
        <!----up end---->
        <div id="facebox" style="position: absolute; z-index: 9999; top: 260px; left: 430px; display: none;overflow: hidden;">
			<div>
				<h2><span id="can_AddToCart"><fmt:message key="add.tocart.seccess"/></span></h2>
				<p><fmt:message key="cart.have.products"><fmt:param value='<span id="basket_count">1</span>'/></fmt:message>
				<span id="basket_total_cash">1.00</span>&nbsp;<fmt:message key="curreny.unit"/></p>
				<p style="color:#666">
				   <span id="go_ToCash" style="display: none;"><input type="button" value='<fmt:message key="goto.settlement" />' onclick="javascript:gotoCash();" /></span> 
					<a href="javascript:showcart(0)"><fmt:message key="buy.others" /></a>
					<span id="product_Less" style="display: none;"><a href="${pageContext.request.contextPath}/leaveword"><fmt:message key="notice.product.less"/></a></span>
				</p>
			</div>
		</div>
        
        <!----down---->
       <div class="t_info_box t_inback">
         <div class="infonav">
           <table width="425" height="41" >
             <tr>
             <td width="70" align="center"><strong>商品详情</strong></td><td width="3" align="center">
             <img src="${pageContext.request.contextPath}/img/group/bg2_06.gif" width="3" height="32" /></td>
             <%-- <td width="142" align="center">客服电话<span class="red"><strong>400-866-3423</strong></span></td>
             <td width="3" align="center"><img src="${pageContext.request.contextPath}/img/group/bg2_06.gif" width="3" height="32" /></td>
             <td width="52" align="center" class="text_red">$48</td> --%>
             <td align="center" ><%-- <img src="${pageContext.request.contextPath}/img/group/gobuy.gif" width="87" height="29" /> --%></td>
           </tr></table>
         </div>
         <div class="tinfo_left">         
           ${product.content }
         </div>
         
         <div class="tinfo_right">
         <c:if test="${not empty partner }">
           <strong>${partner.title }</strong><br />
           <c:if test="${not empty partner.homepage }">
           <strong>主页：</strong><a href="${partner.homepage}">${partner.homepage }</a><br />
           </c:if>
           <strong>地址：</strong>${partner.address}<br />
           <strong>电话：</strong>${partner.phone}<br />
         </c:if>
         </div>
         
         <div class="clear"></div>
       
       
       </div>
        <!----down end---->
    

    </div>
    <!----左边end---->
    
    <div class="clear"></div>
 </div>
<!----两栏end---->
                     <form action="${pageContext.request.contextPath}/basket/save" id="form1" method="post">
                      <input type="hidden" id="prodId" name="prodId" value="${product.prodId}"/>
                      </form>
