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
	private String path;
	private LocalDateTime upload_time;
	
	@JsonIgnore
	@OneToMany(mappedBy="logFile")
	private Set<LogFileLine> lines = new HashSet<LogFileLine>();
				
	
	// Constructors
	
	
	public LogFile() {	
		this.lines = new HashSet<LogFileLine>();
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
		
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public int getId() {
		return id;
	}

	public Set<LogFileLine> getLines() {
		return lines;
	}

		
	// Methods
	
	
	public void addLines() {
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(this.path);
			sc = new Scanner(inputStream, "UTF-8");
			while(sc.hasNextLine()) {
				
				String line = sc.nextLine();
				this.lines.add(new LogFileLine(line));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
//	public void setLines(MultipartFile file) {
//		try {			
//			FileReader fileReader = new FileReader(file);
//			BufferedReader bufferedReader = new BufferedReader(fileReader);
//						
//			int i = 0;			
//			String zeile;
//			while ((zeile = bufferedReader.readLine()) != null) {
//				this.lines.add(zeile);
//				i++;
//			}
//			
//			fileReader.close();
//			bufferedReader.close();
//		
//		} catch (FileNotFoundException e) {			
//			System.out.println("Datei nicht vorhanden");
//		} catch (NumberFormatException e) {			
//			e.printStackTrace();
//		} catch (IOException e) {			
//			e.printStackTrace();
//		}
//	}
	
	
}