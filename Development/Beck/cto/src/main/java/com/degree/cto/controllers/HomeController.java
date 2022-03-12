package com.degree.cto.controllers;

import com.degree.cto.repositorys.PartnersRepository;
import com.degree.cto.repositorys.ReviewsRepository;
import com.degree.cto.repositorys.ServicesListRepository;
import com.degree.cto.repositorys.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private PartnersRepository partnersRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ServicesListRepository servicesListRepository;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("Services", servicesListRepository.findAll());
        model.addAttribute("Team", usersRepository.findByStatus("Працівник"));
        model.addAttribute("Partners", partnersRepository.findAll());
        model.addAttribute("Reviews", reviewsRepository.findAll());
        return "home";
    }
}
