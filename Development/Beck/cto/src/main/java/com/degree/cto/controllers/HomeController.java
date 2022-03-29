package com.degree.cto.controllers;

import com.degree.cto.repositorys.PartnersRepository;
import com.degree.cto.repositorys.ReviewsRepository;
import com.degree.cto.repositorys.ServicesListRepository;
import com.degree.cto.repositorys.UsersRepository;
import com.degree.cto.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private PartnersRepository partnersRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ServicesListRepository servicesListRepository;
    @Autowired
    private HomeService homeService;


    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("Services", servicesListRepository.findAll());
        model.addAttribute("Team", homeService.managementList());
        model.addAttribute("Partners", partnersRepository.findAll());
        model.addAttribute("Reviews", homeService.reviewsGetList());
        try {
            request.getUserPrincipal().getName();
            model.addAttribute("Auth", "yes");
        } catch (NullPointerException e) {
            model.addAttribute("Auth", "no");
        }

        return "home";
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }
}
