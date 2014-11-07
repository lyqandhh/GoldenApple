package cn.gqlife.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.gqlife.entity.Doctor;
import cn.gqlife.entity.DoctorCount;
import cn.gqlife.entity.DoctorDetail;

public interface DoctorDAO {
	public abstract boolean save(Doctor doctor);
	public abstract List<Doctor> getDoctorList(HttpServletRequest request);
	public abstract List<Doctor> getDoctorListByType(HttpServletRequest request,String type);
	public abstract List<Doctor> getDoctorListByAll(HttpServletRequest request);
	public abstract List<DoctorCount> getDoctorCount(HttpServletRequest request,List<Integer> type);
	public abstract int getDoctorCount(String query);
	public abstract Doctor findById(HttpServletRequest request);
	public abstract DoctorDetail findDetailById(HttpServletRequest request);
	public abstract String setDoctorAudit(HttpServletRequest request);
	public abstract String setEnabled(HttpServletRequest request);
}
