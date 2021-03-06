<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
 
<script>
    $(function() {
        $("#addForm").submit(function() {
            if (!checkEmpty("name", "产品名称"))
                return false;
            if (!checkEmpty("subTitle", "小标题"))
                return false;
            if (!checkNumber("originalPrice", "原价格"))
                return false;
            if (!checkNumber("promotePrice", "优惠价格"))
                return false;
            if (!checkInt("stock", "库存"))
                return false;
            return true;
        });
    });
</script>
 
<title>产品管理</title>
 
<div class="workingArea">
 
    <ol class="breadcrumb">
        <li><a href="listCategory">所有分类</a></li>
        <li><a href="admin_product_list?cid=${c.id}">${c.name}</a></li>
        <li class="active">产品管理</li>
    </ol>
 
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
	            <tr class="success">
	                <th>ID</th>
	                <th>图片</th>
	                <th>产品名称</th>
	                <th>产品小标题</th>
	                <th width="53px">原价格</th>
	                <th width="80px">优惠价格</th>
	                <th width="80px">库存数量</th>
	                <th width="80px">图片管理</th>
	                <th width="80px">设置属性</th>
	                <th width="42px">编辑</th>
	                <th width="42px">删除</th>
	            </tr>
            </thead>
            <tbody>
            
            <!-- 这个循环会将List<Product>中的所有的Product类全部都打印出来 -->
            <c:forEach items="${ps}" var="p">
                <tr>
                    <td>${p.id}</td>
                    <td>
                        <c:if test="${!empty p.firstProductImage}">
                        	<!-- 需要知道在编辑中如何修改这里的.jpg的位置才能确定这里的图片是和哪个文件相关 -->
                            <img width="40px" src="img/productSingle/${p.firstProductImage.id}.jpg">
                        </c:if>
                    </td>
                    <td>${p.name}</td>
                    <td>${p.subTitle}</td>
                    <td>${p.originalPrice}</td>
                    <td>${p.promotePrice}</td>
                    <td>${p.stock}</td>
                    <!-- 编辑Product图片的链接 -->
                    <td><a href="admin_productImage_list?pid=${p.id}"><span class="glyphicon glyphicon-picture"></span></a></td>
                    
                    <!-- 编辑属性值的类链接 -->
                    <td><a href="admin_propertyValue_edit?pid=${p.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>
 					
 					<!-- 编辑product的链接 -->
                    <td><a href="admin_product_edit?id=${p.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                    
                    <!-- 从数据库中删除product的链接 -->
                    <td><a deleteLink="true" href="admin_product_delete?id=${p.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
 
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
 
    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp"%>
    </div>
 
    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增产品</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="admin_product_add">
                <table class="addTable">
                    <tr>
                        <td>产品名称</td>
                        <td><input id="name" name="name" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>产品小标题</td>
                        <td><input id="subTitle" name="subTitle" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>原价格</td>
                        <td><input id="originalPrice" value="99.98" name="originalPrice" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td><input id="promotePrice"  value="19.98" name="promotePrice" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>库存</td>
                        <td><input id="stock"  value="99" name="stock" type="text" class="form-control"></td>
                    </tr>
                    
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                        	<!-- 将cid作为隐藏参数传递过去 -->
                            <input type="hidden" name="cid" value="${c.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
 
</div>
 
<%@include file="../include/admin/adminFooter.jsp"%>