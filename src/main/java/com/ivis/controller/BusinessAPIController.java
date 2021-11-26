package com.ivis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessAPIController {

	
	@GetMapping("/hi")
	String hello() {
		return "hello";
	}
}
