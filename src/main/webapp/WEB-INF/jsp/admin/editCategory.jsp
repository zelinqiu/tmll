<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../include/admin/adminHeader.jsp"%>
<%@ include file="../include/admin/adminNavigator.jsp"%>
 
<title>编辑分类</title>
 
 
<script>
	/* $(function(){};) 表示文档加载
	例如：$(function(){document.write("文档加载成功!");document.close();});写在里面的是jquery代码
	$()里面的代码的含义是需要将其中的代码加载完毕之后再运行jquery代码*/
    $(function(){
        $("#editForm").submit(function() {
        	//checkEmpty函数是定义在adminHeader.jsp中的
            if(!checkEmpty("name","分类名称"))
                return false;
            return true;
        });
    });
</script>
 
<div class="workingArea">
 
    <ol class="breadcrumb">
        <li><a href="listCategory">所有分类</a></li>
        <li class="active">编辑分类</li>
    </ol>
 
    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑分类</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_category_update"  enctype="multipart/form-data">
                <table class="editTable">
                    <tr>
                        <td>分类名称</td>
                        <!-- value=${c.name}是在编辑的时候的初始值 -->
                        <td><input  id="name" name="name" value="${c.name}" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>分类图片</td>
                        <td>
                            <input id="categoryPic" name="image" accept="image/*" type="file" />
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" value="${c.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>