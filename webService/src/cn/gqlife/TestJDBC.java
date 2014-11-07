package cn.gqlife;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			System.out.println("测试");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("lyq121524.mysql.rds.aliyuncs.com:3306/golden_apple","lyq","lyq121524");
			System.out.println(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
