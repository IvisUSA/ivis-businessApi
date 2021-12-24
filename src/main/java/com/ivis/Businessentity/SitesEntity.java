package com.ivis.Businessentity;

import java.util.List;

import com.ivis.ApplicationModels.account;


//@Entity
public class SitesEntity {
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	public int accountId;
	public String accountName;
	public String facility;
	public String customerSiteId;
	public String state;
	public String city;
	public String zone;
	public String industry;
	public String batchNo;
	public int potentialId;
	public String unitId;
	public String siteType;
	public String latitude;
	public String longitude;
	public String userName;
	public String userType;
	public String userRole;
	public String contact;
	public String email;
	public String address;
	List<account>siteList;
	
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFacility() {
		return facility;
	}
	public void setFacility(String facility) {
		this.facility = facility;
	}
	
	
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
	public List<account> getSiteList() {
		return siteList;
	}
	public void setSiteList(List<account> siteList) {
		this.siteList = siteList;
	}
	
	
	

	
	
	

}
