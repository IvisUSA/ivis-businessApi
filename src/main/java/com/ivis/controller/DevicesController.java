package com.ivis.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.ApplicationModels.UserMgmtUserModel;
import com.ivis.service.IvisService;
import com.ivis.util.KeycloakUtils;
import com.ivis.util.MngtServerUtils;


@CrossOrigin
@Controller
@RestController
@RequestMapping("/Devices")
public class DevicesController {

	
	@Autowired 
	IvisService  ivis;
	
	
	@PostMapping(path="/getDeviceDetails")
	public Object getDeviceDetails(@RequestBody HashMap<String, String> data)
	{
		if(!data.keySet().containsAll(new ArrayList<String>() {{add("requestType");add("siteId");add("calling_user_name");add("accessToken");add("calling_System_Detail");}} ))
		{
			return new HashMap<String,String>() {{
				put("Status","Failed");
				put("Message","Insufficient details");

			}};
		}
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(data.get("calling_user_name"), data.get("accessToken"));

		
		
		if(accessCheck)
		return ivis.getDeviceDetailsByRequestType2(Integer.parseInt(data.get("siteId")),data.get("requestType"));
		else
		{
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}
	}
	
	
//	new MngtServerUtils().getDeviceDetailsForSite_1_0(1001).get("Status").equals("Success");
}
