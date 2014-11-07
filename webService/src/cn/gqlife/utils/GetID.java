package cn.gqlife.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gqlife.entity.Config;

public class GetID {
	/**
	 * 说明:获取主键的方法
	 * @param table
	 *            表名
	 * @param col
	 *            主键字段名
	 * @return
	 */
	public static int getID(String table, String col) throws Exception {
		ClassPathXmlApplicationContext  ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config)ac.getBean("config");
		Connection conn = null;
		Statement pstat = null;
		ResultSet res = null;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(config.getJdbcDriverUrl());
		String sql = "select max(" + col + ")+1 from " + table;
		pstat = conn.createStatement();
		res = pstat.executeQuery(sql);
		int i = 0;
		while (res.next()) {
			i = res.getInt(1);
			break;
		}
		res.close();
		pstat.close();
		conn.close();
		return i;
	}
}
