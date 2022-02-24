package com.ivis.ApplicationModels;

import java.util.ArrayList;

public class SiteList {

	String status;
	String message;
	ArrayList<site> siteList;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<site> getSiteList() {
		return siteList;
	}
	public void setSiteList(ArrayList<site> siteList) {
		this.siteList = siteList;
	}
	public class site{
			int accountId;
			String accountName;
			String facility;
			String customerSiteId;
			String state;
			String city;
			String zone;
			String industry;
			String batchNo;
			int potentialId;
			String unitId;
			String siteType;
			String latitude;
			String longitude;
			public int getAccountId() {
				return accountId;
			}
			public void setAccountId(int accountId) {
				this.accountId = accountId;
			}
			public String getAccountName() {
				return accountName;
			}
			public void setAccountName(String accountName) {
				this.accountName = accountName;
			}
			public String getFacility() {
				return facility;
			}
			public void setFacility(String facility) {
				this.facility = facility;
			}
			public String getCustomerSiteId() {
				return customerSiteId;
			}
			public void setCustomerSiteId(String customerSiteId) {
				this.customerSiteId = customerSiteId;
			}
			public String getState() {
				return state;
			}
			public void setState(String state) {
				this.state = state;
			}
			public String getCity() {
				return city;
			}
			public void setCity(String city) {
				this.city = city;
			}
			public String getZone() {
				return zone;
			}
			public void setZone(String zone) {
				this.zone = zone;
			}
			public String getIndustry() {
				return industry;
			}
			public void setIndustry(String industry) {
				this.industry = industry;
			}
			public String getBatchNo() {
				return batchNo;
			}
			public void setBatchNo(String batchNo) {
				this.batchNo = batchNo;
			}
			public int getPotentialId() {
				return potentialId;
			}
			public void setPotentialId(int potentialId) {
				this.potentialId = potentialId;
			}
			public String getUnitId() {
				return unitId;
			}
			public void setUnitId(String unitId) {
				this.unitId = unitId;
			}
			public String getSiteType() {
				return siteType;
			}
			public void setSiteType(String siteType) {
				this.siteType = siteType;
			}
			public String getLatitude() {
				return latitude;
			}
			public void setLatitude(String latitude) {
				this.latitude = latitude;
			}
			public String getLongitude() {
				return longitude;
			}
			public void setLongitude(String longitude) {
				this.longitude = longitude;
			}
			
	}
}
