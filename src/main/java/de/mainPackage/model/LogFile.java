package de.mainPackage.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "logfiles")
public class LogFile{	
	
	// Attributes
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="logfile_id")
	private int id;
	
	private String person;	
	private String email;	
	private String info;
	private String fileName;
	private String path;
	private LocalDateTime uploadTime;
	private boolean isScanned;
	
	@JsonIgnore
	@OneToMany(mappedBy="logFile")
	private Set<LogFileLine> lines = new HashSet<LogFileLine>();
	
	@ManyToMany
	@JoinTable(
			name = "logfiles_matches",
			joinColumns = @JoinColumn(name = "logfile_id"),
			inverseJoinColumns = @JoinColumn(name = "help_id")
			)
	private Set<Help> matches = new HashSet<Help>();
				
	
	// Constructors
	
	
	public LogFile() {
		this.isScanned = false;
		this.lines = new HashSet<LogFileLine>();
		this.matches = new HashSet<Help>();
	}
		
	public LogFile(String user, String email, String info, String fileName, String path, LocalDateTime uploadTime) {
		this.person = user;
		this.email = email;
		this.info = info;
		this.fileName = fileName;
		this.path = path;
		this.uploadTime = uploadTime;
		this.isScanned = false;
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

	public boolean getIsScanned() {
		return isScanned;
	}

	public void setIsScanned(boolean isScanned) {
		this.isScanned = isScanned;
	}

	public int getId() {
		return id;
	}

	public Set<LogFileLine> getLines() {
		return lines;
	}
	
	public Set<Help> getMatches() {
		return matches;
	}
	
	
	// Methods
	
	
//	public void addLines() {
//		FileInputStream inputStream = null;
//		Scanner sc = null;
//		try {
//			inputStream = new FileInputStream(this.path);
//			sc = new Scanner(inputStream, "UTF-8");
//			while(sc.hasNextLine()) {
//				
//				String line = sc.nextLine();
//				this.lines.add(new LogFileLine(line));
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public void addHelp(Help help) {
		this.matches.add(help);
	}
	
	
}