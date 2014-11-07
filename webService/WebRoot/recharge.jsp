<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="./inc/header.jsp" %>
    <div class="container theme-showcase">
    	<div class="alert alert-danger alert-dismissable" id="alert" style="display:none">	
    	</div>

		<!-- 查询表单 -->
		<form class="form-inline" id="queryRechargeTongbaoForm" role="form" style="margin-bottom:0px;" action="<%= request.getContextPath() %>/tongbao/recharge.do" method="post">
			<input type="hidden" id="page" name="page" value="1" />
		</form>
		
		<!-- 操作表单 -->
		<div class="bs-callout bs-callout-info">
		<form class="form-inline" id="rechargeForm" role="form" style="margin-bottom:0px;">
			<div class="form-group">
				<label class="sr-only" for="exampleInputEmail2">充值GQ账号</label>
				<input type="text" id="GQID" class="form-control" style="width:200px;" id="exampleInputEmail2" placeholder="充值GQ账号">
			</div>
			<div class="form-group">
				<label class="sr-only" for="exampleInputPassword2">通宝金额</label>
				<input type="number" id="amount" class="form-control" style="width:100px;" id="exampleInputPassword2" placeholder="通宝金额">
			</div>
			<button type="button" class="btn btn-primary" id="rechargeSubmit">充值</button>
		</form>
		</div>
		
		<table class="table table-striped font-size-12">
			<tr>
				<th>订单号</th>
				<th>充值GQ账号</th>
				<th>通宝金额</th>
				<th>日期</th>
				<th>状态</th>
			</tr>
			<c:forEach items="${list}" var="var" varStatus="num">
			<tr>
				<td>${var.OID}</td>
				<td>${var.DID}</td>
				<td>${var.AMOUNT}</td>
				<td>${var.CDATE}</td>
				<td>
					<c:choose>
						<c:when test="${var.ASTATUS == 1}">
							<span class="label label-success">${var.ASTATUSSTR}</span>
						</c:when>
						<c:when test="${var.ASTATUS == 2 }">
							<span class="label label-danger">${var.ASTATUSSTR}</span>
						</c:when>
						<c:otherwise>
							<span class="label label-default">${var.ASTATUSSTR}</span>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</c:forEach>
		</table>
		<script type="text/javascript">pages(${query.count},${query.pageCount},${query.page},"queryRechargeTongbaoForm");</script>
    </div> <!-- /container -->
    <script type="text/javascript">
    $(function() {
    	$("#rechargeSubmit,#rechargeDoSubmit").click(function() {
    		var GQID = $("#GQID").val();
    		var amount = $("#amount").val();
    		if(GQID != "") {
    			if(!isNaN(GQID)) {
    				if(GQID.length == 6) {
    					if(amount != null && parseInt(amount) > 0) {
    						if($(this).attr("id") == "rechargeDoSubmit") {
        						$.ajax({
        							type: "POST",
        							url: "<%= request.getContextPath() %>/tongbao/setRecharge.do",
        							data: "GQID="+GQID+"&amount="+amount,
        							dataType: "json",
        							success: function(msg){
        								if(msg) {
        									alert("充值成功！ ");
        									location.reload();
        								}else {
        									alert("充值失败！ ");
        								}
        							}
        			    		});
    						}else {
    							$.ajax({
        							type: "POST",
        							url: "<%= request.getContextPath() %>/tongbao/getCompanyInfo.do",
        							data: "GQID="+GQID,
        							dataType: "json",
        							success: function(obj){
        								if(obj.GQID) {
        									$("#infoProviderCompanyName").text(obj.providerCompanyName);
        									$("#infoProviderType").text(obj.providerType);
        									$("#infoProviderPhone").text(obj.providerPhone);
        									$("#infoProviderCompanyMailNum").text(obj.providerCompanyMailNum);
        									$("#infoProviderCompanyAddress").text(obj.providerCompanyAddress);
        									$("#infoProviderCompanyBLN").text(obj.providerCompanyBLN);
        									$("#infoProviderCompanyDesc").text(obj.providerCompanyDesc);
        									$('#myModal').modal();
        								}else {
        									$("#alert").text("您输入的GQ账号不存在！");
        				    				$("#alert").fadeIn().animate({opacity: '+=0'}, 3000).fadeOut('fast');
        								}
        							}
        			    		});
    						}
    						return false;
    					}else {
    						//充值金额有误
    						$("#alert").text("通宝充值金额有误！");
    						$("#alert").fadeIn().animate({opacity: '+=0'}, 3000).fadeOut('fast');
    						return false;
    					}
    				}else {
    					//GQID必须为6位
    					$("#alert").text("您输入的GQ账号不正确，GQ账号为6位纯数字！");
    					$("#alert").fadeIn().animate({opacity: '+=0'}, 3000).fadeOut('fast');
    					return false;
    				}
    			}else {
    				//GQID必须为数字
    				$("#alert").text("您输入的GQ账号不正确，GQ账号为6位纯数字！");
    				$("#alert").fadeIn().animate({opacity: '+=0'}, 3000).fadeOut('fast');
    				return false;
    			}
    		}else {
    			//GQID为空
    			$("#alert").text("您输入的GQ账号不正确，GQ账号为6位纯数字！");
    			$("#alert").fadeIn().animate({opacity: '+=0'}, 3000).fadeOut('fast');
    			return false;
    		}
    	});
    });
    </script>
    
    <!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title" id="myModalLabel">GQ商家基础信息</h4>
	      </div>
	      <div class="modal-body">
	      	<div class="table-responsive">
				<table class="table font-size-12">
					<tr>
						<td class="th">商家名称:</td>
						<td class="ta-l"><span id="infoProviderCompanyName"></span></td>

						<td class="th">服务类型:</td>
						<td class="ta-l"><span id="infoProviderType"></span></td>

						<td class="th">电话:</td>
						<td class="ta-l"><span id="infoProviderPhone"></span></td>
					</tr>
					<tr>
						<td class="th">邮编:</td>
						<td class="ta-l"><span id="infoProviderCompanyMailNum"></span></td>

						<td class="th">地址:</td>
						<td class="ta-l"><span id="infoProviderCompanyAddress"></span></td>

						<td class="th">许可证:</td>
						<td class="ta-l"><span id="infoProviderCompanyBLN"></span></td>
					</tr>
					<tr>
						<td class="th">商家简介</td>
						<td colspan="5" class="ta-l"><span id="infoProviderCompanyDesc"></span></td>
					</tr>
				</table>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="rechargeDoSubmit">确定充值</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
<%@ include file="./inc/footer.jsp" %>