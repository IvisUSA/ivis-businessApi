package com.ivis.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.service.IvisService;
import com.ivis.util.KeycloakUtils;



@CrossOrigin
@Controller
@RestController
@RequestMapping("/beats")
public class HeartBeatApiController {

	
	
	@Autowired
	IvisService ivis;
	
	
	@GetMapping("/hi")
	public String test()
	{
		return "hello";
	}
	
	
	@PostMapping("/getDevices")
	public Object getDevicesFromHeartbeat(@RequestBody HashMap<String, String> inputData)
	{
		
		//Note:- We are giving demo data here as we dont have data available. Please change in future.
		
		if(!inputData.keySet().containsAll(new ArrayList<String>() {{add("userName");add("SiteId");add("accessToken");add("calling_System_Detail");}} ))
		{
			return new HashMap<String,String>() {{
				put("Status","Failed");
				put("Message","Insufficient details");
				
			}};
		}
		
		
		String userName = inputData.get("userName");
		String accesstoken = inputData.get("accessToken");
		String callingSystemDetail = inputData.get("calling_System_Detail");
		
		
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(userName, accesstoken);
		
		if(accessCheck) {

			
			
			
			

			return new HashMap<String,Object>(){{
				put("Status","Success");
				put("Message","Valid data");
				put("SiteId","1009");
				
				
				put("Devices",new HashMap<String,Object>() {{
					
					put("DeviceName1",new HashMap<String,Object>() {{
						
						put("ItemName1","ItemValue1");
						put("ItemUpTime1","Item Value Uptime1");
						put("ItemUpTime2","Item Value Uptime2");
					}});
					
					put("DeviceName2",new HashMap<String,Object>() {{
						
						put("ItemName1","ItemValue Uptime1");
						put("ItemName2","ItemValue Uptime2");
					}});
				}});
				
				
			}};
		
		
		}
		else 
			return new HashMap<String,String>() {{
				put("Status","Failed");
				put("Message","Invalid user details");
				
			}};
		
		
	}
	
	
	
	
	
	
}
