package de.mainPackage.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mainPackage.model.LogFile;
import de.mainPackage.model.LogFileLine;
import de.mainPackage.repository.LogFileLineRepository;

@Service
public class LogFileLineService {

	@Autowired
	private LogFileLineRepository lineRepo;
	
	
	
	public LogFileLineService(LogFileLineRepository lineRepo) {
		this.lineRepo = lineRepo;		
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
