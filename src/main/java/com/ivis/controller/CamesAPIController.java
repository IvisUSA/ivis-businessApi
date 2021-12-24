package com.ivis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.Businessentity.BusinessCamesEntity;
import com.ivis.service.IvisService;
@CrossOrigin
@RestController
@RequestMapping("/Cameras")
public class CamesAPIController {
	@Autowired 
	IvisService  ivis;
	
	@GetMapping("/CamerasList_1_0")
	public List<BusinessCamesEntity> getCamesdata(@RequestParam("uId") String uId,@RequestParam(value="accountId", required=false) String  accountId)
	{
		List<BusinessCamesEntity>camesList =null;
		
		camesList = ivis.getCamerasList(uId,accountId);
		return camesList;
	}
}
