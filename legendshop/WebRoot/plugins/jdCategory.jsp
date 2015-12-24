<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商城超酷精美多级分类菜单</title>
<style type="text/css">
body {
	color: #666666;
	font: 12px Arial, Lucida, Verdana, Helvetica, sans-serif;
	height: 100%;
	width: 100%;
	padding: 10px;
}

* {
	margin: 0;
	padding: 0;
}

em {
	font-style: normal;
	margin: 0;
	padding: 0;
}

ul {
	list-style: none;
	margin: 0;
	padding: 0;
}

.clr {
	display: block;
	clear: both;
	overflow: hidden;
	height: 0;
	line-height: 0;
	font-size: 0;
}

img {
	border: 0;
}

a {
	color: #666666;
	text-decoration: none;
	outline: none;
}

a:hover {
	text-decoration: underline;
}

#category {
	background-color: #F8EEDA;
	border: 2px solid #990000;
	width: 201px;
}

#category .title {
	background: url(themes/mall/yhd/styles/default/images/common_bg.gif)
		no-repeat scroll -165px -169px transparent;
	height: 25px;
	width: 195px;
	margin-left: -2px;
	margin-top: -1px;
	padding-top: 12px;
	padding-left: 10px;
}

#category .title a {
	color: #910000;
	cursor: pointer;
	text-decoration: none;
	font-size: 16px;
}

#category .content {
	display: block;
	position: relative;
	z-index: 999
}

#category .content .item, #category .content .hover, #category .content .item:hover
	{
	padding-left: 10px;
	text-align: left;
	height: 36px;
	line-height: 36px;
}

#category .content .item h2 {
	background: url(jiantou.gif) no-repeat;
	cursor: pointer;
	display: block;
	width: 185px;
	height: 35px;
	line-height: 35px;
	border-bottom: 1px solid #DFCA9F;
}

#category .content .item h2 a {
	font: 14px Arial, Lucida, Verdana, Helvetica, sans-serif;
	padding-left: 5px;
	color: #910000;
	font-weight: normal;
	height: 35px;
	line-height: 35px;
}

#category .content .item h2 a:hover {
	text-decoration: none;
}

#category .content .item .out, #category .content .item b {
	display: none;
}

#category .content .hover h2, #category .content .item:hover h2 {
	border: 1px solid #990000;
	margin-left: -1px;
	height: 34px;
	line-height: 34px;
	position: relative;
	z-index: 999;
	_width: 167px;
	width: 167px;
	background: #FFF !important;
}

#category .content .hover .out, #category .content .item:hover .out {
	display: block;
	position: absolute;
	left: 177px;
	top: 0;
	border: 1px #990000 solid;
	background: #fff;
	width: 580px;
	float: left;
	z-index: 998;
	min-height: 360px;
	_height: 360px;
	background: #F8EEDA;
}

#category .content .hover h2 b, #category .content .item:hover h2 b {
	position: absolute;
	display: block;
	height: 34px;
	width: 1px;
	overflow: hidden;
	left: 167px;
	top: 0;
	background: #fff;
	z-index: 1001;
}

#category .content .hover h2 a, #category .content .item:hover h2 a {
	padding-left: 5px;
	color: #910000;
	font: 14px/35px Arial, Lucida, Verdana, Helvetica, sans-serif;
}

#category .content .hover .out .subcategory, #category .content .item:hover .out .subcategory
	{
	width: 400px;
	float: left;
	background: #FFF;
	min-height: 360px;
	height: 100%;
}

#category .content .hover .out .subcategory .dl, #category .content .item:hover .out .subcategory .dl
	{
	border-bottom: 1px dotted #ccc;
	overflow: hidden;
	margin: 0 10px;
	padding: 5px 0;
	float: left;
	display: inline;
}

#category .content .hover .out .subcategory dt, #category .content .item:hover .out .subcategory dt
	{
	float: left;
	width: 70px;
	display: inline-block;
}

#category .content .hover .out .subcategory dt a, #category .content .item:hover .out .subcategory dt a
	{
	color: #BF0000;
	font-weight: bold;
	line-height: 14px;
	margin: 5px 0;
}

#category .content .hover .out .subcategory dd, #category .content .item:hover .out .subcategory dd
	{
	padding-left: 5px;
	float: left;
	width: 300px;
	vertical-align: middle;
}

#category .content .hover .out .subcategory dd a, #category .content .item:hover .out .subcategory dd a
	{
	border-right: 1px solid #F1E3E3;
	color: #545454;
	height: 14px;
	line-height: 14px;
	padding: 0 8px;
	white-space: nowrap;
	margin: 5px 0;
	float: left;
}

#category .content .hover .out .subcategory dd .nbd, #category .content .item:hover .out .subcategory dd .nbd
	{
	border: none;
}

#category .content .hover .out .subcategory dd a:hover, #category .content .item:hover .out .subcategory dd .hover
	{
	color: #bf0000;
}

#category .content .hover .out .hotview, #category .content .item:hover .out .hotview
	{
	float: right;
	padding: 0 18px;
	width: 140px;
	min-height: 360px;
	_height: 360px;
}

#category .content .hover .out .hotview h3, #category .content .item:hover .out .hotview h3
	{
	color: #bf0000;
	font-weight: bold;
	height: 31px;
	line-height: 31px;
	margin: 0;
	padding: 0;
}

