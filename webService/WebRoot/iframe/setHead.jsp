<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<base href="<%=basePath%>">
<title>My JSP 'index.jsp' starting page</title>
<link rel="stylesheet" href="<%=path%>/css/jquery.Jcrop.min.css" type="text/css"></link>
<script type="text/javascript" src="<%=path%>/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.Jcrop.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<!-- Bootstrap core CSS -->
<link href="<%=request.getContextPath()%>/css/bootstrap.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/theme.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
<style type="text/css">
.crop_preview {
	top: 0;
	width: 140px;
	height: 140px;
	overflow: hidden;
}

.pd0 {
	padding-left: 0;
	padding-right: 0;
	padding-bottom: 15px;
}

.jcrop-holder img {
	max-width: none;
}
</style>
</head>
<body onload="checkHead('<%=key%>')">
	<h4>
		<span class="label label-default">设置头像</span>
	</h4>
	<hr />
	<div id=cutDiv class="row" style="display: none;width: 99%">
		<div class="col-md-7 col-xs-7 col-lg-7 col-sm-6 " align="center">
			<div class="panel panel-default">
				<div class="panel-body" id="ajaxSrcImg">
					<img id="srcImg" src="" />
				</div>
				<div class="panel-footer">上传图</div>
			</div>
			<form id=imageForm name=form1 method=post enctype="multipart/form-data">
				<input type="file" class="btn btn-primary" name="userPhoto" id="userPhoto" maxlength="160" onchange="checkImgType(this);" width="300px" />
			</form>
		</div>
		<div class="col-md-5 col-xs-5 col-lg-5 col-sm-6 " align="center">
			<div class="panel panel-default" style="margin-right: 5px">
				<div class="panel-body">
					<div id="preview_box" class="crop_preview">
						<img id="previewImg" src="" />
					</div>
				</div>
				<div class="panel-footer">效果图</div>
			</div>
			<div class="panel panel-default" style="margin-right: 5px">
				<div class="panel-body">
					<img id="nowImg" src="" style="width: 140px;height: 140px" />
				</div>
				<div class="panel-footer">当前头像</div>
			</div>
			<form action="<%=path%>/ImgCropServlet" method="post" name="crop_form">
				<input type="hidden" id="bigImage" name="bigImage" /> <input type="hidden" id="x" name="x" /> <input type="hidden" id="y" name="y" /> <input
					type="hidden" id="w" name="w" /> <input type="hidden" id="h" name="h" />
				<input type="hidden"  name="userId"  value="<%=key%>"/>
				<input type="hidden"  name="oldImgPath" id="oldImgPath">
				<P>
					<button type="button" class="btn btn-primary" style="margin-top: 5px;margin-left: 30px" onclick="imageCrop()">设置为头像</button>
				</P>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		var realPath='<%=path%>';
		function checkHead(id) {
			var data = {
				action : "checkHead",
				dataName : "checkHead",
				query : id
			};
			$.ajax({
				url : realPath + "/getData.do",
				type : "post",
				data : data,
				success : function(result) {
					if (result.isHead == 1) {
						if (result.image.length == 1) {
							$("#cutDiv").css("display", "block");
							var imagePath = realPath
									+ result.image[0].imagePath;
							$("#srcImg").attr("src", imagePath);
							$("#previewImg").attr("src", imagePath);
							$("#nowImg").attr("src", imagePath);
							cutImage();
						} else {
							$("#cutDiv").css("display", "block");
							var imagePath = realPath
									+ "/img/manage/default.png";
							$("#srcImg").attr("src", imagePath);
							$("#previewImg").attr("src", imagePath);
							$("#nowImg").attr("src", imagePath);
							cutImage();
						}
					}
				}
			});
		}
		/**检查图片上传类型*/
		function checkImgType(obj) {
			var file = $("#userPhoto").val();
			if (file == "") {
				alert("请选择上传的头像");
				return;
			} else {
				//判断上传的文件的格式是否正确
				var fileType = file.substring(file.lastIndexOf(".") + 1);
				if (fileType != "png" && fileType != "jpg") {
					alert("上传文件格式错误,请上传png、jpg格式图片");
					obj.outerHTML = obj.outerHTML;
					return;
				} else {
					var url = realPath + "/ImgUploadServlet";
					$.ajaxFileUpload({
						url : url,
						secureuri : false,
						fileElementId : "userPhoto", //file的id
						dataType : "json", //返回数据类型为文本
						success : function(data) {
							if (data.path == "1") {
								alert("请上传宽度小于580像素的图片");
							} else if (data == "2") {
								alert("请上传宽高比不超过2的图片");
							} else if (data == "3") {
								alert("请上传文件大小不大于2M的图片");
							} else {
								var html="<img id=\"srcImg\" src=\""+realPath+"/uploads/"+data.path+"\" />";
								$("#ajaxSrcImg").html(html);
								$("#previewImg").attr("src", realPath+"/uploads/"+data.path);
								$("#oldImgPath").val("/uploads/"+data.path);
								cutImage();	
							}
						}
					})
				}
			}
		}

		//裁剪图像
		function cutImage() {
			$("#srcImg").Jcrop({
				aspectRatio : 1,
				onChange : showCoords,
				onSelect : showCoords,
				minSize : [ 120, 120 ]
			});
			//简单的事件处理程序，响应自onChange,onSelect事件，按照上面的Jcrop调用
			function showCoords(obj) {
				if (parseInt(obj.w) > 0) {
					$("#x").val(obj.x);
					$("#y").val(obj.y);
					$("#w").val(obj.w);
					$("#h").val(obj.h);
					//计算预览区域图片缩放的比例，通过计算显示区域的宽度(与高度)与剪裁的宽度(与高度)之比得到
					var rx = $("#preview_box").width() / obj.w;
					var ry = $("#preview_box").height() / obj.h;
					//通过比例值控制图片的样式与显示
					$("#previewImg").css({
						width : Math.round(rx * $("#srcImg").width()) + "px", //预览图片宽度为计算比例值与原图片宽度的乘积
						height : Math.round(rx * $("#srcImg").height()) + "px", //预览图片高度为计算比例值与原图片高度的乘积
						marginLeft : "-" + Math.round(rx * obj.x) + "px",
						marginTop : "-" + Math.round(ry * obj.y) + "px"
					});
				}
			}
		}
		function imageCrop(){
			var oldImgPath=$("#oldImgPath").val();
			if(oldImgPath==""){
				alert("请先上传要剪裁的图片");
				return;
			}
			var x = $("#x").val();
			var y = $("#y").val();
			var w = $("#w").val();
			var h = $("#h").val();
			if (w == 0 || h == 0) {
				alert("您还没有选择图片的剪切区域,不能进行剪切图片!");
				return;
			}
			if (confirm("确定按照当前大小剪切图片吗,剪裁后效果为右上角效果图")) {
				document.crop_form.submit();
			}
		}
	</script>
</body>
</html>