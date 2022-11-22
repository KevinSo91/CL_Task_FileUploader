package de.mainPackage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="faq")
public class Help{
	
	// Attributes
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="help_id")
	private int id;
	
	// Treffer-Pattern
	private String regEx;
	
	@Column(columnDefinition="TEXT")
	private String helpText;

	private String link;
	

	
	// Contructors
	
	
	public Help() {		
	}
	
	public Help(String regEx, String helpText) {
		super();
		this.regEx = regEx;
		this.helpText = helpText;		
	}
	
	public Help(String regEx, String helpText, String link) {
		super();
		this.regEx = regEx;
		this.helpText = helpText;
		this.link = link;
	}
	

	// Getter / Setter
	
	public int getId() {
		return id;
	}
	
	public String getRegEx() {
		return regEx;
	}

	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}