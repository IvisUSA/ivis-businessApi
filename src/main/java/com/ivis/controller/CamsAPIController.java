package com.ivis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.Businessentity.BusinessCamesEntity;
import com.ivis.Businessentity.BusinessCamesStreamEntity;
import com.ivis.service.IvisService;
@CrossOrigin
@Controller
@RestController
@RequestMapping("/Cameras")
public class CamsAPIController {
	@Autowired 
	IvisService  ivis;
	
	
	@GetMapping("/")
	public String test()
	{
		return "it works";
	}
	
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
	public List<BusinessCamesStreamEntity> getCameraStreamList_1_0(@RequestParam("uId") String uId,@RequestParam(value="accountId", required=false) String  accountId,@RequestParam("calling_user_details") String calling_user_details)
	{
		if(calling_user_details.equals("IVISUSA")) {
		List<BusinessCamesStreamEntity>camesList =null;
		
		camesList = ivis.getCamerasStreamList(uId,accountId);
		return camesList;
		}
		else
			return null;
	}
}
