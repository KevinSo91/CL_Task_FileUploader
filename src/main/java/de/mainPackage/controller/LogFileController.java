package de.mainPackage.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String getUploadPage(Model model) {
		model.addAttribute("logFilesList", logFileService.getAllLogFiles());
		return "upload";
	}
	
	// -> Upload File
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file,
										RedirectAttributes redirectAttributes) {
		
		// Prüfe Datei auf Datei-Typ und Größe
		System.out.println(LogFileService.checkLogFileType(file));
		System.out.println(LogFileService.checkLogFileSize(file));

		// Speichere Datei in Ordner mit aktuellem Datum
		String message = this.logFileService.uploadLogFile(file, "user1", LocalDate.now().toString(), "user1@mail.com", "Test Info");
				
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("file_Type", file.getContentType());
		redirectAttributes.addFlashAttribute("file_Size", file.getSize());
				
		return "redirect:/uploadStatus";		
	}	
	
	// Show Upload Status
	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}	
	
	// Show Logfile Lines
	@GetMapping("/logFile/{logFileId}/lines")
	public String getLogFileLinesFromLogFile(Model model, @PathVariable int logFileId) {
		model.addAttribute("logFile", this.logFileService.getLogFileById(logFileId));
		return "logFileLines";
	}
	
	// Show LogFile Matches
	@GetMapping("/logFile/{logFileId}/matches")
	public String getMatchesFromLogFile(Model model, @PathVariable int logFileId){
		model.addAttribute("logFileMatches", logFileService.getLogFileById(logFileId).getMatches());
		return "logFileMatches";
	}
	
	
	
}
