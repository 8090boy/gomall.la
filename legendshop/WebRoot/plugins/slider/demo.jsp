<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<html lang="en">
<head>
<title>Nivo Slider Demo</title>
<link rel="stylesheet" href="./themes/default/default.css"
	type="text/css" media="screen" />
<link rel="stylesheet" href="./themes/light/light.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="./themes/dark/dark.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="./themes/bar/bar.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="nivo-slider.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="style.css" type="text/css" media="screen" />
</head>
<body>
	<div class="slider theme-default">
		<div id="slider" class="nivoSlider">
			<img src="images/toystory.jpg" data-thumb="images/toystory.jpg"
				alt="" /> <a href="http://dev7studios.com"><img
				src="images/up.jpg" data-thumb="images/up.jpg" alt=""
				title="This is an example of a caption" /></a> <img
				src="images/walle.jpg" data-thumb="images/walle.jpg" alt=""
				data-transition="slideInLeft" /> <img src="images/nemo.jpg"
				data-thumb="images/nemo.jpg" alt="" title="#htmlcaption" />
		</div>
		<div id="htmlcaption" class="nivo-html-caption">
			<strong>This</strong> is an example of a <em>HTML</em> caption with <a
				href="#">a link</a>.
		</div>
	</div>

	<script src="<ls:templateResource item='/common/js/jquery.js'/>"
		type="text/javascript"></script>
	<script type="text/javascript" src="jquery.nivo.slider.js"></script>
	<script type="text/javascript">
    $(window).load(function() {
      //  $('#slider').nivoSlider();
      $('#slider').nivoSlider({
		effect: "random",
		animSpeed: 1000,
		pauseTime: 6000,
		controlNav: true,
		keyboardNav: false,
		captionOpacity: 0.4
	});
    });
    

	
    </script>
</body>
</html>