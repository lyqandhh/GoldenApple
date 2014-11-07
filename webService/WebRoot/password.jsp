<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="./inc/header.jsp"%>
<div class="container theme-showcase">
	<div class="alert alert-danger alert-dismissable" id="alert">新密码要求：最少6位以上任意字符</div>

	<!-- 操作表单 -->
	<div class="bs-callout bs-callout-info">
		<form class="form-inline" id="rechargeForm" role="form" style="margin-bottom:0px;">
			<div class="form-group">
				<label class="sr-only" for="newpassword">输入新密码</label> <input type="text" name="newpassword" class="form-control" style="width:200px;" id="newpassword"
					placeholder="输入新密码">
			</div>
			<button type="button" class="btn btn-primary" id="setPassword">修改密码</button>
		</form>
	</div>
</div>
<script type="text/javascript">
	$("#setPassword").click(function() {
		var newPassword = $("#newpassword").val();
		$.ajax({
			type: "POST",
			url: "<%=request.getContextPath()%>/user/setPassword.do",
			data: "new="+newPassword,
			dataType: "json",
			success: function(msg){
				if(msg) {
					alert("修改密码成功！ ");
					location.href = "<%=request.getContextPath()%>
	/j_spring_security_logout";
										} else {
											alert("修改密码失败！ ");
										}
									}
								});
					});
</script>
<%@ include file="./inc/footer.jsp"%>