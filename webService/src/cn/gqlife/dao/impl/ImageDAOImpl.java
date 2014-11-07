package cn.gqlife.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gqlife.dao.ImageDAO;
import cn.gqlife.entity.Config;
import cn.gqlife.entity.Image;

public class ImageDAOImpl implements ImageDAO {

	@Override
	public List<Image> findImageList(String parentId, String parentType, String imageType) {
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
			String conditions = "";
			String head = "select *   from image  where 1=1 ";
			conditions += " and parentId = " + (parentId == null ? "" : parentId);
			conditions += " and parentType = " + (parentType == null ? "" : parentType);
			conditions += " and imageType = " + (imageType == null ? "" : imageType);
			String order = " order by id desc ";
			rs = stmt.executeQuery(head + conditions + order);
			System.out.println(head + conditions + order);
			List<Image> imageList = new ArrayList<Image>();
			while (rs.next()) {
				Image image = new Image();
				image.setId(rs.getInt("id"));
				image.setImagePath(rs.getString("imagePath"));
				image.setImageType(rs.getInt("imageType"));
				image.setParentId(rs.getInt("parentId"));
				image.setParentType(rs.getInt("parentType"));
				imageList.add(image);
			}
			rs.close();
			stmt.close();
			c.close();
			return imageList;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}

	@Override
	public boolean setImageHead(int parentId, int parentType, int imageType, String headPath) {
		Connection conn = null;
		java.sql.PreparedStatement pstat = null;
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		boolean bool = true;
		try {
			conn = DriverManager.getConnection(config.getJdbcDriverUrl());
			String sql = "update image set imagePath=? where imageType=? and parentId=? and parentType=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, headPath);
			pstat.setInt(2, imageType);
			pstat.setInt(3, parentId);
			pstat.setInt(4, parentType);
			pstat.execute();
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
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

}
