package com.ivis.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.google.gson.Gson;
import com.ivis.ApplicationModels.UserLogin;
import com.ivis.ApplicationModels.UserMgmtUserModel;
import com.ivis.service.IvisService;
import com.ivis.service.ServerConfig;
import com.ivis.util.KeycloakUtils;

@Controller
@CrossOrigin
@RestController
@RequestMapping("/UserMngt")
public class UserManagementController {
	
	
	
	@Autowired
	IvisService ivis;
	
	
	@GetMapping(path="")
	public String test()
	{
		return "<!DOCTYPE html><html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><style>body {  background-color: black;  font-family: cursive;}.glow {  font-size: 80px;  color: #fff;  text-align:center; animation: glow 1s ease-in-out infinite alternate;}@-webkit-keyframes glow {  from {text-shadow: 0 0 10px #fff, 0 0 20px #fff, 0 0 30px #e60073, 0 0 40px #e60073, 0 0 50px #e60073, 0 0 60px #e60073, 0 0 70px #e60073;}  to {    text-shadow: 0 0 20px #fff, 0 0 30px #ff4da6, 0 0 40px #ff4da6, 0 0 50px #ff4da6, 0 0 60px #ff4da6, 0 0 70px #ff4da6, 0 0 80px #ff4da6; }}</style></head><body><h1 class=\"glow\">UserMngt Works Great</h1>   </body></html> \r\n"
				+ "";
	}
	
	
	
	@PostMapping(path="/User/addUser_1_0")
	public Object addUser_1_0(@RequestBody UserMgmtUserModel input)
	{
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(input.getCalling_user_name(), input.getAccess_token());

		
		
		if(accessCheck)
		return ivis.addUser(input);
		else
		{
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}
	}
	
	@PostMapping(path="/User/updateUser_1_0")
	public Object updateUser_1_0(@RequestBody UserMgmtUserModel input)
	{
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(input.getCalling_user_name(), input.getAccess_token());

		
		
		if(accessCheck)
		return ivis.updateUser(input);
		else
		{
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}
	}
	@PostMapping(path="/User/deleteUser_1_0")
	public Object deleteUser_1_0(@RequestBody UserMgmtUserModel input)
	{
		boolean accessCheck = KeycloakUtils.verifyaccesstoken(input.getCalling_user_name(), input.getAccess_token());

		
		
		if(accessCheck)
		return ivis.deleteUser(input);
		else
		{
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid accessToken");

				}
			};
		}
	}
	
	
}
