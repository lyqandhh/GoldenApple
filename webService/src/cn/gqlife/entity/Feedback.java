package cn.gqlife.entity;

import java.sql.Timestamp;

public class Feedback {
	private int id;
	private String feedback_product;
	private String feedback_problem;
	private String feedback_email;
	private String feedback_file_path;
	private String feedback_file_name;
	private Timestamp feedback_add_time;

	public Timestamp getFeedback_add_time() {
		return feedback_add_time;
	}

	public void setFeedback_add_time(Timestamp feedback_add_time) {
		this.feedback_add_time = feedback_add_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFeedback_product(boolean type) {
		if (!type) {
			if (feedback_product.equals("GQ门户、GQ空间")) {
				return "1";
			} else if (feedback_product.equals("GQ移动端")) {
				return "2";
			} else if (feedback_product.equals("GQ行业平台")) {
				return "3";
			}
		}
		return feedback_product == null ? "" : feedback_product;
	}
	public String getFeedback_product() {
		if (feedback_product.equals("1")) {
			return "GQ门户、GQ空间";
		} else if (feedback_product.equals("2")) {
			return "GQ移动端";
		} else if (feedback_product.equals("3")) {
			return "GQ行业平台";
		}
		return feedback_product == null ? "" : feedback_product;
	}
	public String getFeedback_file_name() {
		return feedback_file_name == null ? "" : feedback_file_name;
	}

	public void setFeedback_file_name(String feedback_file_name) {
		this.feedback_file_name = feedback_file_name;
	}
	public void setFeedback_product(String feedback_product) {
		this.feedback_product = feedback_product;
	}
	public String getFeedback_problem() {
		return feedback_problem == null ? "" : feedback_problem;
	}

	public void setFeedback_problem(String feedback_problem) {
		this.feedback_problem = feedback_problem;
	}

	public String getFeedback_email() {
		return feedback_email == null ? "" : feedback_email;
	}

	public void setFeedback_email(String feedback_email) {
		this.feedback_email = feedback_email;
	}

	public String getFeedback_file_path() {
		return feedback_file_path;
	}

	public void setFeedback_file_path(String feedback_file_path) {
		this.feedback_file_path = feedback_file_path == null ? "" : feedback_file_path;
	}

	public Feedback(int id, String feedback_product, String feedback_problem, String feedback_email, String feedback_file_path, String feedback_file_name,
			Timestamp feedback_add_time) {
		super();
		this.id = id;
		this.feedback_product = feedback_product;
		this.feedback_problem = feedback_problem;
		this.feedback_email = feedback_email;
		this.feedback_file_path = feedback_file_path;
		this.feedback_file_name = feedback_file_name;
		this.feedback_add_time = feedback_add_time;
	}

	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}

}
