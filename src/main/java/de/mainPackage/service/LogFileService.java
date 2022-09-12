package de.mainPackage.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.mainPackage.model.LogFile;
import de.mainPackage.model.LogFileLine;
import de.mainPackage.repository.LogFileLineRepository;
import de.mainPackage.repository.LogFileRepository;

@Service
public class LogFileService{
	
	
	// Attributes
	
	@Autowired 
	private LogFileRepository logFileRepo;
	@Autowired
	private LogFileLineRepository lineRepo;
	
	
	private static String uploadFolderDefault = ".\\data\\";
	
	
	// Constructors
		
	
	
	// Methods	
	
	@SuppressWarnings("deprecation")
	public LogFile getLogFileById(int id) {
		return this.logFileRepo.getById(id);
	}
	
	public String uploadLogFile(MultipartFile file, 
								String user,
								String folder, 
								String email, 
								String info) {		
		// return message
		String message = "";
		
		String fileName = file.getOriginalFilename();
		Path directoryPath = Paths.get(uploadFolderDefault + "\\" + folder);
		Path filePath = Paths.get(uploadFolderDefault + "\\" + folder + "\\" + fileName);
		
		// Falls Verzeichnis nicht existiert -> Erstelle Verzeichnis
		if(!Files.exists(directoryPath)) {
			try {
				Files.createDirectories(directoryPath);
			} catch (IOException e) {
				e.printStackTrace();
				message = "Directory Creation failed";
			}
		}
		
		// Schreibe Datei in Verzeichnis		
		try {	
			
			byte[] bytes = file.getBytes();			
			Files.write(filePath, bytes);
			
			message = "You successfully uploaded '" + fileName + "'";
					
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			message = "Upload failed";
		}
		
		// Erstelle Eintrag in Datenbank		
		LogFile logFile = new LogFile(user, email, info, filePath.toString(), LocalDateTime.now());		
		
		logFile.addLines();
		
		logFileRepo.save(logFile);
		
		
//		TEST
//		Schreibe Lines
		
//		FileInputStream inputStream = null;
//		Scanner sc = null;
//		try {
//			inputStream = new FileInputStream(logFile.getPath());
//			sc = new Scanner(inputStream, "UTF-8");
//			while(sc.hasNextLine()) {
//				String line = sc.nextLine();
//				this.lineRepo.save(new LogFileLine(line));
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		TEST
		
		
		
		return message;
	}
	
	
	// Static Methods
	
	public static long checkLogFileSize(MultipartFile file) {		
		return file.getSize();
	}
	
	public static String checkLogFileType(MultipartFile file) {	
		return file.getContentType();
	}
	
	public void saveLines(LogFile logFile) {
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(logFile.getPath());
			sc = new Scanner(inputStream, "UTF-8");
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				this.lineRepo.save(new LogFileLine(line));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}