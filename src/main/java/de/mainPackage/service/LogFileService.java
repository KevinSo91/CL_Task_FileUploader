package de.mainPackage.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.mainPackage.model.Help;
import de.mainPackage.model.LogFile;
import de.mainPackage.model.Match;

@Service
public class LogFileService{	
	
	
	// Attributes
	
	@Autowired
	private HelpService helpService;
	
	private static String uploadFolderDefault = ".\\data\\";
	
	private ArrayList<LogFile> logFiles = new ArrayList<LogFile>();
	
	
	
	// Constructors
	
	
	
	// Methods
	
	
	public boolean createLogFile(LogFile logFile) {
		return logFiles.add(logFile);
	}
	
	@SuppressWarnings("deprecation")
	public LogFile getLogFileById(int logFileId) {
		return this.logFiles.get(logFileId);
	}	
	
	public ArrayList<LogFile> getAllLogFiles(){
		return logFiles;
	}
	
	
	
	// LogFile hochladen
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
		
		// Erstelle LogFile Objekt
		LogFile logFile = new LogFile(user, email, info, fileName ,filePath.toString(), LocalDateTime.now());
		
		// Setze ID auf die nächste verfügbare Ganzzahl (abhängig von Anzahl der bestehenden LogFiles)
		logFile.setId(logFiles.size());
		
		// Speichere Zeilen als String in LogFile.lines ArrayList
		FileInputStream inputStream = null;
		Scanner sc = null;
//		ArrayList<String> logFileLines = new ArrayList<String>();
		try {
			inputStream = new FileInputStream(logFile.getPath());
			sc = new Scanner(inputStream, "UTF-8");
			int indexLine = 0;
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				logFile.addLine(line);
				ArrayList<Help> matches = this.helpService.checkLineForMatches(line);
				if (matches.size() < 0) {
					for (Help help : matches) {
						logFile.addMatch(new Match(indexLine, line, help));
						}					
				}				
//				logFileLines.add(line);
				indexLine++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
				
		
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