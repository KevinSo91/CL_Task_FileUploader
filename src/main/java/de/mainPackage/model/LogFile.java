package de.mainPackage.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import lombok.Getter;

@Getter
public class LogFile{	
	
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	private String info;
	private String fileName;
	private String path;
	private LocalDateTime uploadTime;

	private ArrayList<String> lines = new ArrayList<String>();
	private ArrayList<Match> matches = new ArrayList<Match>();
					

	
	
	
	
	public LogFile() {//		
	}
		
	public LogFile(String info, String fileName, String path, LocalDateTime uploadTime) {
		
		this.info = info;
		this.fileName = fileName;
		this.path = path;
		this.uploadTime = uploadTime;
	}
	
	
	
	// Getter / Setter
		

//	public String getInfo() {
//		return info;
//	}

	public void setInfo(String info) {
		this.info = info;
	}

//	public String getFileName() {
//		return fileName;
//	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

//	public String getPath() {
//		return path;
//	}

	public void setPath(String path) {
		this.path = path;
	}

//	public LocalDateTime getUploadTime() {
//		return uploadTime;
//	}

	public void setUploadTime(LocalDateTime uploadTime) {
		this.uploadTime = uploadTime;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public ArrayList<String> getLines() {
		return lines;
	}
	
	public void addLine(String line) {
		this.lines.add(line);
	}

	public ArrayList<Match> getMatches() {
		return matches;
	}
	
	public void addMatch(Match match) {
		this.matches.add(match);
	}
	
	public void deleteAllMatches() {
		this.matches.clear();
	}
	
	
	
}