package com.degree.cto.controllers;

import com.degree.cto.dtos.ReviewsDTO;
import com.degree.cto.dtos.UsersDTO;
import com.degree.cto.logic.Log.LogService;
import com.degree.cto.repositorys.OrdersRepository;
import com.degree.cto.repositorys.TransactionsInfoRepository;
import com.degree.cto.repositorys.UsersRepository;
import com.degree.cto.security.SecurityService;
import com.degree.cto.services.PersonalPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PersonalPageController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PersonalPageService personalPageService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TransactionsInfoRepository transactionsInfoRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private LogService logService;

    @GetMapping("/personal-page")
    public String personalsPage(HttpServletRequest request) {
        return securityService.profileCheck("/personal-page", "redirect:/@" + request.getUserPrincipal().getName(), request.getUserPrincipal().getName());
    }

    @GetMapping("/@{username}")
    public String personalPage(@PathVariable(value = "username") String username, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("EditAccess", personalPageService.editAccess(request.getUserPrincipal().getName(), username));

            UsersDTO usersDTO = usersRepository.findByPersonalIndent(username);
            model.addAttribute("nameUser", usersDTO.getName());
            model.addAttribute("emailUser", usersDTO.getEmail());
            model.addAttribute("usernameUser", usersDTO.getPersonalIndent());
            model.addAttribute("phoneUser", usersDTO.getPhone());
            model.addAttribute("photoUser", usersDTO.getPhoto_url());
            model.addAttribute("statusUser", usersDTO.getStatus());

            model.addAttribute("TransactionsInfoList", transactionsInfoRepository.findAllByUsername(username));

            model.addAttribute("OrdersForAccountList", ordersRepository.findAllByUsername(username));

            return "personal-page";
        } catch (Exception exception) {
            return personalPageService.pageNotFond(username);
        }
    }

    @PostMapping("/@{username}")
    public String personalPage(@ModelAttribute("reviewsDTO") ReviewsDTO reviewsDTO, @PathVariable(value = "username") String username, HttpServletRequest request, Model model) {
        model.addAttribute("EditAccess", personalPageService.editAccess(request.getUserPrincipal().getName(), username));

        UsersDTO usersDTO = usersRepository.findByPersonalIndent(username);
        model.addAttribute("nameUser", usersDTO.getName());
        model.addAttribute("emailUser", usersDTO.getEmail());
        model.addAttribute("usernameUser", usersDTO.getPersonalIndent());
        model.addAttribute("phoneUser", usersDTO.getPhone());
        model.addAttribute("photoUser", usersDTO.getPhoto_url());
        model.addAttribute("statusUser", usersDTO.getStatus());

        model.addAttribute("TransactionsInfoList", transactionsInfoRepository.findAllByUsername(username));

        model.addAttribute("OrdersForAccountList", ordersRepository.findAllByUsername(username));

        if (reviewsDTO.getText().isEmpty()) {
            model.addAttribute("ReviewStatus", "Помилка відгук пустий!");
            return "personal-page";
        } else {
            personalPageService.createReviews(reviewsDTO, request.getUserPrincipal().getName());
            model.addAttribute("ReviewStatus", "Дякуємо за відгук!");
            logService.addLog("log", "Відгук", "Новий відгук", "Користувач:@" + username + " Створив новий відгук: " + reviewsDTO.getText());
            return "personal-page";
        }
    }

    @PostMapping("/@{username}/edit")
    public String personalPageEdit(@ModelAttribute("usersDTO") UsersDTO  usersDTO, @PathVariable(value = "username") String username) {
        UsersDTO dto = usersRepository.findByPersonalIndent(username);
        dto.setName(usersDTO.getName());
        dto.setPhone(usersDTO.getPhone());
        dto.setEmail(usersDTO.getEmail());
        usersRepository.save(dto);
        logService.addLog("log", "Користувачі", "Зміна особистої інформації", "Користувач:@"+ username + " змінив особисту інформацію.");
        return "redirect:/@" + username;
    }
}
