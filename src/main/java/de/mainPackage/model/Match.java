package de.mainPackage.model;

public class Match{
	
	// Attributes
	
	
	private int id;
	
	private int logFileId;
	private long lineNr;
	private String lineText;
	private Help help;
	
	
	
	// Constructors
	
	
	public Match(int logFileId, long lineNr, String lineText, Help help) {
		this.lineNr = lineNr;
		this.lineText = lineText;
		this.help = help;
	}

	
	
	// Getter / Setter
	

	public long getLineNr() {
		return lineNr;
	}

	public void setLineNr(long lineNr) {
		this.lineNr = lineNr;
	}

	public String getLineText() {
		return lineText;
	}

	public void setLineText(String lineText) {
		this.lineText = lineText;
	}

	public Help getHelp() {
		return help;
	}

	public void setHelp(Help help) {
		this.help = help;
	}

	public int getId() {
		return id;
	}

	public int getLogFileId() {
		return logFileId;
	}

	public void setLogFileId(int logFileId) {
		this.logFileId = logFileId;
	}
	
	
}