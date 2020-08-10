
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>


<!--    model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs); 
        reviews是评论集合
        p是商品集合
        pvs是商品属性值集合-->


<script>
 
$(function(){
	//库存
    var stock = ${p.stock};
    //class=productNumberSetting是购买数量
    $(".productNumberSetting").keyup(function(){
        var num= $(".productNumberSetting").val();
        num = parseInt(num);
        if(isNaN(num))
            num= 1;
        if(num<=0)
            num = 1;
        if(num>stock)
            num = stock;
        $(".productNumberSetting").val(num);
    });
     
    //当点击class=increaseNumber即增加购买量按钮之后，会将num加1
    $(".increaseNumber").click(function(){
        var num= $(".productNumberSetting").val();
        num++;
        if(num>stock)
            num = stock;
        $(".productNumberSetting").val(num);
    });
    //与increaseNumber同理
    $(".decreaseNumber").click(function(){
        var num= $(".productNumberSetting").val();
        --num;
        if(num<=0)
            num=1;
        $(".productNumberSetting").val(num);
    });
     
    
    $(".addCartButton").removeAttr("disabled");
    
    //Ajax异步刷新加入购物车功能
    $(".addCartLink").click(function(){
        var page = "forecheckLogin";
        //Ajax获取page传递过来的结果即result
        $.get(page, function(result){
                    if("success"==result){
                        var pid = ${p.id};
                        var num= $(".productNumberSetting").val();
                        //这里会去访问ForeController中的foreaddCart
                        var addCartpage = "foreaddCart";
                        $.get(addCartpage,{"pid":pid,"num":num},function(result){
                            if("success"==result){
                                $(".addCartButton").html("已加入购物车");
                                $(".addCartButton").attr("disabled","disabled");
                                $(".addCartButton").css("background-color","lightgray")
                                $(".addCartButton").css("border-color","lightgray")
                                $(".addCartButton").css("color","black")                                         
                            }
                         }
                        );
                    } else {
                    	//模态登录
                        $("#loginModal").modal('show');                     
                    }
                }
        );      
        return false;
    });
    
    $(".buyLink").click(function(){
        var page = "forecheckLogin";
        $.get(page,function(result){
                    if("success"==result){
                        var num = $(".productNumberSetting").val();
                        location.href= $(".buyLink").attr("href")+"&num="+num;
                    }
                    else{
                        $("#loginModal").modal('show');                     
                    }
                }
        );      
        return false;
    });
    
    //这是对应modal中的提交按钮的loginSubmitButton
    $("button.loginSubmitButton").click(function(){
        var name = $("#name").val();
        var password = $("#password").val();
        
        if(0==name.length||0==password.length){
            $("span.errorMessage").html("请输入账号密码");
            $("div.loginErrorMessageDiv").show();           
            return false;
        }
        
        //如果输入的帐号密码不是空，进入下面的代码
        var page = "foreloginAjax";
        $.get(page, {"name":name,"password":password},
                function(result){
        			//如果帐号密码存在，重新载入
                    if("success"==result){
                        location.reload();
                    }
        			//如果帐号密码不正确，给出错误信息
                    else{
                        $("span.errorMessage").html("账号密码错误");
                        $("div.loginErrorMessageDiv").show();                       
                    }
                }
        );          
         
        return true;
    });
     
    $("img.smallImage").mouseenter(function(){
        var bigImageURL = $(this).attr("bigImageURL");
        $("img.bigImg").attr("src",bigImageURL);
    });
     
    $("img.bigImg").load(
        function(){
            $("img.smallImage").each(function(){
                var bigImageURL = $(this).attr("bigImageURL");
                img = new Image();
                img.src = bigImageURL;
                 
                img.onload = function(){
                    $("div.img4load").append($(img));
                };
            });     
        }
    );
});
 
</script>
 
<div class="imgAndInfo">
 
    <div class="imgInimgAndInfo">
        <img src="img/productSingle/${p.firstProductImage.id}.jpg" class="bigImg">
        <div class="smallImageDiv">
            <c:forEach items="${p.productSingleImages}" var="pi">
                <img src="img/productSingle_small/${pi.id}.jpg" bigImageURL="img/productSingle/${pi.id}.jpg" class="smallImage">
            </c:forEach>
        </div>
        <div class="img4load hidden" ></div>
    </div>


    <div class="infoInimgAndInfo">
         
        <div class="productTitle">
            ${p.name}
        </div>
        <div class="productSubTitle">
            ${p.subTitle} 
        </div>



        <div class="productPrice">
            <div class="juhuasuan">
                <span class="juhuasuanBig" >聚划算</span>
                <span>此商品即将参加聚划算，<span class="juhuasuanTime">1天19小时</span>后开始，</span>
            </div>



            <div class="productPriceDiv">
                <div class="gouwujuanDiv"><img height="16px" src="img/site/gouwujuan.png">
                <span> 全天猫实物商品通用</span>
                 
                </div>
                <div class="originalDiv">
                    <span class="originalPriceDesc">价格</span>
                    <span class="originalPriceYuan">¥</span>
                    <span class="originalPrice">
                        <fmt:formatNumber type="number" value="${p.originalPrice}" minFractionDigits="2"/>
                    </span>
                </div>

                <div class="promotionDiv">
                    <span class="promotionPriceDesc">促销价 </span>
                    <span class="promotionPriceYuan">¥</span>
                    <span class="promotionPrice">
                        <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/>
                    </span>
                </div>
            </div>
        </div>

        <div class="productSaleAndReviewNumber">
            <div>销量 <span class="redColor boldWord"> ${p.saleCount }</span></div>   
            <div>累计评价 <span class="redColor boldWord"> ${p.reviewCount}</span></div>    
        </div>
        <div class="productNumber">
            <span>数量</span>
            <span>
                <span class="productNumberSettingSpan">
                <input class="productNumberSetting" type="text" value="1">
                </span>
                <span class="arrow">
                    <a href="#nowhere" class="increaseNumber">
                    <span class="updown">
                            <img src="img/site/increase.png">
                    </span>
                    </a>
                     
                    <span class="updownMiddle"> </span>
                    <a href="#nowhere"  class="decreaseNumber">
                    <span class="updown">
                            <img src="img/site/decrease.png">
                    </span>
                    </a>
                     
                </span>
                     
            件</span>
            <span>库存${p.stock}件</span>
        </div>
        <div class="serviceCommitment">
            <span class="serviceCommitmentDesc">服务承诺</span>
            <span class="serviceCommitmentLink">
                <a href="#nowhere">正品保证</a>
                <a href="#nowhere">极速退款</a>
                <a href="#nowhere">赠运费险</a>
                <a href="#nowhere">七天无理由退换</a>
            </span>
        </div>    
        
        <div class="buyDiv">
            <a class="buyLink" href="forebuyone?pid=${p.id}"><button class="buyButton">立即购买</button></a>
            <!-- 点击加入购物车之后，会通过js的Ajax功能异步访问地址foreaddCart -->
            <a href="#nowhere" class="addCartLink"><button class="addCartButton"><span class="glyphicon glyphicon-shopping-cart"></span>加入购物车</button></a>
        </div>
    </div>
     
    <div style="clear:both"></div>
     
</div>