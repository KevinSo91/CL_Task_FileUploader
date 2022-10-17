package de.mainPackage.model;

public class Match{
	
	// Attributes
	
	
	private int id;
	
	private long lineNr;
	private String lineText;
	private Help help;
	
	
	
	// Constructors
	
	
	public Match(long lineNr, String lineText, Help help) {
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
	
	
}