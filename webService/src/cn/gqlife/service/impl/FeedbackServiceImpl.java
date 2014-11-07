package cn.gqlife.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.gqlife.dao.FeedbackDAO;
import cn.gqlife.entity.Feedback;
import cn.gqlife.service.FeedbackService;

public class FeedbackServiceImpl implements FeedbackService {
	FeedbackDAO feedbackDAO;

	public void setFeedbackDAO(FeedbackDAO feedbackDAO) {
		this.feedbackDAO = feedbackDAO;
	}

	@Override
	public String save(Feedback feedback) {
		if (feedback == null) {
			return "表单为空";
		} else if (feedback.getFeedback_product().equals("")) {
			return "反馈产品为空";
		} else if (feedback.getFeedback_problem().equals("")) {
			return "反馈问题为空";
		} else {
			return feedbackDAO.save(feedback) == true ? "保存成功" : "保存失败";
		}
	}

	public Map<String, Object> upload(HttpServletRequest request) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		String path = "";
		String realPath = "";
		Map<String, Object> tempMap = new HashedMap();
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multipartHttpServletRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multipartHttpServletRequest.getFile(iter.next());
				if (file.isEmpty()) {
					break;
				}
				String fileName = file.getOriginalFilename();
				String tempFileExt =fileName.substring(fileName.lastIndexOf(".") + 1);
				tempMap.put("fileName", fileName);
				long time = new Date().getTime();
				String filePath = request.getSession().getServletContext().getRealPath("/") + "file/" ;
				File files = new File(filePath);
				while (!files.exists()) {
					files.mkdirs();
				}
				realPath = "/file/" + time+"."+tempFileExt;
				path = filePath + time+"."+tempFileExt;
				tempMap.put("filePath", realPath);
				File localFile = new File(path);
				try {
					file.transferTo(localFile);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return tempMap;
	}
	@Override
	public List<Feedback> getFeedbackList(String queryType, HttpServletRequest request) {
		if (queryType == null || queryType.equals("0")) {
			return feedbackDAO.getFeedbackListByAll(request);
		} else {
			return feedbackDAO.getFeedbackList(request);
		}
	}
}
