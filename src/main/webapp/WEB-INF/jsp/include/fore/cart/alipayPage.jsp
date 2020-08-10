<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<!-- 这个<div class = "aliPayPageDiv" 定义在webapp/css/fore/style.css的1366行
	实际上，.css文件是事先写好，然后使用的时候就直接选取class  css教程里可以看出来，有些
	是使用 id= 这时候css文件中是 #名字 来定义，而使用 class= 是使用 .名字 来定义
	而css中修饰的都是显示的时候的一些形式，布局，样式等-->
<div class="aliPayPageDiv">
	<div class="aliPayPageLogo">
		<img class="pull-left" src="img/site/simpleLogo.png">
		<!-- 这里的style应该是div自带的样式 -->
		<div style="clear:both"></div>
	</div>
	
	<div>
		<span class="confirmMoneyText">扫一扫付款（元）</span>
		<span class="confirmMoney">
		￥<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2"/></span>
		
	</div>
	<div>
		<img class="aliPayImg" src="img/site/alipay2wei.png">
	</div>

	
	<div>
		<a href="forepayed?oid=${param.oid}&total=${param.total}"><button class="confirmPay">确认支付</button></a>
	</div>

</div>