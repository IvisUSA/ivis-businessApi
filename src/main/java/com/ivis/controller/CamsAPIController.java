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
import com.ivis.service.IvisService;
@CrossOrigin
@Controller
@RestController
@RequestMapping("/Cameras")
public class CamsAPIController {
	@Autowired 
	IvisService  ivis;
	
	@GetMapping("/CamerasList_1_0")
	public List<BusinessCamesEntity> getCamesdata(@RequestParam("uId") String uId,@RequestParam(value="accountId", required=false) String  accountId,@RequestParam("calling_user_details") String calling_user_details)
	{
		if(calling_user_details.equals("IVISUSA")) {
		List<BusinessCamesEntity>camesList =null;
		
		camesList = ivis.getCamerasList(uId,accountId);
		return camesList;
		}
		else
			return null;
	}
}
