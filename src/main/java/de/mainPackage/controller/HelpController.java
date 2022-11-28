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
		model.addAttribute("activePage", "help");
		this.helpService.createHelp(newHelp);
		return "redirect:/help/all";
	}
	
	@PostMapping("/help/deleteHelp")
	public String deleteHelp(Model model, @RequestParam("helpToDeleteId") int helpToDeleteId) {
		model.addAttribute("activePage", "help");
		this.helpService.deleteHelp(helpToDeleteId);
		return "redirect:/help/all";
	}
	
	@PostMapping("/help/updateHelpForm")
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
	
	
	@PostMapping("/help/updateHelp")
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
	
//	@PostMapping("/help/updateHelp")
//	public String updateHelp(Model model, @ModelAttribute("helpToUpdate") Help helpUpdated
//							) {
//		
//		this.helpService.updateHelp(helpUpdated.getId(), helpUpdated.getRegEx(), helpUpdated.getHelpText(), helpUpdated.getLink());
//		
//		return "redirect:/help/all";
//	}
	
	
	
	
	
	
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