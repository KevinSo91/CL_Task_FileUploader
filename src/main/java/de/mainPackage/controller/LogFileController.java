package de.mainPackage.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.mainPackage.model.LogFile;
import de.mainPackage.service.LogFileService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/logfiles")
public class LogFileController {
	
	@Autowired
	private LogFileService logFileService;
	
	@Value("${logfiles.defaultDirectory}")
	private String defaultDirectoy;
		

	
	// Show Logfiles & Upload Form
	@GetMapping({"/", "/all"})
	public String getUploadPage(Model model 
								,@RequestParam(value = "showLinesForLogfileId", required=false) Integer showLinesForLogfileId,
								 @RequestParam(value = "showMatchesForLogfileId", required=false) Integer showMatchesForLogfileId
								) {
		model.addAttribute("activePage", "logfiles");
		model.addAttribute("logFilesList", logFileService.getAllLogFiles());
		// Abhängig von den RequestParams werden die Zeilen oder Matches einer Logfile angezeigt
		if(showLinesForLogfileId != null) {
			model.addAttribute("logFileLines", this.logFileService.getLogFileById(showLinesForLogfileId.intValue()));
		}
		if(showMatchesForLogfileId != null) {
			model.addAttribute("logFileMatches", this.logFileService.getLogFileById(showMatchesForLogfileId.intValue()));
		}
		
		return "logFiles";
	}
	
	
	// Upload LogFile
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("uploadFile") MultipartFile file,
							@RequestParam("info") String info,
							RedirectAttributes redirectAttributes
							) throws IOException {		
		
//--->	TODO: Prüfe: Datei ausgewählt? -> Datei-Typ und Größe

		// Fall: Kein File ausgewählt		
		if(!LogFileService.validateFile(file).equals("ok")) {
			redirectAttributes.addFlashAttribute("messageNoFileError", "No File selected");
			return "redirect:/logfiles/all";
		}
		
		// Speichere Datei in Ordner mit aktuellem Datum
		LogFile logFile = this.logFileService.uploadLogFile(file, LocalDate.now().toString(), info);				
		// Schreibe Zeilen in ArrayList 'lines'
		this.logFileService.saveLogFileLinesInArray(logFile);
		// Prüfe gegen DB auf Treffer-Pattern
		this.logFileService.checkLogFileMatches(logFile);
		
		String messageSuccess = String.format("Successfully uploaded '%s'", logFile.getFileName());		
		redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess);			
		
		return "redirect:/logfiles/all";
	}	
	
	
	// Delete LogFile
	@PostMapping("/delete")
	public String deleteLogFile(@RequestParam int fileId,
									RedirectAttributes redirectAttributes) {		
		String fileName = this.logFileService.deleteLogFile(fileId);
		String messageSuccess = String.format("Successfully deleted '%s'", fileName);
		redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess);		
		return "redirect:/logfiles/all";
	}


	// Delete All Logfiles (On EXIT)
	@PostMapping("/delete/all")
	public String deleteAllLogFiles(RedirectAttributes redirectAttributes) {
		List<Integer> listIds = new ArrayList<Integer>();
		// Schreibt die IDs aller Logfiles in die Liste 'listIds'
		for(LogFile logFile : this.logFileService.getAllLogFiles()) {
			listIds.add(logFile.getId());
		}		
		log.info("Deleting all Logfiles...");		
		// Lösche alle Logfiles/Dateien
		for(int id : listIds) {
			this.logFileService.deleteLogFile(id);
		}
		String messageSuccess = String.format("Successfully deleted all (%s) Logfiles", listIds.size());
		log.info(messageSuccess);		
		redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess);		
		return "redirect:/logfiles/all";
	}
	
	
	// Scan Logfile for Matches
	@PostMapping("/scan")
	public String scanLogFile(@RequestParam int fileId,
									RedirectAttributes redirectAttributes) {
		LogFile logFile = this.logFileService.getLogFileById(fileId);
		logFile.deleteAllMatches();
		this.logFileService.checkLogFileMatches(logFile);
		String messageSuccess = "You successfully scanned '" + logFile.getFileName() + "'";
		redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess);
		return "redirect:/logfiles/all";
	}		
	
	
	
	
	// Schedule
	
	@Scheduled(cron = "${logfiles.delete.cron}")
	public void deleteAllLogFiles() {
		List<Integer> listIds = new ArrayList<Integer>();
		// Schreibt die IDs aller Logfiles in die Liste 'listIds'
		for(LogFile logFile : this.logFileService.getAllLogFiles()) {
			listIds.add(logFile.getId());
		}
		// Lösche alle Logfiles/Dateien
		for(int id : listIds) {
			this.logFileService.deleteLogFile(id);
		}		
		// Lösche Dateien in Default-Folder
		try {
			FileSystemUtils.deleteRecursively(Paths.get(this.defaultDirectoy));
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		log.info("All LogFiles Deleted By Schedule");
	}
	
	
	
}
