package com.ivis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.service.IvisService;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/Client")
public class ClientServicesController {

	@Autowired 
	IvisService  ivis;
	
	
	@GetMapping("/clientServices_1_0")
	private Object getData2(@RequestParam("accountId") String accountId,@RequestParam("Request_type") String Request_type) {
		
	    String url = "http://smstaging.iviscloud.net:8090/cpus/clientServices_1_0/accountId="+accountId;
	    
	    return ivis.mapServices2(url,Request_type);
		
	}
}
