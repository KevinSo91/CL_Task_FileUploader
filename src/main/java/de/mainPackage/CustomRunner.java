package de.mainPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import de.mainPackage.model.Help;
import de.mainPackage.service.HelpService;

@Component
public class CustomRunner implements CommandLineRunner{
	
	@Autowired
	private HelpService helpService;

	// Wird beim Start der Anwendung ausgeführt
	@Override
	public void run(String... args) throws Exception {
		helpService.createHelp(new Help(
				".*\\[\"Bitte geben Sie einen Wert in das erforderliche Feld \\\\u0027Diese Anfrage stellen für:\\\\u0027 ein\\.\\\\u0027\"].*", 
				"Lösung: \r\n"
				+ "die wahrscheinlichste Ursache ist eine Abweichung zwischen Loginnamen im Portal und in der Konnektor Datenbank.\r\n"
				+ "in diesem Fall: Username Portal: henry.koerner@volksbank-pirna.de ; Username lt. springIntern: koernerh\r\n"
				+ "\r\n"
				+ "Hier muss im Anschluss die SpringIntern Datenbank geupdated werden. Bloß nicht das Portal! Da der Kunde die Änderung nicht automatisch mitbekommt und sich dann nicht mehr einloggen kann."
				));
		
	}
	
}