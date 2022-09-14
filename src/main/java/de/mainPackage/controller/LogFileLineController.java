package de.mainPackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
//		logFileService.getLogFileById(LogFileId).setPerson("user2");
//		logFileService.getLogFileById(LogFileId).setIsScanned(true);
//		System.out.println(logFileService.getLogFileById(LogFileId));
//		logFileService.getLogFileById(LogFileId).
		return "upload";
	}
	
}