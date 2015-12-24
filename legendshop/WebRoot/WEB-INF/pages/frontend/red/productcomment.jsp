<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<script
	src=" <ls:templateResource item='/common/js/jquery.validate.js'/>"
	type="text/javascript"></script>
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
<div class="w main">
	<div class="left">
		<div class="m" id="pinfo" style="margin-top: 10px;">
			<div class="mt">
				<h2>商品信息</h2>
			</div>
			<div class="mc">
				<div class="p-img2">
					<a href="${pageContext.request.contextPath}/views/${prod.id}"
						target="_blank"> <img alt=""
						src="<ls:images item='${prod.pic}' scale="1"/>" height="150px"
						width="150px"></a>
				</div>
				<div class="clear"></div>
				<div class="p-name">
					商品：<a href="${pageContext.request.contextPath}/views/${prod.id}"
						target="_blank">${prod.name }</a>
				</div>
				<div class="p-price">
					价格：<strong><fmt:formatNumber type="currency"
							value="${prod.cash}" pattern="${CURRENCY_PATTERN}" /></strong>
				</div>
				<div class="p-grade clearfix">
					<span class="fl">评价得分：</span>
					<div class="fl">
						<div class="star sa4"></div>
						(4分)
					</div>
				</div>
				<div class="num-comment">评论数：6452条</div>
				<div class="btn">
					<a class="btn-append"
						href="http://jd2008.360buy.com/purchase/InitCart.aspx?pid=378342&amp;pcount=1&amp;ptype=1">添加到购物车</a>
				</div>
			</div>
			<div class="AD_L" id="miaozhen8151"></div>
		</div>
		<!--pinfo end-->
	</div>
	<!--left end-->
	<div class="right-extra">
		<form action="${pageContext.request.contextPath}/productcomment/save"
			id="form1" method="post">
			<input type="hidden" value="${prod.prodId}" id="prodId" name="prodId" />
			<div class="m" id="pubcomment" style="margin-top: 10px;">
				<div class="mt">
					<h2>发表评价</h2>
				</div>

				<div class="mc form">
					<div class="prompt">
						欢迎您发表原创、与商品质量相关、对其它用户有参考价值的商品评价；<br> <font color="#cc0000">如果您发表的评价内容与商品本身的质量无关，评价将被删除；</font><br>
						您的评价通过审核后可以获得一定积分奖励，好的评价还会在首页展示并有额外的积分奖励哦！
					</div>

					<dl>
						<dt>
							<b>*</b>标题
						</dt>
						<dd>
							<input type="text" class="text input" name="title" id="title"><label
								id="titleerror">必填，长度在4-20个字之间</label>
						</dd>
					</dl>
					<dl id="dlpoint">

						<dt>
							<b>*</b>评分
						</dt>
						<dd>
							<ul class="list-h form">
								<li><input type="radio" value="5" name="score"
									checked="checked">
								<div class="star sa5"></div> (5分)</li>

								<li><input type="radio" value="4" name="score">
								<div class="star sa4"></div> (4分)</li>
								<li><input type="radio" value="3" name="score">
								<div class="star sa3"></div> (3分)</li>
								<li><input type="radio" value="2" name="score">
								<div class="star sa2"></div> (2分)</li>
								<li><input type="radio" value="1" name="score">
								<div class="star sa1"></div> (1分)</li>
							</ul>

							<span class="clr"></span> <label class="error"
								style="display: none" id="pointError"> 你还没有评分，请评分</label>
						</dd>
					</dl>
					<dl class="hide" id="quality">
						<dt>
							<b>*</b>您对哪方面不满意
						</dt>

						<dd>
							<ul class="list-h">
								<li><input type="radio" value="quality" name="Dissatisfied">商品质量
								</li>
								<li><input type="radio" value="services"
									name="Dissatisfied">服务</li>
							</ul>
							<div class="hide" id="prompt-quality">

								京东商城商品评价主要是针对商品质量进行评价，帮助其他客户了解商品信息，如果您对京东商城的服务不满意，您可以使用我们的交易评价功能，对购物流程中的各项服务进行打分。<a
									href="http://club.360buy.com/JdVote/TradeComment.aspx?ruleid=158861817"
									target="_blank">针对本次交易服务进行打分&gt;&gt;</a><br>
								我们将认真分析您的打分，并改进我们的服务，感谢您对京东商城的支持，谢谢。
							</div>
						</dd>
					</dl>
					<div id="Re_Explain">
						<dl>
							<dt>
								<b>*</b>使用心得
							</dt>

							<dd>
								<textarea class="text textarea" name="content" id="content"></textarea>
								<label id="contenterror">必填，长度在5-200个字之间</label> <label>
									填写您对此商品的使用心得，例如该商品或某功能为您带来的帮助，或使用过程中遇到的问题等。最多可输入200字。</label>
							</dd>
						</dl>
						<dl>

							<dt>
								<b>*</b>优点
							</dt>
							<dd>
								<input type="text" class="text input" name="advtge" id="advtge"><label
									id="advtgeerror">必填，长度在5-100个字之间</label>
							</dd>
						</dl>
						<dl>
							<dt>
								<b>*</b>不足
							</dt>

							<dd>
								<input type="text" value="暂时还没发现缺点哦！" class="text input"
									name="defect" id="defect"><label id="defecterror">必填，长度在5-100个字之间</label>
							</dd>
						</dl>
						<div class="model-prompt error" style="display: none"
							id="Errorpanel">您还有不符合要求的填写项，请检查。</div>
						<input type="submit" class="btn-img btn-submit" value="提交"
							id="saveComment">
					</div>
				</div>

			</div>
		</form>
		<!--pubcomment end-->
	</div>
	<!--right-extra end-->
	<div class="clear"></div>
</div>
<script language="javascript">
 		jQuery(document).ready(function() {
	jQuery("#form1").validate({
		rules: {
			title: {
				required: true,
				minlength: 4,
				maxlength: 20
			},
			content: {
				required: true,
				minlength: 5,
				maxlength: 200
			},
		advtge: {
				required: true,
				minlength: 5,
				maxlength: 200
			},
	   defect: {
				required: true,
				minlength: 5,
				maxlength: 200
			}	
			
		},
		messages: {
            title: {
                required: '<fmt:message key="username.required"/>',
                minlength: '<fmt:message key="username.minlength"/>'
            }
        },
        errorPlacement: function(error, element) {
        $("#Errorpanel").show();
        var name = element.attr("name");
        $("#" +name +"error" ).css("color","red");
	}
      	});
});
 </script>
