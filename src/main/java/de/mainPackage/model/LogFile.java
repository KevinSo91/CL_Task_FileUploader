package de.mainPackage.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogFile{	

	private int id;
		
	private String info;
	private String fileName;
	private String path;
	private LocalDateTime uploadTime;

	private ArrayList<String> lines = new ArrayList<String>();
	private ArrayList<Match> matches = new ArrayList<Match>();
	
	
		
	public LogFile(String info, String fileName, String path, LocalDateTime uploadTime) {
		
		this.info = info;
		this.fileName = fileName;
		this.path = path;
		this.uploadTime = uploadTime;
	}
	
	
	
	public void addLine(String line) {
		this.lines.add(line);
	}
		
	public void addMatch(Match match) {
		this.matches.add(match);
	}
	
	public void deleteAllMatches() {
		this.matches.clear();
	}
		
	
}