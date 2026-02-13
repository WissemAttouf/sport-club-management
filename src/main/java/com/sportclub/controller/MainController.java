package com.sportclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Accueil");
        model.addAttribute("page", "home");
        return "index";  // Retourne index.html
    }

    @GetMapping("/members")
    public String members(Model model) {
        model.addAttribute("title", "Gestion des Membres");
        model.addAttribute("page", "members");
        return "members";  // Retourne members.html
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "À propos");
        model.addAttribute("page", "about");
        return "about";  // Retourne about.html
    }

    @GetMapping("/activities")
    public String activities(Model model) {
        model.addAttribute("title", "Activités Sportives");
        model.addAttribute("page", "activities");
        return "activities";  // Retourne activities.html
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contact");
        model.addAttribute("page", "contact");
        return "contact";  // Retourne contact.html
    }
}