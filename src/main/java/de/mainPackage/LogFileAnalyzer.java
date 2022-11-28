package de.mainPackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class LogFileAnalyzer {
	
	public static void main(String[] args) {
		SpringApplication.run(LogFileAnalyzer.class, args);
		log.info("LogFileAnalyzer started");
		
	}
	
}
