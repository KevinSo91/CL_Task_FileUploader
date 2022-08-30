package de.mainPackage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="lines")
public class LogFileLine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="line_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="logfile_id")
	private LogFile logFile;
	
	private String line;
	
	
	public LogFileLine(String line) {
		this.line = line;
	}
	
	public LogFileLine(String line, LogFile file) {
		this.line = line;
		this.logFile = file;
	}

}
