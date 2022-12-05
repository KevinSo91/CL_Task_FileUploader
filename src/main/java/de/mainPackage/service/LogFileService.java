package de.mainPackage.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.mainPackage.model.Help;
import de.mainPackage.model.LogFile;
import de.mainPackage.model.Match;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LogFileService{	

	
	@Autowired
	private HelpService helpService;
	
	private static String uploadFolderDefault = "${logfiles.defaultDir}";
	
	private ArrayList<LogFile> logFiles = new ArrayList<LogFile>();
	
	private int nextLogFileId = 0;
	
	
	
	
	public String getUploadFolderDefault() {
		return uploadFolderDefault;
	}

	
	
	
	public boolean createLogFile(LogFile logFile) {
		return logFiles.add(logFile);
	}
	
//----->TODO: Optional ?
	public LogFile getLogFileById(int logFileId) {
		for(LogFile logFile : this.logFiles) {
			if(logFile.getId() == logFileId) {
				return logFile;
			}			
		}
		return new LogFile();
	}	
	
	
	public ArrayList<LogFile> getAllLogFiles(){
		return logFiles;
	}
	
	
	public String deleteLogFile(int logFileId) {		

		log.info(String.format("deleting logfile '%s'...", this.getLogFileById(logFileId).getFileName()));
		
		LogFile logFileToDelete = this.getLogFileById(logFileId);
		String fileName = logFileToDelete.getFileName();
		
		// Lösche Datei
		try {
			Files.deleteIfExists(Paths.get(logFileToDelete.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Lösche Object
		this.logFiles.remove(logFileToDelete);
		
		log.info(String.format("successfully deleted logfile '%s'", logFileToDelete.getFileName()));
		
		return fileName;
	}
	
	
	// LogFile hochladen, lokal speichern, Objekt erstellen
	public LogFile uploadLogFile(MultipartFile file,
								String folder, 
								String info) {		
		log.info(String.format("uploading logfile '%s'...", file.getOriginalFilename()));
//		String message = ""; // return message
		
//		// Check if File is Empty
//		if(file.isEmpty()) {
//			
//			return new LogFile();
//		}
		
		if(!validateFile(file).equals("ok")) {
			return new LogFile();
		}
		
		String fileName = file.getOriginalFilename();
		Path directoryPath = Paths.get(uploadFolderDefault + "\\" + folder);
		Path filePath = Paths.get(uploadFolderDefault + "\\" + folder + "\\" + fileName);
		
		log.info(filePath.toString());
		
		// Falls Verzeichnis nicht existiert -> Erstelle Verzeichnis
		if(!Files.exists(directoryPath)) {
			try {
				Files.createDirectories(directoryPath);
			} catch (IOException e) {
				e.printStackTrace();
//				message = "Directory Creation failed";
			}
		}
		
//-----> TODO: Prüfe, ob Datei mit gleichem Namen bereits vorhanden (Benutzer entscheidet / Laufindex (i))
		else {
			int i = 1;
			while(Files.exists(filePath)) {
				filePath = Paths.get(filePath + "(" + i +")");
				fileName = fileName + "(" + i +")";
				i++;
			}
		}
		
		// Schreibe Datei in Verzeichnis		
		try {
			byte[] bytes = file.getBytes();	
			Files.write(filePath, bytes);			
//			message = "You successfully uploaded '" + fileName + "'";					
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
//			message = "Upload failed";
		}
		
		// Erstelle LogFile Objekt
		LogFile logFile = new LogFile(info, fileName ,filePath.toString(), LocalDateTime.now());
		
		// Setze ID und erhöhe 'nextId'
		logFile.setId(this.nextLogFileId);
		nextLogFileId++;
		
		this.logFiles.add(logFile);	
		
		log.info(String.format("successfully uploaded logfile '%s'", fileName));
		
		return logFile;
	}
	
	
	// Schreibe die Zeilen aus dem File in die ArrayList 'lines' 
	public void saveLogFileLinesInArray(LogFile logFile) throws IOException {
		BufferedReader buffReader = null;
		try {
			Reader reader = new FileReader(new File(logFile.getPath()));
			buffReader = new BufferedReader(reader);
			String line;
			while((line = buffReader.readLine()) != null) {
				logFile.addLine(line);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(buffReader != null) {
				buffReader.close();
			}
		}		
	}
	
	
	// Prüfe die Zeilen aus 'lines' gegen das FAQ und schreibe Matches in ArrayList 'matches' 
	public void checkLogFileMatches(LogFile logFile) {
		int lineIndex = 1;
		for(Iterator<String> i = logFile.getLines().iterator(); i.hasNext();) {
			String line = i.next();
			ArrayList<Help> matches = this.helpService.checkLineForMatches(line);
			if (matches.size() > 0) {
				for (Help help : matches) {
					logFile.addMatch(new Match(logFile.getId(), lineIndex, line, help));
				}					
			}
			lineIndex++;
		}
	}	
	
	
	public static String validateFile(MultipartFile file) {
		String returnMessage = "ok";
		if(file == null || file.isEmpty() || file.toString().equals("")) {
			returnMessage = "No file chosen";
		}
		
		return returnMessage;
	}
	
	public static long checkLogFileSize(MultipartFile file) {		
		return file.getSize();
	}
	
	public static String checkLogFileType(MultipartFile file) {	
		return file.getContentType();
	}
	
	
	
	
}