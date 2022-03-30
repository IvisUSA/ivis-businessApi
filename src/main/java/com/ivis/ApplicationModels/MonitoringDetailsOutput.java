package com.ivis.ApplicationModels;

import java.util.ArrayList;


public class MonitoringDetailsOutput {

	
	
	int SiteId;
	
	String monitoringStatus;
	
	ArrayList<MonitoringHoursListModel> MonitoringHours; 
	 String Status;
	 
	 String Message;

	 
	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}


	public String getMessage() {
		return Message;
	}


	public void setMessage(String message) {
		Message = message;
	}


	public int getSiteId() {
		return SiteId;
	}


	public void setSiteId(int SiteId) {
		this.SiteId = SiteId;
	}


	public String getMonitoringStatus() {
		return monitoringStatus;
	}


	public void setMonitoringStatus(String monitoringStatus) {
		this.monitoringStatus = monitoringStatus;
	}


	public ArrayList<MonitoringHoursListModel> getMonitoringHours() {
		return MonitoringHours;
	}


	public void setMonitoringHours(ArrayList<MonitoringHoursListModel> MonitoringHours) {
		this.MonitoringHours = MonitoringHours;
	}




		
		
	
	
	
	
}
