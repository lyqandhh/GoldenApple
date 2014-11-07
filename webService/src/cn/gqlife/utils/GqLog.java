package cn.gqlife.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import cn.gqlife.entity.Config;

public class GqLog {

	/**
	 * 基础日志方法，默认当前时间当前帐号
	 * 
	 * @param title string		标题(可选字段)
	 * @param content string	日志内容
	 * @param destinations string 目标对象(可选字段)
	 * @param actionType string	操作类型：sim:模拟登录，push:推送（有扩展，如有增加请在此注明）
	 */
	public static void log(String title, String content,String destinations,String actionType) {
		ClassPathXmlApplicationContext  ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config)ac.getBean("config");
		String logTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
		String tempStr = getCurrentUser();
		String tempStrs[]=tempStr.split("\\|");
		String operator =tempStrs[0];
		String operatorName=tempStrs[1];
		Connection c = null;
		Statement stmt = null;
		if(StringUtils.isBlank(content) ||StringUtils.isBlank(actionType) ){
			throw new RuntimeException("内容，操作类型不能为空");
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(config.getJdbcDriverUrl());
			c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.executeUpdate("insert into gq_log values(null,'" + title
					+ "','" + content+ "','" + logTime + "','" + operator + "','" + operatorName  + "','"+ destinations + "','" + actionType + "');");
			c.commit();
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}
	
	public static String getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null)
			return null;
		return auth.getName();
	}
}
