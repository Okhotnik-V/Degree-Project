package com.degree.cto.controllers;

import com.degree.cto.logic.Log.FindLogDTO;
import com.degree.cto.logic.Log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DirectorPageController {

    @Autowired
    private LogService logService;

    @GetMapping("director")
    public String director(Model model) {
        model.addAttribute("DateLogStart", "Від першого");
        model.addAttribute("DateLogEnd", "До останнього");
        model.addAttribute("Logs", logService.findFilterList(null));
        return "director-page";
    }

    @PostMapping("director")
    public String director(@ModelAttribute(value = "findLogDTO") FindLogDTO findLogDTO, Model model) {
        if (findLogDTO.getDate() != "") {
            model.addAttribute("DateLogStart", "Вибрано день");
            model.addAttribute("DateLogEnd", findLogDTO.getDate());
            model.addAttribute("Logs", logService.findFilterList(findLogDTO.getDate()));
        } else {
            model.addAttribute("DateLogStart", "Від першого");
            model.addAttribute("DateLogEnd", "До останнього");
            model.addAttribute("Logs", logService.findFilterList(null));
        }
        return "director-page";
    }
}
