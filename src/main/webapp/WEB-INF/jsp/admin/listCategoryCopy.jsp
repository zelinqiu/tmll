<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" isELIgnored="false" import="java.util.*"%>
	
<!-- 引入JSTL -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="../include/admin/adminHeader.jsp" %>
<%@ include file="../include/admin/adminNavigator.jsp" %>

<script>
$(function(){
    $("#addForm").submit(function(){
        if(!checkEmpty("name","分类名称"))
            return false;
        if(!checkEmpty("categoryPic","分类图片"))
            return false;
        return true;
    });
});	
</script>

<title>分类管理</title>
<!-- class="workingArea即使得表格不紧贴左边界 -->
<div class="workingArea">
	<!-- label label-info前端内容，对分类管理进行整理 -->	
	<h1 class="label label-info">分类管理</h1>
	<br>
	<br>
	<!-- listDataTableDiv加不加好像是一样的 -->
	<div class="listDataTableDiv">
		<!-- class的内容是将整个表格变得更加整齐 -->
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<!-- thread加不加都行 -->
			<thead>
				<tr class="success">
				    <th>ID</th>
                    <th>图片</th>
                    <th>分类名称</th>
                    <th>属性管理</th>
                    <th>产品管理</th>
                    <th>编辑</th>
                    <th>删除</th>
            	</tr>
			</thead>
			<tbody>
				<!-- items表示集合 -->
				<c:forEach items="${cs}" var="c">
				<tr>
					<td>${c.id}</td>
					<td><img height="40px" src="img/category/${c.id}.jpg"></td>
					<td>${c.name}</td>
					<td><a href="admin_property_list?cid=${c.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>
					<td><a href="admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>
					<td><a href="admin_category_edit?id=${c.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a deleteLink="true" href="admin_category_delete?id=${c.id}"><span class="glyphicon glyphicon-trash"></span></a></td>     
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<!-- pageDiv将页面按钮放在中间 -->
	<div class="pageDiv">
		<%@ include file="../include/admin/adminPage.jsp" %>
	</div>
	
	<div class="panel panel-warning addDiv">
	  <div class="panel-heading">新增分类</div>
      <div class="panel-body">
      		<form method="post" id="addForm" action="admin_category_add" enctype="multipart/form-data">
      			<table class="addTable">
      				<tr>
      					<td>分类名称</td>
      					<td><input id="name" name="name" type="text" class="form-control"></td>
      				</tr>
      				<tr>
      					<td>分类图片</td>
      					<td>
      						<input id="categoryPic" accept="image/*" type="file" name="image" />
      					</td>
      				</tr>
      				<tr class="submitTR">
      					<td colspan="2" align="center">
      						<button type="submit" class="btn btn-success">提 交</button>
      					</td>
      				</tr>
      			</table>
      		</form>
      </div>
	</div>
</div>

<%@ include file="../include/admin/adminFooter.jsp" %>











