package com.ivis.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.ApplicationModels.UserLogin;
import com.ivis.service.IvisService;

@Controller
@CrossOrigin
@RestController
@RequestMapping("/login")
public class UserLoginController {

	@Autowired 
	IvisService  ivis;
	
	@PostMapping(value="/user")
	public Map<String,String> userLogin(@RequestParam("grant_type") String grant_type,@RequestParam("client_id") String client_id,@RequestParam("client_secret") String client_secret,@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("realm") String realm){
		UserLogin user= new UserLogin();
		user.setClient_id(client_id);
		user.setClient_secret(client_secret);
		user.setGrant_type(grant_type);
		user.setPassword(password);
		user.setRealm(realm);
		user.setUsername(username);
		Map<String,String> response=this.ivis.userLogin(user);
		return response;
	}
}
