package de.mainPackage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mainPackage.model.Help;
import de.mainPackage.repository.HelpRepository;

@Service
public class HelpService{
	
	// Attributes
	
	@Autowired
	private HelpRepository helpRepo;
	
	
	// Methods
	
	
	public List<Help> getAllHelps(){
		return this.helpRepo.findAll();
	}
	
	
	public void createHelp(Help help) {
		this.helpRepo.save(help);
	}
	
	
	// TEST
	public void createTestHelps() {
		ArrayList<Help> helpList = new ArrayList<Help>();
		helpList.add(new Help(".* INFO .*", "Hilfestellung 01 - Dieser Eintrag ist eine Information", "www.link.de"));
		helpList.add(new Help(".* ERROR .*", "Hilfestellung 02 - Dieser Eintrag ist eine Fehlermeldung", "www.link.de"));
		helpList.add(new Help(".* TEST .*", "Hilfestellung 03 - Dieser Eintrag ist ein Test", "www.link.de"));
		this.helpRepo.saveAll(helpList);
	}
	
}