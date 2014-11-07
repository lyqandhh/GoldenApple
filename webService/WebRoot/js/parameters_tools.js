var tools = {
	'doctorType' : function(doctorType,doctorLevel) {
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
		return [doctorType_CN,doctorLevel_CN];
	},
	'doctorLevel' : function(doctorLevel) {
		alert(doctorLevel);
	},
	'doctorStatus' : function(doctorLevel,doctorStatus) {
		alert(doctorLevel+"|"+doctorStatus);
	}
};
