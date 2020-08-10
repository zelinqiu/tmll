<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
<script>
$(function(){
    $("ul.pagination li.disabled a").click(function(){
        return false;
    });
});
</script>
 
 
<nav>
  <!-- ul表示无序列表 -->
  <ul class="pagination">
  	<!-- li接在ul后面，表示无序列表的项 -->
  	<!-- 返回首页的超链接是使用Bootstrap的分页效果来做的 -->
  	<!-- 这里的page.hasPrevious是使用EL表达式，实际上就是返回page.isHasPrevious -->
  	<!-- 当不存在上一页的时候，class为disable -->
    <li <c:if test="${!page.hasPreviouse}"> class="disabled"</c:if>>
      <a href="?start=0${page.param}">
        <span>«</span>
      </a>
    </li>
 
    <li <c:if test="${!page.hasPreviouse}"> class="disabled"</c:if>>
      <a  href="?start=${page.start-page.count}${page.param}">
        <span>‹</span>
      </a>
    </li>   

    <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
	    <li <c:if test="${status.index*page.count==page.start}"> class="disabled"</c:if>>
	        <a href="?start=${status.index*page.count}${page.param}"
	        <c:if test="${status.index*page.count==page.start}"> class="current"</c:if>
	        >${status.count}</a>
	    </li>
    </c:forEach>
 
 
    <li <c:if test="${!page.hasNext}"> class="disabled"</c:if>>
      <a href="?start=${page.start+page.count}${page.param}">
        <span>›</span>
      </a>
    </li>
    <li <c:if test="${!page.hasNext}"> class="disabled"</c:if>>
      <a href="?start=${page.last}${page.param}">
        <span>»</span>
      </a>
    </li>
  </ul>
</nav>


