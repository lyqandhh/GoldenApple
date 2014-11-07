package cn.gqlife.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.gqlife.entity.Doctor;
import cn.gqlife.entity.DoctorDetail;
import cn.gqlife.service.DoctorService;

@Controller
@RequestMapping("/manage")
public class ManageController {
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView index() {
		return new ModelAndView();
	}

	/**
	 * 获得 医生管理数据
	 * @param request
	 * @param response
	 * @return 医生管理页面 doctor_list.jsp
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("doctor_list.do")
	protected String manage(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		DoctorService doctorService = (DoctorService) ac.getBean("doctorService");
		request.setAttribute("list", doctorService.getDoctorList(request));
		request.setAttribute("menuActive", 1);
		return "/doctor_list";
	}
	/**
	 * AJAX获得医生列表
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("doctor_list_ajax.do")
	protected void manageAjax(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		DoctorService doctorService = (DoctorService) ac.getBean("doctorService");
		String doctorType = request.getParameter("doctorType");
		request.setAttribute("menuActive", 1);
		net.sf.json.JSONArray jsonArray = new JSONArray();
		if (doctorType == null || "".equals(doctorType)) {
			jsonArray = net.sf.json.JSONArray.fromObject(doctorService.getDoctorList(request));
		} else {
			jsonArray = net.sf.json.JSONArray.fromObject(doctorService.getDoctorListByType(request, doctorType));
		}
		PrintWriter out = null;
		response.setContentType("application/json");
		try {
			System.out.println(jsonArray.toString());
			out = response.getWriter();
			out.write(jsonArray.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 设置账户冻结
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("setDoctorAudit.do")
	protected void setDoctorAudit(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		DoctorService doctorService = (DoctorService) ac.getBean("doctorService");
		String result="";
		result=doctorService.setDoctorAudit(request);
		result="{"+"\"result\":\""+result+"\"}";
		response.setContentType("application/json");
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 设置解冻账户
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("setDoctorEnable.do")
	protected void setDoctorEnable(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		DoctorService doctorService = (DoctorService) ac.getBean("doctorService");
		String result="";
		result=doctorService.setEnabled(request);
		result="{"+"\"result\":\""+result+"\"}";
		response.setContentType("application/json");
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 修改详细页面跳转
	 * @return
	 */
	@RequestMapping("pageDetail.do")
	protected String pageDetail(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("menuActive", 1);
		return "/detail";
	}
	/**
	 * 获得医生对象
	 * @return
	 */
	@RequestMapping("getObjectByDoctor.do")
	protected String getObjectByDoctor(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("menuActive", 1);
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		DoctorService doctorService = (DoctorService) ac.getBean("doctorService");
		Doctor doctor= doctorService.findById(request);
		DoctorDetail doctorDetail= doctorService.findDetailById(request);
		request.setAttribute("doctor", doctor);
		request.setAttribute("doctorDetail",doctorDetail);
		return "/iframe/basicInformation";
	}
}
