package cn.gqlife.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.gqlife.entity.Feedback;
import cn.gqlife.service.FeedbackService;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
	/**
	 * 保存意见反馈
	 * @param request
	 * @param response
	 * @return 意见反馈页面 feedback_form.jsp
	 */
	@RequestMapping("save.do")
	protected String feedbackSave(HttpServletRequest request, HttpServletResponse response) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		FeedbackService feedbackService = (FeedbackService) ac.getBean("feedbackService");
		Feedback feedback = new Feedback();
		String feedback_email=request.getParameter("feedback_email");
		feedback.setFeedback_email(feedback_email);
		Map<String, Object> fileMap = feedbackService.upload(request);
		feedback.setFeedback_file_path((String) fileMap.get("filePath"));
		feedback.setFeedback_file_name((String) fileMap.get("fileName"));
		feedback.setFeedback_problem(request.getParameter("feedback_problem"));
		feedback.setFeedback_product(request.getParameter("feedback_type"));
		feedback.setFeedback_add_time(new Timestamp(System.currentTimeMillis()));
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("saveStatus", feedbackService.save(feedback));
		if(!"".equals(feedback_email)){
			tempMap.put("feedback_email", feedback_email);
		}
		request.setAttribute("result", tempMap);
		
		return "/feedback_form";
	}
	/**
	 * 反馈信息列表
	 * @param request
	 * @param response
	 * @return 意见反馈列表页面 feedback.jsp
	 */
	@RequestMapping("feedbackList.do")
	protected String feedbackList(HttpServletRequest request, HttpServletResponse response){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		FeedbackService feedbackService = (FeedbackService) ac.getBean("feedbackService");
		String queryType=request.getParameter("queryVisable");
		request.setAttribute("query",queryType );
		request.setAttribute("list", feedbackService.getFeedbackList(queryType, request));
		return "/feedback";
	}
}
