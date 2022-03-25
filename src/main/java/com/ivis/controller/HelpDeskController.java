package com.ivis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.ivis.service.IvisService;
import com.ivis.util.KeycloakUtils;

@Controller
@CrossOrigin
@RestController
@RequestMapping("/helpdesk")
public class HelpDeskController {

	@Autowired
	IvisService server;

	@PostMapping("/getServiceReq_1_0")
	public Object getServiceRequest(@RequestBody HashMap<String, String> inputData) {
		if (!inputData.keySet().containsAll(new ArrayList<String>() {
			{
				add("userName");
				add("accessToken");
				add("calling_System_Detail");
				add("SiteId");
			}
		})) {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Insufficient details");
				}
			};
		}
		String userName = inputData.get("userName");
		String accesstoken = inputData.get("accessToken");
		String callingSystemDetail = inputData.get("calling_System_Detail");
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(userName, accesstoken);
		if (accessCheck) {
			return server.getServiceRequests(inputData);
		} else
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid user details");

				}
			};

	}

	@PostMapping("/addServiceRequest_1_0")
	public Object addServiceRequest(@RequestParam(name = "images", required = false) MultipartFile imagedata,
			@RequestParam("data") String metadata) {
		HashMap<Object,Object> inputData = null;
		try {
		Gson gson = new Gson();    
		inputData = gson.fromJson(metadata,HashMap.class);
		}
		catch(Exception e)
		{
			System.err.println(e);
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid format");
				}
			};
		}
		
		ArrayList<String> requiredkeys = new ArrayList<String>() {
			{
				add("userName");
				add("accessToken");
				add("calling_System_Detail");
			}
		};

		requiredkeys.add("SiteId");
		requiredkeys.add("ServiceName");
		requiredkeys.add("ServiceSubCategory");

		requiredkeys.add("description");



		if (!inputData.keySet().containsAll(requiredkeys)) {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Insufficient details");
				}
			};
		}

		String userName = inputData.get("userName").toString();
		String accesstoken = inputData.get("accessToken").toString();
		String callingSystemDetail = inputData.get("calling_System_Detail").toString();
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(userName, accesstoken);
		
		if(accessCheck)
		{
			return server.addServiceRequest(inputData);
		}
		else
		{
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid user details");

				}
			};
		}
	}

}
