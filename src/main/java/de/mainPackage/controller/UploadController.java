package de.mainPackage.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadController {
	
	private static String uploadFolder = "C:\\uploads\\";

	
	@GetMapping({"/", "/upload"})
	public String getUploadPage() {		
		return "upload";
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file,
										RedirectAttributes redirectAttributes) {
		
		String fileName = file.getOriginalFilename();
				
		try {
//			file.transferTo(new File(uploadFolder + fileName));
			byte[] bytes = file.getBytes();
			Path path = Paths.get(uploadFolder + fileName);
			Files.write(path, bytes);
			
			redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + fileName + "'");
			redirectAttributes.addFlashAttribute("file_Type", file.getContentType());
			redirectAttributes.addFlashAttribute("file_Size", file.getSize());
			
			
			
			
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/uploadStatus";
		
	}
	
	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}
	
	
	
}
