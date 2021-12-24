package com.ivis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.Businessentity.BIAnalyticsEntity;
import com.ivis.service.IvisService;

@CrossOrigin
@RestController
@RequestMapping("/insights")
public class BusinessInsightController {

	@Autowired 
	IvisService  ivis;
	
	@GetMapping("/getAnalyticsListforSite_1_0")
	public List<BIAnalyticsEntity> getBusinessInsights(@RequestParam int client_id){
		
		List<BIAnalyticsEntity> bIAnalytics=this.ivis.getBusinessAnalystics(client_id);
		
		return bIAnalytics;
	}
}
