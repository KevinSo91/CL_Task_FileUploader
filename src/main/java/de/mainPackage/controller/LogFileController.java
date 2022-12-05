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
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	public LogFileController(LogFileService logFileService) {
		this.logFileService = logFileService;
	}
	

	
	// Show Logfiles / Upload
	@GetMapping({"/", "/all"})
	public String getUploadPage(Model model 
								,@RequestParam(value = "zeilenId", required=false) Integer logFileIdLines,
								 @RequestParam(value = "matchesId", required=false) Integer logFileIdMatches
								) {
		model.addAttribute("activePage", "logfiles");
		model.addAttribute("logFilesList", logFileService.getAllLogFiles());
		// Abhängig von den RequestParams werden die Zeilen oder Matches einer Logfile angezeigt
		if(logFileIdLines != null) {
			model.addAttribute("logFileZeilen", this.logFileService.getLogFileById(logFileIdLines.intValue()));
		}
		if(logFileIdMatches != null) {
			model.addAttribute("logFileMatches", this.logFileService.getLogFileById(logFileIdMatches.intValue()));
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
			redirectAttributes.addFlashAttribute("messageUploadError", "No File chosen");
			return "redirect:/logfiles/all";
		}
		
		// Speichere Datei in Ordner mit aktuellem Datum
		LogFile logFile = this.logFileService.uploadLogFile(file, LocalDate.now().toString(), info);
				
		// Schreibe Zeilen in ArrayList 'lines'
		this.logFileService.saveLogFileLinesInArray(logFile);
		// Prüfe gegen DB auf Treffer-Pattern
		this.logFileService.checkLogFileMatches(logFile);
		
		String messageSuccess = "successfully uploaded '" + logFile.getFileName() + "'";
		
		redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess);
		redirectAttributes.addFlashAttribute("file_Type", file.getContentType());
		redirectAttributes.addFlashAttribute("file_Size", file.getSize());
		redirectAttributes.addFlashAttribute("file_Info", info);
		
		
		return "redirect:/logfiles/all";
	}	
	
	
	// Delete LogFile
	@PostMapping("/delete")
	public String deleteLogFilePost(@RequestParam int fileId,
									RedirectAttributes redirectAttributes) {		
		String fileName = this.logFileService.deleteLogFile(fileId);
		String messageSuccess = "successfully deleted '" + fileName + "'";
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
		// Lösche alle Logfiles/Dateien
		for(int id : listIds) {
			this.logFileService.deleteLogFile(id);
		}
		redirectAttributes.addFlashAttribute("messageSuccess", "successfully deleted all (" + listIds.size() + ") Logfiles");
		return "redirect:/logfiles/all";
	}
	
	
	// Scan Logfile for Matches
	@PostMapping("/scan")
	public String scanLogFilePost(@RequestParam int fileId,
									RedirectAttributes redirectAttributes) {
		LogFile logFile = this.logFileService.getLogFileById(fileId);
		logFile.deleteAllMatches();
		this.logFileService.checkLogFileMatches(logFile);
		String messageSuccess = "You successfully scanned '" + logFile.getFileName() + "'";
		redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess);
		return "redirect:/logfiles/all";
	}		
	
	
	
	
	// Methods with Pathvariables
	
	// Delete LogFile (PathVariable)
	@PostMapping("/{logFileId}/delete")
	public String deleteLogFile(@PathVariable int logFileId) {		
		this.logFileService.deleteLogFile(logFileId);
		return "deleteStatus";
	}	
		
	// Show Logfile Lines (PathVariable)
	@GetMapping("/{logFileId}/lines")
	public String getLogFileLinesFromLogFile(Model model, @PathVariable int logFileId) {
		model.addAttribute("logFile", this.logFileService.getLogFileById(logFileId));
		return "logFileLines";
	}
	
	// Show LogFile Matches (PathVariable)
	@GetMapping("/{logFileId}/matches")
	public String getMatchesFromLogFile(Model model, @PathVariable int logFileId){
		model.addAttribute("logFile", this.logFileService.getLogFileById(logFileId));
		return "logFileMatches";
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
			boolean result = FileSystemUtils.deleteRecursively(Paths.get(this.logFileService.getConfigProperties().getDefaultDirectory()));
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		log.info("All LogFiles Deleted By Schedule");
	}
	
	
	
}
