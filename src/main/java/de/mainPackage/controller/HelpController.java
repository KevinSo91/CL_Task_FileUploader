package de.mainPackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.mainPackage.service.HelpService;

@Controller
public class HelpController{
	
	// Attributes
	
	
	@Autowired
	private HelpService helpService;
	
	
	// Contructors
	
	
	// Methods
	
	
	@GetMapping("/help/all")
	public String getAllHelps(Model model) {
		model.addAttribute("helpList", this.helpService.getAllHelps());
		return "help";
	}
	
	@PostMapping("/help/createTestPattern")
	public String createTestPattern() {
		helpService.createTestHelps();
		return "redirect:/help/all";
	}
	
	
}