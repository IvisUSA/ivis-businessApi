package com.ivis.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

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
	public Object  getSitesdata(@RequestBody HashMap<String, String> userdata)
	{
		JSONObject json = new JSONObject();
		try {
			URL url = new URL("http://smstaging.iviscloud.net:8090/keycloakApp/siteList");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", "Bearer {token}");
			http.setRequestProperty("Content-Type", "application/json");

			String data = "{\n    \"userName\" : \""+userdata.get("userName")+"\",\n\n    \"accessToken\" : \""+userdata.get("accessToken")+"\"\n\n}";

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);
			InputStream resp = http.getInputStream();
			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1;) {
				sb.append((char) ch);
			}
			json = new JSONObject(sb.toString());

			http.disconnect();

			return json.toMap();

		} catch (Exception e) {
			System.err.println(e);

			return null;
		}
		
	}
	

}
