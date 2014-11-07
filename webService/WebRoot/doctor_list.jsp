<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.gqlife.ParametersTools"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String sname = request.getServerName();
	String host = "";
	if (sname.indexOf(".") > 0) {
		host = sname.substring(0, 3);
		request.setAttribute("host", host);
		System.out.println(host);
	}
%>
<%@ include file="./inc/header.jsp"%>
<div class="container theme-showcase">
	<div class="topDiv">
		<form class="form-inline" id="queryDoctorForm" role="form" style="text-align:center;margin-bottom:0px;"
			action="<%=request.getContextPath()%>/manage/doctor_list.do" method="post">
			<div class="form-group <c:if test="${!empty query}">has-success</c:if>" style="margin:0 auto;">
				<label class="sr-only" for="inputAppleCount">金苹果号</label> <input type="text" class="form-control input-lg" id="query" placeholder="输入姓名 或  金苹果号 或 职业证号 "
					name="query" value="${query}">
			</div>
			<button type="button" class="btn btn-success" onclick="queryAjax()">查询</button>
		</form>
		<br>
		<table cellpadding="0" cellspacing="0" width="70%" class="staffTable" border="0">
			<tr>
				<td width="11%"><a href="javaScript:queryAjax(1)">用户</a>
				</td>
				<td width="11%"><a href="javaScript:queryAjax(1)">医生</a>
				</td>
				<td width="11%"><a href="javaScript:queryAjax(2)">执业药师</a>
				</td>
				<td width="11%"><a href="javaScript:queryAjax(3)">营养师</a>
				</td>
				<td width="11%"><a href="javaScript:queryAjax(4)">健康管理师</a>
				</td>
				<td width="11%"><a href="javaScript:queryAjax(5)">心理咨询师</a>
				</td>
				<td width="11%"><a href="javaScript:queryAjax(6)">美容师</a>
				</td>
				<td width="11%"><a href="javaScript:queryAjax(7)">健身教练</a>
				</td>
				<td width="11%"><a href="javaScript:queryAjax(8)">育婴师</a>
				</td>
			</tr>
			<tr>
				<td><a href="javaScript:queryAjax(1)"><span id=userCount></span>人</a>
				</td>
				<td><a href="javaScript:queryAjax(1)"><span id=doctorCount></span>人</a>
				</td>
				<td><a href="javaScript:queryAjax(2)"><span id=pharmacistCount></span>人</a>
				</td>
				<td><a href="javaScript:queryAjax(3)"><span id=dietitiansCount></span>人</a>
				</td>
				<td><a href="javaScript:queryAjax(4)"><span id=healthCount></span>人</a>
				</td>
				<td><a href="javaScript:queryAjax(5)"><span id=psychologyCount></span>人</a>
				</td>
				<td><a href="javaScript:queryAjax(6)"><span id=beautyCount></span>人</a>
				</td>
				<td><a href="javaScript:queryAjax(7)"><span id=strongCount></span>人</a>
				</td>
				<td><a href="javaScript:queryAjax(8)"><span id=baby></span>人</a>
				</td>
			</tr>
		</table>
	</div>
	<table class="table table-striped font-size-12" style="margin:0">
		<tr>
			<th width="5%">选择</th>
			<th width="9%">金苹果号</th>
			<th width="9%">类别</th>
			<th width="7%">姓名</th>
			<th width="15%">医院</th>
			<th width="7%">科室</th>
			<th width="13%">级别</th>
			<th width="12%">职业医师号</th>
			<th width="9%">手机号码</th>
			<th width="7%">状态</th>
			<th width="50px">操作</th>
		</tr>
	</table>
	<div id="queryTable" align="center">
		<table class="table table-hover table-condensed font-size-12">
			<c:forEach items="${list}" var="doctor" varStatus="num">
				<tr height="45px" <c:if test="${doctor.enabled==0}">style="color:gray;text-decoration:line-through"</c:if>>
					<td width="5%" style="vertical-align:middle"><input type="radio" value="${doctor.id}" name="doctor_id" style="cursor: pointer;">
					</td>
					<td width="9%" style="vertical-align:middle">${doctor.appleCount}</td>
					<td width="9%" style="vertical-align:middle"><c:choose>
							<c:when test="${doctor.type=='1'}">医生</c:when>
							<c:when test="${doctor.type=='2'}">执业医生</c:when>
							<c:when test="${doctor.type=='3'}">心里咨询师</c:when>
							<c:when test="${doctor.type=='4'}">营养师</c:when>
							<c:when test="${doctor.type=='5'}">健康管理师</c:when>
							<c:when test="${doctor.type=='6'}">育婴师</c:when>
							<c:when test="${doctor.type=='7'}">健身教练</c:when>
							<c:when test="${doctor.type=='8'}">美容师</c:when>
							<c:otherwise>未知</c:otherwise>
						</c:choose></td>
					<td width="7%" style="vertical-align:middle">${doctor.name}</td>
					<td width="15%" style="vertical-align:middle">${doctor.hospital}</td>
					<td width="7%" style="vertical-align:middle">${doctor.departments}</td>
					<td width="13%" style="vertical-align:middle"><c:choose>
							<c:when test="${doctor.type=='1'}">
								<c:choose>
									<c:when test="${doctor.level=='1'}">主任医师</c:when>
									<c:when test="${doctor.level=='2'}">副主任医师</c:when>
									<c:when test="${doctor.level=='3'}">主治医师</c:when>
									<c:when test="${doctor.level=='4'}">住院医师</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${doctor.type=='2'}">
								<c:choose>
									<c:when test="${doctor.level=='1'}">高级(主任药师)</c:when>
									<c:when test="${doctor.level=='2'}">副高级(副主任药师)</c:when>
									<c:when test="${doctor.level=='3'}">中级(主治、主管药师)</c:when>
									<c:when test="${doctor.level=='4'}">初级(药师)</c:when>
									<c:when test="${doctor.level=='5'}">员级(药士)</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${doctor.type=='3'}">
								<c:choose>
									<c:when test="${doctor.level=='1'}">心里咨询师一级</c:when>
									<c:when test="${doctor.level=='2'}">心里咨询师二级</c:when>
									<c:when test="${doctor.level=='3'}">心里咨询师三级</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${doctor.type=='4'}">
								<c:choose>
									<c:when test="${doctor.level=='1'}">高级营养师</c:when>
									<c:when test="${doctor.level=='2'}">营养师</c:when>
									<c:when test="${doctor.level=='3'}">助理营养师</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${doctor.type=='5'}">
								<c:choose>
									<c:when test="${doctor.level=='1'}">一级营养师(国家职业资格一级)</c:when>
									<c:when test="${doctor.level=='2'}">二级营养师(国家职业资格二级)</c:when>
									<c:when test="${doctor.level=='3'}">三级营养师(国家职业资格三级)</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${doctor.type=='6'}">
								<c:choose>
									<c:when test="${doctor.level=='1'}">高级育婴师</c:when>
									<c:when test="${doctor.level=='2'}">育婴师</c:when>
									<c:when test="${doctor.level=='3'}">育婴员</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${doctor.type=='7'}">
								<c:choose>
									<c:when test="${doctor.level=='1'}">指导师级健身员</c:when>
									<c:when test="${doctor.level=='2'}">高级健身教练</c:when>
									<c:when test="${doctor.level=='3'}">中级健身教练</c:when>
									<c:when test="${doctor.level=='4'}">初级健身教练</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${doctor.type=='8'}">
								<c:choose>
									<c:when test="${doctor.level=='1'}">高级技师(国家职业资格一级)</c:when>
									<c:when test="${doctor.level=='2'}">技师(国家职业资格二级)</c:when>
									<c:when test="${doctor.level=='3'}">高级(国家职业资格三级)</c:when>
									<c:when test="${doctor.level=='4'}">中级(国家职业资格四级)</c:when>
									<c:when test="${doctor.level=='5'}">初级(国家职业资格五级)</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>未知</c:otherwise>
						</c:choose></td>
					<td width="12%" style="vertical-align:middle">${doctor.doctorsNum}</td>
					<td width="9%" style="vertical-align:middle">${doctor.tel}</td>
					<td width="7%" style="vertical-align:middle"><c:choose>
							<c:when test="${doctor.status=='0'}">尚未审核</c:when>
							<c:when test="${doctor.status=='1'}">通过审核</c:when>
							<c:when test="${doctor.status=='2'}">未通过审核</c:when>
							<c:otherwise>未知</c:otherwise>
						</c:choose></td>
					<td width="50px" style="vertical-align:middle"><div class="dropdown">
							<img src="<%=path%>/img/manage/computer.png" class="btn dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown"
								onclick="getRadioBoxValue('doctor_id',${doctor.id})"></img>
							<ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu1">
								<c:choose>
									<c:when test="${doctor.status!='1'}">
										<li role="presentation"><a role="menuitem" tabindex="-1" href="javaScript:getAudit('${doctor.name}',${doctor.id})">审核</a>
										</li>
									</c:when>
								</c:choose>
								<c:choose>
									<c:when test="${doctor.enabled==1}">
										<li role="presentation"><a role="menuitem" tabindex="-1" href="javaScript:setEnable(0,'${doctor.loginId}');">冻结</a>
										</li>
									</c:when>
									<c:when test="${doctor.enabled==0}">
										<li role="presentation"><a role="menuitem" tabindex="-1" href="javaScript:setEnable(1,'${doctor.loginId}');">解冻</a>
										</li>
									</c:when>
								</c:choose>
								<li role="presentation"><a role="menuitem" tabindex="-1" href="<%=path%>/manage/pageDetail.do?key=${doctor.id}">修改 </a>
								</li>
								<li role="presentation" class="divider"></li>
								<li role="presentation"><a role="menuitem" tabindex="-1" href="javaScript:getDetail('${doctor.name}',${doctor.id});">详细</a>
								</li>
							</ul>
						</div></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>

