package com.ivis.ApplicationModels;

public class MonitoringHoursListModel {

	
	String monitoringname;
	
	String monitoringhours;
	
	String additionalmonitoringhours;

	public String getMonitoringname() {
		return monitoringname;
	}

	public void setMonitoringname(String monitoringname) {
		this.monitoringname = monitoringname;
	}

	public String getMonitoringhours() {
		return monitoringhours;
	}

	public void setMonitoringhours(String monitoringhours) {
		this.monitoringhours = monitoringhours;
	}

	public String getAdditionalmonitoringhours() {
		return additionalmonitoringhours;
	}

	public void setAdditionalmonitoringhours(String additionalmonitoringhours) {
		this.additionalmonitoringhours = additionalmonitoringhours;
	}

	public MonitoringHoursListModel(String monitoringname, String monitoringhours, String additionalmonitoringhours) {
		
		this.monitoringname = monitoringname;
		this.monitoringhours = monitoringhours;
		this.additionalmonitoringhours = additionalmonitoringhours;
	}
	
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if(obj instanceof MonitoringHoursListModel)
        {
        	MonitoringHoursListModel temp = (MonitoringHoursListModel) obj;
            if(this.monitoringname.equals(temp.monitoringname) && this.monitoringhours.equals(temp.monitoringhours) && this.additionalmonitoringhours.equals(temp.additionalmonitoringhours))
                return true;
        }
        return false;
    }
	
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        
        return (this.monitoringname.hashCode() + this.monitoringhours.hashCode() + this.additionalmonitoringhours.hashCode());        
    }
	
}
