<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<link href="/buildpack/themes/default/style.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="/buildpack/themes/css/core.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="/buildpack/themes/css/print.css" rel="stylesheet"
	type="text/css" media="print" />
<link href="/buildpack/uploadify/css/uploadify.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="/buildpack/uploadify/css/sku.css" rel="stylesheet"
	type="text/css" media="screen" />

<!--[if IE]>
<link href="/buildpack/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<script src="/buildpack/js/speedup.js" type="text/javascript"></script>
<script src="/buildpack/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="/buildpack/js/jquery.cookie.js" type="text/javascript"></script>
<script src="/buildpack/js/jquery.validate.js" type="text/javascript"></script>
<script src="/buildpack/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="/buildpack/xheditor/xheditor-1.1.14-zh-cn.min.js"
	type="text/javascript"></script>
<script src="/buildpack/uploadify/scripts/swfobject.js"
	type="text/javascript"></script>
<script src="/buildpack/uploadify/scripts/jquery.uploadify.v2.1.0.js"
	type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="/buildpack/chart/raphael.js"></script>
<script type="text/javascript" src="/buildpack/chart/g.raphael.js"></script>
<script type="text/javascript" src="/buildpack/chart/g.bar.js"></script>
<script type="text/javascript" src="/buildpack/chart/g.line.js"></script>
<script type="text/javascript" src="/buildpack/chart/g.pie.js"></script>
<script type="text/javascript" src="/buildpack/chart/g.dot.js"></script>

<script src="/buildpack/js/dwz.core.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.util.date.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.validate.method.js"
	type="text/javascript"></script>
<script src="/buildpack/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.drag.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.tree.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.accordion.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.ui.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.theme.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.navTab.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.tab.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.resize.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.dialog.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.stable.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.ajax.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.pagination.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.database.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.effects.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.panel.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.history.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.combox.js" type="text/javascript"></script>
<script src="/buildpack/js/dwz.print.js" type="text/javascript"></script>
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="/buildpack/js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		DWZ.init("/buildpack/ryan.frag.xml", {
			loginUrl : "login_dialog.html",
			loginTitle : "登录", // 弹出登录对话框
			//		loginUrl:"login.html",	// 跳到登录页面
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			}, //【可选】
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			}, //【可选】
			debug : true, // 调试模式 【true|false】
			callback : function() {
				initEnv();
				$("#themeList").theme({
					themeBase : "themes"
				}); // themeBase 相对于index页面的主题base路径
			}
		});
	});
</script>
