<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>用户注册</title>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/bootstrap-theme.min.css">
<script src="./js/jquery-1.11.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
	<div class="row-fluid">
	<h1>请选择你的职业：</h1>
		<div class="span12" style="margin-top: 5px">
			 <button class="btn btn-large btn-block btn-warning" type="button"><h4><B>医生</B></h4></button>
		</div>
		<div class="span12" style="margin-top: 5px">
			 <button class="btn btn-large btn-block btn-warning" type="button"><h4><B>执业药师</B></h4></button>
		</div>
		<div class="span12" style="margin-top: 5px">
			 <button class="btn btn-large btn-block btn-warning" type="button"><h4><B>心理咨询师</B></h4></button>
		</div>
		<div class="span12" style="margin-top: 5px">
			 <button class="btn btn-large btn-block btn-warning" type="button"><h4><B>营养师</B></h4></button>
		</div>
		<div class="span12" style="margin-top: 5px">
			 <button class="btn btn-large btn-block btn-warning" type="button"><h4><B>健康管理师</B></h4></button>
		</div>
		<div class="span12" style="margin-top: 5px">
			 <button class="btn btn-large btn-block btn-warning" type="button"><h4><B>育婴师</B></h4></button>
		</div>
		<div class="span12" style="margin-top: 5px">
			 <button class="btn btn-large btn-block btn-warning" type="button"><h4><B>美容师</B></h4></button>
		</div>
		<div class="span12" style="margin-top: 5px">
			 <button class="btn btn-large btn-block btn-warning" type="button"><h4><B>健身教练</B></h4></button>
		</div>
	</div>
</div>
</body>
</html>
