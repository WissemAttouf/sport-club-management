package com.sportclub.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "404");
                model.addAttribute("errorMessage", "Page non trouvée");
            } else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorCode", "403");
                model.addAttribute("errorMessage", "Accès interdit");
            } else {
                model.addAttribute("errorCode", statusCode);
                model.addAttribute("errorMessage", "Une erreur est survenue");
            }
        } else {
            model.addAttribute("errorCode", "500");
            model.addAttribute("errorMessage", "Erreur interne du serveur");
        }
        
        model.addAttribute("title", "Erreur");
        return "error";
    }
}