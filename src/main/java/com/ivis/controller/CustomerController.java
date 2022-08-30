package com.ivis.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivis.service.IvisService;
import com.ivis.util.KeycloakUtils;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	IvisService ivis;

	@PostMapping(path = "/customersList_1_0")
	public Object customersList_1_0(@RequestBody HashMap<String, String> data) {
		if (!data.keySet().containsAll(new ArrayList<String>() {
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
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(data.get("userName"), data.get("accessToken"));
		if (accessCheck)
			return ivis.customersList_1_0();
		else {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");
				}
			};
		}
	}
	
	@PostMapping(path = "/createCustomer_1_0")
	public Object createCustomer_1_0(@RequestBody HashMap<String, Object> data)
	{

		if (!data.keySet().containsAll(new ArrayList<String>() {
			{
				add("userName");
				add("accessToken");
				add("calling_System_Detail");
				add("firstName");
				add("lastName");
				add("contactPerson");
				add("customerType");
				add("phoneNo");
				add("email");
				add("addresses");
			}
		})) {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Insufficient details");
				}
			};
		}
		

//		for(Object i:addressDetails)
//		{
//			System.out.println(i.toString());
//			ObjectMapper oMapper = null;
//			Map<String, Object> address =oMapper.convertValue(i, Map.class);
//			
//			if (!address.keySet().containsAll(new ArrayList<String>() {
//				{
//					add("addressType");
//					add("line_1");
//					add("country");
//					add("state");
//					add("pin");
//				}})) {
//				return new HashMap<String, String>() {
//					{
//						put("Status", "Failed");
//						put("Message", "Insufficient details");
//					}
//				};
//			}
//		}
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(data.get("userName").toString(), data.get("accessToken").toString());
		if (accessCheck)
			return ivis.createCustomer_1_0(data);
		else {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");
				}
			};
		}
	
	}
}



