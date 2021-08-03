package com.FS705.dto;

public class LogDTO {
	
	private LogDTO() {}
	private static LogDTO instance = new LogDTO();
	public static LogDTO getInstance() {
		return instance;
	}
	
	private int logNo, no;
	private String logIp, logDate, logTarget, logdId, logEtc, logMethod;
	
	public int getLogNo() {
		return logNo;
	}
	public void setLogNo(int logNo) {
		this.logNo = logNo;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getLogIp() {
		return logIp;
	}
	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public String getLogTarget() {
		return logTarget;
	}
	public void setLogTarget(String logTarget) {
		this.logTarget = logTarget;
	}
	public String getLogdId() {
		return logdId;
	}
	public void setLogdId(String logdId) {
		this.logdId = logdId;
	}
	public String getLogEtc() {
		return logEtc;
	}
	public void setLogEtc(String logEtc) {
		this.logEtc = logEtc;
	}
	public String getLogMethod() {
		return logMethod;
	}
	public void setLogMethod(String logMethod) {
		this.logMethod = logMethod;
	}
	
	
}