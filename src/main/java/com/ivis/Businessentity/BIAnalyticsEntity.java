package com.ivis.Businessentity;

import java.util.List;

import com.ivis.ApplicationModels.Analysis;

public class BIAnalyticsEntity {
	
	String service;
	List<Analysis> analytics;
	
	
	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}
	/**
	 * @param service the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}
	/**
	 * @return the analytics
	 */
	public List<Analysis> getAnalytics() {
		return analytics;
	}
	/**
	 * @param analytics the analytics to set
	 */
	public void setAnalytics(List<Analysis> analytics) {
		this.analytics = analytics;
	}
	

}
