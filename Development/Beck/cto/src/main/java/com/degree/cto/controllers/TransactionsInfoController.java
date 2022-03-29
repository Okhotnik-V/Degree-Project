package com.degree.cto.controllers;

import com.degree.cto.dtos.TransactionsInfoDTO;
import com.degree.cto.logic.Log.LogService;
import com.degree.cto.repositorys.TransactionsInfoRepository;
import com.degree.cto.repositorys.UsersRepository;
import com.degree.cto.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TransactionsInfoController {

    @Autowired
    private TransactionsInfoRepository transactionsInfoRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/transactions/{numberTransactions}")
    public String transactions(@PathVariable(value = "numberTransactions") long numberTransactions, Model model, HttpServletRequest request) {
        //<header>
        try {
            if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Менеджер")){model.addAttribute("TeamAccess", "yes"); model.addAttribute("TeamAccessCRM", "yes");}
            if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Рекрутер")){model.addAttribute("TeamAccess", "yes") ;model.addAttribute("TeamAccessTeam", "yes");}
            if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Бухгалтер")){model.addAttribute("TeamAccess", "yes"); model.addAttribute("TeamAccessAcc", "yes");}
            if (usersRepository.findByPersonalIndent(request.getUserPrincipal().getName()).getRole().contentEquals("Директор")){model.addAttribute("TeamAccess", "yes"); model.addAttribute("TeamAccessDirector", "yes");}
        } catch (NullPointerException e){}
        //</header>
        TransactionsInfoDTO transactionsInfoDTO = transactionsInfoRepository.findByNumber(numberTransactions);
        model.addAttribute("Number", transactionsInfoDTO.getNumber());
        model.addAttribute("Type", transactionsInfoDTO.getType());
        model.addAttribute("Username", transactionsInfoDTO.getUsername());
        model.addAttribute("ClockWork", transactionsInfoDTO.getClocks_work());
        model.addAttribute("Info", transactionsInfoDTO.getInfo());
        model.addAttribute("FullInfo", transactionsInfoDTO.getFull_info());
        model.addAttribute("Money", transactionsInfoDTO.getMoney());
        model.addAttribute("UsernameCreator", transactionsInfoDTO.getUsernameCreator());
        model.addAttribute("CreatorRole", usersRepository.findByPersonalIndent(transactionsInfoDTO.getUsernameCreator()).getRole());
        model.addAttribute("Date", transactionsInfoDTO.getDate());
        if (securityService.customAccess("/accountant", request.getUserPrincipal().getName(), "Бухгалтер", "Директор") != "redirect:/personal-page") {
            model.addAttribute("EditAccess", "yes");
        } else {
            model.addAttribute("EditAccess", "no");
        }
        return "transactions-info";
    }

    @PostMapping("/transactions/{numberTransactions}/edit")
    public String transactionsEdit(@PathVariable(value = "numberTransactions") long numberTransactions, @ModelAttribute(value = "transactionsInfoDTO") TransactionsInfoDTO transactionsInfoDTO, HttpServletRequest request) {
        if (securityService.customAccess("/accountant", request.getUserPrincipal().getName(), "Бухгалтер", "Директор") != "redirect:/personal-page") {
            transactionsInfoDTO.setId(transactionsInfoRepository.findByNumber(numberTransactions).getId());
            transactionsInfoDTO.setNumber(numberTransactions);
            transactionsInfoRepository.save(transactionsInfoDTO);
            logService.addLog("log", "Виплати", "Змінено інформацію про виплату", "Виплата №" + numberTransactions + " працівником:@" + transactionsInfoRepository.findByNumber(numberTransactions).getUsernameCreator());
            return "redirect:/transactions/" + numberTransactions;
        } else {
            return "redirect:/personal-page";
        }
    }

    @GetMapping("/transactions/{numberTransactions}/dell")
    public String transactionsDell(@PathVariable(value = "numberTransactions") long numberTransactions, HttpServletRequest request) {
        if (securityService.customAccess("/accountant", request.getUserPrincipal().getName(), "Бухгалтер", "Директор") != "redirect:/personal-page") {
            logService.addLog("log", "Виплати", "Видалено інформацію про виплату", "Виплата №" + numberTransactions + " працівником:@" + transactionsInfoRepository.findByNumber(numberTransactions).getUsernameCreator());
            TransactionsInfoDTO transactionsInfoDTO = transactionsInfoRepository.findByNumber(numberTransactions);
            transactionsInfoRepository.delete(transactionsInfoDTO);
            return "redirect:/accountant/";
        } else {
            return "redirect:/personal-page";
        }
    }
}
