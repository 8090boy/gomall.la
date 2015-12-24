<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>

<!----advtisement---->
<!----advtisementend---->
<!----blue两栏---->
<div class="w">
	<!----右边---->
	<!----右边end---->

	<!----左边---->
	<!----左边end---->

	<div class="clear"></div>
</div>
<!----blue两栏end---->


<!----advtisementend---->
<!----red两栏---->
<div class="w">
	<!----右边---->
	<%@ include file="newsCategory.jsp"%>
	<!----右边end---->

	<!----左边---->
	<div class="index_left">
		<div class="news_wrap">

			<div class="news_bor">

				<h3 class="tit">${news.newsTitle}</h3>
				<div class="nge"></div>
				<p class="author">
					作者: ${news.userName}
					<fmt:formatDate value="${news.newsDate}" pattern="yyyy-MM-dd hh:MM" />
					来源: 速途网我要评论 (2) 访问次数 12408
				</p>
				<c:if test="${ not empty news.newsBrief}">
					<p class="abstract">
						<strong>摘要：</strong>${news.newsBrief}.......</p>
				</c:if>
				<div class="newscontent">${news.newsContent}</div>

				<div class="ding">
					<div class="dingtie">
						<a href="#">顶</a>
					</div>
					<div class="caitie">
						<a href="#">踩</a>
					</div>
					<div class="tishi">
						<p>
							<a href="#">上一篇：没有了</a>
						</p>
						<p>
							<a href="#">下一篇：天使就是聪明点儿的傻瓜</a>
						</p>
					</div>
					<div class="clear"></div>
				</div>

				<div class="conmment">
					<p>
						<span class="conmmenttit"><span class="mingzi">互相尊重客观文明发言</span>
							<span class="mingdi">[北京市网友]</span></span>： <span class="conmmentfa">2012-03-20
							16:28:41 发表 </span>
					<div class="clear"></div>
					</p>

					<p class="commentcont">坚决消灭一切黑社会！</p>
					<p class="commentding">
						<a href="#">顶[2570]</a>&nbsp; &nbsp;<a href="#">回复</a>&nbsp;
						&nbsp;<a href="#">收藏</a>&nbsp; &nbsp;<a href="#">复制</a>
					</p>
					<div class="clear"></div>
				</div>


				<div class="conmment">
					<p>
						<span class="conmmenttit"><span class="mingzi">互相尊重客观文明发言</span>
							<span class="mingdi">[北京市网友]</span></span>： <span class="conmmentfa">2012-03-20
							16:28:41 发表 </span>
					<div class="clear"></div>
					</p>

					<p class="commentcont">坚决消灭一切黑社会！</p>
					<p class="commentding">
						<a href="#">顶[2570]</a>&nbsp; &nbsp;<a href="#">回复</a>&nbsp;
						&nbsp;<a href="#">收藏</a>&nbsp; &nbsp;<a href="#">复制</a>
					</p>
					<div class="clear"></div>
				</div>


				<div class="conmmentping">
					网友评论 已有<span class="num">2</span>条评论。 <span class="gengduo"><a
						href="#">查看更多评论>></a></span>
				</div>

				<div class="pinglun">
					<dl>
						<dt>评论内容：</dt>
						<dd class="nei">
							<input />
						</dd>
						<div class="clear"></div>
						<dt>验证码：</dt>
						<dd class="yan">
							<input />
						</dd>
						<div class="clear"></div>
						<dt>&nbsp;</dt>
						<dd class="yan">
							<span class="tijiao"><a href="#"><img
									src="../images/fb_10.gif" width="96" height="31" /></a></span><span
								class="ti">【网友评论仅代表个人观点】</span>
						</dd>
						<div class="clear"></div>
					</dl>
				</div>

			</div>

			<div class="clear"></div>
		</div>

	</div>
	<!----左边end---->

	<div class="clear"></div>
</div>
<!----red两栏end---->

<!----advtisementend---->
<!----green两栏---->
<div class="w">
	<!----右边---->
	<!----右边end---->

	<!----左边---->
	<!----左边end---->

	<div class="clear"></div>
</div>
<!----green两栏end---->


<!----down两栏---->
<div class="w">
	<!----右边---->
	<!----右边end---->

	<!----左边---->
	<!----左边end---->
	<div class="clear"></div>
</div>
<!----down两栏end---->