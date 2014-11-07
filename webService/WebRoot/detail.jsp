<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.gqlife.ParametersTools"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String key=request.getParameter("key");
%>
<%@ include file="./inc/header.jsp"%>
<link rel="stylesheet" href="<%=path %>/css/iframe.css">
<div class="leftFrame">
	<div style="width:268px;margin:55px auto 0;">
		<div class="vtitle2"><img src="<%=path %>/img/account.png" alt="">&nbsp;我的账号</div>
		<div class="vtitle active2"><em class="v"></em><img src="<%=path %>/img/personal_information.png" class="titlePhoto">&nbsp;个人资料</div>
			<div class="vcon" style="">
			<ul class="vconlist clearfix">
				<li><a href="javascript:setFrame('<%=path %>/iframe/setHead.jsp?key=<%=key%>');">设置头像</a></li>
				<li><a href="javascript:setFrame('<%=path %>/manage/getObjectByDoctor.do?query=<%=key%>');">基本信息</a></li>
				<li><a href="javascript:setFrame('<%=path %>/iframe/credentialManage.jsp');">证件管理</a></li>
				<li><a href="javascript:setFrame('<%=path %>/iframe/contactWay.html');">联系方式</a></li>
				<li><a href="javascript:setFrame('<%=path %>/iframe/workExperience.html');">工作经历</a></li>
				<li><a href="javascript:setFrame('<%=path %>/iframe/studyExperience.html');">学习经历</a></li>
				<li><a href="javascript:setFrame('<%=path %>/iframe/postAddress.html');">邮件地址</a></li>
			</ul>
		</div>
		<div class="vtitle"><em class="v"></em><img src="<%=path %>/img/account_bind.png" class="titlePhoto">&nbsp;账号绑定</div>
			<div class="vcon" style="display: none;">
			<ul class="vconlist clearfix">
				<li><a href="javascript:;">身份设置</a></li>
				<li><a href="javascript:;">手机绑定</a></li>
				<li><a href="javascript:;">更换手机</a></li>
			</ul>
		</div>
		<div class="vtitle"><em class="v"></em><img src="<%=path %>/img/identity.png" class="titlePhoto">&nbsp;身份认证</div>
			<div class="vcon" style="display: none;">
			<ul class="vconlist clearfix">
				<li><a href="javascript:;">基本信息</a></li>
				<li><a href="javascript:;">设置头像</a></li>
				<li><a href="javascript:;">联系方式</a></li>
				<li><a href="javascript:;">工作经历</a></li>
				<li><a href="javascript:;">学习经历</a></li>
				<li><a href="javascript:;">邮件地址</a></li>
			</ul>
		</div>
		<div class="vtitle"><em class="v"></em><img src="<%=path %>/img/account_safety.png" class="titlePhoto">&nbsp;账号安全</div>
			<div class="vcon" style="display: none;">
			<ul class="vconlist clearfix">
				<li><a href="javascript:;">基本信息</a></li>
				<li><a href="javascript:;">设置头像</a></li>
				<li><a href="javascript:;">联系方式</a></li>
				<li><a href="javascript:;">工作经历</a></li>
				<li><a href="javascript:;">学习经历</a></li>
				<li><a href="javascript:;">邮件地址</a></li>
			</ul>
		</div>
		<div class="vtitle"><em class="v"></em><img src="<%=path %>/img/account_manage.png" class="titlePhoto">&nbsp;支付宝账户管理</div>
			<div class="vcon" style="display: none;">
			<ul class="vconlist clearfix">
				<li><a href="javascript:;">基本信息</a></li>
				<li><a href="javascript:;">设置头像</a></li>
				<li><a href="javascript:;">联系方式</a></li>
				<li><a href="javascript:;">工作经历</a></li>
				<li><a href="javascript:;">学习经历</a></li>
				<li><a href="javascript:;">邮件地址</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="rightFrame">
	<iframe src="<%=path %>/manage/getObjectByDoctor.do?query=<%=key%>" frameborder="0" width="100%" id="mainFrame">
		<p>大家好 我是一个iframe</p>
	</iframe>
</div>
<script type="text/javascript">
    var realPath="<%=path %>";
	$(function(){
		//菜单隐藏展开
		var tabs_i=0;
		$('.vtitle').click(function(){	
			var _self = $(this);
			var j = $('.vtitle').index(_self);
			tabs_i = j;
			$('.vtitle em').each(function(index){
				if(index == tabs_i){
					$('em',_self).removeClass('v01').addClass('v02');
				}else{
					$(this).removeClass('v02').addClass('v01');
				}
			});
			$('.vtitle').each(function() {
				$(".vtitle:eq("+tabs_i+")").addClass("active2").siblings(".vtitle").removeClass("active2");
			});
			$(".vcon:eq("+tabs_i+")").slideToggle();
			var imgArr = [realPath+"/img/personal_information.png",realPath+"/img/account_bind.png",realPath+"/img/identity.png",realPath+"/img/account_safety.png",realPath+"/img/account_manage.png",realPath+"/img/enter.png"];
			//初始图片路径
			var imgSrc = $(".titlePhoto:eq("+tabs_i+")").attr("src");
			$(".titlePhoto").each(function(index){
				$(".titlePhoto:eq("+index+")").attr("src",imgArr[index]);
			});
			$(".titlePhoto:eq("+tabs_i+")").attr("src",$(".titlePhoto:eq("+tabs_i+")").attr("src").split(".")[0]+"_active2.png");
		});
	})
	function setFrame(url){
			$("#mainFrame").attr("src",url);
	}
	</script>
<%@ include file="./inc/footer.jsp"%>