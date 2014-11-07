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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=path%>/js/jquery-1.11.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<!-- Bootstrap core CSS -->
<link href="<%=request.getContextPath()%>/css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/theme.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
<link href="../css/iframe.css" rel="stylesheet">
<script src="../js/area.js"></script>
<style type="text/css">
.cursor{
	cursor: pointer;;
}
</style>
</head>
<body>
	<div style="height:50px;"></div>
	<div class="frameTitle">
		<p>基本信息</p>
	</div>
	<hr style="border:1px solid  gray;margin: 0px;padding: 0px" />
	<div class="frameTitle">
		<p class="nextTitle">请填写真实的资料方便大家了解你</p>
	</div>
	<div class="basicInformationDiv">
		<p>
			<span class="textspan_small">姓名</span><input type="text" value="${doctor.name }" id="name" />
		</p>
		<p>
			<span class="textspan_small">性别</span>
			<input type="radio" name="sex" <c:if test="${doctor.sex eq '男'}">checked</c:if> />男
			<input type="radio" name="sex" <c:if test="${doctor.sex eq '女'}">checked</c:if> />女
		</p>
		<p>
			<span class="textspan_small">生日</span> 
			<input class="Wdate" type="text" onClick="WdatePicker()" readonly="readonly" value="${doctor.birthday}">
		</p>
		<p>
			<span class="textspan_small">所在地</span> 
			<select name="" id="province" value="" ></select> 
			<select name="" id="city" value=""></select> 
			<select name="" id="area" value=""></select>
		</p>
		<p>
			<span class="textspan_small">毕业院校</span><input type="text" value="${doctorDetail.school }" id="graduateScholl" />
		</p>
		<p>
			<span class="textspan_small">学历</span><input type="text" value="${doctorDetail.education }" id="education" />
		</p>
		<p>
			<span class="textspan_small">职称</span><input type="text" value="${doctorDetail.job_title }" id="position" />
		</p>
		<p>
			<span class="textspan_small">你的职业</span> 
			<input class="cursor" type="radio" <c:if test="${doctor.type==1}">checked</c:if> name="career">医生 
			<input class="cursor" type="radio" <c:if test="${doctor.type==2}">checked</c:if> name="career">执业药师 
			<input class="cursor" type="radio" <c:if test="${doctor.type==5}">checked</c:if> name="career">心理咨询师 
			<input class="cursor" type="radio" <c:if test="${doctor.type==7}">checked</c:if> name="career">健身教练 <br /> 
			<span class="textspan_small"></span> 
			<input class="cursor" type="radio" <c:if test="${doctor.type==3}">checked</c:if> name="career">营养师
			<input class="cursor" type="radio" <c:if test="${doctor.type==4}">checked</c:if> name="career">健康管理师 
			<input class="cursor" type="radio" <c:if test="${doctor.type==8}">checked</c:if> name="career">育婴师 
			<input class="cursor" type="radio" <c:if test="${doctor.type==6}">checked</c:if> name="career">美容师
		</p>
		<!-- <p>
			<span class="textspan_small">身份证有效期</span> <input type="text" id="startDate" placeholder="年-月-日" style="width:12%;" />~ <input type="text" id="endDate"
				placeholder="年-月-日" style="width:12%;" />
		</p> -->
		<p >
			<button type="button" class="btn btn-primary" style="margin-top: 5px;margin-left: 200px" onclick="imageCrop()">保存</button>
		</p>
	</div>
	<script>
		getAllArea();
	</script>
</body>
</html>
