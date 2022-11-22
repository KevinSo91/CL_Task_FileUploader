package de.mainPackage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.mainPackage.model.Help;
import de.mainPackage.model.LogFile;
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
	
//	@Transactional
	public Help updateHelp(Help oldHelp, String regEx, String helpText, String link) {
		oldHelp.setRegEx(regEx);
		oldHelp.setHelpText(helpText);
		oldHelp.setLink(link);
		return this.helpRepo.save(oldHelp);
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