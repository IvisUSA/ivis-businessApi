package com.ivis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.Businessentity.UserEntity;
import com.ivis.service.IvisService;
@CrossOrigin
@RestController
@RequestMapping("/sites")
public class BusinessAPIController {
	@Autowired 
	IvisService  ivis;
	
	
	@GetMapping("/hi")
	String hello() {
		return "hello";
		}
	@GetMapping("/sitesList_1_0")
	public UserEntity  getSitesdata(@RequestParam("uId") String uId)
	{
		
		UserEntity  userlist = ivis.getImMatrixAvailability(uId);
		return userlist;
		
	}
//	@GetMapping("/CamesList")
//	public List<BusinessCamesEntity> getCamesdata(@RequestParam("uId") String uId,@RequestParam(value="accountId", required=false) String  accountId)
//	{
//		List<BusinessCamesEntity>camesList =null;
//		
//		camesList = ivis.getCamerasList(uId,accountId);
//		return camesList;
//	}
	

}
