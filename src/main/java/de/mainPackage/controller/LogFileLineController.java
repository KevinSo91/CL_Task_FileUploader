package de.mainPackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import de.mainPackage.model.Help;
import de.mainPackage.model.LogFileLine;
import de.mainPackage.service.HelpService;
import de.mainPackage.service.LogFileLineService;
import de.mainPackage.service.LogFileService;

@Controller
public class LogFileLineController{
	
	@Autowired
	private LogFileLineService logFileLineService;	
	@Autowired
	private LogFileService logFileService;
	@Autowired
	private HelpService helpService;
	
	
	// Methods
	
	
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
	
	@PutMapping("/logFileLine/{logFileLine_id}/help/{help_id}")
	public String addHelpToLogfileLine( Model model,
			@PathVariable int logFileLine_id,
			@PathVariable int help_id
			) {
		LogFileLine logFileLine = logFileLineService.getLogFileLineById(logFileLine_id);
		Help help = helpService.getHelp(help_id);
		logFileLine.addMatch(help);
		logFileLineService.createLogFileLine(logFileLine);
		
		model.addAttribute("logFilesList", logFileService.getAllLogFiles());
		return "upload";
	}
	
	@GetMapping("/matches")
	public String getAllMatches() {
		
		return "matches";
	}
	
}