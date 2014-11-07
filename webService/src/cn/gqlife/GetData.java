package cn.gqlife;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gqlife.entity.Image;
import cn.gqlife.service.DoctorService;
import cn.gqlife.service.ImageService;

@Controller
public class GetData {
	@RequestMapping("/getData.do")
	protected void getData(HttpServletRequest request,HttpServletResponse response) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		String action =request.getParameter("action");
		String dataName=request.getParameter("dataName");
		String query=request.getParameter("query");;
		Object data=null;
		String result="";
		if(action.equals("userCount")){
			DoctorService doctorService=(DoctorService) ac.getBean("doctorService");
			data=doctorService.getDoctorCount(query);
			result="{"+"\"dataName\":\""+dataName+"\",\"data\":\""+data+"\"}";
		}else if(action.equals("doctorObjet")){
			DoctorService doctorService=(DoctorService) ac.getBean("doctorService");
			result=JSONObject.fromObject(doctorService.findById(request)).toString();
		}else if(action.equals("doctorDetail")){
			DoctorService doctorService=(DoctorService) ac.getBean("doctorService");
			ImageService imageService = (ImageService) ac.getBean("imageService");
			Map<String, Object> tempMap=new HashedMap();
			tempMap.put("doctor", doctorService.findById(request));
			tempMap.put("doctorDetail", doctorService.findDetailById(request));
			tempMap.put("image", imageService.findImageList(query, "1", "1"));
			result=JSONObject.fromObject(tempMap).toString();
		}else if(action.equals("checkHead")){
			ImageService imageService = (ImageService) ac.getBean("imageService");
			Map<String, Object> tempMap=new HashedMap();
			List<Image> tempList=imageService.findImageList(query, "1", "1");
			tempMap.put("image", tempList);
			int isHead=1;
			if(tempList==null){
				isHead=0;
			}
			tempMap.put("isHead",isHead);
			result=JSONObject.fromObject(tempMap).toString();
		}
		System.out.println("GetData:"+result);
		response.setContentType("application/json");
		try {
			response.getWriter().write(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
