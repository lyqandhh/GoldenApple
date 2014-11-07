<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	Map<String,Object> tempMap=new HashMap<String,Object>();
	tempMap=(Map<String,Object>)request.getAttribute("result")==null?new HashMap<String,Object>():(Map<String,Object>)request.getAttribute("result");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>意见反馈</title>
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/bootstrap-theme.min.css">
<script src="./js/jquery-1.11.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
</head>
<body>
	<center>
		<form name=f action="<%=request.getContextPath()%>/feedback/save.do" enctype="multipart/form-data" method="post" onsubmit="return formCheck(this);">
			<div class="panel panel-primary" style="width: 90%;margin-top: 15px;">
				<div class="panel-heading">
					<h3 class="panel-title" style="text-align: left;">反馈意见表</h3>
				</div>
				<div class="panel-body" style="text-align: left;">
					<h2>
						<strong>欢迎您的意见及建议</strong>
					</h2>
					<p class="lead">感谢您使用金苹果后台管理系统！请告诉我们您对金苹果后台管理系统的意见和建议，我们会参考您的反馈不断优化我们的产品和服务。</p>
				</div>
				<table class="table">
					<tr>
						<td><div class="col-md-6">
								<h3>
									反馈产品：<font color="red">(必填)</font>
								</h3>
								<input type="radio" name="feedback_type" value="1" checked="checked" style="cursor: pointer;">金苹果PC端 <input type="radio" name="feedback_type"
									value="2" style="cursor: pointer;">金苹果移动端 <input type="radio" name="feedback_type" value="3" style="cursor: pointer;">金苹果后台管理
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="col-md-6">
								<h3>
									请详细描述您遇到的问题，您的意见以及建议：<font color="red">(必填)</font>
								</h3>
								<textarea id=feedback_problem name="feedback_problem" rows="4" cols="60"></textarea>
								<div id=error_problem style="display:none;" class="alert alert-warning">请填写反馈问题！</div>
							</div></td>
					</tr>
					<tr>
						<td>
							<div class="col-md-6">
								<input type="file" class="form-control" name="file"> <span class="input-group-btn">
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="col-md-6">
								<h3>
									您的邮箱：<font color="gray">(选填)</font>
								</h3>
								<input type="text" name="feedback_email" id=feedback_email class="form-control" placeholder="（方便我们及时告知您处理结果）"
									value="<%=tempMap.get("feedback_email")==null?"":tempMap.get("feedback_email")%>">
								<div id=error_email style="display:none;" class="alert alert-warning">错误！邮箱格式不正确</div>
							</div>
						</td>
					</tr>
					<tr>
						<td align="center"><input type="submit" id=button class="btn-success" value="提交反馈">
						</td>
					</tr>
				</table>
			</div>
		</form>
		<!-- 提交状态框 -->
		<%
			if(null!=tempMap.get("saveStatus")){
		%>
		<div data-show="true" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">意见反馈</h4>
					</div>
					<div class="modal-body">
						<%=tempMap.get("saveStatus").equals("保存成功")?
						"您的意见已经成功反馈给GQ项目相关部门。如果您填写了邮箱，我们会通过邮件告知相关处理结果。感谢您的支持。"
						:tempMap.get("saveStatus")%>
					</div>
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
	</center>
	<script>
		var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		function formCheck() {
			if ($("#feedback_problem").val()== "") {
				$("#error_problem").show("show");
				$("#feedback_problem").focus();
				setTimeout(function() {
					$("#error_problem").hide("show");
				}, 2000);
				return false;
			}
			if ($("#feedback_email").val() != "") {
				if (!reg.test($("#feedback_email").val())) {
					$("#error_email").show("show");
					$("#feedback_email").focus();
					$("#feedback_email").select()
					setTimeout(function() {
						$("#error_email").hide("show");
					}, 2000);
					return false;
				}
			}
		}
		$('#myModal').modal('show')
	</script>
</body>
</html>
