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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.Businessentity.UserEntity;
import com.ivis.service.IvisService;
import com.ivis.util.KeycloakUtils;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/sites")
public class SitesAPIController {
	@Autowired
	IvisService ivis;

	@GetMapping("/hi")
	String hello() {
		return "hello";
	}

	@PostMapping("/sitesList_2_0")
	public Object getSitesdata(@RequestBody HashMap<String, String> inputData) {

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
		System.out.println(accessCheck);
		if (accessCheck) {

			return ivis.getSiteListByUserName(userName);

		} else
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid user details");

				}
			};

	}

	@PostMapping("/SnapshotUrlsForSitesList_1_0")
	public Object snapshotUrlsForSitesList_1_0(@RequestBody HashMap<String, Object> data) {
		if (!data.keySet().containsAll(new ArrayList<String>() {
			{
				add("sitesList");
				add("accessToken");
				add("calling_System_Detail");
				add("userName");
			}
		})) {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Insufficient details");

				}
			};
		}
		try {
			ArrayList siteList = (ArrayList) data.get("sitesList");
		} catch (Exception e) {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid details");

				}
			};
		}
		String userName = (String) data.get("userName");
		String accessToken = (String) data.get("accessToken");
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(userName, accessToken);

		ArrayList siteList = (ArrayList) data.get("sitesList");

		// TODO
		if (accessCheck) {

			return ivis.getsnapshotUrlsForSitesList_1_0(siteList);
		} else
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid user details");

				}
			};
	}

	
	@PostMapping(path = "/sitesList_1_0")
	public Object sitesList_1_0(@RequestBody HashMap<String, String> data) {
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
			return ivis.sitesList_1_0();
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
