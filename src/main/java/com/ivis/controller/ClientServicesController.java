package com.ivis.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.service.IvisService;
import com.ivis.service.ServerConfig;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/Client")
public class ClientServicesController {

	@Autowired 
	IvisService  ivis;
	
	
	@GetMapping("/clientServices_1_0")
	private Object getData2(@RequestParam("accountId") String accountId,@RequestParam("Request_type") String Request_type,@RequestParam("calling_user_details") String calling_user_details) {
		
		
		if(calling_user_details.equals("IVISUSA")) {
	    String url = ServerConfig.cpusapi+"/clientServices_1_0/accountId="+accountId;
	    
	    return ivis.mapServices2(url,Request_type,accountId);
		}
		else 
			return new HashMap<String, String>() {
			{
				put("Status", "Failed");
				put("Message", "calling_user_details error");
			}
		};
	}
	
	@GetMapping("/notWorkingDays_1_0")
	private Object getNotWorkingDays_1_0(@RequestParam("siteId") int siteId,@RequestParam( "calling_System_Detail") String calling_System_Detail,@RequestParam("year") int year)
	{
		return ivis.getNotWorkingDays_1_0(siteId,year);
	}
}
