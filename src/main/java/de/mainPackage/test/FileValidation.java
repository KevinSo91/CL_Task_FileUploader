package de.mainPackage.test;

import org.springframework.web.multipart.MultipartFile;

public class FileValidation{
	
	public static String checkFileType(MultipartFile file) {
		
		return file.getContentType();
	}
	
	public static long checkFileSize(MultipartFile file) {
		
		return file.getSize();
	}
	
}