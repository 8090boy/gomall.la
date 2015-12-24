function club() {
	new myClub().init();
}
function myClub() {
	var params = {
		Prompt : "?",
		tit : "恭喜，您可用积分兑换红包了！",
		tip : "请填写您的支付宝信息，以便红包兑换现金时使用！<a id=\"nextReg\"  href='javascript:;'>下次兑换</a>",
		sub : "确定",
		payName : "支付宝姓名：",
		payId : "支付宝账户：",
		note : "自定义字词：",
		tipCon : "提示：此项可填写您个人喜欢的字、词、句、数字或符号等等。在修改个人信息时需要，请牢记！"
	};
	var _initUI = function() {
		var del = nT("span", "#close");
		del.innerHTML = "X";
		del.onclick = Event._close;
		var tit = nT("h5");
		tit.innerHTML = params.tit;
		var tit2 = nT("h6");
		tit2.innerHTML = params.tip;
		//
		var con = nT(".clubReg");
		var s1 = "<div><b>" + params.payName + "</b><b><input type=\"text\" name=\"payName\" /></b><b class=\"error\"></b></div>";
		var s2 = "<div><b>" + params.payId + "</b><b><input type=\"text\" name=\"payId\" /></b><b class=\"error\"></b></div>";
		var s3 = "<div><b>" + params.note
				+ "</b><b><input type=\"text\" name=\"note\" /></b><b class=\"error\"></b><b id=\"FAQ\">!</b><div id=\"tipCon\"><b></b>" + params.tipCon
				+ "</div></div>";
		con.innerHTML = s1 + s2 + s3;
		//
		var subm = nT("a", "#submit");
		subm.innerHTML = params.sub;
		subm.onclick = Event.subMit;
		//
		var wrap = $("#wrap")[0];
		wrap.appendChild(del);
		wrap.appendChild(tit);
		wrap.appendChild(tit2);
		wrap.appendChild(con);
		wrap.appendChild(subm);
		wrap.onselectstart = Event._isNoSel;
		wrap.oncontextmenu = Event._isNoSel;
		wrap.style.display = "block";
		var mask = $("#mask")[0];
		mask.style.display = "block";
		mask.onselectstart = Event._isNoSel;
		mask.oncontextmenu = Event._isNoSel;

	};

	var Event = {
		subMit : function() {
			//校验
			if (Event._valiPayName() !== true || Event._aliPayId() !== true || Event._noteIsNull() !== true)
				return;
			jQuery.post("/myClub", {
				'payName' :   $(".clubReg").find("input")[0].value ,
				'payId' :  $(".clubReg").find("input")[1].value ,
				'note' :  $(".clubReg").find("input")[2].value 
			}, function(retData) {
				if(retData){
					alert("OK");
					Event._close();
					return;
				}
				alert("Error !");
			});
		},
		//支付姓名校验
		_valiPayName : function() {
			var payName = $(".clubReg").find("[name=payName]")[0];
			if (/^[\u4E00-\u9FA5]{2,6}$/.test(payName.value)) {
				$(payName.parentElement.parentElement).find(".error")[0].innerHTML = "";
				return true;
			}
			payName.focus();
			$(payName.parentElement.parentElement).find(".error")[0].innerHTML = params.Prompt;
			return false;
		},
		//支付帐号校验
		_aliPayId : function() {
			var payId = $(".clubReg").find("[name=payId]")[0];
			if (/^[1][3|4|5|7|8][\d]{9}$/.test(payId.value)) {
				$(payId.parentElement.parentElement).find(".error")[0].innerHTML = "";
				return true;
			}
			if (/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(payId.value)) {
				$(payId.parentElement.parentElement).find(".error")[0].innerHTML = "";
				return true;
			}
			payId.focus();
			$(payId.parentElement.parentElement).find(".error")[0].innerHTML = params.Prompt;
			return false;
		},
		//支付帐号校验
		_noteIsNull : function() {
			var note = $(".clubReg").find("[name=note]")[0];
			var aaa = note.value;
			aaa = aaa.replace(/\s/g, '');
			aaa = aaa.replace('"', '');
			aaa = aaa.replace("'", '');
			if (aaa.length > 1) {
				$(note.parentElement.parentElement).find(".error")[0].innerHTML = "";
				return true;
			}
			note.focus();
			$(note.parentElement.parentElement).find(".error")[0].innerHTML = params.Prompt;
			return false;

		},

		//关闭自定义字词提示
		_close : function() {
			$("#wrap").find("input")[0].value = "";
			$("#wrap").find("input")[1].value = "";
			$("#wrap").find("input")[2].value = "";
			var wrap = $("#wrap")[0];
			wrap.style.display = "none";
			var mask = $("#mask")[0];
			mask.style.display = "none";
		},
		//自定义字词提示
		showFAQTip : function() {
			$("#tipCon")[0].style.display = "block";
			$(".clubReg").find("[name=note]")[0].focus();
		},
		//自定义字词提示
		hideFAQTip : function() {
			$("#tipCon")[0].style.display = "none";
			$(".clubReg").find("[name=note]")[0].blur();
		},
		_isNoSel : function() {
			return false;
		},
		nextReg : function() {
			$.get("/clubRs?i=o", function(d) {
			//	console.info(d);
				Event._close();
			});
		}
	};

	var _eventBind = function() {
		$("#FAQ")[0].onmousemove = Event.showFAQTip;
		$("#FAQ")[0].onmouseout = Event.hideFAQTip;
		$("#nextReg")[0].onclick = Event.nextReg;
	};
	//初始化界面、绑定事件
	function _init() {
		_initUI();
		_eventBind();
	}
	return {
		init : _init
	};
}

function nT() {
	var a = arguments[0], b = arguments[1];
	if (a != undefined && b != undefined) {
		if (/^[a-zA-Z]{1,}$/i.test(a)) {
			if (/^[#]\w{1,}$/i.test(b)) {
				var c = document.createElement(a);
				c.id = b.slice(1, b.length);
				return c;
			}
			if (/^[.]\w{1,}$/i.test(b)) {
				var c = document.createElement(a);
				c.className = b.slice(1, b.length);
				return c;
			}
		}
	}

	if (a != undefined) {
		if (/^[#]\w{1,}$/i.test(a)) {
			var b = document.createElement("div");
			b.id = a.slice(1, a.length);
			return b;
		}
		if (/^[.]\w{1,}$/i.test(a)) {
			var b = document.createElement("div");
			b.className = a.slice(1, a.length);
			return b;
		}
		return document.createElement(a);
	}

	return document.createElement("div");
}
