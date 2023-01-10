package de.mainPackage.model;

import lombok.Data;

@Data
public class Match{
	
	
	private int id;
	
	private int logFileId;
	private long lineNr;
	private String lineText;
	private Help help;
	
		
	
	public Match(int logFileId, long lineNr, String lineText, Help help) {
		this.lineNr = lineNr;
		this.lineText = lineText;
		this.help = help;
	}

	@Override
	public String toString() {
		String matchAsString;
		matchAsString = "LogfileLine: " + this.lineText + "\nHelpText: " + this.help.getHelpText();		
		return matchAsString;
	}
	
	
}