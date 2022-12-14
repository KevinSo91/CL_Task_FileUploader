package de.mainPackage.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="logfiles_lines")
public class LogFileLine {
	
	// Attributes
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="line_id")
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="logfile_id", referencedColumnName = "logfile_id", nullable=false)
	private LogFile logFile;
	
	@Column(columnDefinition="TEXT")
	private String line;
	
	private long lineNumber;
	
	@ManyToMany
	@JoinTable(
			name = "logfilelines_matches",
			joinColumns = @JoinColumn(name = "logfileline_id"),
			inverseJoinColumns = @JoinColumn(name = "help_id")
			)
	private Set<Help> matches = new HashSet<>();
	
	// Constructors
	
	
	public LogFileLine() {		
	}
	
	public LogFileLine(String line) {
		this.line = line;
	}
	
	public LogFileLine(LogFile logFile, String line, long lineNumber) {
		this.logFile = logFile;
		this.line = line;
		this.lineNumber = lineNumber;
	}
	
		
	// Getter / Setter
		

	public LogFile getLogFile() {
		return logFile;
	}

	public void setLogFile(LogFile logFile) {
		this.logFile = logFile;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public long getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getId() {
		return id;
	}
	
	
	// Methods
	
	
	public void addMatch(Help help) {
		this.matches.add(help);
	}

}
