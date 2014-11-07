package cn.gqlife.entity;

import java.sql.Timestamp;
import java.util.Date;

public class Doctor {
	private int id;
	private String loginId;
	private int appleCount;
	private short type;
	private String name;
	private String sex;
	private Date birthday;
	private String hospital;
	private String departments;
	private short level;
	private long doctorsNum;
	private String tel;
	private short status;
	private String headPath;
	private Timestamp addTime;
	private Timestamp updateTime;
	private String enabled;
	
	public Doctor(int id, String loginId, int appleCount, short type, String name, String sex, Date birthday, String hospital, String departments, short level,
			long doctorsNum, String tel, short status, String headPath, Timestamp addTime, Timestamp updateTime, String enabled) {
		super();
		this.id = id;
		this.loginId = loginId;
		this.appleCount = appleCount;
		this.type = type;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.hospital = hospital;
		this.departments = departments;
		this.level = level;
		this.doctorsNum = doctorsNum;
		this.tel = tel;
		this.status = status;
		this.headPath = headPath;
		this.addTime = addTime;
		this.updateTime = updateTime;
		this.enabled = enabled;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHeadPath() {
		return headPath;
	}
	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}
	public int getId() {
		return id;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAppleCount() {
		return appleCount;
	}
	public void setAppleCount(int appleCount) {
		this.appleCount = appleCount;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public String getDepartments() {
		return departments;
	}
	public void setDepartments(String departments) {
		this.departments = departments;
	}
	public short getLevel() {
		return level;
	}
	public void setLevel(short level) {
		this.level = level;
	}
	public long getDoctorsNum() {
		return doctorsNum;
	}
	public void setDoctorsNum(long doctorsNum) {
		this.doctorsNum = doctorsNum;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
