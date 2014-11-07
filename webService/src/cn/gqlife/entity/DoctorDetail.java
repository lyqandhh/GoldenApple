package cn.gqlife.entity;

public class DoctorDetail {
	private int id;
	private int doctor_id;
	private String school;
	private String education;
	private String job_title;
	private String serviceConcept;
	private String good;
	private String serviceIntroduce;
	private String edu_background;
	private String learning_experience;
	private String research;
	private String cell;
	private String tel;
	private String mail;
	private String qq;
	private String location;
	private String address;
	private String zip;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public String getServiceConcept() {
		return serviceConcept;
	}
	public void setServiceConcept(String serviceConcept) {
		this.serviceConcept = serviceConcept;
	}
	public String getGood() {
		return good;
	}
	public void setGood(String good) {
		this.good = good;
	}
	public String getServiceIntroduce() {
		return serviceIntroduce;
	}
	public void setServiceIntroduce(String serviceIntroduce) {
		this.serviceIntroduce = serviceIntroduce;
	}
	public String getEdu_background() {
		return edu_background;
	}
	public void setEdu_background(String edu_background) {
		this.edu_background = edu_background;
	}
	public String getLearning_experience() {
		return learning_experience;
	}
	public void setLearning_experience(String learning_experience) {
		this.learning_experience = learning_experience;
	}
	public String getResearch() {
		return research;
	}
	public void setResearch(String research) {
		this.research = research;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public DoctorDetail(int id, int doctor_id, String school, String education, String job_title, String serviceConcept, String good, String serviceIntroduce,
			String edu_background, String learning_experience, String research, String cell, String tel, String mail, String qq, String location,
			String address, String zip) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.school = school;
		this.education = education;
		this.job_title = job_title;
		this.serviceConcept = serviceConcept;
		this.good = good;
		this.serviceIntroduce = serviceIntroduce;
		this.edu_background = edu_background;
		this.learning_experience = learning_experience;
		this.research = research;
		this.cell = cell;
		this.tel = tel;
		this.mail = mail;
		this.qq = qq;
		this.location = location;
		this.address = address;
		this.zip = zip;
	}
	public DoctorDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
