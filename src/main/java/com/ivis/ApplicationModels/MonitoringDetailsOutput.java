package com.ivis.ApplicationModels;

import java.util.ArrayList;


public class MonitoringDetailsOutput {

	
	
	int SiteId;
	
	String status;
	
	ArrayList<MonitoringHoursListModel> MonitoringHours; 
	

	public int getSiteId() {
		return SiteId;
	}


	public void setSiteId(int SiteId) {
		this.SiteId = SiteId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public ArrayList<MonitoringHoursListModel> getMonitoringHours() {
		return MonitoringHours;
	}


	public void setMonitoringHours(ArrayList<MonitoringHoursListModel> MonitoringHours) {
		this.MonitoringHours = MonitoringHours;
	}




		
		
	
	
	
	
}
