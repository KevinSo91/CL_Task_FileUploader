package de.mainPackage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mainPackage.repository.HilfestellungRepository;

@Service
public class HilfestellungService{
	
	@Autowired
	private HilfestellungRepository hilfestellungRepo;
	
}