<!-- 账户审核模态框  -->
<!-- Modal -->
<div id="myModal" class="modal fade bs-example-modal-lg  " tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" aria-hidden="true">
	<div class="modal-dialog modal-lg " style='width:1000px'>
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="modal-body">
				<!-- 基础信息 -->
				<div class="panel panel-default">
					<div class="panel-heading">基本信息</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-2 col-sm-6 pd0" align="right">
								<B>姓名：</B>
							</div>
							<div class="col-md-3 col-sm-6 pd0" id="auditName">李逸期</div>
							<div class="col-md-3 col-sm-6 pd0" align="right">
								<B>金苹果号：</B>
							</div>
							<div class="col-md-4 col-sm-6 pd0" id="auditAppleCount">22</div>
						</div>
						<div class="row">
							<div class="col-md-2 col-sm-6 pd0" align="right">
								<B>类别：</B>
							</div>
							<div class="col-md-3 col-sm-6 pd0" id="auditType">执业医生</div>
							<div class="col-md-3 col-sm-6 pd0" align="right">
								<B>级别：</B>
							</div>
							<div class="col-md-4 col-sm-6 pd0" id="auditLevel">副高级(副主任药师)</div>
						</div>
						<div class="row">
							<div class="col-md-2 col-sm-6 pd0" align="right">
								<B>医院：</B>
							</div>
							<div class="col-md-3 col-sm-6 pd0" id="auditHospital">北京第一医院</div>
							<div class="col-md-3 col-sm-6 pd0" align="right">
								<B>科室：</B>
							</div>
							<div class="col-md-4 col-sm-6 pd0" id="auditDepartments">妇产科</div>
						</div>
						<div class="row">
							<div class="col-md-2 col-sm-6 pd0" align="right">
								<B>职业医师号：</B>
							</div>
							<div class="col-md-3 col-sm-6 pd0" id="auditDoctorNum">1101100001204882</div>
							<div class="col-md-3 col-sm-6 pd0" align="right">
								<B>手机号码：</B>
							</div>
							<div class="col-md-4 col-sm-6 pd0" id="auditTel">18859235177</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">证件信息</h3>
					</div>
					<div class="panel-body">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row">
									<div class="col-md-12 col-sm-12 ">
										<h4>
											<B>身份证</B>
										</h4>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 ">
										正面：<img src="<%=path%>/img/manage/default.png" alt="..." class="img-thumbnail">
									</div>
									<div class="col-md-6 col-sm-12 ">
										背面：<img src="<%=path%>/img/manage/default.png" alt="..." class="img-thumbnail">
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row">
									<div class="col-md-12 col-sm-12 ">
										<h4>
											<B>相关证件</B>
										</h4>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 ">
										正面：<img src="<%=path%>/img/manage/default.png" alt="..." class="img-thumbnail">
									</div>
									<div class="col-md-6 col-sm-12 ">
										背面：<img src="<%=path%>/img/manage/default.png" alt="..." class="img-thumbnail">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<input type="hidden" id=auditId />
				<div class="modal-footer">
					<button type="button" class="btn btn-success" onclick="setAudit(1)">通过</button>
					<button type="button" class="btn btn-warning" onclick="setAudit(2)">不通过</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 账户详细  -->
