package de.mainPackage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class TestController {
	
	@GetMapping("/test")
	public String getTest() {
		return "test";
	}
	
	@GetMapping("/test2")
	public String getTest2() {
		return "test2";
	}
	
}