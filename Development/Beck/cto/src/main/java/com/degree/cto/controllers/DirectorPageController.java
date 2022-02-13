package com.degree.cto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DirectorPageController {

    @GetMapping("director")
    public String director(Model model) {
        return "director-page";
    }
}
