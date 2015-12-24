<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<%@include file='/WEB-INF/pages/common/common.jsp'%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" language="javascript"
	src="<ls:templateResource item='/common/js/randomimage.js'/>"></script>
<script type="text/javascript"
	src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js"
	data-appid="100342945"
	data-redirecturi="http://www.legendshop.cn/qc_callback.html"
	charset="utf-8"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="<ls:templateResource item='/common/red/css/errorform.css'/>" />
<!--顶部menu end-->
<!--nav-->
<div class="nav">
	<div class="wrap">
		<p class="dengl">
			<span class="yongh">用户登录</span> <span class="zhuce">还没有注册，现在就
				<a href="<ls:url address='/reg'/>" style="color: #FFF"><b>注册</b></a>
			</span>
		</p>
	</div>
</div>
<!--nav end-->

<div class="w">

	<div class="login_left wrap">
		<div class="news_wrap">

			<div class="news_bor">
				<div class="loginwrap">
					<div class="left">
						<div class="lgtit">用户登录</div>
						<div class="formtable">
							<form name="loginform"
								action="${pageContext.request.contextPath}/p/j_spring_security_check"
								method="POST" onsubmit="return checkRandNum();">
								<input type="hidden" id="rand" name="rand" /> <input
									type="hidden" id="cannonull" name="cannonull"
									value='<fmt:message key="randomimage.errors.required"/>' /> <input
									type="hidden" id="charactors4" name="charactors4"
									value='<ls:i18n key="randomimage.charactors.required" length="4"/>' />
								<input type="hidden" id="errorImage" name="errorImage"
									value='<fmt:message key="error.image.validation"/>' /> <input
									type="hidden" id="returnUrl" name="returnUrl"
									value="${param.returnUrl}" />
								<!-- 1:用户名密码错误， 2：用户已经登录 -->
								<table>
									<tbody>
										<c:choose>
											<c:when test="${param.error == 1}">
												<tr>
													<th>&nbsp;</th>
													<td><label class="error"><fmt:message
																key="error.password.noright" /></label></td>
												</tr>
											</c:when>
											<c:when test="${param.error == 2}">
												<tr>
													<th>&nbsp;</th>
													<td><label class="error"><fmt:message
																key="error.user.logined" /></label></td>
												</tr>
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
										<tr>
											<th width="158">用户名</th>
											<td width="462"><input id="username" type='text'
												name="j_username"
												style="width: 300px; height: 25px; font-size: 15px; line-height: 25px;"
												class="inputstyle" tabindex="1" value='<lb:lastLogingUser/>' />
											</td>
										</tr>
										<tr>
											<th>密码</th>
											<td><input id="pwd" type='password' name='j_password'
												style="width: 300px; height: 25px; font-size: 15px; line-height: 25px;"
												autocomplete="off" class="inputstyle" tabindex="2"
												onload="this.value=''" /></td>
										</tr>
										<lb:userValidationImage>
											<tr>
												<th><fmt:message key="validation.code" /></th>
												<td align="left"><input type="text" id="randNum"
													name="randNum" class="inputstyle" maxlength="4"
													style="width: 50px; height: 22px;" tabindex="3"> <img
													id="randImage" name="randImage"
													src="<ls:templateResource item='/captcha.svl'/>"
													style="vertical-align: middle;" /> &nbsp;<a
													href="javascript:void(0)"
													onclick="javascript:changeRandImg('${pageContext.request.contextPath}')"
													style="font-weight: bold;"><fmt:message
															key="change.random2" /></a></td>
											</tr>
										</lb:userValidationImage>
										<tr>
											<th>&nbsp;</th>
											<td>
												<table>
													<tr>
														<td><input name="submit" type="image"
															src="<ls:templateResource item='/common/red/images/dl_11.gif'/>"
															width="80" height="35" tabindex="4" /></td>
													</tr>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
							</form>
						</div>
						<div>
							<span id="qq_login_btn"></span>
						</div>
					</div>
					<div class="right" align="left">
						<p>
							<span>还不是会员？</span><br />
							<br /> 现在注册，便能立刻享受便宜又放心的购物乐趣<br /> 不想进去了，回到<a
								href='<ls:url address="/index"/>'><span>首页</span></a><br />
							忘记密码了？小事儿，马上<a href='<ls:url address="/resetpassword"/>'><span>找回密码</span></a>
							<br />
							<br />
							<a href='<ls:url address="/reg"/>'><img
								src="../images/dl_07.gif" width="76" height="30" /></a>
						</p>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
	</div>
	<!----左边end---->

	<div class="clear"></div>
</div>
<script type="text/javascript">	
	window.onload = function(){
	   if(window.top.location.href!=location.href)    
		{
    		window.top.location.href=location.href;    
		}

		var userName = document.getElementById("username").value;
		if(userName == null || userName =='' ){
			document.getElementById("username").focus();
		}else{
		    document.getElementById("pwd").focus();
		}
    }
	
	 //调用QC.Login方法，指定btnId参数将按钮绑定在容器节点中
	   QC.Login({
	       //btnId：插入按钮的节点id，必选
	       btnId:"qq_login_btn",	
	       //用户需要确认的scope授权项，可选，默认all
	       scope:"all",
	       //按钮尺寸，可用值[A_XL| A_L| A_M| A_S|  B_M| B_S| C_S]，可选，默认B_S
	       size: "B_M"
	   }, function(reqData, opts){//登录成功
	    userRegister4QQ(reqData);
	   }, function(opts){//注销成功
	         alert('QQ登录 注销成功');
	                     }
	);
	function userRegister4QQ(reqData){
	  if(QC.Login.check()){//如果已登录
	QC.Login.getMe(function(openId, accessToken){
		alert(["当前登录用户的", "openId为："+openId, "accessToken为："+accessToken].join("\n"));
		alert(reqData.nickname);
		//构造用户注册及登录请求
	       var userForm={"nickName":reqData.nickname,"sex":"M","userName":openId};
	       //TODO 服务端注册的及登录动作的Controller要新增一个
	       $.ajax({
				url:"${pageContext.request.contextPath}/userReg", 
				data: userForm,
				type:'post', 
				async : true, //默认为true 异步   
				error: function(jqXHR, textStatus, errorThrown) {
			 		 //console.log(textStatus, errorThrown);
				},
				success:function(result){
				 alert(result);
				}
				});
	       
	       //系统登录后，成功后跳转到用户中心
	     //  window.location.href = "${pageContext.request.contextPath}/p/usercenter";
	});
	}
	}
	

</script>
<!----red两栏end---->
