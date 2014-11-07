package cn.gqlife.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.gqlife.entity.Doctor;
import cn.gqlife.entity.DoctorDetail;

public interface DoctorService {
	public abstract boolean save(Doctor doctor);
	/**
	 * 获得医生列表
	 * @param request
	 * @return 医生列表
	 */
	public abstract List<Doctor> getDoctorList(HttpServletRequest request);
	/**
	 * 根据医生列表获取列表
	 * @param request
	 * @param type 
	 * 医生类别【１：医生2:执业药师3:营养师4:健康管理师5:心理咨询师6:美容师7:健身教练8：育婴师】
	 * @return 医生列表
	 */
	public abstract List<Doctor> getDoctorListByType(HttpServletRequest request,String type);
	/**
	 * 获得医生数量
	 * @param request
	 * @return Map<String"医生类别", int"数量">
	 */
	public abstract Map<String, Integer> getDoctorCount(HttpServletRequest request);
	/**
	 * 获得医生类别
	 * @param query 医生类别
	 * 医生类别【１：医生2:执业药师3:营养师4:健康管理师5:心理咨询师6:美容师7:健身教练8：育婴师】
	 * @return
	 */
	public abstract int getDoctorCount(String query);
	/**
	 * 查找医生基本信息
	 * @param request 医生ID
	 * @return Doctor对象
	 */
	public abstract Doctor findById(HttpServletRequest request);
	/**
	 * 查找医生详细信息
	 * @param request
	 * @return DoctorDetail
	 */
	public abstract DoctorDetail findDetailById(HttpServletRequest request);
	/**
	 * 审核医生
	 * @param request
	 * @return success:成功/error：失败
	 */
	public abstract String setDoctorAudit(HttpServletRequest request);
	/**
	 * 冻结/解冻医生
	 * @param request
	 * @return success:成功/error：失败
	 */
	public abstract String setEnabled(HttpServletRequest request);
}
