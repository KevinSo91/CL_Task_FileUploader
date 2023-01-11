package de.mainPackage.model;

import lombok.Data;

@Data
public class Match{
	
	
	private int id;
	
	private int logFileId;
	private long lineNr;
	private String lineText;
	private Help help;
	private String exportText;
		
	
	public Match(int logFileId, long lineNr, String lineText, Help help) {
		this.lineNr = lineNr;
		this.lineText = lineText;
		this.help = help;
		this.exportText = this.toExportFormat(lineText, help);
	}

//	@Override
//	public String toString() {
//		String matchAsString;
//		matchAsString = "LogfileLine: " + this.lineText + "\nHelpText: " + this.help.getHelpText();		
//		return matchAsString;
//	}
	
	public String toExportFormat(String lineText, Help help) {
		String exportText;		
		exportText = String.format("Logfile Zeile:\n%1$s\n\nHilfestellung:\n%2$s\n\nWeiterführender Link:\n%3$s", lineText, help.getHelpText(), help.getLink());
		
		return exportText;
	}
	
	
}