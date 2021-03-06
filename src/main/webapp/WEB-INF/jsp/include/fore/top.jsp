<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
 
<nav class="top ">

	<!-- 其他拦截器内容部分 -->
    <a href="${contextPath}">
        <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
        天猫首页
    </a>
 
    <span>喵，欢迎来到天猫</span>
 	
 	<!-- user是拦截器拦截内容 -->
    <c:if test="${!empty user}">
        <a href="loginPage">${user.name}</a>
        <a href="forelogout">退出</a>
    </c:if>
 
    <c:if test="${empty user}">
        <a href="loginPage">请登录</a>
        <a href="registerPage">免费注册</a>
    </c:if>
 
    <span class="pull-right">
            <a href="forebought">我的订单</a>
            <a href="forecart">
            <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
            购物车<strong>${cartTotalItemNumber}</strong>件</a>
        </span>
 
</nav>