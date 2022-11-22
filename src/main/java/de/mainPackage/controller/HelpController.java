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
@RequestMapping("/")
public class HelpController{
	
	// Attributes
	
	
	@Autowired
	private HelpService helpService;
	
	
	
	
	@GetMapping("/help/all")
	public String getAllHelps(Model model, @ModelAttribute("newHelp") Help newHelp) {
		model.addAttribute("activePage", "help");
		model.addAttribute("helpList", this.helpService.getAllHelps());
		return "help";
	}
	
	@PostMapping("/help/createHelp")
	public String createNewHelp(Model model, @ModelAttribute("newHelp") Help newHelp) {
		this.helpService.createHelp(newHelp);
		return "redirect:/help/all";
	}
	
	@GetMapping("/help/updateHelpForm")
	public String getUpdateHelp(Model model, @RequestParam("helpToUpdateId") Integer helpToUpdateId
								, @ModelAttribute("helpUpdated") Help helpUpdated
								) {
		model.addAttribute("helpToUpdate", this.helpService.getHelpById(helpToUpdateId));		
		
		//redirectAttributes
		return "updateHelp";
	}
	
	
	@PostMapping("/help/updateHelp")
	public String updateHelp(Model model, @ModelAttribute("helpUpdated") Help helpUpdated) {
		Help helpToEdit = this.helpService.getHelpById(helpUpdated.getId());		
		
		this.helpService.updateHelp(helpToEdit, helpUpdated.getRegEx(), helpUpdated.getHelpText(), helpUpdated.getLink());
		
		model.addAttribute("activePage", "help");
		model.addAttribute("helpList", this.helpService.getAllHelps());
		return "help";
	}
	
//	@GetMapping("/help/update")
//	public String getUpdateHelpPage(Model model, @RequestParam(value="helpToEditId") Integer helpToEditId)
//	{
//		model.addAttribute("oldHelpToEdit", this.helpService.getHelpById(helpToEditId));
//		return "updateHelp";
//	}
//	
//		
//	// Edit Help
//	@PostMapping("/help/update")
//	public String updateHelp(Model model,							
//							@ModelAttribute Help newHelpToEdit, 
//							RedirectAttributes redirectAttributes) {		
//		Help oldHelp = this.helpService.getHelpById(newHelpToEdit.getId());
//		this.helpService.updateHelp(oldHelp, newHelpToEdit.getRegEx(), newHelpToEdit.getHelpText(), newHelpToEdit.getLink());
//		redirectAttributes.addFlashAttribute("messageSuccess", "Successfully updated Help");
//		return "redirect:/help";
//	}	
	
	
	// TESTING
	@PostMapping("/help/createTestPattern")
	public String createTestPattern() {
		helpService.createTestHelps();
		return "redirect:/help/all";
	}
	
	
	
	
}