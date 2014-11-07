package cn.gqlife.entity;

public class DoctorCount {
	private int type;
	private int count;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public DoctorCount(int type, int count) {
		super();
		this.type = type;
		this.count = count;
	}
	public DoctorCount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
