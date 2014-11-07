package cn.gqlife.entity;

public class Image {
	private int id;
	private int imageType;
	private String imagePath;
	private int parentId;
	private int parentType;

	public Image(int id, int imageType, String imagePath, int parentId, int parentType) {
		super();
		this.id = id;
		this.imageType = imageType;
		this.imagePath = imagePath;
		this.parentId = parentId;
		this.parentType = parentType;
	}
	public int getParentType() {
		return parentType;
	}

	public void setParentType(int parentType) {
		this.parentType = parentType;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getImageType() {
		return imageType;
	}
	public void setImageType(int imageType) {
		this.imageType = imageType;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
