<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-cn">
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="/ico/favicon.png">
<title>金苹果-管理后台-v0.1</title>
<script src="<%=request.getContextPath()%>/js/jquery-1.11.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<!-- Bootstrap core CSS -->
<link href="<%=request.getContextPath()%>/css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/theme.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/common.js"></script>
<!-- 自定义字段工具类 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/parameters_tools.js"></script>
<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
   <style type="text/css">
   .pd0{padding-left:0;
   		padding-right: 0;	
   		padding-bottom: 15px;
   }
   </style>
</head>

<body>
	<!-- Fixed navbar -->
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">金苹果-管理后台</a>
			</div>
			<div class="navbar-collapse collapse">
				<input type="hidden" id="menuActive" value="${menuActive}" />
				<ul class="nav navbar-nav">
					<sec:authorize access="hasRole('ROLE_TONGBAO')">
						<li id="menuTongbaoRecharge"><a href="<%=request.getContextPath()%>/manage/doctor_list.do">资金管理</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_AUITTONGBAO')">
						<li id="menuTongbaoAudit"><a href="<%=request.getContextPath()%>/manage/doctor_list.do">认证管理</a></li>
					</sec:authorize>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li id="menuManage"><a href="<%=request.getContextPath()%>/manage/doctor_list.do">账户管理</a></li>
						<li id="menuManageService"><a href="<%=request.getContextPath()%>/manage/doctor_list.do">接口管理</a></li>
						<li id="menuManagePush"><a href="<%=request.getContextPath()%>/manage/doctor_list.do">新闻资讯</a></li>
						<li id="menuManageTongbaoka"><a href="<%=request.getContextPath()%>/manage/doctor_list.do">资金管理</a></li>
					</sec:authorize>
				</ul>
				<sec:authentication var="principal" property="principal" />
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> <span id="accountname"></span> <b class="caret"></b> </a>
						<ul class="dropdown-menu">
							<li><a href="<%=request.getContextPath()%>/user/password.do">修改密码</a></li>
						</ul>
					</li>
					<li class="pull-right"><a href="<%=request.getContextPath()%>/j_spring_security_logout">注销</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
	<script type="text/javascript">
		var date = "${principal.username}";
		var words = date.split("|");
		document.getElementById("accountname").innerHTML = words[1];
	</script>