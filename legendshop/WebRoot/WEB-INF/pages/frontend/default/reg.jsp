<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/pages/common/common.jsp"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<script
	src="${pageContext.request.contextPath}/common/js/jquery.validate.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/common/default/css/errorform.css" />
<table width="960px" cellspacing="0" cellpadding="0" class="tables">
	<tr>
		<td class="titlebg"><fmt:message key="register.title" /></td>
	</tr>
	<tr>
		<td height="2" bgcolor="#FFFFF6"><table width="100%"
				cellspacing="0" cellpadding="0" height="100%">
				<tr>
					<td align="left">
						<form action="${pageContext.request.contextPath}/userReg"
							method="post" name="userRegForm" id="userRegForm"
							enctype="multipart/form-data">
							<div style="margin-left: 100px">

								<c:forEach items="${User_Messages.callBackList}" var="callback">
									<li><font color="red">${callback.callBackTitle}
											${callback.callBackDesc}</font></li>
								</c:forEach>
							</div>
							<br>
							<table width="100%" id="regTab">
								<tr>
									<td width="33%" height="29"><div align="right">
											<font color="#ff0000">*</font>
											<fmt:message key="Phone" />
											：
										</div></td>
									<td width="67%"><div align="left">
											<input class="input" type="text" name="name" id="name"
												maxlength="11" value="${userForm.name}" /> <input
												class="input" type="hidden" name="nickName" id="nickName"
												maxlength="20" value="${userForm.nickName}"
												readonly="readonly"> <input class="input"
												type="hidden" name="userMail" id="userMail"
												value="${userForm.userMail}" maxlength="50"> <span
												id="userAreardyExists"></span>
											<div id="checkArea">
												<H3>输入手机短信校验码</H3>
												<input class="input" type="text" name="checkNo" id="checkNo"
													maxlength="4">
											</div>
										</div></td>
								</tr>

								<tr>
									<td height="29"><div align="right">
											<font color="#ff0000">*</font> 密码 ：
										</div></td>
									<td height="29"><div align="left">
											<input class="input" type="text" name="password"
												id="password" maxlength="15"> <input class="input"
												type="hidden" name="password2" id="password2"
												readonly="readonly" maxlength="15"><span>不能少于6位！</span>
										</div></td>
								</tr>

								<c:if
									test="${supportOpenShop == 'true' && 'C2C' == applicationScope.BUSINESS_MODE}">
									<tr>
										<td><br>
											<div align="right">
												<fmt:message key="openShop" />
												：
											</div></td>
										<td><br> <input type="checkbox" id="openShop"
											name="openShop" onclick="javascript:isOpenShop(this)"
											style="margin-left: 10px" /></td>
									</tr>
								</c:if>
								<tr>
									<td colspan="2" width="100%">
										<div id="shopDetail" style="display: none">
											<table width="100%" cellpadding="0" cellspacing="0">
												<tr>
													<td colspan="2">
														<div style="margin-left: 150px">
															<fmt:message key="mall.register.required" />
															<c:if test="${validationOnOpenShop == 'true'}">
																<li><fmt:message key="mall.register.shop.required" />：</li>
															</c:if>
														</div> <br>
													</td>
												</tr>
												<tr>
													<td width="33%" height="29"><div align="right">
															<font color="#ff0000">*</font>
															<fmt:message key="mall.name" />
															：
														</div></td>
													<td width="67%" height="29"><div align="left">
															<input name="shopDetail.siteName" id="siteName"
																class="input" maxlength="50" />
														</div></td>
												</tr>
												<tr>
													<td height="29"><div align="right">
															<font color="#ff0000">*</font>
															<fmt:message key="idcard.number" />
															：
														</div></td>
													<td height="29"><div align="left">
															<input type="text" name="shopDetail.idCardNum"
																id="idCardNum" class="input" maxlength="20"
																maxlength="50" />
														</div></td>
												</tr>
												<tr>
													<td height="29"><div align="right">
															<font color="#ff0000">*</font>
															<fmt:message key="address" />
															：
														</div></td>
													<td height="29"><div align="left">
															<input style="WIDTH: 250px;" name="shopDetail.postAddr"
																id="postAddr" size="20" class="input" maxlength="300" />
														</div></td>
												</tr>
												<tr>
													<td height="29"><div align="right">
															<font color="#ff0000">*</font>
															<fmt:message key="mall.type" />
															：
														</div></td>
													<td height="29"><div align="left">
															&nbsp;&nbsp;
															<fmt:message key="type.business" />
															<input type="radio" checked="checked"
																name="shopDetail.type" id="type" value="1"
																onclick="javascript:changeType(1)" />&nbsp;
															<fmt:message key="type.individual" />
															<input type="radio" name="shopDetail.type" value="0"
																onclick="javascript:changeType(0)" />
														</div></td>
												</tr>

												<tr>
													<td height="29"><div align="right">
															<font color="#ff0000">*</font>
															<fmt:message key="upload.IDcard.pic" />
															：
														</div></td>
													<td height="29"><div align="left">
															<input type="file"
																style="WIDTH: 250px; margin-left: 10px"
																name="shopDetail.idCardPicFile" id="trafficPicFile"
																class="input" />
														</div></td>
												</tr>
												<tr>
													<td height="29" colspan="2">
														<div id="trafficPicDiv">
															<table width="100%" cellpadding="0" cellspacing="0">
																<tr>
																	<td width="33%">
																		<div align="right">
																			<font color="#ff0000">*</font>
																			<fmt:message key="upload.business.pic" />
																			：<br>
																		</div>
																	</td>
																	<td><input type="file"
																		style="WIDTH: 250px; margin-left: 10px;"
																		name="shopDetail.trafficPicFile" id="trafficPicFile"
																		class="input" /></td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td height="29"><div align="right">
											<fmt:message key="UserAgreement" />
										</div></td>
									<td height="29"><br /> <textarea id="regItem"
											name="regItem" class="lgtext"
											style="width: 600px; height: 100px; font-size: 9pt">${regItem }</textarea>
								</tr>
								<tr>
									<td height="35" colspan="2" align="center"><input
										type="submit" value='<fmt:message key="submit"/>' class="s" />

									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table></td>
	</tr>
