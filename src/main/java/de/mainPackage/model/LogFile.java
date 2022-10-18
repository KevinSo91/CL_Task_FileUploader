package de.mainPackage.model;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class LogFile{	
	
	// Attributes
	
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String person;	
	private String email;	
	private String info;
	private String fileName;
	private String path;
	private LocalDateTime uploadTime;
//	private boolean isScanned = false;	

	private ArrayList<String> lines = new ArrayList<String>();
	private ArrayList<Match> matches = new ArrayList<Match>();
					
	
	// Constructors
	
	
	public LogFile() {//		
	}
		
	public LogFile(String user, String email, String info, String fileName, String path, LocalDateTime uploadTime) {
		this.person = user;
		this.email = email;
		this.info = info;
		this.fileName = fileName;
		this.path = path;
		this.uploadTime = uploadTime;
	}
			
	
	// Getter / Setter
	
	
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public LocalDateTime getUploadTime() {
		return uploadTime;
	}

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
		
	
	// Methods
		
	
	
	
	
}