package de.mainPackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import de.mainPackage.service.LogFileLineService;
import de.mainPackage.service.LogFileService;

@Controller
public class LogFileLineController{
	
	@Autowired
	private LogFileLineService logFileLineService;	
	@Autowired
	private LogFileService logFileService;
	
	@PostMapping("/scanFile/{LogFileId}")
	public String CreateLines(@PathVariable int LogFileId) {
		
		logFileLineService.saveLines(logFileService.getLogFileById(LogFileId));
		return "upload";
		
	}
	
	@GetMapping("/logFileLines")
	public String getAllLogFileLines(Model model) {
		model.addAttribute("logFileLinesList", logFileLineService.getAllLines());
		return "logFileLines";
	}
	
}