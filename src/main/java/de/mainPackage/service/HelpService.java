package de.mainPackage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mainPackage.model.Help;
import de.mainPackage.repository.HelpRepository;

@Service
public class HelpService{
	
	@Autowired
	private HelpRepository helpRepo;
	
	
	
	public void createHelp(Help help) {
		this.helpRepo.save(help);
	}
	
	@SuppressWarnings("deprecation")
	public Help getHelpById(int id) {
		return this.helpRepo.getById(id);
	}
	
	public List<Help> getAllHelps(){
		return this.helpRepo.findAll();
	}	
	
	
	// Prüfe Logfile-Eintrag gegen FAQ
	public ArrayList<Help> checkLineForMatches(String line){
		// Liste aller Hilfen aus FAQ
		List<Help> helpList = this.helpRepo.findAll();
		// Liste aller Hilfen der Matches
		ArrayList<Help> matchedHelpList = new ArrayList<Help>();
		for(Help help : helpList) {
			Pattern pattern = Pattern.compile(help.getRegEx());
			Matcher matcher = pattern.matcher(line);
			if (matcher.matches()) {
				matchedHelpList.add(help);
			}
		}
		return matchedHelpList;
	}
	
	
	// TEST-Patterns
	public void createTestHelps() {
		ArrayList<Help> helpList = new ArrayList<Help>();
		helpList.add(new Help(".* INFO .*", "Hilfestellung 01 - Dieser Eintrag ist eine Information", "www.link1.de"));
		helpList.add(new Help(".* ERROR .*", "Hilfestellung 02 - Dieser Eintrag ist eine Fehlermeldung", "www.link2.de"));
		helpList.add(new Help(".* TEST .*", "Hilfestellung 03 - Dieser Eintrag ist ein Test", "www.link3.de"));
		this.helpRepo.saveAll(helpList);
	}
	
}