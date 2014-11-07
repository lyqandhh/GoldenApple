package cn.gqlife.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.gqlife.entity.Feedback;

public interface FeedbackService {
	public abstract String save(Feedback feedback);

	public abstract Map<String, Object> upload(HttpServletRequest request);

	public abstract List<Feedback> getFeedbackList(String queryType, HttpServletRequest request);
}
