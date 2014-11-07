var realPath="..";
	//获得各个类型用户数量
	getData("userCount","userCount",1);
	$("#userCount").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
	getData("userCount","doctorCount",1);
	$("#doctorCount").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
	getData("userCount","pharmacistCount",2);
	$("#pharmacistCount").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
	getData("userCount","dietitiansCount",3);
	$("#dietitiansCount").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
	getData("userCount","healthCount",4);
	$("#healthCount").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
	getData("userCount","psychologyCount",5);
	$("#psychologyCount").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
	getData("userCount","beautyCount",6);
	$("#beautyCount").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
	getData("userCount","strongCount",7);
	$("#strongCount").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
	getData("userCount","baby",8);
	$("#baby").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
	
	//获得数据 通用方法
	function getData(action,dataName,query) {
		var data = {
			action: action,
			dataName : dataName,
			query : query
		};
		$.ajax({
			url : realPath+"/getData.do",
			type : "post",
			data : data,
			success : function(result) {
				if(action=="userCount"){
					if ($("#" + result.dataName) != null) {
						$("#" + result.dataName).html(result.data);
					}
				}else if(action=="doctorObjet"){
					$("#auditName").html(result.name);
					$("#auditAppleCount").html(result.appleCount);
					$("#auditType").html(tools.doctorType(result.type,result.level)[0]);
					$("#auditLevel").html(tools.doctorType(result.type,result.level)[1]);
					$("#auditHospital").html(result.hospital);
					$("#auditDepartments").html(result.departments);
					$("#auditDoctorNum").html(result.doctorsNum);
					$("#auditTel").html(result.tel);
					$("#auditId").val(result.id);
				}else if(action=="doctorDetail"){
					var doctor =result.doctor;
					var doctorDetail=result.doctorDetail;
					var imageList=result.image;
					$("#detailName").html(doctor.name);
					$("#detailAppleCount").html(doctor.appleCount);
					$("#detailSex").html(doctor.sex);
					$("#detailType").html(tools.doctorType(doctor.type,doctor.level)[0]);
					$("#detailLevel").html(tools.doctorType(doctor.type,doctor.level)[1]);
					$("#detailHospital").html(doctor.hospital);
					$("#detailDepartments").html(doctor.departments);
					$("#detailDoctorNum").html(doctor.doctorsNum);
					
					$("#detailSchool").html(doctorDetail.school);
					$("#detailEducation").html(doctorDetail.education);
					$("#detailJobTitle").html(doctorDetail.job_title);
					$("#detailServiceConcept").html(doctorDetail.serviceConcept);
					$("#detailGood").html(doctorDetail.good);
					$("#detailServiceIntroduce").html(doctorDetail.serviceIntroduce);
					$("#detailEduBackground").html(doctorDetail.edu_background);
					$("#detailLearningExperience").html(doctorDetail.learning_experience);
					$("#detailResearch").html(doctorDetail.research);
					$("#detailCell").html(doctorDetail.cell);
					$("#detailTel").html(doctorDetail.tel);
					$("#detailMail").html(doctorDetail.mail);
					$("#detailQQ").html(doctorDetail.qq);
					$("#detailLocation").html(doctorDetail.location);
					$("#detailAddress").html(doctorDetail.address);
					$("#detailZip").html(doctorDetail.zip);
					
					for ( var i = 0; i < imageList.length; i++) {
						if(imageList[i].imageType==1){
							$("#detailHeadImage").attr("src",realPath+imageList[i].imagePath);
						}
					};
				}
			}
		});
	}
	//获得审核信息
	function getAudit(name,id){
		$("#myModal").modal('toggle');
		$("#myModalLabel").html(name);
		
		$("#auditName").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#auditAppleCount").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#auditType").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#auditLevel").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#auditHospital").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#auditDepartments").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#auditDoctorNum").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#auditTel").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		getData("doctorObjet","id",id);
	}
	//获得详细信息
	function getDetail(name,id){
		$("#myDetail").modal('toggle');
		$("#detailModalLabel").html(name);
		$("#detailHeadImage").attr("src",realPath+"/img/manage/default.png")
		$("#detailName").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailAppleCount").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailSex").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailType").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailLevel").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailHospital").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailDepartments").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailDoctorNum").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		
		$("#detailSchool").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailEducation").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailJobTitle").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailServiceConcept").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailGood").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailServiceIntroduce").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailEduBackground").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailLearningExperience").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailResearch").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailCell").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailTel").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailMail").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailQQ").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailLocation").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailAddress").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		$("#detailZip").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading.gif\">");
		
		getData("doctorDetail","id",id);
	}
	
	//设置审核结果
	function setAudit(status){
		var auditId=$("#auditId").val();
		var data = {
			id: auditId,
			status : status,
		};
		$.ajax({
			url : realPath+"/manage/setDoctorAudit.do",
			type : "post",
			data : data,
			success : function(result) {
				if(result.result=="success"){
					$("#resultDiv_success").modal('toggle');
					$("#myModal").modal('toggle');
					queryAjax();
				}else {
					$("#resultDiv_error").modal('toggle');
				}
			}
		});
	}
	//设置冻结/解冻
	function setEnable(enabled,loginId){
		var data = {
			enabled: enabled,
			loginId : loginId,
		};
		$.ajax({
			url : realPath+"/manage/setDoctorEnable.do",
			type : "post",
			data : data,
			success : function(result) {
				if(result.result=="success"){
					$("#resultDiv_success").modal('toggle');
					queryAjax();
				}else {
					$("#resultDiv_error").modal('toggle');
				}
			}
		});
	}
	//或的选择Radio值（通用方法）
	function getRadioBoxValue(radioName, radiovalue) {
		var obj = document.getElementsByName(radioName);
		for (i = 0; i < obj.length; i++) {
			if (obj[i].value == radiovalue) {
				obj[i].checked = true;
			}
		}
		return true;
	}
	//ajax查询列表
	function queryAjax(doctorType){
		var queryType=$("#query").val();
		var data={
				query:queryType,
				doctorType:doctorType	
				};
		$("#queryTable").html("<img alt=\"加载中\" src=\""+realPath+"/img/loading_big.gif\">");
		$.ajax({
			url : realPath+"/manage/doctor_list_ajax.do",
			type : "post",
			data : data,
			success : function(result) {
				var tableHead="<table class=\"table table-hover table-condensed font-size-12\">";
				var tableMain="";
				for ( var i = 0; i < result.length; i++) {
					var doctorType=result[i].type;
					var doctorLevel=result[i].level;
					var doctorType_CN="";
					var doctorLevel_CN="";
					if(doctorType==1){
						doctorType_CN="医生";
						if(doctorLevel==1){
							doctorLevel_CN="主任医师";
						}else if(doctorLevel==2){
							doctorLevel_CN="副主任医师";
						}else if(doctorLevel==3){
							doctorLevel_CN="主治医师";
						}else if(doctorLevel==4){
							doctorLevel_CN="住院医师";
						}
					}else if(doctorType==2){
						doctorType_CN="执业医生";
						if(doctorLevel==1){
							doctorLevel_CN="高级(主任药师)";
						}else if(doctorLevel==2){
							doctorLevel_CN="副高级(副主任药师)";
						}else if(doctorLevel==3){
							doctorLevel_CN="中级(主治、主管药师)";
						}else if(doctorLevel==4){
							doctorLevel_CN="初级(药师)";
						}else if(doctorLevel==5){
							doctorLevel_CN="员级(药士)";
						}
					}else if(doctorType==3){
						doctorType_CN="心里咨询师";
						if(doctorLevel==1){
							doctorLevel_CN="心里咨询师一级";
						}else if(doctorLevel==2){
							doctorLevel_CN="心里咨询师二级";
						}else if(doctorLevel==3){
							doctorLevel_CN="心里咨询师三级";
						}
					}else if(doctorType==4){
						doctorType_CN="营养师";
						if(doctorLevel==1){
							doctorLevel_CN="高级营养师";
						}else if(doctorLevel==2){
							doctorLevel_CN="营养师";
						}else if(doctorLevel==3){
							doctorLevel_CN="助理营养师";
						}
					}else if(doctorType==5){
						doctorType_CN="健康管理师";
						if(doctorLevel==1){
							doctorLevel_CN="一级营养师(国家职业资格一级)";
						}else if(doctorLevel==2){
							doctorLevel_CN="二级营养师(国家职业资格二级)";
						}else if(doctorLevel==3){
							doctorLevel_CN="三级营养师(国家职业资格三级)";
						}
					}else if(doctorType==6){
						doctorType_CN="育婴师";
						if(doctorLevel==1){
							doctorLevel_CN="高级育婴师";
						}else if(doctorLevel==2){
							doctorLevel_CN="育婴师";
						}else if(doctorLevel==3){
							doctorLevel_CN="育婴员";
						}
					}else if(doctorType==7){
						doctorType_CN="健身教练";
						if(doctorLevel==1){
							doctorLevel_CN="指导师级健身员";
						}else if(doctorLevel==2){
							doctorLevel_CN="高级健身教练";
						}else if(doctorLevel==3){
							doctorLevel_CN="中级健身教练";
						}else if(doctorLevel==4){
							doctorLevel_CN="初级健身教练";
						}
					}else if(doctorType==8){
						doctorType_CN="美容师";
						if(doctorLevel==1){
							doctorLevel_CN="高级技师(国家职业资格一级)";
						}else if(doctorLevel==2){
							doctorLevel_CN="技师(国家职业资格二级)";
						}else if(doctorLevel==3){
							doctorLevel_CN="高级(国家职业资格三级)";
						}else if(doctorLevel==4){
							doctorLevel_CN="中级(国家职业资格四级)";
						}else if(doctorLevel==5){
							doctorLevel_CN="初级(国家职业资格五级)";
						}
					}
					tableMain+="<tr height=\"45px\" "+(result[i].enabled=='1'?"":"style=\"color:gray;text-decoration:line-through\"")+">";
					tableMain+="<td width=\"5%\" style=\"vertical-align:middle\">"+"<input type='radio' value='"+result[i].id+"' name='doctor_id' style='cursor: pointer;'>"+"</td>";
					tableMain+="<td width=\"9%\" style=\"vertical-align:middle\">"+result[i].appleCount+"</td>";
					tableMain+="<td width=\"9%\" style=\"vertical-align:middle\">"+doctorType_CN+"</td>";
					tableMain+="<td width=\"7%\" style=\"vertical-align:middle\">"+result[i].name+"</td>";
					tableMain+="<td width=\"15%\" style=\"vertical-align:middle\">"+result[i].hospital+"</td>";
					tableMain+="<td width=\"7%\" style=\"vertical-align:middle\">"+result[i].departments+"</td>";
					tableMain+="<td width=\"13%\" style=\"vertical-align:middle\">"+doctorLevel_CN+"</td>";
					tableMain+="<td width=\"12%\" style=\"vertical-align:middle\">"+result[i].doctorsNum+"</td>";
					tableMain+="<td width=\"9%\" style=\"vertical-align:middle\">"+result[i].tel+"</td>";
					tableMain+="<td width=\"7%\" style=\"vertical-align:middle\">"+(result[i].status==0?"尚未审核":result[i].status==1?"通过审核":"未通过审核")+"</td>";
					tableMain+="<td width=\"50px\" style=\"vertical-align:middle\">";
					tableMain+="<div class=\"dropdown\">";
					tableMain+="<img src='"+realPath+"/img/manage/computer.png' class=\"btn dropdown-toggle\" id=\"dropdownMenu1\" data-toggle=\"dropdown\"";
					tableMain+="onclick=\"getRadioBoxValue('doctor_id',"+result[i].id+")\"></img>";
					tableMain+="<ul class=\"dropdown-menu pull-right\" role=\"menu\" aria-labelledby=\"dropdownMenu1\">";
					if(result[i].status!="1"){
					tableMain+="<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"javaScript:getAudit('"+result[i].name+"',"+result[i].id+")\">审核</a></li>";
					}
					tableMain+="<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\""+realPath+"/manage/pageDetail.do?key="+result[i].id+"\">修改</a></li>";
					if(result[i].enabled=='1'){
						tableMain+="<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"javaScript:setEnable(0,'"+result[i].loginId+"');\">冻结</a></li>";
					}else if(result[i].enabled=='0'){
						tableMain+="<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"javaScript:setEnable(1,'"+result[i].loginId+"');\">解冻</a></li>";
					}
					tableMain+="<li role=\"presentation\" class=\"divider\"></li>";
					tableMain+="<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\""+realPath+"/detail.jsp"+"\">详细</a></li>";
					tableMain+="</ul></div></td>";
					tableMain+="</tr>";
				}
				var tableEnd="</table>";
				var html=tableHead+tableMain+tableEnd;
				$("#queryTable").html(html)
			}
		});
	}