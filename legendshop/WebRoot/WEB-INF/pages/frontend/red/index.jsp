<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/common/default/js/funcs.js"></script>
<!----advtisement---->
<div class="w" style="margin-top: 7px; height: 488px;">
	<div class="indexgg">
		<div class="ggtit">
			<div class="ggmore">
				<a href="#"><img src="../images/gg_more.jpg" width="56"
					height="39" /></a>
			</div>
			<h3>商城公告</h3>
		</div>
		<ul class="lglist">
			<c:forEach items="${requestScope.pubList}" var="pub">
				<li><a href="${pub.msg}">• ${pub.title}</a></li>
			</c:forEach>
		</ul>
	</div>

	<div class="flashNews indexflash" style="width: 650px; height: 323px">
		<div id="Switch_1">
			<a id="imglink1" target="_blank"><img id="img1" width="650px"
				height="323px" onmouseover="pauseSwitch();"
				onmouseout="goonSwitch();" /></a>
		</div>
		<div id="Switch_2" style="display: none;">
			<a id="imglink2" target="_blank"><img id="img2" width="650px"
				height="323px" onmouseover="pauseSwitch();"
				onmouseout="goonSwitch();" /></a>
		</div>
		<div id="Switch_3" style="display: none;">
			<a id="imglink3" target="_blank"><img id="img3" width="650px"
				height="323px" onmouseover="pauseSwitch();"
				onmouseout="goonSwitch();" /></a>
		</div>
		<div id="Switch_4" style="display: none;">
			<a id="imglink4" target="_blank"><img id="img4" width="650px"
				height="323px" onmouseover="pauseSwitch();"
				onmouseout="goonSwitch();" /></a>
		</div>
		<div id="Switch_5" style="display: none;">
			<a id="imglink5" target="_blank"><img id="img5" width="650px"
				height="323px" onmouseover="pauseSwitch();"
				onmouseout="goonSwitch();" /></a>
		</div>
		<div id="Switch_6" style="display: none;">
			<a id="imglink6" target="_blank"><img id="img6" width="650px"
				height="323px" onmouseover="pauseSwitch();"
				onmouseout="goonSwitch();" /></a>
		</div>
		<div id="Switch_7" style="display: none;">
			<a id="imglink7" target="_blank"><img id="img7" width="650px"
				height="323px" onmouseover="pauseSwitch();"
				onmouseout="goonSwitch();" /></a>
		</div>
		<div id="Switch_8" style="display: none;">
			<a id="imglink8" target="_blank"><img id="img8" width="650px"
				height="323px" onmouseover="pauseSwitch();"
				onmouseout="goonSwitch();" /></a>
		</div>
		<div id="SwitchTitle" onmouseover="pauseSwitch();"
			onmouseout="goonSwitch();"></div>
		<ul id="SwitchNav" style="margin: 0px"></ul>
		<div class="bg" onmouseover="pauseSwitch();"
			onmouseout="goonSwitch();"></div>
	</div>
	<div class="indexhot">
		<!-- 最新产品 -->
		<c:forEach items="${requestScope.newestProdList}" var="prod"
			varStatus="status" end="5">
			<a href="${pageContext.request.contextPath}/views/${prod.prodId}"
				target="_blank"> <img
				src="<ls:images item="${prod.pic}" scale='2'/>" alt="${prod.name}"
				width="150px" height="150px" />
			</a>
		</c:forEach>
	</div>
	<div class="clear"></div>
