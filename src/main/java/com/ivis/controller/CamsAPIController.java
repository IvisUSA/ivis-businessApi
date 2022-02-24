package com.ivis.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.ApplicationModels.CamStreamListModel;
import com.ivis.ApplicationModels.CamStreamListModelWithActiveCams;
import com.ivis.Businessentity.BusinessCamesStreamEntity;
import com.ivis.service.IvisService;
import com.ivis.util.KeycloakUtils;
@CrossOrigin
@Controller
@RestController
@RequestMapping("/Cameras")
public class CamsAPIController {
	@Autowired 
	IvisService  ivis;
	
	
	
	@GetMapping("/CamerasList_1_0")
	public List<BusinessCamesStreamEntity> getCamesdata(@RequestParam("uId") String uId,@RequestParam(value="accountId", required=false) String  accountId,@RequestParam("calling_user_details") String calling_user_details)
	{
		if(calling_user_details.equals("IVISUSA")) {
		List<BusinessCamesStreamEntity>camesList =null;
		
		camesList = ivis.getCamerasList(uId,accountId);
		return camesList;
		}
		else
			return null;
	}
	
	@PostMapping("/CameraStreamList_1_0")
	public Object getCameraStreamList_1_0(@RequestBody HashMap<String,String> data)
	{
		if(!data.keySet().containsAll(new ArrayList<String>() {{add("userName");add("accessToken");add("calling_System_Detail");}} ))
		{
			return new HashMap<String,String>() {{
				put("Status","Failed");
				put("Message","Insufficient details");
				
			}};
		}
		
		
		
		String accountId = null;
		
		String userName = data.get("userName");
		if(data.containsKey("SiteId"))
		   accountId = data.get("SiteId");
		
		
		String accessToken = data.get("accessToken");
		
		
		
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(userName, accessToken);
		
		if(accessCheck) {
		
		List<CamStreamListModelWithActiveCams> camesList =null;
		
		if(data.containsKey("SiteId"))
		camesList = ivis.getCamerasStreamList2(userName,accountId);
		
		
		else
			camesList = ivis.getCamerasStreamList2(userName,null);
			
		
		return camesList;}
		else 
			return null;

	}
	
	
}
