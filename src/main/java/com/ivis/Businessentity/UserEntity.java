package com.ivis.Businessentity;

import java.util.List;

public class UserEntity {

	
	public String userName;
	public String userType;
	public String userRole;
	public String contact;
	public String email;
	public String address;
	
	List<BusinessEntity>siteList;

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

	public List<BusinessEntity> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<BusinessEntity> siteList) {
		this.siteList = siteList;
	}
	
	
	
}
