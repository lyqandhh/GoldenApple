package cn.gqlife.entity;

public class OrderList {   
    private String OID;   
    private String EXP;
    private String TTYPE;   
    private String TTYPESTR;   
    private String DID;   
    private String STATUS;   
    private String AMOUNT;   
    private String CDATE;   
    private String DDATE;
    private String ASTATUS;
    private String ASTATUSSTR;
    
	public String getOID() {
		return OID;
	}
	public void setOID(String oID) {
		OID = oID;
	}
	public String getEXP() {
		return EXP;
	}
	public void setEXP(String eXP) {
		EXP = eXP;
	}
	public String getTTYPE() {
		return TTYPE;
	}
	public void setTTYPE(String tTYPE) {
		if(tTYPE.equals("1")) {
			setTTYPESTR("支付");
		}else if(tTYPE.equals("2")) {
			setTTYPESTR("收取");
		}else if(tTYPE.equals("3")) {
			setTTYPESTR("充值");
		}else if(tTYPE.equals("4")) {
			setTTYPESTR("抵扣");
		}else if(tTYPE.equals("5")) {
			setTTYPESTR("提现");
		}
		TTYPE = tTYPE;
	}
	public String getDID() {
		return DID;
	}
	public void setDID(String dID) {
		DID = dID;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getCDATE() {
		return CDATE;
	}
	public void setCDATE(String cDATE) {
		CDATE = cDATE;
	}
	public String getDDATE() {
		return DDATE;
	}
	public void setDDATE(String dDATE) {
		DDATE = dDATE;
	}
	public String getASTATUS() {
		return ASTATUS;
	}
	public void setASTATUS(String aSTATUS) {
		if(aSTATUS.equals("0")) {
			setASTATUSSTR("未审核");
		}else if(aSTATUS.equals("1")) {
			setASTATUSSTR("已通过");
		}else if(aSTATUS.equals("2")) {
			setASTATUSSTR("不通过");
		}
		ASTATUS = aSTATUS;
	}
	public String getASTATUSSTR() {
		return ASTATUSSTR;
	}
	public void setASTATUSSTR(String aSTATUSSTR) {
		ASTATUSSTR = aSTATUSSTR;
	}
	public String getTTYPESTR() {
		return TTYPESTR;
	}
	public void setTTYPESTR(String tTYPESTR) {
		TTYPESTR = tTYPESTR;
	}   
}