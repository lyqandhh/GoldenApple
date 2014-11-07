<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String key = request.getParameter("key");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link href="../css/iframe.css" rel="stylesheet">
</head>
<body>
	<div style="height:50px;"></div>
	<div class="frameTitle">
		<p>证件管理</p>
	</div>
	<hr style="border:1px solid  gray;margin: 0px;padding: 0px" />
	<table width="90%" height="300px" cellspacing="0" cellpadding="0" class="credentialTable" border="1">
		<tr>
			<td width="20%">证件名称</td>
			<td width="10%">证件级别</td>
			<td width="30%">证件号码</td>
			<td width="30%">照片</td>
		</tr>
		<tr height="20">
			<td>身份证</td>
			<td></td>
			<td></td>
			<td><a href="">上传照片</a></td>
		</tr>
		<tr height="20">
			<td>执业医师证</td>
			<td></td>
			<td></td>
			<td><a href="">上传照片</a></td>
		</tr>
		<tr height="20">
			<td>执业药师证</td>
			<td></td>
			<td></td>
			<td><a href="">上传照片</a></td>
		</tr>
		<tr height="20">
			<td>营养师证</td>
			<td></td>
			<td></td>
			<td><a href="">上传照片</a></td>
		</tr>
		<tr height="20">
			<td>健康管理师证</td>
			<td></td>
			<td></td>
			<td><a href="">上传照片</a></td>
		</tr>
		<tr height="20">
			<td>心理咨询师证</td>
			<td></td>
			<td></td>
			<td><a href="">上传照片</a></td>
		</tr>
		<tr height="20">
			<td>育婴师证</td>
			<td></td>
			<td></td>
			<td><a href="">上传照片</a></td>
		</tr>
		<tr height="20">
			<td>美容师证</td>
			<td></td>
			<td></td>
			<td><a href="">上传照片</a></td>
		</tr>
		<tr height="20">
			<td>健身教练证</td>
			<td></td>
			<td></td>
			<td><a href="">上传照片</a></td>
		</tr>
	</table>
</body>
</html>