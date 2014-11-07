package cn.gqlife.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gqlife.dao.DoctorDAO;
import cn.gqlife.entity.Config;
import cn.gqlife.entity.Doctor;
import cn.gqlife.entity.DoctorCount;
import cn.gqlife.entity.DoctorDetail;

public class DoctorDAOImpl implements DoctorDAO {

	@Override
	public List<Doctor> getDoctorList(HttpServletRequest request) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		String _query = request.getParameter("query");
		request.setAttribute("query", _query);
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(config.getJdbcDriverUrl());
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String conditions="";
			String head="select *,(select enabled from users  where  username = d.loginId ) as enabled  from doctors as d where 1=1 ";
			if(null!=_query&&!"".equals(_query)){
				conditions+=" and name like "+"\"%"+_query+"%\"";
				conditions+=" or appleCount like "+"\"%"+_query+"%\"";
				conditions+=" or doctorsNum like "+"\"%"+_query+"%\"";
			}
			String order=" order by addTime desc limit 20";
			rs = stmt.executeQuery(head+conditions+order);
			System.out.println(head+conditions+order);
			List<Doctor> doctorList = new ArrayList<Doctor>();
			while (rs.next()) {
				Doctor doctor = new Doctor();
				doctor.setId(rs.getInt("id"));
				doctor.setEnabled(rs.getString("enabled"));
				doctor.setLoginId(rs.getString("loginId"));
				doctor.setAppleCount(rs.getInt("appleCount"));
				doctor.setType(rs.getShort("type"));
				doctor.setName(rs.getString("name"));
				doctor.setSex(rs.getString("sex"));
				doctor.setBirthday(rs.getDate("birthday"));
				doctor.setHospital(rs.getString("hospital"));
				doctor.setDepartments(rs.getString("departments"));
				doctor.setLevel(rs.getShort("level"));
				doctor.setDoctorsNum(rs.getLong("doctorsNum"));
				doctor.setTel(rs.getString("tel"));
				doctor.setStatus(rs.getShort("status"));
				doctor.setAddTime(rs.getTimestamp("addTime"));
				doctor.setUpdateTime(rs.getTimestamp("updateTime"));
				doctorList.add(doctor);
			}
			rs.close();
			stmt.close();
			c.close();
			return doctorList;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}
	@Override
	public List<Doctor> getDoctorListByAll(HttpServletRequest request) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		Map<String, String> _query = new HashMap<String, String>();
		_query.put("queryType", request.getParameter("queryVisable"));
		_query.put("queryDoctorName", request.getParameter("inputDoctorName"));
		_query.put("queryAppleCount", request.getParameter("inputAppleCount"));
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(config.getJdbcDriverUrl());
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String conditions="";
			String head="select *,(select enabled from users  where  username = d.loginId ) as enabled from doctors as d where 1=1 ";
			if(null!=_query.get("queryDoctorName")&&!"".equals(_query.get("queryDoctorName"))){
				conditions+=" and name like "+"\"%"+_query.get("queryDoctorName")+"%\"";
			}
			if(null!=_query.get("queryAppleCount")&&!"".equals(_query.get("queryAppleCount"))){
				conditions+=" and appleCount like "+"\"%"+_query.get("queryAppleCount")+"%\"";
			}
			if(null!=_query.get("queryType")&&!"".equals(_query.get("queryType"))){
				conditions+=" and status = "+_query.get("queryType");
			}
			String order=" order by addTime desc limit 20";
			rs = stmt.executeQuery(head+conditions+order);
			System.out.println(head+conditions+order);
			List<Doctor> doctorList = new ArrayList<Doctor>();
			while (rs.next()) {
				Doctor doctor = new Doctor();
				doctor.setId(rs.getInt("id"));
				doctor.setEnabled(rs.getString("enabled"));
				doctor.setLoginId(rs.getString("loginId"));
				doctor.setAppleCount(rs.getInt("appleCount"));
				doctor.setType(rs.getShort("type"));
				doctor.setName(rs.getString("name"));
				doctor.setSex(rs.getString("sex"));
				doctor.setBirthday(rs.getDate("birthday"));
				doctor.setHospital(rs.getString("hospital"));
				doctor.setDepartments(rs.getString("departments"));
				doctor.setLevel(rs.getShort("level"));
				doctor.setDoctorsNum(rs.getLong("doctorsNum"));
				doctor.setTel(rs.getString("tel"));
				doctor.setStatus(rs.getShort("status"));
				doctor.setAddTime(rs.getTimestamp("addTime"));
				doctor.setUpdateTime(rs.getTimestamp("updateTime"));
				doctorList.add(doctor);
			}
			rs.close();
			stmt.close();
			c.close();
			return doctorList;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}

	@Override
	public boolean save(Doctor doctor) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<DoctorCount> getDoctorCount(HttpServletRequest request,List<Integer> type) {
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
			String types="";
			for (int i = 0; i < type.size(); i++) {
				types+=type.get(i)+",";
			}
			types+="0";
			String sql="select  type ,count(id) from doctors where type in ("+types+") group by type";
			rs = stmt.executeQuery(sql);
			System.out.println(sql);
			List<DoctorCount> tempList = new ArrayList<DoctorCount>();
			while (rs.next()) {
				DoctorCount doctorCount=new DoctorCount();
				doctorCount.setType(rs.getInt(1));
				doctorCount.setCount(rs.getInt(2));
				tempList.add(doctorCount);
			}
			rs.close();
			stmt.close();
			c.close();
			return tempList;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}
	@Override
	public int getDoctorCount(String query) {
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
			String conditions="";
			String head="select count(id) from doctors where 1=1 ";
			conditions+=" and type = "+query;
			String order=" ";
			rs = stmt.executeQuery(head+conditions+order);
			System.out.println(head+conditions+order);
			int result=0;
			while (rs.next()) {
				result=rs.getInt(1);
			}
			rs.close();
			stmt.close();
			c.close();
			return result;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return 0;
	}
	@Override
	public List<Doctor> getDoctorListByType(HttpServletRequest request,String type) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		String _query = request.getParameter("query");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(config.getJdbcDriverUrl());
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String conditions="";
			String head="select *,(select enabled from users  where  username = d.loginId ) as enabled from doctors as d  where 1=1 ";
			conditions+=" and type = "+type;
			if(null!=_query&&!"".equals(_query)){
				conditions+=" and name like "+"\"%"+_query+"%\"";
				conditions+=" or appleCount like "+"\"%"+_query+"%\"";
				conditions+=" or doctorsNum like "+"\"%"+_query+"%\"";
			}
			String order=" order by addTime desc limit 20";
			rs = stmt.executeQuery(head+conditions+order);
			System.out.println(head+conditions+order);
			List<Doctor> doctorList = new ArrayList<Doctor>();
			while (rs.next()) {
				Doctor doctor = new Doctor();
				doctor.setId(rs.getInt("id"));
				doctor.setEnabled(rs.getString("enabled"));
				doctor.setLoginId(rs.getString("loginId"));
				doctor.setAppleCount(rs.getInt("appleCount"));
				doctor.setType(rs.getShort("type"));
				doctor.setName(rs.getString("name"));
				doctor.setSex(rs.getString("sex"));
				doctor.setBirthday(rs.getDate("birthday"));
				doctor.setHospital(rs.getString("hospital"));
				doctor.setDepartments(rs.getString("departments"));
				doctor.setLevel(rs.getShort("level"));
				doctor.setDoctorsNum(rs.getLong("doctorsNum"));
				doctor.setTel(rs.getString("tel"));
				doctor.setStatus(rs.getShort("status"));
				doctor.setAddTime(rs.getTimestamp("addTime"));
				doctor.setUpdateTime(rs.getTimestamp("updateTime"));
				doctorList.add(doctor);
			}
			rs.close();
			stmt.close();
			c.close();
			return doctorList;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}
	@Override
	public Doctor findById(HttpServletRequest request) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		String _query = request.getParameter("query");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(config.getJdbcDriverUrl());
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String conditions="";
			String head="select * ,(select enabled from users  where  username = d.loginId ) as enabled from doctors as d where 1=1 ";
			conditions+=" and id = "+_query;
			String order=" ";
			rs = stmt.executeQuery(head+conditions+order);
			System.out.println(head+conditions+order);
			Doctor doctor = new Doctor();
			while (rs.next()) {
				doctor.setId(rs.getInt("id"));
				doctor.setEnabled(rs.getString("enabled"));
				doctor.setLoginId(rs.getString("loginId"));
				doctor.setAppleCount(rs.getInt("appleCount"));
				doctor.setType(rs.getShort("type"));
				doctor.setName(rs.getString("name"));
				doctor.setSex(rs.getString("sex"));
				doctor.setBirthday(rs.getDate("birthday"));
				doctor.setHospital(rs.getString("hospital"));
				doctor.setDepartments(rs.getString("departments"));
				doctor.setLevel(rs.getShort("level"));
				doctor.setDoctorsNum(rs.getLong("doctorsNum"));
				doctor.setTel(rs.getString("tel"));
				doctor.setStatus(rs.getShort("status"));
				doctor.setAddTime(rs.getTimestamp("addTime"));
				doctor.setUpdateTime(rs.getTimestamp("updateTime"));
			}
			rs.close();
			stmt.close();
			c.close();
			return doctor;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}
	@Override
	public String setDoctorAudit(HttpServletRequest request) {
		short status=Short.valueOf(request.getParameter("status"));
		int id=Integer.valueOf(request.getParameter("id"));
		Connection conn = null;
		java.sql.PreparedStatement pstat = null;
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		String result = "success";
		try {
			conn =DriverManager.getConnection(config.getJdbcDriverUrl());
			String sql = "update doctors set status=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setShort(1, status);
			pstat.setInt(2, id);
			pstat.execute();
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		} finally {
			try {
				pstat.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return result;
	}
	@Override
	public String setEnabled(HttpServletRequest request) {
		String enabled=request.getParameter("enabled");
		String loginId=request.getParameter("loginId");
		Connection conn = null;
		java.sql.PreparedStatement pstat = null;
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		String result = "success";
		try {
			conn =DriverManager.getConnection(config.getJdbcDriverUrl());
			String sql = "update users set enabled=? where username=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, enabled);
			pstat.setString(2, loginId);
			pstat.execute();
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		} finally {
			try {
				pstat.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return result;
	}
	@Override
	public DoctorDetail findDetailById(HttpServletRequest request) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Config config = (Config) ac.getBean("config");
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		String _query = request.getParameter("query");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(config.getJdbcDriverUrl());
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String conditions="";
			String head="select *  from doctor_detail as d where 1=1 ";
			conditions+=" and id = "+_query;
			String order=" ";
			rs = stmt.executeQuery(head+conditions+order);
			System.out.println(head+conditions+order);
			DoctorDetail doctorDetail = new DoctorDetail();
			while (rs.next()) {
				doctorDetail.setId(rs.getInt("id"));
				doctorDetail.setDoctor_id(rs.getInt("doctor_id"));
				doctorDetail.setSchool(rs.getString("school"));
				doctorDetail.setEducation(rs.getString("education"));
				doctorDetail.setJob_title(rs.getString("job_title"));
				doctorDetail.setServiceConcept(rs.getString("serviceConcept"));
				doctorDetail.setGood(rs.getString("good"));
				doctorDetail.setServiceIntroduce(rs.getString("serviceIntroduce"));
				doctorDetail.setEdu_background(rs.getString("edu_background"));
				doctorDetail.setLearning_experience(rs.getString("learning_experience"));
				doctorDetail.setResearch(rs.getString("research"));
				doctorDetail.setCell(rs.getString("cell"));
				doctorDetail.setTel(rs.getString("tel"));
				doctorDetail.setMail(rs.getString("mail"));
				doctorDetail.setQq(rs.getString("qq"));
				doctorDetail.setLocation(rs.getString("location"));
				doctorDetail.setAddress(rs.getString("address"));
				doctorDetail.setZip(rs.getString("zip"));
			}
			rs.close();
			stmt.close();
			c.close();
			return doctorDetail;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}

}
