<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.gqlife.entity.Feedback"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
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
	<div class="bs-callout bs-callout-warning">
		<form class="form-inline" id="queryCompanyForm" role="form" style="margin-bottom:0px;" action="<%=request.getContextPath()%>/feedback/feedbackList.do"
			method="post">
			<div class="form-group ">
				<select class="form-control" name="queryVisable" id="queryVisable">
					<option value="0" <c:if test="${query == 0}">selected</c:if>>------反馈产品------</option>
					<option value="1" <c:if test="${query == 1}">selected</c:if>>GQ门户、GQ空间</option>
					<option value="2" <c:if test="${query == 2}">selected</c:if>>GQ移动端</option>
					<option value="3" <c:if test="${query == 3}">selected</c:if>>GQ行业平台</option>
				</select>
			</div>
			<button type="submit" class="btn btn-success">查询</button>
			<button type="button" class="btn btn-link" onclick="location.reload();">清除条件</button>
		</form>
	</div>

	<table class="table table-striped font-size-12">
		<tr>
			<th width="10%">序号</th>
			<th width="10%">反馈产品</th>
			<th width="40%">反馈问题、建议</th>
			<th width="10%">反馈邮箱</th>
			<th width="15%">附件</th>
			<th width="15%">反馈时间</th>
		</tr>
		<%
			List<Feedback> tempList =new ArrayList<Feedback>();
			tempList=(List<Feedback>)request.getAttribute("list");
			if(tempList!=null){
				for(int i=0;i<tempList.size();i++){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
				String str = df.format(tempList.get(i).getFeedback_add_time());
		%>
		<tr>
			<td><%=i+1%></td>
			<td><%=tempList.get(i).getFeedback_product()%></td>
			<td><%=tempList.get(i).getFeedback_problem()%></td>
			<td><%=tempList.get(i).getFeedback_email()%></td>
			<td>
				<%
					if(!tempList.get(i).getFeedback_file_path().endsWith("")){
				%> <a href="javascript:void(0)" onclick="test('<%=request.getContextPath()+tempList.get(i).getFeedback_file_path()%>');"><%=tempList.get(i).getFeedback_file_name() %></a> <a
				style="margin-left: 3px;" href="javaScript:window.location.href='<%=request.getContextPath()+tempList.get(i).getFeedback_file_path()%>'"> <span data-toggle="tooltip"
					data-placement="right" data-original-title="点击查看网站" class="glyphicon glyphicon-share-alt"></span> </a> <%
				 	}
				 %>
			</td>
			<td><%=str%></td>
		</tr>
		<%
			}
			}
		%>
	</table>

	<!-- Modal -->
	<div class="modal fade" id="confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>


</div>
<!-- /container -->
<script type="text/javascript">
	function test(url) {
		window.location.href = decodeURI(url);
	};
</script>
<%@ include file="./inc/footer.jsp"%>