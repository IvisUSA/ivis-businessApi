package com.ivis.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
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
	IvisService  ivis;
	
	
	@GetMapping("/hi")
	String hello() {
		return "hello";
		}
	@GetMapping("/sitesList_1_0")
	public UserEntity  getSitesdata(@RequestParam("uId") String uId,@RequestParam("calling_user_details") String calling_user_details)
	{
		if(calling_user_details.equals("IVISUSA")) {
		UserEntity  userlist = ivis.getImMatrixAvailability(uId);
		return userlist;
		}
		else
			return null;
		
	}
//	@GetMapping("/CamesList")
//	public List<BusinessCamesEntity> getCamesdata(@RequestParam("uId") String uId,@RequestParam(value="accountId", required=false) String  accountId)
//	{
//		List<BusinessCamesEntity>camesList =null;
//		
//		camesList = ivis.getCamerasList(uId,accountId);
//		return camesList;
//	}
	
	@PostMapping("/sitesList_2_0")
	public Object  getSitesdata(@RequestBody HashMap<String, String> inputData)
	{
		
		if(!inputData.keySet().containsAll(new ArrayList<String>() {{add("userName");add("accessToken");add("calling_System_Detail");}} ))
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

			
			return ivis.getSiteListByUserName(userName);
		
		
		}
		else 
			return new HashMap<String,String>() {{
				put("Status","Failed");
				put("Message","Invalid user details");
				
			}};
		
		
	}
	

}
