package com.degree.cto.controllers;

import com.degree.cto.dtos.UsersDTO;
import com.degree.cto.logic.Log.LogService;
import com.degree.cto.repositorys.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TeamManagementController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LogService logService;

    @GetMapping("/team/management")
    public String teamManagement(Model model) {
        List<UsersDTO> usersDTOList = usersRepository.findByStatus("Працівник");
        model.addAttribute("teamListSize", usersDTOList.size());
        model.addAttribute("teamList", usersDTOList);
        return "team-management";
    }

    @PostMapping("/team/management/find")
    public String teamManagementFind(@ModelAttribute(value = "usersDTO") UsersDTO usersDTO, Model model) {
        UsersDTO usersDTLogic = usersRepository.findByPersonalIndent(usersDTO.getPersonalIndent());
        try {
            if (usersDTLogic == null && usersDTLogic.getStatus() != "Працівник") {
                model.addAttribute("Error", "Користувача не знайдено");
            } else {
                List<UsersDTO> usersDTOList = usersRepository.findByPersonalIndentAndStatus(usersDTO.getPersonalIndent(), "Працівник");
                model.addAttribute("teamListSize", usersDTOList.size());
                model.addAttribute("teamList", usersDTLogic);
            }
        } catch (Exception e) {
            model.addAttribute("Error", "Користувача не знайдено");
        }
        return "team-management";
    }

    @PostMapping("/team/management/add")
    public String teamManagementAdd(@ModelAttribute(value = "usersDTO") UsersDTO usersDTO, Model model) {
        try {
            UsersDTO usersDTOLogic = usersRepository.findByPersonalIndent(usersDTO.getPersonalIndent());
            usersDTOLogic.setStatus("Працівник");
            usersDTOLogic.setRole(usersDTO.getRole());
            int randomAvatar = 1 + (int) (Math.random() * 4);
            usersDTOLogic.setPhoto_url("/assets/img/avatars/teams/" + randomAvatar + ".JPG");
            usersRepository.save(usersDTOLogic);
            logService.addLog("log", "Працівники", "Додавання працівника", "Користувач:@" + usersDTO.getPersonalIndent() + " тепер працівник");
            return "redirect:/team/management";
        } catch (Exception e) {
            model.addAttribute("Error", "Користувача не знайдено");
            return "team-management";
        }
    }

    @GetMapping("/team/management/dell")
    public String teamManagementDell(@RequestParam String username) {
        UsersDTO usersDTOLogic = usersRepository.findByPersonalIndent(username);
        usersDTOLogic.setStatus("Клієнт");
        usersDTOLogic.setRole("Клієнт");
        usersRepository.save(usersDTOLogic);
        logService.addLog("log", "Працівники", "Видалення працівника", "Користувач:@" + username + " більше непрацівник");
        return "redirect:/team/management";
    }
}
