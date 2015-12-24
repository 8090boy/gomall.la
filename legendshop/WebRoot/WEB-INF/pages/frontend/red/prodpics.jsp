<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/common.jsp"%>
<%@ include file="/WEB-INF/pages/common/taglib.jsp"%>
<script src="<ls:templateResource item='/common/js/jquery.js'/>"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/plugins/jqzoom/js/jquery.jqzoom-core.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/jqzoom/css/jquery.jqzoom.css"
	type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/common/red/js/productpics.js"></script>
<div class="preview">
	<div class="jqzoom" id="spec-n1">
		<a href="<ls:photo item='${prod.pic}'/>" class="jqzoom" rel='gal1'
			title="${prod.name}"> <img width="350" height="350"
			src="<ls:images item='${prod.pic}' scale='0'/>">
		</a>
	</div>
	<c:if
		test="${requestScope.prodPics != null && fn:length(requestScope.prodPics) > 0}">
		<div id="spec-n5">
			<div id="spec-list">
				<div id="spec-left" class="control  back"></div>
				<div id="spec-right" class="control forward"></div>
				<div
					style="position: relative; overflow: hidden; z-index: 1; width: 300px; height: 56px;">
					<ul class="list-h"
						style="width: 720px; overflow: hidden; position: absolute; left: 0pt; top: 0pt; margin-top: 0pt; margin-left: 0pt;">


						<!-- 图片列表 begin -->
						<c:forEach items="${requestScope.prodPics}" var="pics">
							<li><a
								href="${pageContext.request.contextPath}/productGallery/${prod.prodId}"
								target="_blank"> <img
									src="<ls:images item='${pics.filePath}' sacle='3'/>" width="50"
									height="45"
									style="border: 1px solid rgb(204, 204, 204); padding: 2px;" /></a>
							</li>
						</c:forEach>
						<!-- 图片列表 end -->

					</ul>
				</div>
			</div>
		</div>
	</c:if>
</div>

<script type="text/javascript">

$(document).ready(function() {
	$('.jqzoom').jqzoom({
            zoomType: 'standard',
            lens:true,
            preloadImages: false,
            alwaysOn:false,
            zoomWidth:414,
            zoomHeight:350
        });
        $('.zoomPad').css('z-index','auto');
        //小图片滚动
        $("#spec-list").infiniteCarousel();
});

</script>