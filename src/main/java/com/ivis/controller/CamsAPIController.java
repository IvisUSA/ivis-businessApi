package com.ivis.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

import com.ivis.Businessentity.BusinessCamesStreamEntity;
import com.ivis.service.IvisService;
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
	
	@GetMapping("/CameraStreamList_1_0")
	public List<Object> getCameraStreamList_1_0(@RequestParam("uId") String uId,@RequestParam(value="accountId", required=false) String  accountId)
	{
		
		List<Object> camesList =null;
		
		camesList = ivis.getCamerasStreamList2(uId,accountId);
		return camesList;

	}
	
	@PostMapping("/CameraStreamList_2_0")
	public Object getCameraStreamList_2_0(@RequestBody HashMap<String, String> userdata)
	{
		JSONArray json = new JSONArray();
		try {
			URL url = new URL("http://smstaging.iviscloud.net:8090/keycloakApp/camList");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Content-Type", "application/json");

			String data = "{\n    \"userName\" : \""+userdata.get("userName")+"\",\n\"siteid\":"+userdata.get("siteid")+",\n    \"accessToken\" : \""+userdata.get("accessToken")+"\"\n\n}";

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);
			InputStream resp = http.getInputStream();
			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1;) {
				sb.append((char) ch);
			}
			json = new JSONArray(sb.toString());

			http.disconnect();

			return json.toList();

		} catch (Exception e) {
			System.err.println(e);

			return null;
		}
	}
}
