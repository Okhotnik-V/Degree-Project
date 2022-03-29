package com.degree.cto.controllers;

import com.degree.cto.logic.Log.FindLogDTO;
import com.degree.cto.logic.Log.LogService;
import com.degree.cto.repositorys.UsersRepository;
import com.degree.cto.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DirectorPageController {

    @Autowired
    private LogService logService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/director")
    public String director(Model model, HttpServletRequest request) {
        if (securityService.customAccess("/director", request.getUserPrincipal().getName(), "Директор", "Директор") != "redirect:/personal-page") {
            //<header>
            try {
                if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Менеджер")){model.addAttribute("TeamAccess", "yes"); model.addAttribute("TeamAccessCRM", "yes");}
                if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Рекрутер")){model.addAttribute("TeamAccess", "yes") ;model.addAttribute("TeamAccessTeam", "yes");}
                if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Бухгалтер")){model.addAttribute("TeamAccess", "yes"); model.addAttribute("TeamAccessAcc", "yes");}
                if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Директор")){model.addAttribute("TeamAccess", "yes"); model.addAttribute("TeamAccessDirector", "yes");}
            } catch (NullPointerException e){}
            //</header>

            model.addAttribute("DateLogStart", "Від першого");
            model.addAttribute("DateLogEnd", "До останнього");
            model.addAttribute("Logs", logService.findFilterList(null));
            return securityService.customAccess("director-page", request.getUserPrincipal().getName(), "Директор", "Директор");
        } else {
            return "redirect:/personal-page";
        }
    }

    @PostMapping("/director")
    public String director(@ModelAttribute(value = "findLogDTO") FindLogDTO findLogDTO, Model model, HttpServletRequest request) {
        if (securityService.customAccess("/director", request.getUserPrincipal().getName(), "Директор", "Директор") != "redirect:/personal-page") {
            //<header>
            try {
                if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Менеджер")){model.addAttribute("TeamAccess", "yes"); model.addAttribute("TeamAccessCRM", "yes");}
                if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Рекрутер")){model.addAttribute("TeamAccess", "yes") ;model.addAttribute("TeamAccessTeam", "yes");}
                if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Бухгалтер")){model.addAttribute("TeamAccess", "yes"); model.addAttribute("TeamAccessAcc", "yes");}
                if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Директор")){model.addAttribute("TeamAccess", "yes"); model.addAttribute("TeamAccessDirector", "yes");}
            } catch (NullPointerException e){}
            //</header>
            if (findLogDTO.getDate() != "") {
                model.addAttribute("DateLogStart", "Вибрано день");
                model.addAttribute("DateLogEnd", findLogDTO.getDate());
                model.addAttribute("Logs", logService.findFilterList(findLogDTO.getDate()));
            } else {
                model.addAttribute("DateLogStart", "Від першого");
                model.addAttribute("DateLogEnd", "До останнього");
                model.addAttribute("Logs", logService.findFilterList(null));
            }
            return securityService.customAccess("director-page", request.getUserPrincipal().getName(), "Директор", "Директор");
        } else {
            return "redirect:/personal-page";
        }
    }
}
