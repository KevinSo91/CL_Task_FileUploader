package de.mainPackage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.mainPackage.model.Help;
import de.mainPackage.service.HelpService;

@Controller
@RequestMapping("/help")
public class HelpController{
	
	
	@Autowired
	private HelpService helpService;
		
	
	
	@GetMapping({"/", "/all"})
	public String getAllHelps(Model model, @ModelAttribute("newHelp") Help newHelp) {
		model.addAttribute("activePage", "help");
		model.addAttribute("helpList", this.helpService.getAllHelps());
		return "help";
	}
	
	@PostMapping("/createHelp")
	public String createNewHelp(Model model, @ModelAttribute("newHelp") Help newHelp) {
		model.addAttribute("activePage", "help");
		this.helpService.createHelp(newHelp);
		return "redirect:/help/all";
	}
	
	@PostMapping("/deleteHelp")
	public String deleteHelp(Model model, @RequestParam("helpToDeleteId") int helpToDeleteId) {
		model.addAttribute("activePage", "help");
		this.helpService.deleteHelp(helpToDeleteId);
		return "redirect:/help/all";
	}
	
	@PostMapping("/updateHelpForm")
	public String getUpdateHelp(Model model, @RequestParam("helpToUpdateId") int helpToUpdateId) {
		model.addAttribute("activePage", "help");
		Help helpToUpdate = this.helpService.getHelpById(helpToUpdateId);
		model.addAttribute("helpToUpdate", helpToUpdate);
		model.addAttribute("id", helpToUpdateId);
		model.addAttribute("regEx", helpToUpdate.getRegEx());
		model.addAttribute("helpText", helpToUpdate.getHelpText());
		model.addAttribute("link", helpToUpdate.getLink());
		return "updateHelp";
	}	
	
	@PostMapping("/updateHelp")
	public String updateHelp(Model model
							, @RequestParam(value="helpId") int helpId
							, @RequestParam(value="regEx", required=false) String helpRegEx
							, @RequestParam(value="helpText", required=false) String helpText
							, @RequestParam(value="link", required=false) String link
							) {
		model.addAttribute("activePage", "help");
		this.helpService.updateHelp(helpId, helpRegEx, helpText, link);
		
		return "redirect:/help/all";
	}
	
	
	
	// TESTING
	@PostMapping("/createTestPattern")
	public String createTestPattern() {
		helpService.createTestHelps();
		return "redirect:/help/all";
	}
	
	
	
	
}