</div>
<!-- 每个tag，一个用户有多个 -->
<c:forEach items="${requestScope.tagList}" var="tag" varStatus="status">
	<!----advtisementend---->
	<c:forEach items="${tag.advertisementList}" var="adv">
		<div class="w banner1">
			<a href="${adv.linkUrl}"><img
				src="<ls:photo item='${adv.picUrl}'/>" title="${adv.title}"
				width="100%" /></a>
		</div>
	</c:forEach>

	<!----blue两栏---->
	<div class="w">
		<!----右边---->
		<div class="index_right">
			<div class="side2">
				<h3>
					<span><a href="#">更多团购 &gt;</a></span>今日团购
				</h3>
				<div class="sm_pic">
					<img src="../images/promins.jpg" width="85" height="85" />
				</div>
				<p class="it_info">
					<a href="#">仅38元，欢享价值300元iMovie影城套餐，24小时可选，1张券可供2-3人看，在1间情侣包厢浪漫观影，第84届奥斯卡佳片通看。超</a><br />
					<span class="graytxt" style="text-decoration: line-through;">原价：300.00</span>
				</p>
				<div class="itprice">
					<a href="#">团购价：<span>￥80</span></a>
				</div>

			</div>

			<div style="margin-top: 12px;">
				<img src="../images/radv1.jpg" />
			</div>
			<div style="margin-top: 10px;">
				<img src="../images/radv2.jpg" />
			</div>


		</div>
		<!----右边end---->

		<!----左边---->
		<div class="index_left">
			<div class="indexlp blueback">
				<h3>${tag.name}</h3>
				<ul>
					<li class="focus"><a href="#">精品推荐</a></li>
					<!-- 二级分类 -->
					<c:forEach items="${tag.sort.nsort}" var="nsort" varStatus="status"
						begin="0" end="4">
						<li><a href="#">${nsort.nsortName}</a></li>
					</c:forEach>

				</ul>
			</div>
			<div class="indexlpdiv">
				<div class="lpleft smback">
					<ul>
						<!-- 三级分类 -->
						<c:forEach items="${tag.sort.nsort}" var="nsort" begin="0" end="0">
							<c:forEach items="${nsort.subSort}" var="subSort" begin="0"
								end="11">
								<li><a href="#">• ${subSort.nsortName}</a></li>
							</c:forEach>
						</c:forEach>
					</ul>
				</div>
				<!-- 精品推荐 -->
				<div class="lptable">
					<table>
						<tr>
							<c:forEach items="${tag.commendProdList}" var="prod"
								varStatus="status">
								<td>
									<div class="ppicdiv">
										<a
											href="${pageContext.request.contextPath}/views/${prod.prodId}">
											<img src="<ls:images item='${prod.pic}' scale='1'/>"
											width="150px" height="150px" title="${prod.name}" />
										</a>
									</div>
									<p>${prod.name}
										<c:if test="${not empty prod.cash}">
											<br />
											<fmt:message key="prod_cash" />
											<span><fmt:formatNumber type="currency"
													value="${prod.cash}" pattern="${CURRENCY_PATTERN}" /></span>
										</c:if>
									</p>
								</td>
								<c:if test="${(status.index+1)%4==0&&(status.index+1)>=4}">
						</tr>
						<tr>
							</c:if>
							</c:forEach>
						</tr>
					</table>
				</div>
				<div class="clear"></div>
			</div>

		</div>
		<!----左边end---->

		<div class="clear"></div>
	</div>
	<!----blue两栏end---->



</c:forEach>

<!----advtisementend---->
<c:forEach items="${requestScope.INDEX_ADV_1}" var="adv">
	<div class="w banner1">
		<a href="${adv.linkUrl}"><img
			src="<ls:photo item='${adv.picUrl}'/>" title="${adv.title}"
			width="100%" /></a>
	</div>
</c:forEach>

