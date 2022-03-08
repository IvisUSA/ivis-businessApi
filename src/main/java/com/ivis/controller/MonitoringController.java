package com.ivis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.ApplicationModels.CamStreamListModelWithActiveCams;
import com.ivis.service.IvisService;
import com.ivis.util.KeycloakUtils;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/Monitoring")
public class MonitoringController {

	
	@Autowired 
	IvisService  ivis;
	
	
	@PostMapping(path="/getMonitoringDetails")
	public Object monitoringDetails(@RequestBody HashMap<String,String> data)
	{
		if(!data.keySet().containsAll(new ArrayList<String>() {{add("SiteId");add("accessToken");add("calling_System_Detail");add("userName");}} ))
		{
			return new HashMap<String,String>() {{
				put("Status","Failed");
				put("Message","Insufficient details");
				
			}};
		}
		
		String siteId = data.get("SiteId");
		String accessToken = data.get("accessToken");
		String calling_System_Detail = data.get("calling_System_Detail");
		String userName = data.get("userName");
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(userName, accessToken);
		
		if(accessCheck) {
			
		try {
		
		return ivis.getMonitoringDetails(siteId);
		
		}
		catch(Exception e)
		{
			return new HashMap<String,String>() {{
				put("Status","Failed");
				put("Message","Invalid Details");
				
			}};
		}
			
		
		}
		
		else
		{
			return new HashMap<String,String>() {{
				put("Status","Failed");
				put("Message","Invalid token");
				
			}};
		}
		
	}
	
}
