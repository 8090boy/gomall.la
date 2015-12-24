<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
<script
	src=" <ls:templateResource item='/common/js/jquery.validate.js'/>"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/common/red/css/errorform.css" />
<script type="text/javascript" language="javascript"
	src="<ls:templateResource item='/common/js/randomimage.js'/>"></script>
<script type="text/javascript">
		 		//used by reg.js
				var contextPath = '${pageContext.request.contextPath}';
		</script>
<script type="text/javascript" language="javascript"
	src="<ls:templateResource item='/common/red/js/reg.js'/>"></script>
<!--nav-->
<div class="nav">
	<div class="wrap">
		<p class="dengl">
			<span class="yongh">注册新用户</span> <span class="zhuce">我已经注册，现在就
				<a href="<ls:url address='/login'/>" style="color: #FFF"><b>登录</b></a>
			</span>
		</p>
	</div>
</div>
<!--nav end-->
<c:forEach items="${requestScope.USER_REG_ADV_950}" var="adv">
	<div class="w" style="margin: 0 auto; margin-top: 10px">
		<a href="${adv.linkUrl}"><img
			src="<ls:photo item='${adv.picUrl}'/>" title="${adv.title}"
			width="1220px" /></a>
	</div>
</c:forEach>
<div class="w">
	<div class="login_left wrap">
		<div class="news_wrap">
			<div class="news_bor">
				<div class="loginwrap">
					<div class="leftr">
						<div class="lgtit">

							<div class="pagetablg">
								<ul>
									<c:choose>
										<c:when
											test="${supportOpenShop == 'true' && 'C2C' == applicationScope.BUSINESS_MODE}">
											<c:choose>
												<c:when test="${param.openshop == 1 }">
													<li id="personal">个人用户</li>
													<li id="business" class="onlg">企业用户</li>
												</c:when>
												<c:otherwise>
													<li id="personal" class="onlg">个人用户</li>
													<li id="business">企业用户</li>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<li id="personal" class="onlg">个人用户</li>
										</c:otherwise>
									</c:choose>


								</ul>
							</div>

						</div>

						<form action="${pageContext.request.contextPath}/userReg"
							method="post" name="userRegForm" id="userRegForm"
							enctype="multipart/form-data">
							<div class="formtable" style="text-align: center;">

								<table id="personalTable" style="margin: 0 auto;" width="700px">
									<tbody>
										<th width="150"><strong>帐户信息：</strong></th>
										<td>&nbsp;</td>
										<td class="hint">&nbsp;</td>
										</tr>
										<tr>
											<th width="150">用户名：</th>
											<td><input id="name" name="name" type="text" value=""
												class="inputstyle" maxlength="15" /></td>
											<td class="hint"></td>
										</tr>
										<tr>
											<th>设置密码：</th>
											<td><input id="password" name="password" type="password"
												class="inputstyle" /></td>
											<td class="hint">&nbsp;</td>
										</tr>
										<tr>
											<th>确认密码：</th>
											<td><input id="password2" name="password2"
												type="password" class="inputstyle" /></td>
											<td class="hint">&nbsp;</td>
										</tr>
										<tr>
											<th>邮箱：</th>
											<td><input id="userMail" name="userMail" type="text"
												class="inputstyle" /></td>
											<td class="hint">&nbsp;</td>
										</tr>

									</tbody>
								</table>

								<!--企业用户-->
								<c:if
									test="${supportOpenShop == 'true' && 'C2C' == applicationScope.BUSINESS_MODE}">
									<table id="businessTable"
										style="margin: 0 auto; display: none;" width="700px">
										<tbody>
											<tr>
												<th width="150"><strong>商城信息：</strong></th>
												<td>&nbsp;<input type="hidden" id="openShop"
													name="openShop" /></td>
												<td class="hint">&nbsp;</td>
											</tr>
											<tr>
												<th>商城：</th>
												<td><input id="shopDetail.siteName"
													name="shopDetail.siteName" type="text" class="inputstyle" /></td>
												<td class="hint">&nbsp;</td>
											</tr>
											<tr>
												<th>身份证号码：</th>
												<td><input id="shopDetail.idCardNum"
													name="shopDetail.idCardNum" type="text" class="inputstyle"
													maxlength="18" /></td>
												<td class="hint">&nbsp;</td>
											</tr>
											<tr>
												<th>地址：</th>
												<td><input id="shopDetail.postAddr"
													name="shopDetail.postAddr" type="text" class="inputstyle" /></td>
												<td class="hint">&nbsp;</td>
											</tr>
											<tr>
												<th>商城类型：</th>
												<td><fmt:message key="type.business" /> <input
													type="radio" checked="checked" name="shopDetail.type"
													id="type" value="1" onclick="javascript:changeType(1)" />&nbsp;
													<fmt:message key="type.individual" /> <input type="radio"
													name="shopDetail.type" value="0"
													onclick="javascript:changeType(0)" /></td>
												<td class="hint">&nbsp;</td>
											</tr>
											<tr>
												<th>上传身份证照片：</th>
												<td><input id="shopDetail.idCardPicFile"
													name="idCardPicFile" type="file" class="inputstyle" /></td>
												<td class="hint">&nbsp;</td>
											</tr>
											<tr id="trafficPicDiv">
												<th>上传营业执照照片：</th>
												<td><input id="shopDetail.trafficPicFile"
													name="trafficPicFile" type="file" class="inputstyle" /></td>
												<td class="hint">&nbsp;</td>
											</tr>
										</tbody>
									</table>
								</c:if>
								<table style="margin: 0 auto;" width="700px">
									<tbody>
										<tr>
											<th width="150">验证码：</th>
											<td><input type="text" id="randNum" name="randNum"
												class="inputstyle" maxlength="4"
												style="font-size: 11pt; width: 50px; height: 22px"
												tabindex="3"> <img id="randImage" name="randImage"
												src="<ls:templateResource item='/captcha.svl'/>"
												style="vertical-align: middle;" /> &nbsp;<a
												href="javascript:void(0)"
												onclick="javascript:changeRandImg('${pageContext.request.contextPath}')"
												style="font-weight: bold;"><fmt:message
														key="change.random2" /></a></td>
											<td class="hint">&nbsp;</td>
										</tr>
									</tbody>
								</table>

								<table style="margin: 0 auto;">
									<tbody>
										<tr>
											<th height="73">&nbsp;</th>
											<td><input name="submit" type="image"
												src="<ls:templateResource item='/common/red/images/btntong_10.gif'/>"
												width="173" height="45" /></td>
										</tr>
										<tr>
											<th colspan="3"><textarea id="regItem" name="regItem"
													class="lgtext" style="width: 600px; height: 100px">${regItem }</textarea>
											</th>
										</tr>
									</tbody>

								</table>

							</div>
						</form>
					</div>

					<div class="clear"></div>
				</div>
			</div>
		</div>
	</div>
	<!----左边end---->

	<div class="clear"></div>
