package com.ivis.ApplicationModels;

import java.util.ArrayList;

public class MonitoringDetailsInput {

	int potentailId;
	
	ArrayList<MonitoringHoursListModel> monitoringHours; 
	


	public int getPotentailId() {
		return potentailId;
	}

	public void setPotentailId(int potentailId) {
		this.potentailId = potentailId;
	}

	public ArrayList<MonitoringHoursListModel> getMonitoringHours() {
		return monitoringHours;
	}

	public void setMonitoringHours(ArrayList<MonitoringHoursListModel> monitoringHours) {
		this.monitoringHours = monitoringHours;
	}




	
}
