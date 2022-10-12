package de.mainPackage.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.mainPackage.model.LogFile;
import de.mainPackage.repository.LogFileRepository;

@Service
public class LogFileService{	
	
	
	// Attributes
	
	
	@Autowired 
	private LogFileRepository logFileRepo;
	@Autowired
	private LogFileLineService logFileLineService;
	
	private static String uploadFolderDefault = ".\\data\\";
	
	private ArrayList<LogFile> logFiles = new ArrayList<LogFile>();
	
	
	// Constructors
	
	
	// Methods
	
	public boolean createLogFile(LogFile logFile) {
		return logFiles.add(logFile);
	}
	
	public ArrayList<LogFile> getAllLogFiles(){
		return logFiles;
	}
	
	@SuppressWarnings("deprecation")
	public LogFile getLogFileById(int logFileId) {
		return this.logFileRepo.getById(logFileId);
	}
	
	// LogFile hochladen und die LogFiles + Zeilen in DB speichern
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
		
		// Erstelle Eintrag in Tabelle 'logfiles'		
		LogFile logFile = new LogFile(user, email, info, fileName ,filePath.toString(), LocalDateTime.now());		
		this.logFiles.add(logFile);		
		
		
		return message;
	}
		
	
	// Static Methods
	
	
	public static long checkLogFileSize(MultipartFile file) {		
		return file.getSize();
	}
	
	public static String checkLogFileType(MultipartFile file) {	
		return file.getContentType();
	}
	

	
	
}