</div>
<!----red两栏end---->
<script language="javascript">
   jQuery.validator.setDefaults({

	});
	
  	jQuery.validator.addMethod("stringCheck", function(value, element) {
     return value.isAlpha();}, '<fmt:message key="user.reg.username"/>'); 
	
   jQuery.validator.addMethod("checkName1", function(value, element) {
     return checkName(value);}, '<fmt:message key="errors.user.exists"><fmt:param value=""/></fmt:message>'); 
     
   jQuery.validator.addMethod("checkEmail1", function(value, element) {
     return checkEmail(value);}, '<fmt:message key="errors.email.exists"><fmt:param value=""/></fmt:message>'); 
     
jQuery(document).ready(function() {
	jQuery("#userRegForm").validate({
		rules: {
			name: {
				required: true,
				minlength: 4,
				stringCheck: true,
				checkName1: true
			},
			password: {
				required: true,
				minlength: 6
			},
			password2: {
				required: true,
				minlength: 6,
				equalTo: "#password"
			},
			userMail: {
				required: true,
				email: true,
				checkEmail1: true
			},
			"shopDetail.siteName": {
				required: "#openShop:checked",
				minlength: 2
			},
            "shopDetail.postAddr": {
				required: "#openShop:checked",
				minlength: 2
			},
		    "shopDetail.idCardNum": {
				required: "#openShop:checked",
				minlength: 15
			},
		    "shopDetail.type": {
				required: "#openShop:checked"
			},
		    "shopDetail.idCardPicFile": {
				required: "#openShop:checked"
			},			
		    "shopDetail.trafficPicFile": {
				required: "#openShop:checked",
				required: "#type:checked"
			}		
			
		},
		messages: {
            name: {
                required: '<fmt:message key="username.required"/>',
                minlength: '<fmt:message key="username.minlength"/>'
            },
            password: {
                required: '<fmt:message key="password.required"/>',
                minlength: '<fmt:message key="password.minlength"/>'
            },
            password2: {
                required: '<fmt:message key="password.required"/>',
                minlength: '<fmt:message key="password.minlength"/>',
                equalTo: '<fmt:message key="password.equalTo"/>'
            },
            userMail:{
               required: '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>',
               email: '<fmt:message key="errors.email"><fmt:param value=""/></fmt:message>'
            },     
            "shopDetail.siteName": {
               required: '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>',
               minlength: '<fmt:message key="errors.minlength"><fmt:param value=""/><fmt:param value="2"/></fmt:message>'
            },   
            "shopDetail.postAddr":{
               required: '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>',
               minlength: '<fmt:message key="errors.minlength"><fmt:param value=""/><fmt:param value="2"/></fmt:message>'
            }, 
            "shopDetail.idCardNum": {
               required: '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>',
               minlength: '<fmt:message key="errors.minlength"><fmt:param value=""/><fmt:param value="15"/></fmt:message>'
            }, 
            "shopDetail.type": {
               required: '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>'
            },             
 		    "shopDetail.idCardPicFile":  {
				required: '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>'
			},		           
 		    "shopDetail.trafficPicFile": {
				required: '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>'
			}
        }
	});
	
	// check if confirm password is still valid after password changed
	jQuery("#password").blur(function() {
		jQuery("#password2").valid();
	});
	
	jQuery("#name").focus();
	
	changeregTab();
	showbusiness();
});

</script>
