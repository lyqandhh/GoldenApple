package cn.gqlife;

public class ParametersTools {
	private static String doctorType="";
	private static String doctorLever="";
	private static String doctorStatus="";
	public static String getDoctorType(Object object) {
		if(object.equals("1")){
			doctorType="执业医师";
		}
		return doctorType;
	}
	public static String getDoctorLever(Object object) {
		if(object.equals("1")){
			doctorLever="主任";
		}else if(object.equals("2")){
			doctorLever="副主任";
		}
		return doctorLever;
	}
	public static String getDoctorStatus(Object object) {
		if(object.equals("1")){
			doctorStatus="通过审核";
		}else if(object.equals("2")){
			doctorStatus="未通过审核";
		}
		return doctorStatus;
	}
	
}