<!----blue两栏---->
<div class="w">
	<!----右边---->
	<div class="index_right">
		<div class="side2">
			<h3>
				<span><a href="#">更多团购 &gt;</a></span>今日团购
			</h3>
			<div class="sm_pic">
				<img src="../images/promins.jpg" width="85" height="85" />
			</div>
			<p class="it_info">
				<a href="#">仅38元，欢享价值300元iMovie影城套餐，24小时可选，1张券可供2-3人看，在1间情侣包厢浪漫观影，第84届奥斯卡佳片通看。超</a><br />
				<span class="graytxt" style="text-decoration: line-through;">原价：300.00</span>
			</p>
			<div class="itprice">
				<a href="#">团购价：<span>￥80</span></a>
			</div>

		</div>

		<div style="margin-top: 12px;">
			<img src="../images/radv1.jpg" />
		</div>
		<div style="margin-top: 10px;">
			<img src="../images/radv2.jpg" />
		</div>


	</div>
	<!----右边end---->

	<!----左边---->
	<div class="index_left">
		<div class="indexlp blueback">
			<h3>电脑数码1</h3>
			<ul>
				<li class="focus"><a href="#">特价商品</a></li>
				<li><a href="#">笔记本</a></li>
				<li><a href="#">影音数码</a></li>
				<li><a href="#">DIY攒机</a></li>
				<li><a href="#">办公打印</a></li>
			</ul>
		</div>
		<div class="indexlpdiv">
			<div class="lpleft smback">
				<ul>
					<li><a href="#">• 台式机</a></li>
					<li><a href="#">• 数码相机</a></li>
					<li><a href="#">• 笔记本</a></li>
					<li><a href="#">• 单反相机</a></li>
					<li><a href="#">• 平板电脑</a></li>
					<li><a href="#">• MP3/MP4</a></li>
					<li><a href="#">• 台式机</a></li>
					<li><a href="#">• 数码相机</a></li>
					<li><a href="#">• 笔记本</a></li>
					<li><a href="#">• 单反相机</a></li>
					<li><a href="#">• 平板电脑</a></li>
					<li><a href="#">• MP3/MP4</a></li>
				</ul>
			</div>
			<div class="lptable">
				<table>
					<tr>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sm1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sm2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sm2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sm1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sm2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sm1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sm1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sm2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
					</tr>
				</table>
			</div>
			<div class="clear"></div>
		</div>




	</div>
	<!----左边end---->

	<div class="clear"></div>
</div>
<!----blue两栏end---->


<!----advtisementend---->

<c:forEach items="${requestScope.INDEX_ADV_2}" var="adv">
	<div class="w banner1">
		<a href="${adv.linkUrl}"><img
			src="<ls:photo item='${adv.picUrl}'/>" title="${adv.title}"
			width="100%" /></a>
	</div>
</c:forEach>

<!----red两栏---->
<div class="w">
	<!----右边---->
	<div class="index_right">
		<div class="side2">
			<h3>
				<span><a href="<ls:url address='/allbrands'/>">更多品牌 &gt;</a></span>品牌旗舰店
			</h3>
			<div class="banddiv">
				<table border="1">
					<tr>
						<td><a href="#"><img src="../images/band1.jpg" /></a></td>
						<td><a href="#"><img src="../images/band2.jpg" /></a></td>
						<td><a href="#"><img src="../images/band3.jpg" /></a></td>
					</tr>
					<tr>
						<td><a href="#"><img src="../images/band4.jpg" /></a></td>
						<td><a href="#"><img src="../images/band5.jpg" /></a></td>
						<td><a href="#"><img src="../images/band6.jpg" /></a></td>
					</tr>
					<tr>
						<td><a href="#"><img src="../images/band7.jpg" /></a></td>
						<td><a href="#"><img src="../images/band8.jpg" /></a></td>
						<td><a href="#"><img src="../images/band9.jpg" /></a></td>
					</tr>
				</table>
			</div>
		</div>

		<div class="side2" style="margin-top: 10px;">
			<h3>特价促销</h3>
			<ul class="lista font12 blue_a listimg1"
				style="margin-left: 10px; height: 212px;">
				<li><a href="#">品牌女包季末清仓69元起</a></li>
				<li><a href="#">我比美食更有诱惑力！---韩国明进工坊</a></li>
				<li><a href="#">樱春季 忙护理 资生堂全场满196赠！</a></li>
				<li><a href="#">IDF买就送精美丝巾</a></li>
				<li><a href="#">三月迎春，最爱就“饰”你！</a></li>
				<li><a href="#">三月迎春，最爱就“饰”你！</a></li>
				<li><a href="#">三月迎春，最爱就“饰”你！</a></li>
				<li><a href="#">三月迎春，最爱就“饰”你！</a></li>

			</ul>
		</div>
	</div>
	<!----右边end---->

	<!----左边---->
	<div class="index_left">
		<div class="indexlp redback">
			<h3>电脑数码2</h3>
			<ul>
				<li class="focus"><a href="#">特价商品</a></li>
				<li><a href="#">笔记本</a></li>
				<li><a href="#">影音数码</a></li>
				<li><a href="#">DIY攒机</a></li>
				<li><a href="#">办公打印</a></li>
			</ul>
		</div>
		<div class="indexlpdiv">
			<div class="lpleft ssback">
				<ul>
					<li><a href="#">• 女装</a></li>
					<li><a href="#">• 女鞋</a></li>
					<li><a href="#">• 面部护理</a></li>
					<li><a href="#">• 身体护理</a></li>
					<li><a href="#">• 魅力彩妆</a></li>
					<li><a href="#">• 香水</a></li>
					<li><a href="#">• 女装</a></li>
					<li><a href="#">• 女鞋</a></li>
					<li><a href="#">• 面部护理</a></li>
					<li><a href="#">• 身体护理</a></li>
					<li><a href="#">• 魅力彩妆</a></li>
					<li><a href="#">• 香水</a></li>
				</ul>
			</div>
			<div class="lptable">
				<table>
					<tr>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/ss1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/ss2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/ss2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/ss1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/ss2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/ss1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/ss1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/ss2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
					</tr>
				</table>
			</div>
			<div class="clear"></div>
		</div>




	</div>
	<!----左边end---->

	<div class="clear"></div>
