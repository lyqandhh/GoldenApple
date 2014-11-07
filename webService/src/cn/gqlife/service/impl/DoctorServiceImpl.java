package cn.gqlife.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;

import cn.gqlife.dao.DoctorDAO;
import cn.gqlife.entity.Doctor;
import cn.gqlife.entity.DoctorCount;
import cn.gqlife.entity.DoctorDetail;
import cn.gqlife.service.DoctorService;

public class DoctorServiceImpl implements DoctorService {
	DoctorDAO doctorDAO;

	public void setDoctorDAO(DoctorDAO doctorDAO) {
		this.doctorDAO = doctorDAO;
	}

	@Override
	public boolean save(Doctor doctor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Doctor> getDoctorList(HttpServletRequest request) {
		return doctorDAO.getDoctorList(request);
	}

	@Override
	public Map<String, Integer> getDoctorCount(HttpServletRequest request) {
		Map<String, Integer> tempMap = new HashedMap();
		List<Integer> tempList = new ArrayList<Integer>();
		tempList.add(1);
		tempList.add(2);
		tempList.add(3);
		tempList.add(4);
		tempList.add(5);
		tempList.add(6);
		tempList.add(7);
		tempList.add(8);
		List<DoctorCount> list = doctorDAO.getDoctorCount(request, tempList);
		for (int i = 0; i < list.size(); i++) {
			 if (list.get(i).getType() == 1) {
				tempMap.put("doctor", list.get(i).getCount());
			} else if (list.get(i).getType() == 2) {
				tempMap.put("pharmacist", list.get(i).getCount());
			} else if (list.get(i).getType() == 3) {
				tempMap.put("dietitians", list.get(i).getCount());
			} else if (list.get(i).getType() == 4) {
				tempMap.put("health", list.get(i).getCount());
			} else if (list.get(i).getType() == 5) {
				tempMap.put("psychology", list.get(i).getCount());
			} else if (list.get(i).getType() == 6) {
				tempMap.put("beauty", list.get(i).getCount());
			}else if (list.get(i).getType() == 6) {
				tempMap.put("strong", list.get(i).getCount());
			} else if (list.get(i).getType() == 8) {
				tempMap.put("baby", list.get(i).getCount());
			}
		}
		if ("".equals(tempMap.get("doctor")) || tempMap.get("doctor") == null) {
			tempMap.put("doctor", 0);
		}
		if ("".equals(tempMap.get("pharmacist")) || tempMap.get("pharmacist") == null) {
			tempMap.put("pharmacist", 0);
		}
		if ("".equals(tempMap.get("dietitians")) || tempMap.get("dietitians") == null) {
			tempMap.put("dietitians", 0);
		}
		if ("".equals(tempMap.get("health")) || tempMap.get("health") == null) {
			tempMap.put("health", 0);
		}
		if ("".equals(tempMap.get("psychology")) || tempMap.get("psychology") == null) {
			tempMap.put("psychology", 0);
		}
		if ("".equals(tempMap.get("beauty")) || tempMap.get("beauty") == null) {
			tempMap.put("beauty", 0);
		}
		if ("".equals(tempMap.get("strong")) || tempMap.get("strong") == null) {
			tempMap.put("strong", 0);
		}
		if ("".equals(tempMap.get("baby")) || tempMap.get("baby") == null) {
			tempMap.put("baby", 0);
		}
		return tempMap;
	}

	@Override
	public int getDoctorCount(String query) {
		// TODO Auto-generated method stub
		return doctorDAO.getDoctorCount(query);
	}

	@Override
	public List<Doctor> getDoctorListByType(HttpServletRequest request,String type) {
		// TODO Auto-generated method stub
		return doctorDAO.getDoctorListByType(request, type);
	}

	@Override
	public Doctor findById(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return doctorDAO.findById(request);
	}

	@Override
	public String setDoctorAudit(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return doctorDAO.setDoctorAudit(request);
	}

	@Override
	public String setEnabled(HttpServletRequest request) {
		
		return doctorDAO.setEnabled(request);
	}

	@Override
	public DoctorDetail findDetailById(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return doctorDAO.findDetailById(request);
	}

}
