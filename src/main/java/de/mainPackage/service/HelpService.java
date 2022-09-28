package de.mainPackage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mainPackage.repository.HelpRepository;

@Service
public class HelpService{
	
	@Autowired
	private HelpRepository helpRepo;
	
}