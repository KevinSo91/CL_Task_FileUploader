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
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.mainPackage.model.Help;
import de.mainPackage.model.LogFile;
import de.mainPackage.model.Match;
import de.mainPackage.repository.LogFileRepository;

@Service
public class LogFileService{		
	
	@Autowired
	private HelpService helpService;
	
	@Autowired
	private LogFileRepository logFileRepo;
	
	private static String uploadFolderDefault = ".\\data\\";
	
	private ArrayList<LogFile> logFiles = new ArrayList<LogFile>();
	
	private int nextLogFileId = 0;
	
	
	
	// Repo-----------------------------------------------------------------------------------------------------
	
	public LogFile createLogFileRepo(String info, String fileName, String path, LocalDateTime uploadTime){
		return this.logFileRepo.save(new LogFile (info, fileName, path, uploadTime));
	}
	
	public Optional<LogFile> getLogFileByIdRepo(int logFileId) {
		return this.logFileRepo.findById(logFileId);
	}	
	
	public List<LogFile> getAllLogFilesRepo(){
		return this.logFileRepo.findAll();
	}
	
	public String deleteLogFileRepo(int logFileId) {
//--->	TODO: Delete File
		String deletedFileName = this.logFileRepo.findById(logFileId).get().getFileName();
		this.logFileRepo.deleteById(logFileId);
		return deletedFileName;
	}
	
	
	// LogFile hochladen, lokal speichern, Objekt erstellen
	public LogFile uploadLogFileRepo(MultipartFile file,
								String folder, 
								String info) {
//		String message = ""; // return message
		
		String fileName = file.getOriginalFilename();
		Path directoryPath = Paths.get(uploadFolderDefault + "\\" + folder);
		Path filePath = Paths.get(uploadFolderDefault + "\\" + folder + "\\" + fileName);
		
		// Falls Verzeichnis nicht existiert -> Erstelle Verzeichnis
		if(!Files.exists(directoryPath)) {
			try {
				Files.createDirectories(directoryPath);
			} catch (IOException e) {
				e.printStackTrace();
//				message = "Directory Creation failed";
			}
		}
//--->	TODO: User Entscheidung
		// Falls Datei mit gleichem Namen bereits vorhanden
		else {
			while(Files.exists(filePath)) {
				filePath = Paths.get(filePath + "(2)");
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
		return logFileRepo.save(new LogFile(info, fileName ,filePath.toString(), LocalDateTime.now()));		
	}	
	
	// Schreibe die Zeilen aus dem File in die ArrayList 'lines' 
	public void saveLogFileLinesInArrayRepo(LogFile logFile) throws IOException {
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
		} finally {
			if(buffReader != null) {
				buffReader.close();
			}
		}		
	}	
	
	// Prüfe die Zeilen aus 'lines' gegen das FAQ und schreibe Matches in ArrayList 'matches' 
	public void checkLogFileMatchesRepo(LogFile logFile) {
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
	
	
	
	
	
	// No Repo -------------------------------------------------------------------------------------------
	
//	public boolean createLogFile(LogFile logFile) {
//		return logFiles.add(logFile);
//	}
//	
//	public LogFile getLogFileById(int logFileId) {
//		for(LogFile logFile : this.logFiles) {
//			if(logFile.getId() == logFileId) {
//				return logFile;
//			}			
//		}
////----->TODO: Optional ?
//		return new LogFile();
//	}
//	
//	public ArrayList<LogFile> getAllLogFiles(){
//		return logFiles;
//	}
//	
//	public String deleteLogFile(int logFileId) {
////----->TODO: DeleteFile()
//		LogFile logFileToDelete = null;
//		String fileName = null;
//		// Prüfe Logfiles auf passende ID
//		for(LogFile logFile : this.logFiles) {
//			if(logFile.getId() == logFileId) {
//				logFileToDelete = logFile;
//				fileName = logFile.getFileName();
//			}
//		}
//		this.logFiles.remove(logFileToDelete);
//		
//		return fileName;
//	}
//	
//	
//	// LogFile hochladen, lokal speichern, Objekt erstellen
//	public LogFile uploadLogFile(MultipartFile file,
//								String folder, 
//								String info) {		
//		
////		String message = ""; // return message
//		
//		String fileName = file.getOriginalFilename();
//		Path directoryPath = Paths.get(uploadFolderDefault + "\\" + folder);
//		Path filePath = Paths.get(uploadFolderDefault + "\\" + folder + "\\" + fileName);
//		
//		// Falls Verzeichnis nicht existiert -> Erstelle Verzeichnis
//		if(!Files.exists(directoryPath)) {
//			try {
//				Files.createDirectories(directoryPath);
//			} catch (IOException e) {
//				e.printStackTrace();
////				message = "Directory Creation failed";
//			}
//		}
//		
////-----> TODO: Prüfe, ob Datei mit gleichem Namen bereits vorhanden (Benutzer entscheidet / Laufindex (i))
//		
//		// Schreibe Datei in Verzeichnis		
//		try {
//			byte[] bytes = file.getBytes();	
//			Files.write(filePath, bytes);			
////			message = "You successfully uploaded '" + fileName + "'";					
//		} catch (IllegalStateException | IOException e) {
//			e.printStackTrace();
////			message = "Upload failed";
//		}
//		
//		// Erstelle LogFile Objekt
//		LogFile logFile = new LogFile(info, fileName ,filePath.toString(), LocalDateTime.now());
//		
//		// Setze ID und erhöhe 'nextId'
//		logFile.setId(this.nextLogFileId);
//		nextLogFileId++;
//		
//		this.logFiles.add(logFile);			
//		
//		return logFile;
//	}
//	
//	// Schreibe die Zeilen aus dem File in die ArrayList 'lines' 
//	public void saveLogFileLinesInArray(LogFile logFile) throws IOException {
//		BufferedReader buffReader = null;
//		try {
//			Reader reader = new FileReader(new File(logFile.getPath()));
//			buffReader = new BufferedReader(reader);
//			String line;
//			while((line = buffReader.readLine()) != null) {
//				logFile.addLine(line);
//			}
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally {
//			if(buffReader != null) {
//				buffReader.close();
//			}
//		}		
//	}
//	
//	// Prüfe die Zeilen aus 'lines' gegen das FAQ und schreibe Matches in ArrayList 'matches' 
//	public void checkLogFileMatches(LogFile logFile) {
//		int lineIndex = 1;
//		for(Iterator<String> i = logFile.getLines().iterator(); i.hasNext();) {
//			String line = i.next();
//			ArrayList<Help> matches = this.helpService.checkLineForMatches(line);
//			if (matches.size() > 0) {
//				for (Help help : matches) {
//					logFile.addMatch(new Match(logFile.getId(), lineIndex, line, help));
//				}					
//			}
//			lineIndex++;
//		}
//	}
	
	// Static Methods
	
	
	public static long checkLogFileSize(MultipartFile file) {		
		return file.getSize();
	}
	
	public static String checkLogFileType(MultipartFile file) {	
		return file.getContentType();
	}
	

	
	
}