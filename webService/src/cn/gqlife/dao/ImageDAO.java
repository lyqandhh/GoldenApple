package cn.gqlife.dao;

import java.util.List;

import cn.gqlife.entity.Image;

public interface ImageDAO {
	/**
	 * 
	 * @param parentId 图片关联对象ID 
	 * @param parentType 图片关联对象类别[1:医生,2：普通用户]
	 * @param imageType 图片种类[1:头像,2:身份证,3:身份证背面,4:医生职业照,5:医生职业照背面]
	 * @return
	 */
	public List<Image> findImageList(String parentId,String parentType,String imageType);
	public boolean setImageHead(int parentId,int parentType,int imageType,String headPath);
}
