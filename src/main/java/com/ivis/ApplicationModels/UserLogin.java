package com.ivis.ApplicationModels;

public class UserLogin {

	String grant_type;
	String client_id;
	String client_secret;
	String username;
	String password;
	String realm;
	/**
	 * @return the grant_type
	 */
	public String getGrant_type() {
		return grant_type;
	}
	/**
	 * @param grant_type the grant_type to set
	 */
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	/**
	 * @return the client_id
	 */
	public String getClient_id() {
		return client_id;
	}
	/**
	 * @param client_id the client_id to set
	 */
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	/**
	 * @return the client_secret
	 */
	public String getClient_secret() {
		return client_secret;
	}
	/**
	 * @param client_secret the client_secret to set
	 */
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the realm
	 */
	public String getRealm() {
		return realm;
	}
	/**
	 * @param realm the realm to set
	 */
	public void setRealm(String realm) {
		this.realm = realm;
	}
	
	
}
