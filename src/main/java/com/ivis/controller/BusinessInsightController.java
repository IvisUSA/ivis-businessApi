package com.ivis.controller;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ivis.Businessentity.BIAnalyticsEntity;
import com.ivis.service.IvisService;
import com.ivis.service.ServerConfig;
import com.ivis.util.BiUtils;
import com.ivis.util.CpusUtils;


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
	@GetMapping(path = "/biAnalyticsReport_2_0", produces=MediaType.APPLICATION_JSON_VALUE)
	public Object biAnalyticsReport2(@RequestParam( "SiteId") int SiteId,@RequestParam(value = "fromDate") String FromDate ,@RequestParam(value = "toDate")  String ToDate,@RequestParam( "calling_System_Detail") String calling_System_Detail ){
		
		if(calling_System_Detail.equals("IVISUSA")) {
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
	@GetMapping(path = "/analyticTrends_2_0", produces=MediaType.APPLICATION_JSON_VALUE)
	public Object analyticTrends_2_0(@RequestParam( "SiteId") int SiteId,@RequestParam( "calling_System_Detail") String calling_System_Detail,@RequestParam(value = "analyticTypeId")int analyticTypeId ,@RequestParam(value = "date", required=false) @DateTimeFormat(pattern="yyyy/mm/dd") Date date  ){
		
//		if(calling_System_Detail.equals("Mobile_App")) {
		if(true) {
		Object bITrends=ivis.getAnalyticTrends2(SiteId,date,analyticTypeId);
		
		return bITrends;
		}
		else return null;

	}
	@GetMapping(path = "/getPdfReport")
	public Object getPdfReport(@RequestParam( "siteId") int siteId,@RequestParam(value = "startdate") String startdate ,@RequestParam(value = "enddate")  String enddate,@RequestParam( "calling_System_Detail") String calling_System_Detail )
	{

		
//		if(calling_System_Detail.equals("Mobile_App")) {
		if(true) {
		
		
		return new BiUtils().getPdfReport(Integer.parseInt( new CpusUtils().GetClientIdFromSiteId(siteId)), startdate, enddate);
		
//		    return new ModelAndView("redirect:" + ServerConfig.ivisBiApi + "/download/getPdfReport?id="+new CpusUtils().GetClientIdFromSiteId(siteId)+"&startdate="+startdate+"&enddate="+enddate);
		}
		else return null;

	
	}
	
	
	@GetMapping(path = "/emailPdf")
	public Object emailPdf(@RequestParam( "emailId") String emailId,@RequestParam( "siteId") int siteId,@RequestParam(value = "startdate") String startdate ,@RequestParam(value = "enddate")  String enddate,@RequestParam( "calling_System_Detail") String calling_System_Detail )
	{

		
//		if(calling_System_Detail.equals("Mobile_App")) {
		if(new CpusUtils().GetClientIdFromSiteId(siteId)!=null) {
		
		
//		return new BiUtils().getPdfReport(Integer.parseInt( new CpusUtils().GetClientIdFromSiteId(siteId)), startdate, enddate);
		
			return new HashMap<String, String>() {
				{
					put("Status", "Success");
					put("Message", "Email sent successfully");
				}
			};
			
//		    return new ModelAndView("redirect:" + ServerConfig.ivisBiApi + "/download/getPdfReport?id="+new CpusUtils().GetClientIdFromSiteId(siteId)+"&startdate="+startdate+"&enddate="+enddate);
		}
		else 

			return new HashMap<String, String>() {
			{
				put("Status", "Failed");
				put("Message", "Failed processing request");
			}
		};

	
		
	}

}