</div>
<!----red两栏end---->



<!----advtisementend---->

<c:forEach items="${requestScope.INDEX_ADV_3}" var="adv">
	<div class="w banner1">
		<a href="${adv.linkUrl}"><img
			src="<ls:photo item='${adv.picUrl}'/>" title="${adv.title}"
			width="100%" /></a>
	</div>
</c:forEach>


<!----green两栏---->
<div class="w">
	<!----右边---->
	<div class="index_right">
		<div class="side2">
			<h3>
				<span><a href="<ls:url address='/allbrands'/>">更多品牌 &gt;</a></span>品牌旗舰店
			</h3>
			<div class="banddiv">
				<table height="127" border="1">
					<tr>
						<td><a href="#"><img src="../images/band1.jpg" /></a></td>
						<td><a href="#"><img src="../images/band2.jpg" /></a></td>
						<td><a href="#"><img src="../images/band3.jpg" /></a></td>
					</tr>
					<tr>
						<td><a href="#"><img src="../images/band4.jpg" /></a></td>
						<td><a href="#"><img src="../images/band5.jpg" /></a></td>
						<td><a href="#"><img src="../images/band6.jpg" /></a></td>
					</tr>
					<tr>
						<td><a href="#"><img src="../images/band7.jpg" /></a></td>
						<td><a href="#"><img src="../images/band8.jpg" /></a></td>
						<td><a href="#"><img src="../images/band9.jpg" /></a></td>
					</tr>
				</table>
			</div>
		</div>

		<div style="margin-top: 15px;">
			<img src="../images/radv1.jpg" />
		</div>
		<div style="margin-top: 14px;">
			<img src="../images/radv2.jpg" />
		</div>
	</div>
	<!----右边end---->

	<!----左边---->
	<div class="index_left">
		<div class="indexlp greenback">
			<h3>电脑数码3</h3>
			<ul>
				<li class="focus"><a href="#">特价商品</a></li>
				<li><a href="#">笔记本</a></li>
				<li><a href="#">影音数码</a></li>
				<li><a href="#">DIY攒机</a></li>
				<li><a href="#">办公打印</a></li>
			</ul>
		</div>
		<div class="indexlpdiv">
			<div class="lpleft spback">
				<ul>
					<li><a href="#">• 进口食品</a></li>
					<li><a href="#">• 休闲食品</a></li>
					<li><a href="#">• 面部护理</a></li>
					<li><a href="#">• 身体护理</a></li>
					<li><a href="#">• 魅力彩妆</a></li>
					<li><a href="#">• 香水</a></li>
					<li><a href="#">• 美体养颜</a></li>
					<li><a href="#">• 进口食品</a></li>
					<li><a href="#">• 休闲食品</a></li>
					<li><a href="#">• 面部护理</a></li>
					<li><a href="#">• 身体护理</a></li>
					<li><a href="#">• 魅力彩妆</a></li>
					<li><a href="#">• 香水</a></li>
					<li><a href="#">• 美体养颜</a></li>
				</ul>
			</div>
			<div class="lptable">
				<table>
					<tr>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sp1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sp2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sp2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sp1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sp2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sp1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sp1.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
						<td>
							<div class="ppicdiv">
								<a href="#"><img src="../images/sp2.jpg" /></a>
							</div>
							<p>
								G4 i5 14寸笔记本<br />商城价：<span>￥3629.00</span>
							</p>
						</td>
					</tr>
				</table>
			</div>
			<div class="clear"></div>
		</div>




	</div>
	<!----左边end---->

	<div class="clear"></div>
