package de.mainPackage.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.mainPackage.model.LogFile;
import de.mainPackage.service.LogFileService;

@Controller
public class LogFileController {
	
	// Attributes
	
	
	@Autowired
	private LogFileService logFileService;
	
	

	// Constructors
	
	
	public LogFileController(LogFileService logFileService) {
		this.logFileService = logFileService;
	}
	
	
	
	// Methods
	
	
	// Show Logfiles / Upload
	@GetMapping({"/", "/upload"})
	public String getUploadPage(Model model 
								,@RequestParam(value = "zeilenId", required=false) Integer logFileIdLines,
								 @RequestParam(value = "matchesId", required=false) Integer logFileIdMatches
								) {
		model.addAttribute("activePage", "upload");
		model.addAttribute("logFilesList", logFileService.getAllLogFilesRepo());
		// Abhängig von den RequestParams werden die Zeilen oder Matches einer Logfile angezeigt
		if(logFileIdLines != null) {
			model.addAttribute("logFileZeilen", this.logFileService.getLogFileByIdRepo(logFileIdLines.intValue()).get());
		}
		if(logFileIdMatches != null) {
			model.addAttribute("logFileMatches", this.logFileService.getLogFileByIdRepo(logFileIdMatches.intValue()).get());
		}
		
		return "logFiles";
	}
	
	// Upload File
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file,
							@RequestParam("info") String info,
							RedirectAttributes redirectAttributes
							) throws IOException {
		
//		// Prüfe Datei auf Datei-Typ und Größe
//		System.out.println(LogFileService.checkLogFileType(file));
//		System.out.println(LogFileService.checkLogFileSize(file));
		
//		TODO: Prüfe lokale Speicherkapazität
		
		// Speichere Datei in Ordner mit aktuellem Datum
		LogFile logFile = this.logFileService.uploadLogFileRepo(file, LocalDate.now().toString(), info);
		this.logFileService.saveLogFileLinesInArrayRepo(logFile);
		this.logFileService.checkLogFileMatchesRepo(logFile);
		String message = "You successfully uploaded '" + logFile.getFileName() + "'";
		
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("file_Type", file.getContentType());
		redirectAttributes.addFlashAttribute("file_Size", file.getSize());
		redirectAttributes.addFlashAttribute("file_Info", info);
				
		return "redirect:/uploadStatus";		
	}	
	
		
	// Delete LogFile
	@PostMapping("/logfile/delete")
	public String deleteLogFilePost(@RequestParam int fileId,
									RedirectAttributes redirectAttributes) {		
		String fileName = this.logFileService.deleteLogFileRepo(fileId);
		String message = "You successfully deleted '" + fileName + "'";
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/logfile/delete/deleteStatus";
	}			
	
	// Show Delete Status
	@GetMapping("/logfile/delete/deleteStatus")
	public String deleteStatus() {
		return "deleteStatus";
	}	
		
	// Show Upload Status
	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}	
	
	// Delete LogFile (PathVariable)
	@PostMapping("logfile/{logFileId}/delete")
	public String deleteLogFile(@PathVariable int logFileId) {		
		this.logFileService.deleteLogFileRepo(logFileId);
		return "deleteStatus";
	}	
		
	// Show Logfile Lines (PathVariable)
	@GetMapping("/logfile/{logFileId}/lines")
	public String getLogFileLinesFromLogFile(Model model, @PathVariable int logFileId) {
		model.addAttribute("logFile", this.logFileService.getLogFileByIdRepo(logFileId));
		return "logFileLines";
	}
	
	// Show LogFile Matches (PathVariable)
	@GetMapping("/logfile/{logFileId}/matches")
	public String getMatchesFromLogFile(Model model, @PathVariable int logFileId){
		model.addAttribute("logFile", this.logFileService.getLogFileByIdRepo(logFileId));
		return "logFileMatches";
	}
	
	
	
}
