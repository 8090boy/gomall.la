<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@include file='/WEB-INF/pages/common/taglib.jsp'%>
<!-- 商品分类 -->
<div class="mini_m">
	<div class="mt">
		<h2>${sort.sortName}</h2>
	</div>
	<div class="mc">
		<c:forEach items="${nsortList}" var="nsort">
			<div class="item" id="subSort" name="subSort">
				<h3>${nsort.nsortName}</h3>
				<ul>
					<c:forEach items="${nsort.subSort}" var="subSort">
						<li><a
							href="${pageContext.request.contextPath}/snsort/${sort.sortId}-${nsort.nsortId}-${subSort.nsortId}">${subSort.nsortName}</a></li>
					</c:forEach>
				</ul>
			</div>
		</c:forEach>
	</div>
</div>
<script language="JavaScript" type="text/javascript">
         jQuery(document).ready(function(){
         $("div #subSort:first").addClass("current");
         $(".mc div h3").click(function(){
	    	 if($(this).parent().hasClass("current")){
	    	 	 $(this).parent().removeClass("current");
	    	 }else{
	    	 	 $(this).parent().addClass("current");
	    	 }
	  	  });
        });
         </script>