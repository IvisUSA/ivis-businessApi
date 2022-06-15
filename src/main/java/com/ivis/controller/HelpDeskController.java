package com.ivis.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.ivis.service.IvisService;
import com.ivis.util.KeycloakUtils;

import okhttp3.MultipartBody;

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
		if (accessCheck) 
			return server.getServiceRequests(inputData);
		 else
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid user details");

				}
			};

	}


	@PostMapping("/addServiceRequest_1_0")
	public Object addServiceRequest(@RequestParam("siteId") Integer accountId,@RequestParam("serviceName") String serviceCategoryName,
			@RequestParam("subServiceName") String serviceSubCategoryName,@RequestParam("userName") String userName,
			@RequestParam("calling_System_Detail") String calling_System_Detail,@RequestParam(name = "Attachements",
			required = false) MultipartFile[] attachements,@RequestParam("description") String description,
			@RequestParam(name = "preferredTimeToCall", required = false) String preferredTimeToCall,
			@RequestParam(name = "priority", required = false) String priority,@RequestParam(name = "remarks", required = false) String remarks,
			@RequestParam("accessToken") String accessToken) {
		if(!KeycloakUtils.verifyaccesstoken(userName, accessToken)) {
		
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}
		else
		
			return server.addOrUpdateServiceRequest(accountId,null,serviceCategoryName,serviceSubCategoryName,
					userName,calling_System_Detail,attachements,description,
					preferredTimeToCall,priority,remarks);
		
		
	
	}
	
	@PostMapping("/updateServiceRequest_1_0")
	public Object updateServiceRequest(@RequestParam("siteId") Integer accountId,@RequestParam("serviceId") Integer serviceId,@RequestParam("serviceName") String serviceCategoryName,
			@RequestParam("subServiceName") String serviceSubCategoryName,@RequestParam("userName") String userName,
			@RequestParam("calling_System_Detail") String calling_System_Detail,@RequestParam(name = "Attachements",
			required = false) MultipartFile[] attachements,@RequestParam("description") String description,
			@RequestParam(name = "preferredTimeToCall", required = false) String preferredTimeToCall,
			@RequestParam(name = "priority", required = false) String priority,@RequestParam(name = "remarks", required = false) String remarks,
			@RequestParam("accessToken") String accessToken) {
		if(!KeycloakUtils.verifyaccesstoken(userName, accessToken))
		{
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}
		else
		{
			return server.addOrUpdateServiceRequest(accountId,serviceId,serviceCategoryName,serviceSubCategoryName,
					userName,calling_System_Detail,attachements,description,
					preferredTimeToCall,priority,remarks);
		}
		
	
	}
	
	@PostMapping("/deleteServiceRequest_1_0")
	public Object deleteServiceRequest(@RequestBody HashMap<String,Object> data)
	{
		if (!data.keySet().containsAll(new ArrayList<String>() {
			{
				add("userName");
				add("accessToken");
				add("calling_System_Detail");
				add("siteId");
				add("serviceId");
			}
		})) {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Insufficient details");
				}
			};
		}
		
		String userName		= (String) data.get("userName");
		String accessToken	= (String) data.get("accessToken");
		Integer accountId	= (Integer) data.get("siteId");
		Integer serviceId	= (Integer) data.get("serviceId");
		String remarks 		= (String) data.get("calling_System_Detail");
		if(!KeycloakUtils.verifyaccesstoken(userName, accessToken))
		{
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}
		else
		{

			return server.deleteServiceRequest(userName,accountId,serviceId,remarks);
		}
		
	}
	
	@PostMapping("/categoryList_1_0")
	public Object categoriesList(@RequestBody HashMap<String, String> inputData)
	{
		if (!inputData.keySet().containsAll(new ArrayList<String>() {
			{
				add("userName");
				add("accessToken");
				add("calling_System_Detail");
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
			return server.getCategoryList(inputData);
		} else
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid user details");

				}
			};
		
	}
	
	@PostMapping("/createServiceCat_1_0")
	public Object createServiceCat_1_0(@RequestBody HashMap<String, String> inputData)
	{
		if (!inputData.keySet().containsAll(new ArrayList<String>() {
			{
				add("userName");
				add("accessToken");
				add("calling_System_Detail");

				add("catName");
				add("description");
				add("catIconPath");
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
			return server.createServiceCat_1_0(inputData);
		} else
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid user details");

				}
			};
		
	}
	@PostMapping("/createServiceSubcat")
	public Object createServiceSubcat(@RequestBody HashMap<String, String> inputData)
	{
		if (!inputData.keySet().containsAll(new ArrayList<String>() {
			{
				add("userName");
				add("accessToken");
				add("calling_System_Detail");

				add("serviceSubcatName");
				add("description");
				add("serviceSubcatIconPath");
				add("serviceCatId");
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
			return server.createServiceSubcat(inputData);
		} else
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid user details");

				}
			};
		
	}

}
