
<!-- 竖状分类菜单右侧的推荐产品列表 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
$(function(){
	$("div.productsAsideCategorys div.row a").each(function(){
		var v = Math.round(Math.random() * 6);
		if(v == 1)
			$(this).css("color","#87CEFA");
	});
});

</script>

<c:forEach items="${cs}" var="c">
	<!-- 第一层遍历出所有的分类 -->
	<div cid="${c.id}" class="productsAsideCategorys">
	
		<!-- 第二层遍历取出每个分类的商品集合 -->
		<c:forEach items="${c.productsByRow}" var="ps">
			<div class="row show1">
			
				<!-- 遍历8个商品中的每一个 -->
				<c:forEach items="${ps}" var="p">
					<c:if test="${!empty p.subTitle}">
					
						<a href="foreproduct?pid=${p.id}">
							<!-- 这个遍历假设subTitle不只一个，遍历所有的title选取第一个 -->
							<c:forEach items="${fn:split(p.subTitle, ' ')}" var="title" varStatus="st">
								<c:if test="${st.index==0}">
									${title}
								</c:if>
							</c:forEach>
						</a>
					</c:if>
				</c:forEach>
				<div class="seperator"></div>
			</div>		
		</c:forEach>
	</div>			
</c:forEach>
	
