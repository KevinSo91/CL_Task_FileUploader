package de.mainPackage.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "logfiles")
public class LogFile{
	
	
	// Attributes
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String person;
	
	private String email;	
	private String info;
	private String path;
	
	private LocalDateTime upload_time;
	
	
	
	// Constructors
	
	public LogFile() {		
	}
		
	public LogFile(String user, String email, String info, String path, LocalDateTime upload_time) {
		this.person = user;
		this.email = email;
		this.info = info;
		this.path = path;
		this.upload_time = upload_time;
	}
			
	
	// Getter/Setter	
	
	public int getLogFileId() {
		return id;
	}

	public String getUser() {
		return person;
	}

	public void setUser(String user) {
		this.person = user;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public LocalDateTime getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(LocalDateTime upload_time) {
		this.upload_time = upload_time;
	}

	
	
	// Methods
	
	
	
	
	
}