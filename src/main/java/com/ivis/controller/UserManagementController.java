package com.ivis.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.ivis.ApplicationModels.UserLogin;
import com.ivis.ApplicationModels.UserMgmtUserModel;
import com.ivis.service.IvisService;
import com.ivis.service.ServerConfig;
import com.ivis.util.KeycloakUtils;

import jdk.internal.util.xml.impl.Input;

@Controller
@CrossOrigin
@RestController
@RequestMapping("/User")
public class UserManagementController {

	@Autowired
	IvisService ivis;

	@GetMapping(path = "")
	public String test() {
		return "<!DOCTYPE html><html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><style>body {  background-color: black;  font-family: cursive;}.glow {  font-size: 80px;  color: #fff;  text-align:center; animation: glow 1s ease-in-out infinite alternate;}@-webkit-keyframes glow {  from {text-shadow: 0 0 10px #fff, 0 0 20px #fff, 0 0 30px #e60073, 0 0 40px #e60073, 0 0 50px #e60073, 0 0 60px #e60073, 0 0 70px #e60073;}  to {    text-shadow: 0 0 20px #fff, 0 0 30px #ff4da6, 0 0 40px #ff4da6, 0 0 50px #ff4da6, 0 0 60px #ff4da6, 0 0 70px #ff4da6, 0 0 80px #ff4da6; }}</style></head><body><h1 class=\"glow\">UserMngt Works Great</h1>   </body></html> \r\n"
				+ "";
	}

	@PostMapping(path = "/addUser_1_0")
	public Object addUser_1_0(@RequestBody HashMap<String, Object> input) {

		if (!input.keySet().containsAll(new ArrayList<String>() {
			{
				add("username");
				add("password");
				add("firstname");
				add("lastname");
				add("email");
				add("gender");
				add("realm");
				add("contactNumber-1");
				add("accesstoken");
				add("callingUsername");
				add("callingSystemDetail");

			}
		})) {

			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Insufficient details");

				}
			};
		}

		boolean accessCheck = KeycloakUtils.verifyaccesstoken(input.get("callingUsername").toString(),
				input.get("accesstoken").toString());

		if (accessCheck)
			return ivis.addUser(input);
		else {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}
	}
	
	
	
	

	@PostMapping(path = "/updateUser_1_0")
	public Object updateUser_1_0(@RequestBody HashMap<String, Object> input) {
		if (!input.keySet().containsAll(new ArrayList<String>() {
			{
				add("username");
				add("firstname");
				add("lastname");
				add("roleList");
				add("realm");
				add("email");
				add("gender");
				add("contactNumber-1");

			}
		})) {

			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Insufficient details");

				}
			};
		}

		boolean accessCheck = KeycloakUtils.verifyaccesstoken(input.get("callingUsername").toString(),
				input.get("accesstoken").toString());

		if (accessCheck)
			return ivis.updateUser(input);
		else {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}
	}
	@PostMapping(path="/getUser_1_0")
	public Object getUser1(@RequestBody HashMap<String, String> input) {
		if (!input.keySet().containsAll(new ArrayList<String>() {
			{
				add("callingUsername");
				add("accesstoken");
				add("callingSystemDetail");

			}
		})) {

			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Insufficient details");
					

				}
			};
		}
		
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(input.get("callingUsername").toString(),
				input.get("accesstoken").toString());
		if(accessCheck) {
		
			if(input.get("callingSystemDetail").equals("portal") || input.get("callingSystemDetail").equals("mobile")) {	
				return ivis.getuserDetailsPortal(input);
			}
			else if(input.get("username")!=null || input.get("email")!=null){
				return ivis.getuserDetails(input);
			}
			else {
				return new HashMap<String, String>() {
					{
						put("Status", "Failed");
						put("Message", "Insufficient details");

					}
				};
			}
			
		}
		
		else {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}
		
		
	}

	//@PostMapping(path = "/getUser_1_0")
	public Object getUser(@RequestBody HashMap<String, String> input) {

		if (!input.keySet().containsAll(new ArrayList<String>() {
			{
				add("username");
				add("accesstoken");
				add("callingUsername");
				add("callingSystemDetail");

			}
		}))
			if (!input.keySet().containsAll(new ArrayList<String>() {
				{
					add("email");
					add("accesstoken");
					add("callingUsername");
					add("callingSystemDetail");

				}
			}))
				if (!input.keySet().containsAll(new ArrayList<String>() {
					{
						add("email");
						add("username");
						add("accesstoken");
						add("callingUsername");
						add("callingSystemDetail");

					}
				})) {

					return new HashMap<String, String>() {
						{
							put("Status", "Failed");
							put("Message", "Insufficient details");

						}
					};
				}

		boolean accessCheck = KeycloakUtils.verifyaccesstoken(input.get("callingUsername").toString(),
				input.get("accesstoken").toString());
       
		if (accessCheck)
			return ivis.getuserDetails(input);
		else {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}

	}
	
	
	
	@PostMapping("/UpdateProfilePic_1_0")
	public Object uploadImage(@RequestParam(value="callingUsername",required = false) String callingUsername,
			@RequestParam(value="accesstoken",required = false) String accesstoken,
			@RequestParam(value="callingSystemDetail",required = false) String callingSystemDetail,@RequestParam(value = "image",required = false) MultipartFile imageUpload) throws IOException {
		if(callingUsername!=null && accesstoken != null && callingSystemDetail != null && !(imageUpload==null)&& !(imageUpload.getOriginalFilename().isEmpty()) ) {
			return new LinkedHashMap<String, String>() {
				{
					put("status", "Success");
					put("message", "ImageUpdated");
					put("image","http://usmgmt.iviscloud.net:444/ivis-us-allsiteimages/profilepics/default.png");

				}
			};
		}
		
		return new HashMap<String, String>() {
			{
				
				put("Status", "Failed");
				put("Message", "Insufficent Details");

			}
		};
		
		
	}

}
