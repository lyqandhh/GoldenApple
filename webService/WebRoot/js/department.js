//下拉框加载全国所有地区数据
function getAllDepartment(){
	if(document.getElementById('department') == null || document.getElementById('disease') == null){
		alert("地区加载失败");
		return;
	}
	var department = document.getElementById('department');
	var disease = document.getElementById("disease");
	var departmentIndex = 0;
	//各科室以及疾病数据
	var departmentList = [
			{"name":'妇产科', diseaseList:['月经','宫颈/盆腔科','流产','孕产期','阴道炎','避孕不孕备孕','肿瘤']},
			{"name":'儿科', diseaseList:['皮肤','治疗用药','发育','营养','感冒发烧','哺乳','预防']},
			{"name":'内科', diseaseList:['发烧感冒咳嗽','胸痛','肝胆脾','胃肠道','头晕头痛','肾/尿液','血液']},
			{"name":'皮肤性病科', diseaseList:['红色皮疹','癣/脚气','青春痘','性病','病毒感染','毛发汗腺','色素斑']},
			{"name":'男性泌尿科', diseaseList:['早泄','尿路/前列腺','包皮长','阳痿','阴茎短小','男性不育','结石']},
			{"name":'整形美容科', diseaseList:['拉双眼皮','隆胸','抽脂']},
			{"name":'骨科', diseaseList:['骨折','骨折','关节炎','腰背痛','运动损伤','足部问题','脱臼']},
			{"name":'心理科', diseaseList:['焦虑','精神失常','抑郁']},
			{"name":'心血管科', diseaseList:['冠心病','高血压','瓣膜病','高血脂','心律不齐','心脏衰竭','心肌炎']},
			{"name":'脑神经科', diseaseList:['脑神经科','癫痫','失眠']},
			{"name":'营养科', diseaseList:['减肥','增体重','孕期','糖尿病','小儿营养','胃炎','贫血']},
			{"name":'口腔科', diseaseList:['口腔溃疡','牙龈出血','龋齿']},
			{"name":'眼科', diseaseList:['近视远视散光','结膜炎','眼睑']},
			{"name":'耳鼻喉科', diseaseList:['过敏性鼻炎','咽炎','中耳炎']},
			{"name":'中医科', diseaseList:['脾肺肾','气血不足','脉象','上火','中药','手脚冰凉','舌苔']},
			{"name":'肿瘤科', diseaseList:['肺','乳腺','肝胆','淋巴瘤','胃','前列腺']},
			{"name":'内分泌科', diseaseList:['糖尿病','肥胖','发育','甲状腺','肾上腺','电解质紊乱','垂体']},
			{"name":'基因检测科', diseaseList:['肺','乳腺','肝胆','淋巴瘤','胃','前列腺']}
		];
		for (var i = 0; i < departmentList.length; i++) {
			var option = document.createElement("option");
			option.innerText = departmentList[i].name;
			department.add(option);
		};
		department.onchange = department.onselect = function() {
			disease.options.length = 0;
			var diseaseList = departmentList[this.selectedIndex].diseaseList;
			for (var i = 0; i < diseaseList.length; i++) {
				var option = document.createElement("option");
				option.innerText = diseaseList[i];
				disease.add(option);
			};
		}
		department.onchange();
}