<!-- Modal -->
<div id="myDetail" class="modal fade bs-example-modal-lg  " tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"
	aria-hidden="true">
	<div class="modal-dialog modal-lg " style='width:1000px'>
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="detailModalLabel"></h4>
			</div>
			<div class="modal-body">
				<!-- 详细信息 -->
				<div class="panel panel-default">
					<div class="panel-heading">详细信息</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-2 col-sm-6 pd0" align="left">
								<img id=detailHeadImage src="<%=path%>/img/manage/default.png" style="margin-left: 10px" alt="头像" class="img-thumbnail">
							</div>
							<div class="col-md-10 col-sm-6 pd0" >
								<div class="row">
									<div class="col-md-2 col-sm-6 pd0" align="right">
										<B>姓名：</B>
									</div>
									<div class="col-md-3 col-sm-6 pd0" id="detailName">李逸期</div>
									<div class="col-md-3 col-sm-6 pd0" align="right">
										<B>金苹果号：</B>
									</div>
									<div class="col-md-4 col-sm-6 pd0" id="detailAppleCount">22</div>
								</div>
								<div class="row">
									<div class="col-md-2 col-sm-6 pd0" align="right">
										<B>性别：</B>
									</div>
									<div class="col-md-3 col-sm-6 pd0" id="detailSex">男</div>
									<div class="col-md-3 col-sm-6 pd0" align="right">
										<B>类别：</B>
									</div>
									<div class="col-md-4 col-sm-6 pd0" id="detailType">执业医生</div>
								</div>
								<div class="row">
									<div class="col-md-2 col-sm-6 pd0" align="right">
										<B>级别：</B>
									</div>
									<div class="col-md-3 col-sm-6 pd0" id="detailLevel">副高级(副主任药师)</div>
									<div class="col-md-3 col-sm-6 pd0" align="right">
										<B>医院：</B>
									</div>
									<div class="col-md-4 col-sm-6 pd0" id="detailHospital">北京第一医院</div>
								</div>
								<div class="row">
									<div class="col-md-2 col-sm-6 pd0" align="right">
										<B>科室：</B>
									</div>
									<div class="col-md-3 col-sm-6 pd0" id="detailDepartments">妇产科</div>
									<div class="col-md-3 col-sm-6 pd0" align="right">
										<B>职业医师号：</B>
									</div>
									<div class="col-md-4 col-sm-6 pd0" id="detailDoctorNum">1101100001204882</div>
								</div>
							</div>
						</div>
						<!-- 俺决定利用表格做一条分割线 = =！ -->
						<table class="table">
							<tr><th></th></tr>
						</table>
						<div class="row">
							<div class="col-md-3 col-sm-6 pd0" align="right">
								<B>毕业学校：</B>
							</div>
							<div class="col-md-2 col-sm-6 pd0" id="detailSchool">华西医科大学</div>
							<div class="col-md-1 col-sm-6 pd0" align="right">
								<B>学历：</B>
							</div>
							<div class="col-md-1 col-sm-6 pd0" id="detailEducation">博士</div>
							<div class="col-md-1 col-sm-6 pd0" align="right">
								<B>职称：</B>
							</div>
							<div class="col-md-4 col-sm-6 pd0" id="detailJobTitle">主任医师</div>
						</div>
						<div class="row">
							<div class="col-md-3 col-sm-6 pd0" align="right">
								<B>服务理念：</B>
							</div>
							<div class="col-md-2 col-sm-6 pd0" id="detailServiceConcept">让天下没有疾病</div>
							<div class="col-md-1 col-sm-6 pd0" align="right">
								<B>擅长：</B>
							</div>
							<div class="col-md-6 col-sm-6 pd0" id="detailGood">对处罚法风微风无欠费王菲王菲王菲企鹅夫妻愤青番茄番茄</div>
						</div>
						<div class="row">
							<div class="col-md-3 col-sm-6 pd0" align="right">
								<B>服务介绍：</B>
							</div>
							<div class="col-md-9 col-sm-6 pd0" id="detailServiceIntroduce">医疗咨询、医疗救助</div>
						</div>
						<div class="row">
							<div class="col-md-3 col-sm-6 pd0" align="right">
								<B>医学教育背景介绍：</B>
							</div>
							<div class="col-md-9 col-sm-6 pd0" id="detailEduBackground">对处罚法风微风无欠费王菲王菲王菲企鹅夫妻愤青番茄番茄。</div>
						</div>
						<div class="row">
							<div class="col-md-3 col-sm-6 pd0" align="right">
								<B>学习经历：</B>
							</div>
							<div class="col-md-9 col-sm-6 pd0" id="detailLearningExperience">对处罚法风微风无欠费王菲王菲王菲企鹅夫妻愤青番茄番茄。</div>
						</div>
						<div class="row">
							<div class="col-md-3 col-sm-6 pd0" align="right">
								<B>学术研究成果及获奖介绍：</B>
							</div>
							<div class="col-md-9 col-sm-6 pd0" id="detailResearch">对处罚法风微风无欠费王菲王菲王菲企鹅夫妻愤青番茄番茄。</div>
						</div>
						<!--联系方式  -->
						<table class="table">
							<tr><th></th></tr>
						</table>
						<div class="row">
							<div class="col-md-1 col-sm-6 pd0" align="right">
								<B>手机号：</B>
							</div>
							<div class="col-md-1 col-sm-6 pd0" id="detailCell">18859215171</div>
							<div class="col-md-2 col-sm-6 pd0" align="right">
								<B>固定电话：</B>
							</div>
							<div class="col-md-1 col-sm-6 pd0" id="detailTel">4294362</div>
							<div class="col-md-1 col-sm-6 pd0" align="right">
								<B>邮箱：</B>
							</div>
							<div class="col-md-2 col-sm-6 pd0" id="detailMail">382217215@qq.com</div>
							<div class="col-md-1 col-sm-6 pd0" align="right">
								<B>QQ号：</B>
							</div>
							<div class="col-md-3 col-sm-6 pd0" id="detailQQ">382217215</div>
						</div>
						<div class="row">
							<div class="col-md-1 col-sm-6 pd0" align="right">
								<B>所在地：</B>
							</div>
							<div class="col-md-1 col-sm-6 pd0" id="detailLocation">河南  开封</div>
							<div class="col-md-2 col-sm-6 pd0" align="right">
								<B>详细地址：</B>
							</div>
							<div class="col-md-4 col-sm-6 pd0" id="detailAddress">中国北京海定区345号</div>
							<div class="col-md-1 col-sm-6 pd0" align="right">
								<B>邮编：</B>
							</div>
							<div class="col-md-3 col-sm-6 pd0" id="detailZip">382217215</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">证件信息</h3>
					</div>
					<div class="panel-body">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row">
									<div class="col-md-12 col-sm-12 ">
										<h4>
											<B>身份证</B>
										</h4>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 ">
										正面：<img src="<%=path%>/img/manage/default.png" alt="..." class="img-thumbnail">
									</div>
									<div class="col-md-6 col-sm-12 ">
										背面：<img src="<%=path%>/img/manage/default.png" alt="..." class="img-thumbnail">
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row">
									<div class="col-md-12 col-sm-12 ">
										<h4>
											<B>相关证件</B>
										</h4>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 ">
										正面：<img src="<%=path%>/img/manage/default.png" alt="..." class="img-thumbnail">
									</div>
									<div class="col-md-6 col-sm-12 ">
										背面：<img src="<%=path%>/img/manage/default.png" alt="..." class="img-thumbnail">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<input type="hidden" id=auditId />
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div data-show="true" class="modal fade" id="resultDiv_success" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 120px">
		<div class="modal-content" align="center">
			<div class="modal-body">操作成功</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<div data-show="true" class="modal fade" id="resultDiv_error" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 120px">
		<div class="modal-content" align="center">
			<div class="modal-body">操作失败</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<!-- /container -->
<script src="<%=request.getContextPath()%>/js/manage/index.js"></script>
<%@ include file="./inc/footer.jsp"%>