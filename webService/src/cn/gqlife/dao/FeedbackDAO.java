package cn.gqlife.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.gqlife.entity.Feedback;


public interface FeedbackDAO {
	public abstract boolean save(Feedback feedback);
	public abstract List<Feedback> getFeedbackList(HttpServletRequest request);
	public abstract List<Feedback> getFeedbackListByAll(HttpServletRequest request);
}
