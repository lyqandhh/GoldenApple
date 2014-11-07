<%@page import="cn.gqlife.GqboApplication"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!doctype html>
<html lang="zh-ch">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/bootstrap-theme.min.css">
<script src="./js/jquery-1.11.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<link href="css/login.css" rel="stylesheet">
<title>金苹果后台管理系统登陆</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div style="height:70px;"></div>
			<div class="col-xs-1 col-md-1 col-lg-1"></div>
			<div class="col-xs-10 col-md-10 col-lg-10" style="">
				<div class="col-xs-7 col-md-7 col-lg-7">
					<div class="col-xs-7 col-md-7 col-lg-7">
						<img src="img/login/logo.jpg" class="img-responsive" alt="" />
					</div>
					<div class="col-xs-5 col-md-5 col-lg-5">
						<img src="img/login/heart.png" class="img-responsive" />
						<div class="downloadDiv">
							<a href="">点击下载</a><br /> <img src="img/login/ios.jpg" alt=""> <img src="img/login/andriod.jpg" alt="">
						</div>
					</div>
				</div>
				<form name="f" action="<%=request.getContextPath()%>/j_spring_security_check" method="post" class="margin-base-vertical">
					<div class="col-xs-5 col-md-5 col-lg-5">
						<div class="rightDiv">
							<div style="height:50px;"></div>
							<label for="tel" class="formLabel">手机号</label>
							<p>
								<input type="text" class="form-control tel" id="tel" name="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}">
							</p>
							<label for="password" class="formLabel">密码</label>
							<p>
								<input type="password" class="form-control tel" name="j_password" id="password">
							</p>
							<a href="" class="forgetPassword">忘记密码</a>
							<div class="btnDiv">
								<button type="submit" class="btn btn-info" id="login">登录</button>
								<!-- <br /> <br />
								<button type="button" class="btn btn-success" id="login" onclick="window.location.href='register.jsp'">注册</button>
							 --></div>
						</div>
					</div>
				</form>
				<%
					String error = request.getParameter("error");
					if (error != null && error.length() > 0) {
				%>
				<div data-show="true" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">意见反馈</h4>
							</div>
							<div class="modal-body">用户名或密码错误！</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>
				<%
					}
				%>
			</div>
			<div class="col-xs-1 col-md-1 col-lg-1"></div>
		</div>
	</div>
	<script type="text/javascript">
	$('#myModal').modal('show');
	</script>
</body>
</html>