</div>
<!----green两栏end---->




<!----down两栏---->
<div class="w">
	<!----右边---->
	<div class="index_right">
		<div class="side2">
			<h3>时尚资讯</h3>
			<ul class="lista font12 blue_a listimg1" style="margin-left: 10px;">
				<li><a href="#">品牌女包季末清仓69元起</a></li>
				<li><a href="#">我比美食更有诱惑力！---韩国明进工坊</a></li>
				<li><a href="#">樱春季 忙护理 资生堂全场满196赠！</a></li>
				<li><a href="#">IDF买就送精美丝巾</a></li>
				<li><a href="#">三月迎春，最爱就“饰”你！</a></li>
				<li><a href="#">三月迎春，最爱就“饰”你！</a></li>
				<li><a href="#">三月迎春，最爱就“饰”你！</a></li>
			</ul>
		</div>
	</div>
	<!----右边end---->

	<!----左边---->
	<div class="index_left">
		<div class="hotevent">
			<div class="eventbox">
				<h3>热门晒单</h3>
				<div class="eventlist">
					<ul>
						<li style="height: 60px; display: list-item;">
							<div class="p-img">
								<a href="#"><img width="50" height="50"
									src="http://img10.360buyimg.com/N5/3015/b0724ba8-7f85-4f3a-bb61-3c2bc7a8e341.jpg"><b
									class="ci cix1"></b></a>
							</div>
							<div class="p-name">
								<a href="#">质量可靠，系统稳定，值得拥有</a>
							</div>
							<div class="p-detail">
								<a href="#">dell+百度确实是个好的组，便于上手。大屏耗电是软肋，节约情况下2至3天待机应该无悬念，具体见我的评价。</a>
							</div>
						</li>
						<li style="height: 60px; display: list-item;">
							<div class="p-img">
								<a href="#"><img width="50" height="50"
									src="http://img10.360buyimg.com/N5/3015/b0724ba8-7f85-4f3a-bb61-3c2bc7a8e341.jpg"><b
									class="ci cix1"></b></a>
							</div>
							<div class="p-name">
								<a href="#">质量可靠，系统稳定，值得拥有</a>
							</div>
							<div class="p-detail">
								<a href="#">dell+百度确实是个好的组，便于上手。大屏耗电是软肋，节约情况下2至3天待机应该无悬念，具体见我的评价。</a>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="eventbox">
				<h3>热门活动</h3>
				<div class="eventlist">
					<ul style="border: 0;">
						<li style="height: 60px; display: list-item;">
							<div class="p-img">
								<a href="#"><img width="50" height="50"
									src="http://img10.360buyimg.com/N5/3015/b0724ba8-7f85-4f3a-bb61-3c2bc7a8e341.jpg"><b
									class="ci cix2"></b></a>
							</div>
							<div class="p-name">
								<a href="#">质量可靠，系统稳定，值得拥有</a>
							</div>
							<div class="p-detail">
								<a href="#">dell+百度确实是个好的组，便于上手。大屏耗电是软肋，节约情况下2至3天待机应该无悬念，具体见我的评价。</a>
							</div>
						</li>
						<li style="height: 60px; display: list-item;">
							<div class="p-img">
								<a href="#"><img width="50" height="50"
									src="http://img10.360buyimg.com/N5/3015/b0724ba8-7f85-4f3a-bb61-3c2bc7a8e341.jpg"><b
									class="ci cix2"></b></a>
							</div>
							<div class="p-name">
								<a href="#">质量可靠，系统稳定，值得拥有</a>
							</div>
							<div class="p-detail">
								<a href="#">dell+百度确实是个好的组，便于上手。大屏耗电是软肋，节约情况下2至3天待机应该无悬念，具体见我的评价。</a>
							</div>
						</li>
					</ul>
				</div>
			</div>

			<div class="clear"></div>
		</div>

	</div>
	<!----左边end---->
	<div class="clear"></div>
