package cn.gqlife.service.impl;

import java.util.List;

import cn.gqlife.dao.ImageDAO;
import cn.gqlife.entity.Image;
import cn.gqlife.service.ImageService;

public class ImageServiceImpl implements ImageService {
	ImageDAO imageDAO;
	
	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}
	@Override
	public List<Image> findImageList(String parentId, String parentType, String imageType) {
		// TODO Auto-generated method stub
		return imageDAO.findImageList(parentId, parentType, imageType);
	}
	@Override
	public boolean setImageHead(int parentId, int parentType, int imageType, String headPath) {
		// TODO Auto-generated method stub
		return imageDAO.setImageHead(parentId, parentType, imageType, headPath);
	}

}
