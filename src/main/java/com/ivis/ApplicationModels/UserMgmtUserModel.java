
package com.ivis.ApplicationModels;

import java.util.ArrayList;


public class UserMgmtUserModel implements Cloneable{

	String userName;
	String password;
	String firstName;
	String lastName;
	ArrayList<Object> role_List;

	String email;
	String gender;
	String realm;
	int client_Id;
	String contactNumber_1;
	String contactNumber_2;
	String active;
	String country;
	String address_line1;
	String address_Line2;
	String district;
	String state;
	String city;
	int pin;
	String employee;
	String employeeId;
	String access_token;
	String calling_user_name;
	String calling_system_detail;
	String safety_escort;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public ArrayList<Object> getRole_List() {
		return role_List;
	}
	public void setRole_List(ArrayList<Object> role_List) {
		this.role_List = role_List;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRealm() {
		return realm;
	}
	public void setRealm(String realm) {
		this.realm = realm;
	}
	public int getClient_Id() {
		return client_Id;
	}
	public void setClient_Id(int client_Id) {
		this.client_Id = client_Id;
	}
	public String getContactNumber_1() {
		return contactNumber_1;
	}
	public void setContactNumber_1(String contactNumber_1) {
		this.contactNumber_1 = contactNumber_1;
	}
	public String getContactNumber_2() {
		return contactNumber_2;
	}
	public void setContactNumber_2(String contactNumber_2) {
		this.contactNumber_2 = contactNumber_2;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress_line1() {
		return address_line1;
	}
	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}
	public String getAddress_Line2() {
		return address_Line2;
	}
	public void setAddress_Line2(String address_Line2) {
		this.address_Line2 = address_Line2;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
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
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getCalling_user_name() {
		return calling_user_name;
	}
	public void setCalling_user_name(String calling_user_name) {
		this.calling_user_name = calling_user_name;
	}
	public String getCalling_system_detail() {
		return calling_system_detail;
	}
	public void setCalling_system_detail(String aalling_system_detail) {
		this.calling_system_detail = aalling_system_detail;
	}
	public String getSafety_escort() {
		return safety_escort;
	}
	public void setSafety_escort(String safety_escort) {
		this.safety_escort = safety_escort;
	}
	
	
	   public Object clone() throws CloneNotSupportedException
	    {
	        return super.clone();
	    }
	
}