</div>
<!----down两栏end---->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/common/default/js/couplet.js"></script>
<script type="text/javascript">

	var showad = '${not empty COUPLET}' == 'true';

	var Toppx = 128;         
	var AdDivW = 110;       
	var AdDivH = 300;       
	var PageWidth = 980;   
	var MinScreenW = 1024;  

    window.onscroll=scallWin;
    window.onresize=scallWin;
    window.onload=scallWin;
    
    if(showad){
	var ClosebuttonHtml = '<div align="right" style="position: absolute;top:0px;right:0px;margin:2px;padding:0px;z-index:2000;"><a href="javascript:void(0)" onclick="hidead()" style="color:black;text-decoration:none;font-size:12px;"><img src="${pageContext.request.contextPath}/common/default/images/close.gif"/></a></div>'
	var AdContentHtml = '<div align="center" style="color:green;font-size:23pt;font-family:黑体;"><a href="${COUPLET.linkUrl}" target="_blank"><img src="<ls:photo item='${COUPLET.picUrl}'/>" width="110px" border="0"></a></div>';
	document.write('<div id="coupletLeftDiv"  style="position: absolute;border: 1px solid #CCCCCC; background-color:#EEEEE2;z-index:1000;width:'+AdDivW+'px;top:-1000px;word-break:break-all;display:none;">'+ClosebuttonHtml+'<div>'+AdContentHtml+'</div></div>');
	document.write('<div id="coupletRightDiv" style="position: absolute;border: 1px solid #CCCCCC; background-color:#EEEEE2;z-index:1000;width:'+AdDivW+'px;top:-1000px;word-break:break-all;display:none;">'+ClosebuttonHtml+'<div>'+AdContentHtml+'</div></div>');
	}

        //indexJpg
    var Switcher = new Array();
    var MaxScreen = '${MaxScreen}' ;
    var dataArray = jQuery.parseJSON( '${indexJSON}');
   // var dataArray = eval('${indexJSON}');
    if(dataArray!="undefined" || dataArray != null){
        for(var i in dataArray){
                     MaxScreen = dataArray.length;
                     var ii = parseInt(i);
                     var data = dataArray[i] ;
                     Switcher[ii+1] = Array();
                     Switcher[ii+1]['title'] = data.title ;
                     Switcher[ii+1]['stitle'] = data.stitle ;
                     Switcher[ii+1]['link'] = data.titleLink ;
                     var imgorder = "img"+(ii+1);
                     var imgs = document.getElementById(imgorder);
                     imgs.src = '${pageContext.request.contextPath}/photoserver/photo/' + data.img;
                     imgs.alt = data.alt;
                     var linkorder = "imglink"+(ii+1); 
                     var links = document.getElementById(linkorder);
                     links.href = data.link;
            }
        }
        if(MaxScreen == 0){
                     Switcher[1] = Array();
                     Switcher[1]['title'] = 'LegendShop - 微商(微型商城)平台';
                     Switcher[1]['stitle'] = '基于J2EE的MVC架构的高性能、独立可配置的多用户商城系统' ;
                     Switcher[1]['link'] = '${DOMAIN_NAME}' ;
                     var imgorder = "img1";
                     var imgs = document.getElementById(imgorder);
                     imgs.src = '${pageContext.request.contextPath}/common/default/images/common.jpg';
                     imgs.alt = '基于J2EE的MVC架构的高性能、独立可配置的多用户商城系统';
                
                     var linkorder = "imglink1";
                     var links = document.getElementById(linkorder);
                     links.href = '${DOMAIN_NAME}';
                     MaxScreen = 1;
                    
           }
            switchPic(1);
            refreshSwitchTimer = setTimeout('reSwitchPic()', 6000);
  
</script>
