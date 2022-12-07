package de.mainPackage.service;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.mainPackage.model.Help;
import de.mainPackage.repository.HelpRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HelpService{
	
	@Autowired
	private HelpRepository helpRepo;
	
	
	@Transactional
	public Help createHelp(Help help) {
		log.info("Creating new Help...");
		Help newHelp = this.helpRepo.save(help);
		log.info(String.format("New Help created (id=%s)" , newHelp.getId()));
		return newHelp;
	}
		
	public Help getHelpById(int id) {
		return this.helpRepo.getReferenceById(id);
	}
	
	public List<Help> getAllHelps(){
		return this.helpRepo.findAll();
	}	
	
	@Transactional
	public Help updateHelp(int helpId, String regEx, String helpText, String link) {
		log.info(String.format("Updating Help (id=%s) ..." , helpId));
		Help oldHelp = this.getHelpById(helpId);
		oldHelp.setRegEx(regEx);
		oldHelp.setHelpText(helpText);
		oldHelp.setLink(link);
		Help updatedHelp = this.helpRepo.save(oldHelp);		
		log.info(String.format("Successfully updated Help (id=%s)" , helpId));		
		return updatedHelp;
	}
	
	@Transactional
	public Help deleteHelp(int helpToDeleteId) {
		Help HelpToDelete = this.helpRepo.getReferenceById(helpToDeleteId);
		log.info(String.format("Deleting Help (id=%s) ..." , HelpToDelete.getId()));
		this.helpRepo.deleteById(helpToDeleteId);
		log.info(String.format("Help successfully deleted (id=%s)", HelpToDelete.getId()));
		return HelpToDelete;
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
	
	// ---------------------------- TESTING -------------------------
	
	// TEST-Patterns erstellen
	public void createTestHelps() {
		ArrayList<Help> helpList = new ArrayList<Help>();
		helpList.add(new Help(".* INFO .*", "Hilfestellung 01 - Dieser Eintrag ist eine Information", "www.link1.de"));
		helpList.add(new Help(".* ERROR .*", "Hilfestellung 02 - Dieser Eintrag ist eine Fehlermeldung", "www.link2.de"));
		helpList.add(new Help(".* TEST .*", "Hilfestellung 03 - Dieser Eintrag ist ein Test", "www.link3.de"));
		this.helpRepo.saveAll(helpList);
	}
	
}