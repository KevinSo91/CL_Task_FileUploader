package de.mainPackage.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mainPackage.model.LogFile;
import de.mainPackage.model.LogFileLine;
import de.mainPackage.repository.LogFileLineRepository;
import de.mainPackage.repository.LogFileRepository;

@Service
public class LogFileLineService {

	// Attributes
	
	@Autowired
	private LogFileLineRepository lineRepo;
	@Autowired
	private LogFileRepository logFileRepo;
	
	// Constructors
	
//	public LogFileLineService(LogFileLineRepository lineRepo) {
//		this.lineRepo = lineRepo;		
//	}
	
	//Methods
	
	
	public LogFileLine createLogFileLine(LogFileLine logFileLine) {
		return this.lineRepo.save(logFileLine);
	}
	
	
	public List<LogFileLine> getAllLines() {
		return lineRepo.findAll();
	}
	
	@SuppressWarnings("deprecation")
	public LogFileLine getLogFileLineById(int logFileLineId) {
		return this.lineRepo.getById(logFileLineId);
	}
	
	public void saveLines(LogFile logFile) {
		FileInputStream inputStream = null;
		Scanner sc = null;
		ArrayList<LogFileLine> logFileLines = new ArrayList<LogFileLine>();
		try {
			inputStream = new FileInputStream(logFile.getPath());
			sc = new Scanner(inputStream, "UTF-8");
			long lineIndex = 0;
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				logFileLines.add(new LogFileLine(logFile, line, lineIndex));
//				this.lineRepo.save(new LogFileLine(logFile, line, lineIndex));
				lineIndex++ ;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		lineRepo.saveAll(logFileLines);
		logFile.setIsScanned(true);
		logFileRepo.save(logFile);
	}
	
}
