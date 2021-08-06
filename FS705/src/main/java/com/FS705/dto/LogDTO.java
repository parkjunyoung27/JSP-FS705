package com.FS705.dto;

import java.util.Date;

public class LogDTO {
	
	private int logNo, totalCount;
	private String logIp, logTarget, logdId, logEtc, logMethod;
	private Date logDate;
	
	public int getLogNo() {
		return logNo;
	}
	public void setLogNo(int logNo) {
		this.logNo = logNo;
	}
	public String getLogIp() {
		return logIp;
	}
	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}
	
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
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
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
	
}