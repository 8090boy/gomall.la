<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<div class="o-mt">
	<h2>订单中心</h2>
</div>
<div class="form search-01">
	<div class="item">
		<div class="fl fore">
			<select name="" class="sele" id="orderType">
				<c:choose>
					<c:when test="${not empty orderType and orderType==1 }">
						<option value="1" selected="selected">近一个月订单</option>
					</c:when>
					<c:otherwise>
						<option value="1">近一个月订单</option>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${not empty orderType and orderType==2 }">
						<option value="2" selected="selected">一个月前订单</option>
					</c:when>
					<c:otherwise>
						<option value="2">一个月前订单</option>
					</c:otherwise>
				</c:choose>

			</select> <select name="" class="sele" id="orderActiveStatus">
				<c:choose>
					<c:when
						test="${not empty orderActiveStatus and orderActiveStatus==1 }">
						<option value="1" selected="selected">有效订单</option>
					</c:when>
					<c:otherwise>
						<option value="1">有效订单</option>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when
						test="${not empty orderActiveStatus and orderActiveStatus==2}">
						<option value="2" selected="selected">已取消订单</option>
					</c:when>
					<c:otherwise>
						<option value="2">已取消订单</option>
					</c:otherwise>
				</c:choose>
			</select>
		</div>
		<div class="fr">
			<c:choose>
				<c:when test="${not empty kwText and kwText!='商品、订单编号'}">
					<input type="text" value="${kwText }" class="text" name=""
						id="ip_keyword">
				</c:when>
				<c:otherwise>
					<input type="text" value="商品、订单编号" class="text" name=""
						id="ip_keyword">
				</c:otherwise>
			</c:choose>
			<input type="button" class="bti" id="btn_keyword" value="查 询" name="">
		</div>
		<div class="clear"></div>
	</div>
</div>

<!--user info-->
<!--user info end-->

<!-------------订单---------------->
<div id="recommend" class="m10 recommend" style="display: block;">
	<div class="pagetab m10">
		<ul>
			<li class="on"><c:choose>
					<c:when test="${not empty orderType and orderType==2 }">
						<span>一个月前的订单</span>
					</c:when>
					<c:otherwise>
						<span>近一个月订单</span>
					</c:otherwise>
				</c:choose></li>
			<li><span>&nbsp;</span></li>
		</ul>
	</div>

	<table width="100%" cellspacing="0" cellpadding="0" class="buytable">
		<tbody>
			<tr>
				<th width="12%">订单编号</th>
				<th width="28%">订单商品</th>
				<th width="10%">收货人</th>
				<th width="12%">订单金额</th>
				<th width="12%">下单时间</th>
				<th width="12%">订单状态</th>
				<th width="14%">操作</th>
			</tr>

			<c:forEach items="${requestScope.list}" var="order"
				varStatus="status">
				<tr>
					<td><a
						href='${pageContext.request.contextPath}/p/orderDetail/${order.subNumber}'
						target="_blank" class="" title="查看订单详情">${order.subNumber}</a></td>
					<td>${order.prodName}</td>
					<td>${order.orderName}</td>
					<td><fmt:formatNumber type="currency" value="${order.total}"
							pattern="${CURRENCY_PATTERN}" /><br>${order.payTypeName}</td>
					<td><span class="ftx-03"> <fmt:formatDate
								value="${ order.subDate}" type="date" /><br /> <fmt:formatDate
								value="${ order.subDate}" type="time" />
					</span></td>
					<td><span class="ftx-03"> <ls:optionGroup type="label"
								required="true" cache="true" beanName="ORDER_STATUS"
								selectedValue="${order.status}" />
					</span></td>
					<td class="order-doi"><a
						href='${pageContext.request.contextPath}/p/orderDetail/${order.subNumber}'
						target="_blank" title="查看订单详情">查看</a>|<a target="_blank"
						href="${pageContext.request.contextPath}/p/orderDetail/${order.subNumber}?comment=true"
						title="对商品质量进行评价">评价</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>
<!-------------订单end---------------->



<div id="recommend" class="m10 recommend" style="display: block;">
	<div class="pagetab m10">
		<ul>
			<li class="on"><span>猜你喜欢</span></li>
			<li><span>推荐服务</span></li>
		</ul>
	</div>

	<div id="group" class="tabcon">

		<div class="suits" style="width: 100%;">
			<ul class="list">
				<li>
					<div class="p-img">
						<a target="_blank" href="#"><img width="100" height="100"
							src="../images/sample100.jpg"> </a>
					</div>
					<div class="p-name">
						<a target="_blank" href="#">现代（HYUNDAI）HY-N11 2.4G无线鼠标</a>
					</div>
					<div class="choose">
						<span class="p-price"><strong>￥39.00</strong> </span>
					</div>
				</li>
				<li>
					<div class="p-img">
						<a target="_blank" href="#"><img width="100" height="100"
							src="../images/sample100.jpg"> </a>
					</div>
					<div class="p-name">
						<a target="_blank" href="#">现代（HYUNDAI）HY-N11 2.4G无线鼠标</a>
					</div>
					<div class="choose">
						<span class="p-price"><strong>￥39.00</strong> </span>
					</div>
				</li>
				<li>
					<div class="p-img">
						<a target="_blank" href="#"><img width="100" height="100"
							src="../images/sample100.jpg"> </a>
					</div>
					<div class="p-name">
						<a target="_blank" href="#">现代（HYUNDAI）HY-N11 2.4G无线鼠标</a>
					</div>
					<div class="choose">
						<span class="p-price"><strong>￥39.00</strong> </span>
					</div>
				</li>
				<li>
					<div class="p-img">
						<a target="_blank" href="#"><img width="100" height="100"
							src="../images/sample100.jpg"> </a>
					</div>
					<div class="p-name">
						<a target="_blank" href="#">现代（HYUNDAI）HY-N11 2.4G无线鼠标</a>
					</div>
					<div class="choose">
						<span class="p-price"><strong>￥39.00</strong> </span>
					</div>
				</li>
				<li>
					<div class="p-img">
						<a target="_blank" href="#"><img width="100" height="100"
							src="../images/sample100.jpg"> </a>
					</div>
					<div class="p-name">
						<a target="_blank" href="#">现代（HYUNDAI）HY-N11 2.4G无线鼠标</a>
					</div>
					<div class="choose">
						<span class="p-price"><strong>￥39.00</strong> </span>
					</div>
				</li>
				<li>
					<div class="p-img">
						<a target="_blank" href="#"><img width="100" height="100"
							src="../images/sample100.jpg"> </a>
					</div>
					<div class="p-name">
						<a target="_blank" href="#">现代（HYUNDAI）HY-N11 2.4G无线鼠标</a>
					</div>
					<div class="choose">
						<span class="p-price"><strong>￥39.00</strong> </span>
					</div>
				</li>
			</ul>
		</div>

		<div class="clear"></div>
	</div>
	<!--group end-->
</div>

<div class="m10">
	<img src="../images/usadd.jpg" width="984" height="118" />
</div>
<script type="text/javascript">
<!--
	$(document).ready(function() {
		myOrder.bindMyOrderContentPage();
	});
//-->
</script>