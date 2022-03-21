package com.degree.cto.controllers;

import com.degree.cto.dtos.*;
import com.degree.cto.logic.OrderStatusDAO;
import com.degree.cto.logic.Log.LogService;
import com.degree.cto.repositorys.*;
import com.degree.cto.security.SecurityService;
import com.degree.cto.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersWorkTeamRepository ordersWorkTeamRepository;

    @Autowired
    private OrdersCheckRepository ordersCheckRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStatusDAO autoCreateCollections;

    @Autowired
    private LogService logService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/order/{numberOrder}")
    public String order(@PathVariable(value = "numberOrder") long numberOrder, Model model, HttpServletRequest request) {
        OrdersDTO ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        List<OrdersCheckDTO> ordersCheckDTOList = ordersCheckRepository.findAllByNumberOrder(numberOrder);
        List<OrdersWorkTeamDTO> ordersWorkTeamDTOList = ordersWorkTeamRepository.findAllByNumberOrder(numberOrder);
        UsersDTO usersDTO =  usersRepository.findByPersonalIndent(ordersDTO.getUsername());
        model.addAttribute("NumberOrder", numberOrder);
        model.addAttribute("UserName", usersDTO.getName());
        model.addAttribute("UserIndent", usersDTO.getPersonalIndent());
        model.addAttribute("UserPhoneNumber", usersDTO.getPhone());
        model.addAttribute("UserEmail", usersDTO.getEmail());
        model.addAttribute("BrandCar", ordersDTO.getBrandCar());
        model.addAttribute("ModelCar", ordersDTO.getModelCar());
        model.addAttribute("YearsCar", ordersDTO.getYearsCar());
        model.addAttribute("Service", ordersDTO.getService());
        model.addAttribute("Price", ordersDTO.getPrice());
        model.addAttribute("DateCreateOrder", ordersDTO.getDateCreateOrder());
        model.addAttribute("DateWork", ordersDTO.getDateWork());
        model.addAttribute("PhoneCall", ordersDTO.getPhoneCall());
        model.addAttribute("Message", ordersDTO.getMessage());
        model.addAttribute("StatusWork", ordersDTO.getStatusWork());

        model.addAttribute("OrdersCheckDTOList", ordersCheckDTOList);
        model.addAttribute("ordersWorkTeamDTOList", ordersWorkTeamDTOList);

        return "order";
    }

    @GetMapping("/order/{numberOrder}/edit")
    public String orderEdit(@PathVariable(value = "numberOrder") long numberOrder, Model model, HttpServletRequest request) {
        autoCreateCollections.CheckCollectionsOrderStatus();

        OrdersDTO ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        List<OrdersCheckDTO> ordersCheckDTOList = ordersCheckRepository.findAllByNumberOrder(numberOrder);
        List<OrdersWorkTeamDTO> ordersWorkTeamDTOList = ordersWorkTeamRepository.findAllByNumberOrder(numberOrder);
        model.addAttribute("editSaveLink", "/order/" + numberOrder + "/edit");
        model.addAttribute("editSaveLinkTeamAdd", "/order/" + numberOrder + "/edit/team/add");
        model.addAttribute("editSaveLinkCheckAdd", "/order/" + numberOrder + "/edit/check/add");
        model.addAttribute("OrderNumber", numberOrder);
        model.addAttribute("BrandCar", ordersDTO.getBrandCar());
        model.addAttribute("ModelCar", ordersDTO.getModelCar());
        model.addAttribute("YearsCar", ordersDTO.getYearsCar());
        model.addAttribute("Service", ordersDTO.getService());
        model.addAttribute("Price", ordersDTO.getPrice());
        model.addAttribute("DateCreateOrder", ordersDTO.getDateCreateOrder());
        model.addAttribute("DateWork", ordersDTO.getDateWork());
        model.addAttribute("PhoneCall", ordersDTO.getPhoneCall());
        model.addAttribute("Message", ordersDTO.getMessage());
        model.addAttribute("StatusWork", ordersDTO.getStatusWork());
        model.addAttribute("Archive", ordersDTO.getArchive());
        model.addAttribute("NetProfit", ordersDTO.getNetProfit());

        model.addAttribute("StatusWorkList", orderStatusRepository.findAll());

        model.addAttribute("OrdersCheckDTOList", ordersCheckDTOList);
        model.addAttribute("ordersWorkTeamDTOList", ordersWorkTeamDTOList);

        return securityService.customAccess("order-edit", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @PostMapping("/order/{numberOrder}/edit")
    public String orderEditSave(@PathVariable(value = "numberOrder") long numberOrder, @ModelAttribute(value = "ordersDTOEdit") OrdersDTO ordersDTOEdit, Model model, HttpServletRequest request) {
        autoCreateCollections.CheckCollectionsOrderStatus();

        OrdersDTO ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        List<OrdersCheckDTO> ordersCheckDTOList = ordersCheckRepository.findAllByNumberOrder(numberOrder);
        List<OrdersWorkTeamDTO> ordersWorkTeamDTOList = ordersWorkTeamRepository.findAllByNumberOrder(numberOrder);

        model.addAttribute("ordersWorkTeamDTOList", ordersWorkTeamDTOList);

        if (orderService.orderCreate(ordersDTOEdit, request.getUserPrincipal().getName()) == null) {
            logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + "Успішно змінено " + "працівником:@" + request.getUserPrincipal().getName());
            return securityService.customAccess("redirect:/order/" + ordersDTO.getOrderNumber() + "/edit", request.getUserPrincipal().getName(), "Менеджер", "Директор");
        } else {
            return securityService.customAccess(String.valueOf(model.getAttribute("editSaveLink")), request.getUserPrincipal().getName(), "Менеджер", "Директор");
        }
    }

    @PostMapping("/order/{numberOrder}/edit/team/add")
    public String orderEditAddTeam(@PathVariable(value = "numberOrder") long numberOrder, @ModelAttribute(value = "ordersWorkTeamDTO") OrdersWorkTeamDTO ordersWorkTeamDTO, Model model, HttpServletRequest request) {
        orderService.orderAddTeam(ordersWorkTeamDTO, numberOrder);
        logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + "Успішно змінено, додано виконавця замовлення: " + ordersWorkTeamDTO.getUsername());
        return securityService.customAccess("redirect:/order/" + numberOrder + "/edit", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @GetMapping("/order/{numberOrder}/edit/team/del")
    public String orderEditDelTeam(@RequestParam String del, @PathVariable(value = "numberOrder") long numberOrder, Model model, HttpServletRequest request) {
        orderService.orderDellTeam(del, numberOrder);
        logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + "Успішно змінено, видалено виконавця замовлення: " +  del);
        return securityService.customAccess("redirect:/order/" + numberOrder + "/edit", request.getUserPrincipal().getName(), "Менеджер", "Директор");

    }

    @PostMapping("/order/{numberOrder}/edit/check/add")
    public String orderEditAddCheck(@PathVariable(value = "numberOrder") long numberOrder, @ModelAttribute(value = "ordersCheckDTO") OrdersCheckDTO ordersCheckDTO, Model model, HttpServletRequest request) {
        orderService.orderAddCheck(ordersCheckDTO, numberOrder);
        logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + "Успішно змінено, додано елемент в чек-лист: " + ordersCheckDTO.getName());
        return securityService.customAccess("redirect:/order/" + numberOrder + "/edit", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @GetMapping("/order/{numberOrder}/edit/check/del")
    public String orderEditDelCheck(@RequestParam String del, @PathVariable(value = "numberOrder") long numberOrder, Model model, HttpServletRequest request) {
        orderService.orderDellCheck(Long.parseLong(del), Math.toIntExact(numberOrder));
        logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + "Успішно змінено, видалено елемент з чек-листу " + del);
        return securityService.customAccess("redirect:/order/" + numberOrder + "/edit", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @GetMapping("/order/create")
    public String createOrder(HttpServletRequest request, Model model) {
        UsersDTO usersDTO = usersRepository.findByPersonalIndent(request.getUserPrincipal().getName());
        model.addAttribute("UserName", usersDTO.getPersonalIndent());
        model.addAttribute("UserPhone", usersDTO.getPhone());
        model.addAttribute("UserEmail", usersDTO.getEmail());
        return "order-create";
    }

    @PostMapping("/order/create")
    public String createOrder(@ModelAttribute("ordersDTO") OrdersDTO ordersDTO, HttpServletRequest request, Model model) {

        if (orderService.orderCreate(ordersDTO, request.getUserPrincipal().getName()) == null) {
            logService.addLog("log", "Замовлення", "Створення замовлення", "Створено нове замовлення користувачем:@" + request.getUserPrincipal().getName());
            return "redirect:/order/" + ordersDTO.getOrderNumber();
        } else {
            return "order-create";
        }
    }

    @GetMapping("/order/management")
    public String management(Model model, HttpServletRequest request) {
        List<OrdersDTO> allOrders = ordersRepository.findByArchive("Ні");
        List<OrdersDTO> newOrders = ordersRepository.findByStatusWorkAndArchive("Опрацювання", "Ні");
        List<OrdersDTO> confirmedOrders = ordersRepository.findByStatusWorkAndArchive("Підтверджено", "Ні");
        List<OrdersDTO> workOrders = ordersRepository.findByStatusWorkAndArchive("Виконується", "Ні");
        List<OrdersDTO> completeOrders = ordersRepository.findByStatusWorkAndArchive("Виконано", "Ні");

        model.addAttribute("AllOrderListSize", allOrders.size());
        model.addAttribute("NewOrders", newOrders);
        model.addAttribute("ConfirmedOrders", confirmedOrders);
        model.addAttribute("WorkOrders", workOrders);
        model.addAttribute("CompleteOrders", completeOrders);

        model.addAttribute("NewOrdersSize", newOrders.size());
        model.addAttribute("ConfirmedOrdersSize", confirmedOrders.size());
        model.addAttribute("WorkOrdersSize", workOrders.size());
        model.addAttribute("CompleteOrdersSize", completeOrders.size());

        return securityService.customAccess("order-management",request.getUserPrincipal().getName(), "Менеджер", "Директор");

    }

    @PostMapping("/order/management/find")
    public String managementFind(@ModelAttribute("ordersDTO") OrdersDTO ordersDTO ,Model model, HttpServletRequest request) {

        if (ordersDTO.getOrderNumber() == 0) {
            return "redirect:/order/management";
        } else {
            List<OrdersDTO> allOrders = ordersRepository.findByArchive("Ні");
            List<OrdersDTO> newOrders = ordersRepository.findByOrderNumberAndStatusWorkAndArchive(ordersDTO.orderNumber,"Опрацювання", "Ні");
            List<OrdersDTO> confirmedOrders = ordersRepository.findByOrderNumberAndStatusWorkAndArchive(ordersDTO.orderNumber,"Підтверджено", "Ні");
            List<OrdersDTO> workOrders = ordersRepository.findByOrderNumberAndStatusWorkAndArchive(ordersDTO.orderNumber,"Виконується", "Ні");
            List<OrdersDTO> completeOrders = ordersRepository.findByOrderNumberAndStatusWorkAndArchive(ordersDTO.orderNumber,"Виконано", "Ні");

            model.addAttribute("AllOrderListSize", allOrders.size());
            model.addAttribute("NewOrders", newOrders);
            model.addAttribute("ConfirmedOrders", confirmedOrders);
            model.addAttribute("WorkOrders", workOrders);
            model.addAttribute("CompleteOrders", completeOrders);

            model.addAttribute("NewOrdersSize", newOrders.size());
            model.addAttribute("ConfirmedOrdersSize", confirmedOrders.size());
            model.addAttribute("WorkOrdersSize", workOrders.size());
            model.addAttribute("CompleteOrdersSize", completeOrders.size());

            return securityService.customAccess("order-management",request.getUserPrincipal().getName(), "Менеджер", "Директор");
        }
    }

    @GetMapping("/order/management/archive/add")
    public String orderArchiveAdd(@RequestParam long numberOrder, HttpServletRequest request) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        ordersDTO.setArchive("Так");
        ordersRepository.save(ordersDTO);
        logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + "Перенесено до архіву");
        return securityService.customAccess("redirect:/order/management", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }


    @GetMapping("/order/management/archive/dell")
    public String orderArchiveDell(@RequestParam long numberOrder, HttpServletRequest request) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        ordersDTO.setArchive("Ні");
        ordersRepository.save(ordersDTO);
        logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + "Перенесено з архіву");
        return securityService.customAccess("redirect:/order/management/archive", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @GetMapping("/order/management/order/dell")
    public String orderOrderDell(@RequestParam long numberOrder, HttpServletRequest request) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        ordersRepository.delete(ordersDTO);
        logService.addLog("log", "Замовлення", "Видалено замовлення", "Замовлення №" + numberOrder);
        return securityService.customAccess("redirect:/order/management", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @GetMapping("/order/management/order/status/new")
    public String orderOrderStatusNew(@RequestParam long numberOrder, HttpServletRequest request) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        ordersDTO.setStatusWork("Опрацювання");
        ordersRepository.save(ordersDTO);
        logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + ". Змінено статус на опрацювання");
        return securityService.customAccess("redirect:/order/management", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @GetMapping("/order/management/order/status/confirmed")
    public String orderStatusConfirmed(@RequestParam long numberOrder, HttpServletRequest request) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        ordersDTO.setStatusWork("Підтверджено");
        ordersRepository.save(ordersDTO);
        logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + ". Змінено статус на підтверджено");
        return securityService.customAccess("redirect:/order/management", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @GetMapping("/order/management/order/status/work")
    public String orderStatusWork(@RequestParam long numberOrder, HttpServletRequest request) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        ordersDTO.setStatusWork("Виконується");
        ordersRepository.save(ordersDTO);
        logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + ". Змінено статус на виконується");
        return securityService.customAccess("redirect:/order/management", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @GetMapping("/order/management/order/status/complete")
    public String orderStatusComplete(@RequestParam long numberOrder, HttpServletRequest request) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        ordersDTO.setStatusWork("Виконано");
        ordersRepository.save(ordersDTO);
        logService.addLog("log", "Замовлення", "Редагування замовлення", "Замовлення №" + numberOrder + ". Змінено статус на виконано");
        return securityService.customAccess("redirect:/order/management", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @GetMapping("/order/management/archive")
    public String managementArchive(Model model, HttpServletRequest request) {
        List<OrdersDTO> ordersDTOS = ordersRepository.findByArchive("Так");
        model.addAttribute("OrdersSize", ordersDTOS.size());
        model.addAttribute("Orders", ordersDTOS);
        return securityService.customAccess("order-archive", request.getUserPrincipal().getName(), "Менеджер", "Директор");
    }

    @PostMapping("/order/management/archive/find")
    public String managementArchiveFind(@ModelAttribute("ordersDTO") OrdersDTO ordersDTO ,Model model, HttpServletRequest request) {
        if (ordersDTO.getOrderNumber() == 0) {
            return securityService.customAccess("redirect:/order/management/archive", request.getUserPrincipal().getName(), "Менеджер", "Директор");
        } else {
            List<OrdersDTO> ordersDTOS = ordersRepository.findByArchiveAndOrderNumber("Так", ordersDTO.orderNumber);
            model.addAttribute("OrdersSize", ordersDTOS.size());
            model.addAttribute("Orders", ordersDTOS);
            return securityService.customAccess("order-archive", request.getUserPrincipal().getName(), "Менеджер", "Директор");
        }
    }
}
