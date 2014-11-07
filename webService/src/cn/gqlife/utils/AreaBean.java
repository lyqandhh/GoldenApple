package cn.gqlife.utils;

import java.util.List;

public class AreaBean {
	
	private String areaId; 
	private String areaName;
	private List<CityBean> cityBeanList;
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public List<CityBean> getCityBeanList() {
		return cityBeanList;
	}
	public void setCityBeanList(List<CityBean> cityBeanList) {
		this.cityBeanList = cityBeanList;
	}


}
