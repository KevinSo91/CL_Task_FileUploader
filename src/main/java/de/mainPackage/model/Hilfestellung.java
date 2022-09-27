package de.mainPackage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="faq")
public class Hilfestellung{
	
	// Attributes
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="hilfestellung_id")
	private int id;
	
	// Treffer-Pattern
	private String regEx;
	
	@Column(columnDefinition="TEXT")
	private String hilfestellung;
	
	private String link;

	public String getRegEx() {
		return regEx;
	}

	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}