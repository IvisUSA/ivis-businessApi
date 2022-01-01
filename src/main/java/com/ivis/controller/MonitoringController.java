package com.ivis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.service.IvisService;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/Monitoring")
public class MonitoringController {

	
	@Autowired 
	IvisService  ivis;
	
	
	@GetMapping(path="/getMonitoringDetails")
	public Object monitoringDetails(@RequestParam("calling_user_details") String calling_user_details, @RequestParam("deviceId") String deviceId)
	{
		
		if(calling_user_details.equals("IVISUSA")) {
		
			
			return ivis.getMonitoringDetails(deviceId);
		}
		else
		return null;
	}
	
}
