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
}