#category .content .hover .out .hotview div a, #category .content .item:hover .out .hotview div a
	{
	color: #666666;
	display: block;
	margin-right: 10px;
	white-space: nowrap;
}
</style>
</head>
<body>
	<div id="category">
		<h2 class="title">
			<a href="category/goods" target="_blank">所有商品分类</a>
		</h2>
		<div class="content">
			<div class="item" onmouseover="this.className='hover';"
				onmouseout="this.className='item';">
				<h2>
					<a href="http://www.sweiku.com" target="_blank">服装服饰</a><b></b>
				</h2>
				<div class="out">
					<div class="subcategory">
						<div class="dl">
							<dt>
								<a href="http://www.sweiku.com" target="_blank" title="女装">女装</a>
							</dt>
							<dd>
								<a href="http://www.sweiku.com" title="毛衣" target="_blank">毛衣</a>
								<a href="http://www.sweiku.com?app=search&amp;cate_id=15"
									title="羊毛衫" target="_blank">羊毛衫</a> <a
									href="http://www.sweiku.com?app=search&amp;cate_id=16"
									title="皮衣" target="_blank">皮衣</a> <a
									href="http://www.sweiku.com?app=search&amp;cate_id=17"
									title="棉衣" target="_blank" class="nbd">棉衣</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a href="http://www.sweiku.com" target="_blank" title="男装">男装</a>
							</dt>
							<dd>
								<a href="http://www.sweiku.com" title="夹克" target="_blank">夹克</a>
								<a href="http://www.sweiku.com?app=search&amp;cate_id=19"
									title="毛衣" target="_blank">毛衣</a> <a
									href="http://www.sweiku.com0" title="棉衣" target="_blank">棉衣</a>
								<a href="http://www.sweiku.com1" title="羽绒" target="_blank">羽绒</a>
								<a href="http://www.sweiku.com2" title="牛仔裤" target="_blank"
									class="nbd">牛仔裤</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a href="http://www.sweiku.com" target="_blank" title="内衣">内衣</a>
							</dt>
							<dd>
								<a href="http://www.sweiku.com" title="保暖" target="_blank">保暖</a>
								<a href="http://www.sweiku.com4" title="文胸" target="_blank">文胸</a>
								<a href="http://www.sweiku.com5" title="睡衣" target="_blank">睡衣</a>
								<a href="http://www.sweiku.com6" title="内裤" target="_blank">内裤</a>
								<a href="http://www.sweiku.com7" title="袜子" target="_blank"
									class="nbd">袜子</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a href="http://www.sweiku.com" target="_blank" title="冬装新款">冬装新款</a>
							</dt>
							<dd>
								<a href="http://www.sweiku.com" title="羽绒服" target="_blank">羽绒服</a>
								<a href="http://www.sweiku.com9" title="呢大衣" target="_blank">呢大衣</a>
								<a href="http://www.sweiku.com?app=search&amp;cate_id=30"
									title="裤子" target="_blank" class="nbd">裤子</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a href="http://www.sweiku.com" target="_blank" title="童装">童装</a>
							</dt>
							<dd>
								<a href="http://www.sweiku.com" title="套装" target="_blank">套装</a>
								<a href="http://www.sweiku.com?app=search&amp;cate_id=32"
									title="羽绒" target="_blank">羽绒</a> <a
									href="http://www.sweiku.com?app=search&amp;cate_id=33"
									title="棉裤" target="_blank">棉裤</a> <a
									href="http://www.sweiku.com?app=search&amp;cate_id=33"
									title="棉裤" target="_blank">棉裤</a> <a
									href="http://www.sweiku.com?app=search&amp;cate_id=33"
									title="棉裤" target="_blank">棉裤</a> <a
									href="http://www.sweiku.com?app=search&amp;cate_id=33"
									title="棉裤" target="_blank">棉裤</a> <a
									href="http://www.sweiku.com?app=search&amp;cate_id=33"
									title="棉裤" target="_blank">棉裤</a> <a
									href="http://www.sweiku.com?app=search&amp;cate_id=33"
									title="棉裤" target="_blank">棉裤</a><a
									href="http://www.sweiku.com?app=search&amp;cate_id=34"
									title="童靴" target="_blank" class="nbd">童靴</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
					</div>
					<div class="hotview">
						<h3>热门品牌</h3>
						<div>
							作者：口明明口
							<p>
								<a href="http://www.sweiku.com" target="_blank">思维酷-IT专业博客</a>
							</p>
						</div>
					</div>
				</div>
			</div>

			<div class="item" onmouseover="this.className='hover';"
				onmouseout="this.className='item';">
				<h2>
					<a target="_blank" href="http://www.sweiku.com">箱包鞋帽</a><b></b>
				</h2>
				<div class="out">
					<div class="subcategory">
						<div class="dl">
							<dt>
								<a title="箱包" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=35">箱包</a>
							</dt>
							<dd>
								<a target="_blank" title="女包"
									href="http://www.sweiku.com?app=search&amp;cate_id=41">女包</a> <a
									target="_blank" title="韩版"
									href="http://www.sweiku.com?app=search&amp;cate_id=42">韩版</a> <a
									target="_blank" title="男包"
									href="http://www.sweiku.com?app=search&amp;cate_id=43">男包</a> <a
									target="_blank" title="旅行包"
									href="http://www.sweiku.com?app=search&amp;cate_id=44">旅行包</a>
								<a class="nbd" target="_blank" title="真皮"
									href="http://www.sweiku.com?app=search&amp;cate_id=45">真皮</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="珠宝" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=36">珠宝</a>
							</dt>
							<dd>
								<a target="_blank" title="钻戒"
									href="http://www.sweiku.com?app=search&amp;cate_id=46">钻戒</a> <a
									target="_blank" title="翡翠"
									href="http://www.sweiku.com?app=search&amp;cate_id=47">翡翠</a> <a
									target="_blank" title="施华洛"
									href="http://www.sweiku.com?app=search&amp;cate_id=48">施华洛</a>
								<a class="nbd" target="_blank" title="千足金"
									href="http://www.sweiku.com?app=search&amp;cate_id=49">千足金</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="手表" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=37">手表</a>
							</dt>
							<dd>
								<a target="_blank" title="Casio"
									href="http://www.sweiku.com?app=search&amp;cate_id=50">Casio</a>
								<a target="_blank" title="浪琴"
									href="http://www.sweiku.com?app=search&amp;cate_id=51">浪琴</a> <a
									target="_blank" title="天梭"
									href="http://www.sweiku.com?app=search&amp;cate_id=52">天梭</a> <a
									class="nbd" target="_blank" title="机械表"
									href="http://www.sweiku.com?app=search&amp;cate_id=53">机械表</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="配饰" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=38">配饰</a>
							</dt>
							<dd>
								<a target="_blank" title="保暖"
									href="http://www.sweiku.com?app=search&amp;cate_id=54">保暖</a> <a
									target="_blank" title="围巾"
									href="http://www.sweiku.com?app=search&amp;cate_id=55">围巾</a> <a
									target="_blank" title="帽子"
									href="http://www.sweiku.com?app=search&amp;cate_id=56">帽子</a> <a
									target="_blank" title="皮带"
									href="http://www.sweiku.com?app=search&amp;cate_id=57">皮带</a> <a
									class="nbd" target="_blank" title="毛线"
									href="http://www.sweiku.com?app=search&amp;cate_id=58">毛线</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="饰品" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=39">饰品</a>
							</dt>
							<dd>
								<a target="_blank" title="项链"
									href="http://www.sweiku.com?app=search&amp;cate_id=59">项链</a> <a
									target="_blank" title="耳饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=60">耳饰</a> <a
									target="_blank" title="发饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=61">发饰</a> <a
									target="_blank" title="手镯"
									href="http://www.sweiku.com?app=search&amp;cate_id=62">手镯</a> <a
									class="nbd" target="_blank" title="手链"
									href="http://www.sweiku.com?app=search&amp;cate_id=63">手链</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="眼镜" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=40">眼镜</a>
							</dt>
							<dd>
								<a target="_blank" title="太阳镜"
									href="http://www.sweiku.com?app=search&amp;cate_id=64">太阳镜</a>
								<a target="_blank" title="眼镜架"
									href="http://www.sweiku.com?app=search&amp;cate_id=65">眼镜架</a>
								<a target="_blank" title="Zippo"
									href="http://www.sweiku.com?app=search&amp;cate_id=66">Zippo</a>
								<a class="nbd" target="_blank" title="烟具"
									href="http://www.sweiku.com?app=search&amp;cate_id=67">烟具</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="女鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=148">女鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="高跟鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=151">高跟鞋</a>
								<a target="_blank" title="雪地靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=152">雪地靴</a>
								<a target="_blank" title="满帮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=153">满帮鞋</a>
								<a class="nbd" target="_blank" title="套脚鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=154">套脚鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="男鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=149">男鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="皮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=155">皮鞋</a>
								<a target="_blank" title="休闲鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=156">休闲鞋</a>
								<a target="_blank" title="运动鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=157">运动鞋</a>
								<a target="_blank" title="系带鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=158">系带鞋</a>
								<a class="nbd" target="_blank" title="商务正装鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=159">商务正装鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="童鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=150">童鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="童靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=160">童靴</a>
								<a target="_blank" title="跑步鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=161">跑步鞋</a>
								<a class="nbd" target="_blank" title="板鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=162">板鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
					</div>
					<div class="hotview">
						<h3>热门品牌</h3>
						<div>
							作者：口明明口
							<p>
								<a href="http://www.sweiku.com" target="_blank">思维酷-IT专业博客</a>
							</p>
						</div>
					</div>
				</div>
			</div>

			<div class="item" onmouseover="this.className='hover';"
				onmouseout="this.className='item';">
				<h2>
					<a target="_blank" href="http://www.sweiku.com">箱包鞋帽</a><b></b>
				</h2>
				<div class="out">
					<div class="subcategory">
						<div class="dl">
							<dt>
								<a title="箱包" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=35">箱包</a>
							</dt>
							<dd>
								<a target="_blank" title="女包"
									href="http://www.sweiku.com?app=search&amp;cate_id=41">女包</a> <a
									target="_blank" title="韩版"
									href="http://www.sweiku.com?app=search&amp;cate_id=42">韩版</a> <a
									target="_blank" title="男包"
									href="http://www.sweiku.com?app=search&amp;cate_id=43">男包</a> <a
									target="_blank" title="旅行包"
									href="http://www.sweiku.com?app=search&amp;cate_id=44">旅行包</a>
								<a class="nbd" target="_blank" title="真皮"
									href="http://www.sweiku.com?app=search&amp;cate_id=45">真皮</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="珠宝" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=36">珠宝</a>
							</dt>
							<dd>
								<a target="_blank" title="钻戒"
									href="http://www.sweiku.com?app=search&amp;cate_id=46">钻戒</a> <a
									target="_blank" title="翡翠"
									href="http://www.sweiku.com?app=search&amp;cate_id=47">翡翠</a> <a
									target="_blank" title="施华洛"
									href="http://www.sweiku.com?app=search&amp;cate_id=48">施华洛</a>
								<a class="nbd" target="_blank" title="千足金"
									href="http://www.sweiku.com?app=search&amp;cate_id=49">千足金</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="手表" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=37">手表</a>
							</dt>
							<dd>
								<a target="_blank" title="Casio"
									href="http://www.sweiku.com?app=search&amp;cate_id=50">Casio</a>
								<a target="_blank" title="浪琴"
									href="http://www.sweiku.com?app=search&amp;cate_id=51">浪琴</a> <a
									target="_blank" title="天梭"
									href="http://www.sweiku.com?app=search&amp;cate_id=52">天梭</a> <a
									class="nbd" target="_blank" title="机械表"
									href="http://www.sweiku.com?app=search&amp;cate_id=53">机械表</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="配饰" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=38">配饰</a>
							</dt>
							<dd>
								<a target="_blank" title="保暖"
									href="http://www.sweiku.com?app=search&amp;cate_id=54">保暖</a> <a
									target="_blank" title="围巾"
									href="http://www.sweiku.com?app=search&amp;cate_id=55">围巾</a> <a
									target="_blank" title="帽子"
									href="http://www.sweiku.com?app=search&amp;cate_id=56">帽子</a> <a
									target="_blank" title="皮带"
									href="http://www.sweiku.com?app=search&amp;cate_id=57">皮带</a> <a
									class="nbd" target="_blank" title="毛线"
									href="http://www.sweiku.com?app=search&amp;cate_id=58">毛线</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="饰品" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=39">饰品</a>
							</dt>
							<dd>
								<a target="_blank" title="项链"
									href="http://www.sweiku.com?app=search&amp;cate_id=59">项链</a> <a
									target="_blank" title="耳饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=60">耳饰</a> <a
									target="_blank" title="发饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=61">发饰</a> <a
									target="_blank" title="手镯"
									href="http://www.sweiku.com?app=search&amp;cate_id=62">手镯</a> <a
									class="nbd" target="_blank" title="手链"
									href="http://www.sweiku.com?app=search&amp;cate_id=63">手链</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="眼镜" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=40">眼镜</a>
							</dt>
							<dd>
								<a target="_blank" title="太阳镜"
									href="http://www.sweiku.com?app=search&amp;cate_id=64">太阳镜</a>
								<a target="_blank" title="眼镜架"
									href="http://www.sweiku.com?app=search&amp;cate_id=65">眼镜架</a>
								<a target="_blank" title="Zippo"
									href="http://www.sweiku.com?app=search&amp;cate_id=66">Zippo</a>
								<a class="nbd" target="_blank" title="烟具"
									href="http://www.sweiku.com?app=search&amp;cate_id=67">烟具</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="女鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=148">女鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="高跟鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=151">高跟鞋</a>
								<a target="_blank" title="雪地靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=152">雪地靴</a>
								<a target="_blank" title="满帮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=153">满帮鞋</a>
								<a class="nbd" target="_blank" title="套脚鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=154">套脚鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="男鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=149">男鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="皮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=155">皮鞋</a>
								<a target="_blank" title="休闲鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=156">休闲鞋</a>
								<a target="_blank" title="运动鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=157">运动鞋</a>
								<a target="_blank" title="系带鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=158">系带鞋</a>
								<a class="nbd" target="_blank" title="商务正装鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=159">商务正装鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="童鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=150">童鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="童靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=160">童靴</a>
								<a target="_blank" title="跑步鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=161">跑步鞋</a>
								<a class="nbd" target="_blank" title="板鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=162">板鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
					</div>
					<div class="hotview">
						<h3>热门品牌</h3>
						<div>
							作者：口明明口
							<p>
								<a href="http://www.sweiku.com" target="_blank">思维酷-IT专业博客</a>
							</p>
						</div>
					</div>
				</div>
			</div>
			<div class="item" onmouseover="this.className='hover';"
				onmouseout="this.className='item';">
				<h2>
					<a target="_blank" href="http://www.sweiku.com">箱包鞋帽</a><b></b>
				</h2>
				<div class="out">
					<div class="subcategory">
						<div class="dl">
							<dt>
								<a title="箱包" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=35">箱包</a>
							</dt>
							<dd>
								<a target="_blank" title="女包"
									href="http://www.sweiku.com?app=search&amp;cate_id=41">女包</a> <a
									target="_blank" title="韩版"
									href="http://www.sweiku.com?app=search&amp;cate_id=42">韩版</a> <a
									target="_blank" title="男包"
									href="http://www.sweiku.com?app=search&amp;cate_id=43">男包</a> <a
									target="_blank" title="旅行包"
									href="http://www.sweiku.com?app=search&amp;cate_id=44">旅行包</a>
								<a class="nbd" target="_blank" title="真皮"
									href="http://www.sweiku.com?app=search&amp;cate_id=45">真皮</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="珠宝" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=36">珠宝</a>
							</dt>
							<dd>
								<a target="_blank" title="钻戒"
									href="http://www.sweiku.com?app=search&amp;cate_id=46">钻戒</a> <a
									target="_blank" title="翡翠"
									href="http://www.sweiku.com?app=search&amp;cate_id=47">翡翠</a> <a
									target="_blank" title="施华洛"
									href="http://www.sweiku.com?app=search&amp;cate_id=48">施华洛</a>
								<a class="nbd" target="_blank" title="千足金"
									href="http://www.sweiku.com?app=search&amp;cate_id=49">千足金</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="手表" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=37">手表</a>
							</dt>
							<dd>
								<a target="_blank" title="Casio"
									href="http://www.sweiku.com?app=search&amp;cate_id=50">Casio</a>
								<a target="_blank" title="浪琴"
									href="http://www.sweiku.com?app=search&amp;cate_id=51">浪琴</a> <a
									target="_blank" title="天梭"
									href="http://www.sweiku.com?app=search&amp;cate_id=52">天梭</a> <a
									class="nbd" target="_blank" title="机械表"
									href="http://www.sweiku.com?app=search&amp;cate_id=53">机械表</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="配饰" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=38">配饰</a>
							</dt>
							<dd>
								<a target="_blank" title="保暖"
									href="http://www.sweiku.com?app=search&amp;cate_id=54">保暖</a> <a
									target="_blank" title="围巾"
									href="http://www.sweiku.com?app=search&amp;cate_id=55">围巾</a> <a
									target="_blank" title="帽子"
									href="http://www.sweiku.com?app=search&amp;cate_id=56">帽子</a> <a
									target="_blank" title="皮带"
									href="http://www.sweiku.com?app=search&amp;cate_id=57">皮带</a> <a
									class="nbd" target="_blank" title="毛线"
									href="http://www.sweiku.com?app=search&amp;cate_id=58">毛线</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="饰品" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=39">饰品</a>
							</dt>
							<dd>
								<a target="_blank" title="项链"
									href="http://www.sweiku.com?app=search&amp;cate_id=59">项链</a> <a
									target="_blank" title="耳饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=60">耳饰</a> <a
									target="_blank" title="发饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=61">发饰</a> <a
									target="_blank" title="手镯"
									href="http://www.sweiku.com?app=search&amp;cate_id=62">手镯</a> <a
									class="nbd" target="_blank" title="手链"
									href="http://www.sweiku.com?app=search&amp;cate_id=63">手链</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="眼镜" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=40">眼镜</a>
							</dt>
							<dd>
								<a target="_blank" title="太阳镜"
									href="http://www.sweiku.com?app=search&amp;cate_id=64">太阳镜</a>
								<a target="_blank" title="眼镜架"
									href="http://www.sweiku.com?app=search&amp;cate_id=65">眼镜架</a>
								<a target="_blank" title="Zippo"
									href="http://www.sweiku.com?app=search&amp;cate_id=66">Zippo</a>
								<a class="nbd" target="_blank" title="烟具"
									href="http://www.sweiku.com?app=search&amp;cate_id=67">烟具</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>

						<div class="dl">
							<dt>
								<a title="男鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=149">男鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="皮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=155">皮鞋</a>
								<a target="_blank" title="休闲鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=156">休闲鞋</a>
								<a target="_blank" title="运动鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=157">运动鞋</a>
								<a target="_blank" title="系带鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=158">系带鞋</a>
								<a class="nbd" target="_blank" title="商务正装鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=159">商务正装鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="童鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=150">童鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="童靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=160">童靴</a>
								<a target="_blank" title="跑步鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=161">跑步鞋</a>
								<a class="nbd" target="_blank" title="板鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=162">板鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
					</div>
					<div class="hotview">
						<h3>热门品牌</h3>
						<div>
							作者：口明明口
							<p>
								<a href="http://www.sweiku.com" target="_blank">思维酷-IT专业博客</a>
							</p>
						</div>
					</div>
				</div>
			</div>

			<div class="item" onmouseover="this.className='hover';"
				onmouseout="this.className='item';">
				<h2>
					<a target="_blank" href="http://www.sweiku.com">箱包鞋帽</a><b></b>
				</h2>
				<div class="out">
					<div class="subcategory">
						<div class="dl">
							<dt>
								<a title="箱包" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=35">箱包</a>
							</dt>
							<dd>
								<a target="_blank" title="女包"
									href="http://www.sweiku.com?app=search&amp;cate_id=41">女包</a> <a
									target="_blank" title="韩版"
									href="http://www.sweiku.com?app=search&amp;cate_id=42">韩版</a> <a
									target="_blank" title="男包"
									href="http://www.sweiku.com?app=search&amp;cate_id=43">男包</a> <a
									target="_blank" title="旅行包"
									href="http://www.sweiku.com?app=search&amp;cate_id=44">旅行包</a>
								<a class="nbd" target="_blank" title="真皮"
									href="http://www.sweiku.com?app=search&amp;cate_id=45">真皮</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="珠宝" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=36">珠宝</a>
							</dt>
							<dd>
								<a target="_blank" title="钻戒"
									href="http://www.sweiku.com?app=search&amp;cate_id=46">钻戒</a> <a
									target="_blank" title="翡翠"
									href="http://www.sweiku.com?app=search&amp;cate_id=47">翡翠</a> <a
									target="_blank" title="施华洛"
									href="http://www.sweiku.com?app=search&amp;cate_id=48">施华洛</a>
								<a class="nbd" target="_blank" title="千足金"
									href="http://www.sweiku.com?app=search&amp;cate_id=49">千足金</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="手表" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=37">手表</a>
							</dt>
							<dd>
								<a target="_blank" title="Casio"
									href="http://www.sweiku.com?app=search&amp;cate_id=50">Casio</a>
								<a target="_blank" title="浪琴"
									href="http://www.sweiku.com?app=search&amp;cate_id=51">浪琴</a> <a
									target="_blank" title="天梭"
									href="http://www.sweiku.com?app=search&amp;cate_id=52">天梭</a> <a
									class="nbd" target="_blank" title="机械表"
									href="http://www.sweiku.com?app=search&amp;cate_id=53">机械表</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="配饰" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=38">配饰</a>
							</dt>
							<dd>
								<a target="_blank" title="保暖"
									href="http://www.sweiku.com?app=search&amp;cate_id=54">保暖</a> <a
									target="_blank" title="围巾"
									href="http://www.sweiku.com?app=search&amp;cate_id=55">围巾</a> <a
									target="_blank" title="帽子"
									href="http://www.sweiku.com?app=search&amp;cate_id=56">帽子</a> <a
									target="_blank" title="皮带"
									href="http://www.sweiku.com?app=search&amp;cate_id=57">皮带</a> <a
									class="nbd" target="_blank" title="毛线"
									href="http://www.sweiku.com?app=search&amp;cate_id=58">毛线</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="饰品" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=39">饰品</a>
							</dt>
							<dd>
								<a target="_blank" title="项链"
									href="http://www.sweiku.com?app=search&amp;cate_id=59">项链</a> <a
									target="_blank" title="耳饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=60">耳饰</a> <a
									target="_blank" title="发饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=61">发饰</a> <a
									target="_blank" title="手镯"
									href="http://www.sweiku.com?app=search&amp;cate_id=62">手镯</a> <a
									class="nbd" target="_blank" title="手链"
									href="http://www.sweiku.com?app=search&amp;cate_id=63">手链</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="眼镜" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=40">眼镜</a>
							</dt>
							<dd>
								<a target="_blank" title="太阳镜"
									href="http://www.sweiku.com?app=search&amp;cate_id=64">太阳镜</a>
								<a target="_blank" title="眼镜架"
									href="http://www.sweiku.com?app=search&amp;cate_id=65">眼镜架</a>
								<a target="_blank" title="Zippo"
									href="http://www.sweiku.com?app=search&amp;cate_id=66">Zippo</a>
								<a class="nbd" target="_blank" title="烟具"
									href="http://www.sweiku.com?app=search&amp;cate_id=67">烟具</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="女鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=148">女鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="高跟鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=151">高跟鞋</a>
								<a target="_blank" title="雪地靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=152">雪地靴</a>
								<a target="_blank" title="满帮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=153">满帮鞋</a>
								<a class="nbd" target="_blank" title="套脚鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=154">套脚鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="男鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=149">男鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="皮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=155">皮鞋</a>
								<a target="_blank" title="休闲鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=156">休闲鞋</a>
								<a target="_blank" title="运动鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=157">运动鞋</a>
								<a target="_blank" title="系带鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=158">系带鞋</a>
								<a class="nbd" target="_blank" title="商务正装鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=159">商务正装鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>

					</div>
					<div class="hotview">
						<h3>热门品牌</h3>
						<div>
							作者：口明明口
							<p>
								<a href="http://www.sweiku.com" target="_blank">思维酷-IT专业博客</a>
							</p>
						</div>
					</div>
				</div>
			</div>

			<div class="item" onmouseover="this.className='hover';"
				onmouseout="this.className='item';">
				<h2>
					<a target="_blank" href="http://www.sweiku.com">箱包鞋帽</a><b></b>
				</h2>
				<div class="out">
					<div class="subcategory">
						<div class="dl">
							<dt>
								<a title="箱包" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=35">箱包</a>
							</dt>
							<dd>
								<a target="_blank" title="女包"
									href="http://www.sweiku.com?app=search&amp;cate_id=41">女包</a> <a
									target="_blank" title="韩版"
									href="http://www.sweiku.com?app=search&amp;cate_id=42">韩版</a> <a
									target="_blank" title="男包"
									href="http://www.sweiku.com?app=search&amp;cate_id=43">男包</a> <a
									target="_blank" title="旅行包"
									href="http://www.sweiku.com?app=search&amp;cate_id=44">旅行包</a>
								<a class="nbd" target="_blank" title="真皮"
									href="http://www.sweiku.com?app=search&amp;cate_id=45">真皮</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="珠宝" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=36">珠宝</a>
							</dt>
							<dd>
								<a target="_blank" title="钻戒"
									href="http://www.sweiku.com?app=search&amp;cate_id=46">钻戒</a> <a
									target="_blank" title="翡翠"
									href="http://www.sweiku.com?app=search&amp;cate_id=47">翡翠</a> <a
									target="_blank" title="施华洛"
									href="http://www.sweiku.com?app=search&amp;cate_id=48">施华洛</a>
								<a class="nbd" target="_blank" title="千足金"
									href="http://www.sweiku.com?app=search&amp;cate_id=49">千足金</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="手表" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=37">手表</a>
							</dt>
							<dd>
								<a target="_blank" title="Casio"
									href="http://www.sweiku.com?app=search&amp;cate_id=50">Casio</a>
								<a target="_blank" title="浪琴"
									href="http://www.sweiku.com?app=search&amp;cate_id=51">浪琴</a> <a
									target="_blank" title="天梭"
									href="http://www.sweiku.com?app=search&amp;cate_id=52">天梭</a> <a
									class="nbd" target="_blank" title="机械表"
									href="http://www.sweiku.com?app=search&amp;cate_id=53">机械表</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="配饰" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=38">配饰</a>
							</dt>
							<dd>
								<a target="_blank" title="保暖"
									href="http://www.sweiku.com?app=search&amp;cate_id=54">保暖</a> <a
									target="_blank" title="围巾"
									href="http://www.sweiku.com?app=search&amp;cate_id=55">围巾</a> <a
									target="_blank" title="帽子"
									href="http://www.sweiku.com?app=search&amp;cate_id=56">帽子</a> <a
									target="_blank" title="皮带"
									href="http://www.sweiku.com?app=search&amp;cate_id=57">皮带</a> <a
									class="nbd" target="_blank" title="毛线"
									href="http://www.sweiku.com?app=search&amp;cate_id=58">毛线</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="饰品" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=39">饰品</a>
							</dt>
							<dd>
								<a target="_blank" title="项链"
									href="http://www.sweiku.com?app=search&amp;cate_id=59">项链</a> <a
									target="_blank" title="耳饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=60">耳饰</a> <a
									target="_blank" title="发饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=61">发饰</a> <a
									target="_blank" title="手镯"
									href="http://www.sweiku.com?app=search&amp;cate_id=62">手镯</a> <a
									class="nbd" target="_blank" title="手链"
									href="http://www.sweiku.com?app=search&amp;cate_id=63">手链</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>

						<div class="dl">
							<dt>
								<a title="女鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=148">女鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="高跟鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=151">高跟鞋</a>
								<a target="_blank" title="雪地靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=152">雪地靴</a>
								<a target="_blank" title="满帮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=153">满帮鞋</a>
								<a class="nbd" target="_blank" title="套脚鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=154">套脚鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="男鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=149">男鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="皮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=155">皮鞋</a>
								<a target="_blank" title="休闲鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=156">休闲鞋</a>
								<a target="_blank" title="运动鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=157">运动鞋</a>
								<a target="_blank" title="系带鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=158">系带鞋</a>
								<a class="nbd" target="_blank" title="商务正装鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=159">商务正装鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="童鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=150">童鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="童靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=160">童靴</a>
								<a target="_blank" title="跑步鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=161">跑步鞋</a>
								<a class="nbd" target="_blank" title="板鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=162">板鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
					</div>
					<div class="hotview">
						<h3>热门品牌</h3>
						<div>
							作者：口明明口
							<p>
								<a href="http://www.sweiku.com" target="_blank">思维酷-IT专业博客</a>
							</p>
						</div>
					</div>
				</div>
			</div>

			<div class="item" onmouseover="this.className='hover';"
				onmouseout="this.className='item';">
				<h2>
					<a target="_blank" href="http://www.sweiku.com">箱包鞋帽</a><b></b>
				</h2>
				<div class="out">
					<div class="subcategory">
						<div class="dl">
							<dt>
								<a title="箱包" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=35">箱包</a>
							</dt>
							<dd>
								<a target="_blank" title="女包"
									href="http://www.sweiku.com?app=search&amp;cate_id=41">女包</a> <a
									target="_blank" title="韩版"
									href="http://www.sweiku.com?app=search&amp;cate_id=42">韩版</a> <a
									target="_blank" title="男包"
									href="http://www.sweiku.com?app=search&amp;cate_id=43">男包</a> <a
									target="_blank" title="旅行包"
									href="http://www.sweiku.com?app=search&amp;cate_id=44">旅行包</a>
								<a class="nbd" target="_blank" title="真皮"
									href="http://www.sweiku.com?app=search&amp;cate_id=45">真皮</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="珠宝" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=36">珠宝</a>
							</dt>
							<dd>
								<a target="_blank" title="钻戒"
									href="http://www.sweiku.com?app=search&amp;cate_id=46">钻戒</a> <a
									target="_blank" title="翡翠"
									href="http://www.sweiku.com?app=search&amp;cate_id=47">翡翠</a> <a
									target="_blank" title="施华洛"
									href="http://www.sweiku.com?app=search&amp;cate_id=48">施华洛</a>
								<a class="nbd" target="_blank" title="千足金"
									href="http://www.sweiku.com?app=search&amp;cate_id=49">千足金</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="手表" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=37">手表</a>
							</dt>
							<dd>
								<a target="_blank" title="Casio"
									href="http://www.sweiku.com?app=search&amp;cate_id=50">Casio</a>
								<a target="_blank" title="浪琴"
									href="http://www.sweiku.com?app=search&amp;cate_id=51">浪琴</a> <a
									target="_blank" title="天梭"
									href="http://www.sweiku.com?app=search&amp;cate_id=52">天梭</a> <a
									class="nbd" target="_blank" title="机械表"
									href="http://www.sweiku.com?app=search&amp;cate_id=53">机械表</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="配饰" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=38">配饰</a>
							</dt>
							<dd>
								<a target="_blank" title="保暖"
									href="http://www.sweiku.com?app=search&amp;cate_id=54">保暖</a> <a
									target="_blank" title="围巾"
									href="http://www.sweiku.com?app=search&amp;cate_id=55">围巾</a> <a
									target="_blank" title="帽子"
									href="http://www.sweiku.com?app=search&amp;cate_id=56">帽子</a> <a
									target="_blank" title="皮带"
									href="http://www.sweiku.com?app=search&amp;cate_id=57">皮带</a> <a
									class="nbd" target="_blank" title="毛线"
									href="http://www.sweiku.com?app=search&amp;cate_id=58">毛线</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="饰品" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=39">饰品</a>
							</dt>
							<dd>
								<a target="_blank" title="项链"
									href="http://www.sweiku.com?app=search&amp;cate_id=59">项链</a> <a
									target="_blank" title="耳饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=60">耳饰</a> <a
									target="_blank" title="发饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=61">发饰</a> <a
									target="_blank" title="手镯"
									href="http://www.sweiku.com?app=search&amp;cate_id=62">手镯</a> <a
									class="nbd" target="_blank" title="手链"
									href="http://www.sweiku.com?app=search&amp;cate_id=63">手链</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="眼镜" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=40">眼镜</a>
							</dt>
							<dd>
								<a target="_blank" title="太阳镜"
									href="http://www.sweiku.com?app=search&amp;cate_id=64">太阳镜</a>
								<a target="_blank" title="眼镜架"
									href="http://www.sweiku.com?app=search&amp;cate_id=65">眼镜架</a>
								<a target="_blank" title="Zippo"
									href="http://www.sweiku.com?app=search&amp;cate_id=66">Zippo</a>
								<a class="nbd" target="_blank" title="烟具"
									href="http://www.sweiku.com?app=search&amp;cate_id=67">烟具</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="女鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=148">女鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="高跟鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=151">高跟鞋</a>
								<a target="_blank" title="雪地靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=152">雪地靴</a>
								<a target="_blank" title="满帮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=153">满帮鞋</a>
								<a class="nbd" target="_blank" title="套脚鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=154">套脚鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>

						<div class="dl">
							<dt>
								<a title="童鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=150">童鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="童靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=160">童靴</a>
								<a target="_blank" title="跑步鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=161">跑步鞋</a>
								<a class="nbd" target="_blank" title="板鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=162">板鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
					</div>
					<div class="hotview">
						<h3>热门品牌</h3>
						<div>
							作者：口明明口
							<p>
								<a href="http://www.sweiku.com" target="_blank">思维酷-IT专业博客</a>
							</p>
						</div>
					</div>
				</div>
			</div>

			<div class="item" onmouseover="this.className='hover';"
				onmouseout="this.className='item';">
				<h2>
					<a target="_blank" href="http://www.sweiku.com">箱包鞋帽</a><b></b>
				</h2>
				<div class="out">
					<div class="subcategory">
						<div class="dl">
							<dt>
								<a title="箱包" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=35">箱包</a>
							</dt>
							<dd>
								<a target="_blank" title="女包"
									href="http://www.sweiku.com?app=search&amp;cate_id=41">女包</a> <a
									target="_blank" title="韩版"
									href="http://www.sweiku.com?app=search&amp;cate_id=42">韩版</a> <a
									target="_blank" title="男包"
									href="http://www.sweiku.com?app=search&amp;cate_id=43">男包</a> <a
									target="_blank" title="旅行包"
									href="http://www.sweiku.com?app=search&amp;cate_id=44">旅行包</a>
								<a class="nbd" target="_blank" title="真皮"
									href="http://www.sweiku.com?app=search&amp;cate_id=45">真皮</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="珠宝" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=36">珠宝</a>
							</dt>
							<dd>
								<a target="_blank" title="钻戒"
									href="http://www.sweiku.com?app=search&amp;cate_id=46">钻戒</a> <a
									target="_blank" title="翡翠"
									href="http://www.sweiku.com?app=search&amp;cate_id=47">翡翠</a> <a
									target="_blank" title="施华洛"
									href="http://www.sweiku.com?app=search&amp;cate_id=48">施华洛</a>
								<a class="nbd" target="_blank" title="千足金"
									href="http://www.sweiku.com?app=search&amp;cate_id=49">千足金</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="手表" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=37">手表</a>
							</dt>
							<dd>
								<a target="_blank" title="Casio"
									href="http://www.sweiku.com?app=search&amp;cate_id=50">Casio</a>
								<a target="_blank" title="浪琴"
									href="http://www.sweiku.com?app=search&amp;cate_id=51">浪琴</a> <a
									target="_blank" title="天梭"
									href="http://www.sweiku.com?app=search&amp;cate_id=52">天梭</a> <a
									class="nbd" target="_blank" title="机械表"
									href="http://www.sweiku.com?app=search&amp;cate_id=53">机械表</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="配饰" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=38">配饰</a>
							</dt>
							<dd>
								<a target="_blank" title="保暖"
									href="http://www.sweiku.com?app=search&amp;cate_id=54">保暖</a> <a
									target="_blank" title="围巾"
									href="http://www.sweiku.com?app=search&amp;cate_id=55">围巾</a> <a
									target="_blank" title="帽子"
									href="http://www.sweiku.com?app=search&amp;cate_id=56">帽子</a> <a
									target="_blank" title="皮带"
									href="http://www.sweiku.com?app=search&amp;cate_id=57">皮带</a> <a
									class="nbd" target="_blank" title="毛线"
									href="http://www.sweiku.com?app=search&amp;cate_id=58">毛线</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="饰品" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=39">饰品</a>
							</dt>
							<dd>
								<a target="_blank" title="项链"
									href="http://www.sweiku.com?app=search&amp;cate_id=59">项链</a> <a
									target="_blank" title="耳饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=60">耳饰</a> <a
									target="_blank" title="发饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=61">发饰</a> <a
									target="_blank" title="手镯"
									href="http://www.sweiku.com?app=search&amp;cate_id=62">手镯</a> <a
									class="nbd" target="_blank" title="手链"
									href="http://www.sweiku.com?app=search&amp;cate_id=63">手链</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="眼镜" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=40">眼镜</a>
							</dt>
							<dd>
								<a target="_blank" title="太阳镜"
									href="http://www.sweiku.com?app=search&amp;cate_id=64">太阳镜</a>
								<a target="_blank" title="眼镜架"
									href="http://www.sweiku.com?app=search&amp;cate_id=65">眼镜架</a>
								<a target="_blank" title="Zippo"
									href="http://www.sweiku.com?app=search&amp;cate_id=66">Zippo</a>
								<a class="nbd" target="_blank" title="烟具"
									href="http://www.sweiku.com?app=search&amp;cate_id=67">烟具</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="女鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=148">女鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="高跟鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=151">高跟鞋</a>
								<a target="_blank" title="雪地靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=152">雪地靴</a>
								<a target="_blank" title="满帮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=153">满帮鞋</a>
								<a class="nbd" target="_blank" title="套脚鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=154">套脚鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="男鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=149">男鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="皮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=155">皮鞋</a>
								<a target="_blank" title="休闲鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=156">休闲鞋</a>
								<a target="_blank" title="运动鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=157">运动鞋</a>
								<a target="_blank" title="系带鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=158">系带鞋</a>
								<a class="nbd" target="_blank" title="商务正装鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=159">商务正装鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>

					</div>
					<div class="hotview">
						<h3>热门品牌</h3>
						<div>
							作者：口明明口
							<p>
								<a href="http://www.sweiku.com" target="_blank">思维酷-IT专业博客</a>
							</p>
						</div>
					</div>
				</div>
			</div>
			<div class="item" onmouseover="this.className='hover';"
				onmouseout="this.className='item';">
				<h2>
					<a target="_blank" href="http://www.sweiku.com">箱包鞋帽</a><b></b>
				</h2>
				<div class="out">
					<div class="subcategory">
						<div class="dl">
							<dt>
								<a title="箱包" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=35">箱包</a>
							</dt>
							<dd>
								<a target="_blank" title="女包"
									href="http://www.sweiku.com?app=search&amp;cate_id=41">女包</a> <a
									target="_blank" title="韩版"
									href="http://www.sweiku.com?app=search&amp;cate_id=42">韩版</a> <a
									target="_blank" title="男包"
									href="http://www.sweiku.com?app=search&amp;cate_id=43">男包</a> <a
									target="_blank" title="旅行包"
									href="http://www.sweiku.com?app=search&amp;cate_id=44">旅行包</a>
								<a class="nbd" target="_blank" title="真皮"
									href="http://www.sweiku.com?app=search&amp;cate_id=45">真皮</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="珠宝" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=36">珠宝</a>
							</dt>
							<dd>
								<a target="_blank" title="钻戒"
									href="http://www.sweiku.com?app=search&amp;cate_id=46">钻戒</a> <a
									target="_blank" title="翡翠"
									href="http://www.sweiku.com?app=search&amp;cate_id=47">翡翠</a> <a
									target="_blank" title="施华洛"
									href="http://www.sweiku.com?app=search&amp;cate_id=48">施华洛</a>
								<a class="nbd" target="_blank" title="千足金"
									href="http://www.sweiku.com?app=search&amp;cate_id=49">千足金</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="手表" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=37">手表</a>
							</dt>
							<dd>
								<a target="_blank" title="Casio"
									href="http://www.sweiku.com?app=search&amp;cate_id=50">Casio</a>
								<a target="_blank" title="浪琴"
									href="http://www.sweiku.com?app=search&amp;cate_id=51">浪琴</a> <a
									target="_blank" title="天梭"
									href="http://www.sweiku.com?app=search&amp;cate_id=52">天梭</a> <a
									class="nbd" target="_blank" title="机械表"
									href="http://www.sweiku.com?app=search&amp;cate_id=53">机械表</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="配饰" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=38">配饰</a>
							</dt>
							<dd>
								<a target="_blank" title="保暖"
									href="http://www.sweiku.com?app=search&amp;cate_id=54">保暖</a> <a
									target="_blank" title="围巾"
									href="http://www.sweiku.com?app=search&amp;cate_id=55">围巾</a> <a
									target="_blank" title="帽子"
									href="http://www.sweiku.com?app=search&amp;cate_id=56">帽子</a> <a
									target="_blank" title="皮带"
									href="http://www.sweiku.com?app=search&amp;cate_id=57">皮带</a> <a
									class="nbd" target="_blank" title="毛线"
									href="http://www.sweiku.com?app=search&amp;cate_id=58">毛线</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="饰品" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=39">饰品</a>
							</dt>
							<dd>
								<a target="_blank" title="项链"
									href="http://www.sweiku.com?app=search&amp;cate_id=59">项链</a> <a
									target="_blank" title="耳饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=60">耳饰</a> <a
									target="_blank" title="发饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=61">发饰</a> <a
									target="_blank" title="手镯"
									href="http://www.sweiku.com?app=search&amp;cate_id=62">手镯</a> <a
									class="nbd" target="_blank" title="手链"
									href="http://www.sweiku.com?app=search&amp;cate_id=63">手链</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="眼镜" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=40">眼镜</a>
							</dt>
							<dd>
								<a target="_blank" title="太阳镜"
									href="http://www.sweiku.com?app=search&amp;cate_id=64">太阳镜</a>
								<a target="_blank" title="眼镜架"
									href="http://www.sweiku.com?app=search&amp;cate_id=65">眼镜架</a>
								<a target="_blank" title="Zippo"
									href="http://www.sweiku.com?app=search&amp;cate_id=66">Zippo</a>
								<a class="nbd" target="_blank" title="烟具"
									href="http://www.sweiku.com?app=search&amp;cate_id=67">烟具</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="女鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=148">女鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="高跟鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=151">高跟鞋</a>
								<a target="_blank" title="雪地靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=152">雪地靴</a>
								<a target="_blank" title="满帮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=153">满帮鞋</a>
								<a class="nbd" target="_blank" title="套脚鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=154">套脚鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="男鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=149">男鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="皮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=155">皮鞋</a>
								<a target="_blank" title="休闲鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=156">休闲鞋</a>
								<a target="_blank" title="运动鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=157">运动鞋</a>
								<a target="_blank" title="系带鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=158">系带鞋</a>
								<a class="nbd" target="_blank" title="商务正装鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=159">商务正装鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>

					</div>
					<div class="hotview">
						<h3>热门品牌</h3>
						<div>
							作者：口明明口
							<p>
								<a href="http://www.sweiku.com" target="_blank">思维酷-IT专业博客</a>
							</p>
						</div>
					</div>
				</div>
			</div>
			<div class="item" onmouseover="this.className='hover';"
				onmouseout="this.className='item';">
				<h2>
					<a target="_blank" href="http://www.sweiku.com">箱包鞋帽</a><b></b>
				</h2>
				<div class="out">
					<div class="subcategory">
						<div class="dl">
							<dt>
								<a title="箱包" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=35">箱包</a>
							</dt>
							<dd>
								<a target="_blank" title="女包"
									href="http://www.sweiku.com?app=search&amp;cate_id=41">女包</a> <a
									target="_blank" title="韩版"
									href="http://www.sweiku.com?app=search&amp;cate_id=42">韩版</a> <a
									target="_blank" title="男包"
									href="http://www.sweiku.com?app=search&amp;cate_id=43">男包</a> <a
									target="_blank" title="旅行包"
									href="http://www.sweiku.com?app=search&amp;cate_id=44">旅行包</a>
								<a class="nbd" target="_blank" title="真皮"
									href="http://www.sweiku.com?app=search&amp;cate_id=45">真皮</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="珠宝" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=36">珠宝</a>
							</dt>
							<dd>
								<a target="_blank" title="钻戒"
									href="http://www.sweiku.com?app=search&amp;cate_id=46">钻戒</a> <a
									target="_blank" title="翡翠"
									href="http://www.sweiku.com?app=search&amp;cate_id=47">翡翠</a> <a
									target="_blank" title="施华洛"
									href="http://www.sweiku.com?app=search&amp;cate_id=48">施华洛</a>
								<a class="nbd" target="_blank" title="千足金"
									href="http://www.sweiku.com?app=search&amp;cate_id=49">千足金</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="手表" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=37">手表</a>
							</dt>
							<dd>
								<a target="_blank" title="Casio"
									href="http://www.sweiku.com?app=search&amp;cate_id=50">Casio</a>
								<a target="_blank" title="浪琴"
									href="http://www.sweiku.com?app=search&amp;cate_id=51">浪琴</a> <a
									target="_blank" title="天梭"
									href="http://www.sweiku.com?app=search&amp;cate_id=52">天梭</a> <a
									class="nbd" target="_blank" title="机械表"
									href="http://www.sweiku.com?app=search&amp;cate_id=53">机械表</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="配饰" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=38">配饰</a>
							</dt>
							<dd>
								<a target="_blank" title="保暖"
									href="http://www.sweiku.com?app=search&amp;cate_id=54">保暖</a> <a
									target="_blank" title="围巾"
									href="http://www.sweiku.com?app=search&amp;cate_id=55">围巾</a> <a
									target="_blank" title="帽子"
									href="http://www.sweiku.com?app=search&amp;cate_id=56">帽子</a> <a
									target="_blank" title="皮带"
									href="http://www.sweiku.com?app=search&amp;cate_id=57">皮带</a> <a
									class="nbd" target="_blank" title="毛线"
									href="http://www.sweiku.com?app=search&amp;cate_id=58">毛线</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="饰品" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=39">饰品</a>
							</dt>
							<dd>
								<a target="_blank" title="项链"
									href="http://www.sweiku.com?app=search&amp;cate_id=59">项链</a> <a
									target="_blank" title="耳饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=60">耳饰</a> <a
									target="_blank" title="发饰"
									href="http://www.sweiku.com?app=search&amp;cate_id=61">发饰</a> <a
									target="_blank" title="手镯"
									href="http://www.sweiku.com?app=search&amp;cate_id=62">手镯</a> <a
									class="nbd" target="_blank" title="手链"
									href="http://www.sweiku.com?app=search&amp;cate_id=63">手链</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="眼镜" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=40">眼镜</a>
							</dt>
							<dd>
								<a target="_blank" title="太阳镜"
									href="http://www.sweiku.com?app=search&amp;cate_id=64">太阳镜</a>
								<a target="_blank" title="眼镜架"
									href="http://www.sweiku.com?app=search&amp;cate_id=65">眼镜架</a>
								<a target="_blank" title="Zippo"
									href="http://www.sweiku.com?app=search&amp;cate_id=66">Zippo</a>
								<a class="nbd" target="_blank" title="烟具"
									href="http://www.sweiku.com?app=search&amp;cate_id=67">烟具</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="女鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=148">女鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="高跟鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=151">高跟鞋</a>
								<a target="_blank" title="雪地靴"
									href="http://www.sweiku.com?app=search&amp;cate_id=152">雪地靴</a>
								<a target="_blank" title="满帮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=153">满帮鞋</a>
								<a class="nbd" target="_blank" title="套脚鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=154">套脚鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>
						<div class="dl">
							<dt>
								<a title="男鞋" target="_blank"
									href="http://www.sweiku.com?app=search&amp;cate_id=149">男鞋</a>
							</dt>
							<dd>
								<a target="_blank" title="皮鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=155">皮鞋</a>
								<a target="_blank" title="休闲鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=156">休闲鞋</a>
								<a target="_blank" title="运动鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=157">运动鞋</a>
								<a target="_blank" title="系带鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=158">系带鞋</a>
								<a class="nbd" target="_blank" title="商务正装鞋"
									href="http://www.sweiku.com?app=search&amp;cate_id=159">商务正装鞋</a>
								<div class="clr"></div>
							</dd>
							<div class="clr"></div>
						</div>

					</div>
					<div class="hotview">
						<h3>热门品牌</h3>
						<div>
							作者：口明明口
							<p>
								<a href="http://www.sweiku.com" target="_blank">思维酷-IT专业博客</a>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>