</table>


<script type="text/javascript">
 

		function sendCheckMob(value) {
			$.get("${pageContext.request.contextPath}/getCheckNo", {
				mob : value
			}, function(data) {
				alert( data );
			if(!data == "true"){
				return false;
			} 
			});
			return true;
		}
		jQuery.validator.setDefaults({});
		String.prototype.isAlpha = function() {
			return (this.replace(/^(1[3|4|7|5|8])[\d]{9}$/g, "").length == 0);
		};
	
		jQuery.validator.addMethod("checkName", function(value, element) {
					return checkName();
		}, '已占用！');
		
			jQuery.validator.addMethod("addEmail", function(value, element) {
			if (!value.isAlpha()) return false;
			jQuery("#userMail").val(value + "@gomall.la");
			//发送验证码
			if (sendCheckMob(value)) {
				jQuery("#mask").css("display", "block");
				jQuery("#checkArea").css("display", "block");
				jQuery("#checkNo").focus();
				return true;
			}
			return false;

		}, '手机格式不正确！');
			jQuery.validator.addMethod("isOK", function(value, element) {
				jQuery("#mask").css("display", "none");
				jQuery("#checkArea").css("display", "none");
				jQuery("#checkNo").css("display", "none");
				jQuery("#password").focus();
			return true;
		}, '已经占用！');
		jQuery(document)
				.ready(
						function() {
							jQuery("#userRegForm")
									.validate(
											{
												rules : {
													name : {
														required : true,
														minlength : 11,
														checkName: true,													
														addEmail : true
													},
													checkNo : {
														required : true,
														minlength : 4,
														digits:true,
														isOK : true
													},
													password : {
														required : true,
														minlength : 6
													},
													"shopDetail.postAddr" : {
														required : "#openShop:checked",
														minlength : 2
													},
													"shopDetail.idCardNum" : {
														required : "#openShop:checked",
														minlength : 15
													},
													"shopDetail.type" : {
														required : "#openShop:checked"
													},
													"shopDetail.idCardPicFile" : {
														required : "#openShop:checked"
													},
													"shopDetail.trafficPicFile" : {
														required : "#openShop:checked",
														required : "#type:checked"
													}

												},
												messages : {
													name : {
														required : '请输入手机',
														minlength : "手机号不正确!"
													},
													password : {
														required : '请输入密码',
														minlength : "至少６位数!"
													},

													userAdds : {
														required : '必填！'
													},
													"shopDetail.siteName" : {
														required : '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>',
														minlength : '<fmt:message key="errors.minlength"><fmt:param value=""/><fmt:param value="2"/></fmt:message>'
													},
													"shopDetail.postAddr" : {
														required : '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>',
														minlength : '<fmt:message key="errors.minlength"><fmt:param value=""/><fmt:param value="2"/></fmt:message>'
													},
													"shopDetail.idCardNum" : {
														required : '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>',
														minlength : '<fmt:message key="errors.minlength"><fmt:param value=""/><fmt:param value="15"/></fmt:message>'
													},
													"shopDetail.type" : {
														required : '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>'
													},
													"shopDetail.idCardPicFile" : {
														required : '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>'
													},
													"shopDetail.trafficPicFile" : {
														required : '<fmt:message key="errors.required"><fmt:param value=""/></fmt:message>'
													}
												}
											});

							jQuery("#password").blur(
									function() {
										jQuery("#password2").val(
												jQuery("#password").val());
									});
							jQuery("#name").blur(function() {
								jQuery("#nickName").val(jQuery("#name").val());
							});

							jQuery("#name").focus();

						});

		// propose username by combining first- and lastname
		function checkName() {
			var result = true;
			var nameValue = jQuery("#name").val();

			if (nameValue != null && nameValue != '') {
				if (nameValue.length == 11 && nameValue.isAlpha()) {
					$.ajax({
						url : "${pageContext.request.contextPath}/isUserExist",
						data : {
							"userName" : nameValue
						},
						type : 'post',
						async : false, //默认为true 异步   
						error : function(jqXHR, textStatus, errorThrown) {
							//console.log(textStatus, errorThrown);
						},
						success : function(retData) {
							if ('true' == retData) {
								result = false;
							}
						}
					});
				}
			}
			return result;
		}

		function isEmail(str) {
			var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
			return reg.test(str);
		}

		function isOpenShop(obj) {
			if (obj.checked) {
				document.getElementById("shopDetail").style.display = "block";
			} else {
				document.getElementById("shopDetail").style.display = "none";
			}
		}

		function changeType(obj) {
			if (obj == 1) {
				document.getElementById("trafficPicDiv").style.display = "block";
			} else {
				document.getElementById("trafficPicDiv").style.display = "none";
			}
		}
	</script>
