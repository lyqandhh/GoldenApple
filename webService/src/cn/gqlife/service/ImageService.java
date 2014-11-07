package cn.gqlife.service;

import java.util.List;

import cn.gqlife.entity.Image;

public interface ImageService {
	/**
	 * 
	 * @param parentId 图片关联对象ID 
	 * @param parentType 图片关联对象类别[1:医生,2：普通用户]
	 * @param imageType 图片种类[1:头像,2:身份证,3:身份证背面,4:医生职业照,5:医生职业照背面]
	 * @return
	 */
	public List<Image> findImageList(String parentId,String parentType,String imageType);
	/**
	 * 
	 * @param parentId 图片关联对象ID 
	 * @param parentType 图片关联对象类别[1:医生,2：普通用户]
	 * @param imageType 图片种类[1:头像,2:身份证,3:身份证背面,4:医生职业照,5:医生职业照背面]
	 * @param headPath 图片路径
	 * @return
	 */
	public boolean setImageHead(int parentId,int parentType,int imageType,String headPath);
}
