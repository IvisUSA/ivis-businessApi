package com.ivis.ApplicationModels;

import java.util.ArrayList;

public class SnapshortUrlsForAccountModel {

	int accountId;
	
	
	ArrayList<Camera> camList;
	

	
	
	
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public ArrayList<Camera> getCamList() {
		return camList;
	}

	public void setCamList(ArrayList<Camera> camList) {
		this.camList = camList;
	}

	public class Camera{
		String cameraId;
		String displayName;
		int displayOrder;
		
		String host;
		
		int httpPort;
		
		
		
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public int getHttpPort() {
			return httpPort;
		}
		public void setHttpPort(int httpPort) {
			this.httpPort = httpPort;
		}
		public String getCameraId() {
			return cameraId;
		}
		public void setCameraId(String cameraId) {
			this.cameraId = cameraId;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public int getDisplayOrder() {
			return displayOrder;
		}
		public void setDisplayOrder(int displayOrder) {
			this.displayOrder = displayOrder;
		}
		
		
		
	}
	
	
}
