package de.mainPackage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mainPackage.repository.LogFileLineRepository;

@Service
public class LogFileLineService {

	@Autowired
	private LogFileLineRepository lineRepo;
	
	public LogFileLineService(LogFileLineRepository lineRepo) {
		this.lineRepo = lineRepo;		
	}
	
}
