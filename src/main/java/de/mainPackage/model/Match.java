package de.mainPackage.model;

import lombok.Data;

@Data
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

	
}