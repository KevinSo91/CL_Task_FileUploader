package de.mainPackage.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	private ArrayList<String> lines;
	
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
		this.lines = new ArrayList<String>();
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
	
	public void setLines() {
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(this.path);
			sc = new Scanner(inputStream, "UTF-8");
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				this.lines.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Methods
	
	
	
	
	
}