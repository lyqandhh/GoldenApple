package cn.gqlife.dao.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.context.SecurityContextImpl;

import cn.gqlife.dao.UserDAO;
import cn.gqlife.entity.Config;

public class UserDAOImpl implements UserDAO{
	@Override
	public boolean setPassword(HttpServletRequest request) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		String newPassword = request.getParameter("new");
		if (newPassword == null) {
			// 没有新密码
			return false;
		} else if (newPassword.length() < 6) {
			// 长度少于6
			return false;
		}

		MessageDigest messageDigest;
		String hashedPass = "";
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(newPassword.getBytes(), 0, newPassword.length());
			hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
			if (hashedPass.length() < 32) {
				hashedPass = "0" + hashedPass;
			}
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(config.getJdbcDriverUrl());
			c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.executeUpdate("update users set password = '" + hashedPass + "' where username	= '" + securityContextImpl.getAuthentication().getName() + "'");
			c.commit();
			stmt.close();
			c.close();
			return true;
		} catch (Exception e) {
			System.exit(0);
		}
		return false;
	}

}
