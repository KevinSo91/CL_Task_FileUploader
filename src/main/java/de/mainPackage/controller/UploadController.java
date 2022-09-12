package de.mainPackage.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.mainPackage.service.LogFileService;

@Controller
public class UploadController {
	
	@Autowired
	private LogFileService logFileService;

	
	@Autowired
	public UploadController(LogFileService logFileService) {
		this.logFileService = logFileService;
	}
	
	
	@GetMapping({"/", "/upload"})
	public String getUploadPage() {		
		return "upload";
	}

	
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file,
										RedirectAttributes redirectAttributes) {
		
		// Prüfe Datei auf Datei-Typ und Größe
		System.out.println(LogFileService.checkLogFileType(file));
		System.out.println(LogFileService.checkLogFileSize(file));

		// Speichere Datei in Ordner mit aktuellem Datum
		String message = this.logFileService.uploadLogFile(file, "user1", LocalDate.now().toString(), "user1@mail.com", "Test Info");
//		this.logFileService.saveLines(logFileService.getLogFileById(1));
		
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("file_Type", file.getContentType());
		redirectAttributes.addFlashAttribute("file_Size", file.getSize());
				
		return "redirect:/uploadStatus";
		
	}
	
	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}
	
	
	@PostMapping("/upload/scan")
	public String scanFile() {
		return "";
	}
	
	
	
}
