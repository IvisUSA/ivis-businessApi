package com.ivis.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.Businessentity.BIAnalyticsEntity;
import com.ivis.service.IvisService;


@Controller
@CrossOrigin
@RestController
@RequestMapping("/insights")
public class BusinessInsightController {

	@Autowired 
	IvisService  ivis;
	
	
	
	@GetMapping("/")
	public String test()
	{
		return "it works";
	}
	
	
	@GetMapping("/getAnalyticsListforSite_1_0")
	public Object getBusinessInsights(@RequestParam( "SiteId") int client_id,@RequestParam( "calling_System_Detail") String calling_System_Detail,@RequestParam(value = "date", required=false) @DateTimeFormat(pattern="yyyy/mm/dd") Date date  ){
		
		
		if(calling_System_Detail.equals("IVISUSA")) {
		Object bIAnalytics=ivis.getBusinessAnalystics2(client_id,date);
		
		return bIAnalytics;
		}
		else
			return new HashMap<String,String>() {{
				put("Status","Failed");
				put("Message","Please pass calling_System_Detail");
			}};
	}
	

	
	
	
	@GetMapping(path = "/biAnalyticsReport_1_0", produces=MediaType.APPLICATION_JSON_VALUE)
	public Object biAnalyticsReport(@RequestParam( "SiteId") int SiteId,@RequestParam(value = "fromDate", required=false) @DateTimeFormat(pattern="yyyy/mm/dd") Date FromDate ,@RequestParam(value = "toDate", required=false) @DateTimeFormat(pattern="yyyy/mm/dd") Date ToDate,@RequestParam( "calling_System_Detail") String calling_System_Detail ){
		
		if(calling_System_Detail.equals("IVISUSA")) {
		Object bIAnalytics=ivis.getbiAnalyticsReport2(SiteId,FromDate,ToDate);
		
		return bIAnalytics;
		}
		else return new HashMap<String,String>() {{
			put("Status","Failed");
			put("Message","Please pass calling_System_Detail");
		}};

	}
	@GetMapping(path = "/biAnalyticsReport_1_2", produces=MediaType.APPLICATION_JSON_VALUE)
	public Object biAnalyticsReport2(@RequestParam( "SiteId") int SiteId,@RequestParam(value = "fromDate") String FromDate ,@RequestParam(value = "toDate")  String ToDate,@RequestParam( "calling_user_details") String calling_user_details ){
		
		if(calling_user_details.equals("IVISUSA")) {
		Object bIAnalytics=ivis.getbiAnalyticsReport3(SiteId,FromDate,ToDate);
		
		return bIAnalytics;
		}
		else return null;

	}
	@GetMapping(path = "/analyticTrends_1_0", produces=MediaType.APPLICATION_JSON_VALUE)
	public Object analyticTrends_1_0(@RequestParam( "SiteId") int SiteId,@RequestParam( "calling_System_Detail") String calling_System_Detail,@RequestParam(value = "analyticTypeId")int analyticTypeId ,@RequestParam(value = "date", required=false) @DateTimeFormat(pattern="yyyy/mm/dd") Date date  ){
		
//		if(calling_System_Detail.equals("Mobile_App")) {
		if(true) {
		Object bITrends=ivis.getAnalyticTrends(SiteId,date,analyticTypeId);
		
		return bITrends;
		}
		else return null;

	}
	

}
