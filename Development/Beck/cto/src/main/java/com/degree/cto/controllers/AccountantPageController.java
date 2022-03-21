package com.degree.cto.controllers;

import com.degree.cto.dtos.OrdersDTO;
import com.degree.cto.dtos.TransactionsInfoDTO;
import com.degree.cto.logic.Log.LogService;
import com.degree.cto.repositorys.TransactionsInfoRepository;
import com.degree.cto.security.SecurityService;
import com.degree.cto.services.AccountantPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountantPageController {

    @Autowired
    private LogService logService;

    @Autowired
    private AccountantPageService accountantPageService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/accountant")
    public String accountantPage(Model model, HttpServletRequest request) {
        long[] moneyInfo = accountantPageService.ordersDTOListFindAndTransactionsMoney(accountantPageService.defaultDate());
        model.addAttribute("Date", accountantPageService.defaultDate());
        model.addAttribute("Profit", moneyInfo[0]);
        model.addAttribute("OrderSize", moneyInfo[1]);
        model.addAttribute("Costs", moneyInfo[2]);
        model.addAttribute("NetPrice", moneyInfo[0] - moneyInfo[2]);

        model.addAttribute("Transactions", accountantPageService.transactionsInfoDTOS(accountantPageService.defaultDate()));
        model.addAttribute("TransactionsOrders", accountantPageService.ordersDTOList(accountantPageService.defaultDate()));
        return securityService.customAccess("accountant-page", request.getUserPrincipal().getName(), "Бухгалтер", "Директор");
    }

    @PostMapping("/accountant")
    public String accountantPage(@ModelAttribute(value = "infoDto") TransactionsInfoDTO infoDTO , Model model, HttpServletRequest request) {
        long[] moneyInfo = accountantPageService.ordersDTOListFindAndTransactionsMoney(infoDTO.logicDate);
        model.addAttribute("Date", infoDTO.logicDate);
        model.addAttribute("Profit", moneyInfo[0]);
        model.addAttribute("OrderSize", moneyInfo[1]);
        model.addAttribute("Costs", moneyInfo[2]);
        model.addAttribute("NetPrice", moneyInfo[0] - moneyInfo[2]);

        model.addAttribute("Transactions", accountantPageService.transactionsInfoDTOS(infoDTO.logicDate));
        model.addAttribute("TransactionsOrders", accountantPageService.ordersDTOList(infoDTO.logicDate));

        return securityService.customAccess("accountant-page", request.getUserPrincipal().getName(), "Бухгалтер", "Директор");
    }

    @PostMapping("/accountant/addCoast")
    public String accountantAddCoast(@ModelAttribute(value = "tDTO") TransactionsInfoDTO tDTO, HttpServletRequest request, Model model) {
        if (securityService.customAccess("accountant-page", request.getUserPrincipal().getName(), "Бухгалтер", "Директор") != "redirect:/personal-page") {
            if (accountantPageService.addCoast(tDTO, request.getUserPrincipal().getName())) {
                logService.addLog("log", "Виплата", tDTO.getType(), "Виконано виплату №" + tDTO.getNumber() + ". Cума:" + tDTO.money);
                return "redirect:/accountant";
            } else {
                model.addAttribute("Error", "Помилка: Потрібно заповнити усі поля!");
                return securityService.customAccess("accountant-page", request.getUserPrincipal().getName(), "Бухгалтер", "Директор");
            }
        } else {
            return "redirect:/personal-page";
        }
    }
}
