package cn.gqlife.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gqlife.dao.FeedbackDAO;
import cn.gqlife.entity.Config;
import cn.gqlife.entity.Feedback;
import cn.gqlife.utils.GetID;

public class FeedbackDAOImpl implements FeedbackDAO {

	@Override
	public boolean save(Feedback feedback) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		Connection conn = null;
		PreparedStatement pstat = null;
		boolean bool = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(config.getJdbcDriverUrl());
			conn.setAutoCommit(false);
			String sql = "insert into feedback(id,feedback_product,feedback_problem,feedback_email,feedback_file_path,feedback_file_name,feedback_add_time) values(?,?,?,?,?,?,?)";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, GetID.getID("feedback", "id"));
			pstat.setShort(2, Short.parseShort(feedback.getFeedback_product(false)));
			pstat.setString(3, feedback.getFeedback_problem());
			pstat.setString(4, feedback.getFeedback_email());
			pstat.setString(5, feedback.getFeedback_file_path());
			pstat.setString(6, feedback.getFeedback_file_name());
			pstat.setTimestamp(7, feedback.getFeedback_add_time());
			pstat.execute();
		} catch (Exception e) {
			bool = false;
			e.printStackTrace();
		} finally {
			try {
				pstat.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return bool;
	}

	@Override
	public List<Feedback> getFeedbackList(HttpServletRequest request) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		String searchType= request.getParameter("queryVisable");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(config.getJdbcDriverUrl());
			c.setAutoCommit(false);
			stmt = c.createStatement();
			rs = stmt.executeQuery("select * from feedback where feedback_product=\'"+searchType+"\' order by id desc limit 20");
			List<Feedback> FeedbackList = new ArrayList<Feedback>();
			while (rs.next()) {
				Feedback feedback = new Feedback();
				feedback.setId(rs.getInt("id"));
				feedback.setFeedback_product(String.valueOf(rs.getShort("feedback_product")));
				feedback.setFeedback_problem(rs.getString("feedback_problem"));
				feedback.setFeedback_file_path(rs.getString("feedback_file_path"));
				feedback.setFeedback_file_name(rs.getString("feedback_file_name"));
				feedback.setFeedback_email(rs.getString("feedback_email"));
				feedback.setFeedback_add_time(rs.getTimestamp("feedback_add_time"));
				FeedbackList.add(feedback);
			}
			rs.close();
			stmt.close();
			c.close();
			return FeedbackList;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}

	@Override
	public List<Feedback> getFeedbackListByAll(HttpServletRequest request) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(config.getJdbcDriverUrl());
			c.setAutoCommit(false);
			stmt = c.createStatement();
			rs = stmt.executeQuery("select * from feedback  order by id desc limit 20");
			List<Feedback> FeedbackList = new ArrayList<Feedback>();
			while (rs.next()) {
				Feedback feedback = new Feedback();
				feedback.setId(rs.getInt("id"));
				feedback.setFeedback_product(String.valueOf(rs.getShort("feedback_product")));
				feedback.setFeedback_problem(rs.getString("feedback_problem"));
				feedback.setFeedback_file_path(rs.getString("feedback_file_path"));
				feedback.setFeedback_file_name(rs.getString("feedback_file_name"));
				feedback.setFeedback_email(rs.getString("feedback_email"));
				feedback.setFeedback_add_time(rs.getTimestamp("feedback_add_time"));
				FeedbackList.add(feedback);
			}
			rs.close();
			stmt.close();
			c.close();
			return FeedbackList;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}

}
