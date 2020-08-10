<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
 
<script>

    $(function(){ 
        <c:if test="${!empty msg}">
	        $("span.errorMessage").html("${msg}");
	        $("div.loginErrorMessageDiv").show();
        </c:if>
 
        $("form.loginForm").submit(function(){
            if(0==$("#name").val().length||0==$("#password").val().length){
                $("span.errorMessage").html("请输入账号密码");
                $("div.loginErrorMessageDiv").show();
                return false;
            }
            return true;
        });
 
        $("form.loginForm input").keyup(function(){
            $("div.loginErrorMessageDiv").hide();
        });
 
        var left = window.innerWidth/2+162;
        $("div.loginSmallDiv").css("left",left);
    })
</script>
 
<div id="loginDiv" style="position: relative">
 
    <div class="simpleLogo">
        <a href="${contextPath}"><img src="img/site/simpleLogo.png"></a>
    </div>
 
    <img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">
 
 	<!-- 这个jsp会调用foreController中的方法，当用户名或者密码不正确的时候，会再次跳转到
 	这个jsp，并传递msg信息，由js显示 -->
    <form class="loginForm" action="forelogin" method="post">
        <div id="loginSmallDiv" class="loginSmallDiv">
            <div class="loginErrorMessageDiv">
                <div class="alert alert-danger" >
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                    <span class="errorMessage"></span>
                </div>
            </div>
 
            <div class="login_acount_text">用户登录</div>
            <div class="loginInput " >
                <span class="loginInputIcon ">
                    <span class=" glyphicon glyphicon-user"></span>
                </span>
                <input id="name" name="name" placeholder="请输入用户名" type="text">
            </div>
 
            <div class="loginInput " >
                <span class="loginInputIcon ">
                    <span class=" glyphicon glyphicon-lock"></span>
                </span>
                <input id="password" name="password" type="password" placeholder="请输入密码" type="text">
            </div>
            <span class="text-danger">不要输入真实的天猫账号密码</span><br><br>
 
            <div>
                <a class="notImplementLink" href="#nowhere">忘记登录密码</a>
                <a href="registerPage" class="pull-right">免费注册</a>
            </div>
            <div style="margin-top:20px">
                <button class="btn btn-block redButton" type="submit">登录</button>
            </div>
        </div>
    </form>
 
</div>    