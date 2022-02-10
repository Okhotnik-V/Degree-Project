package com.degree.cto.services;

import com.degree.cto.dtos.OrdersCheckDTO;
import com.degree.cto.dtos.OrdersDTO;
import com.degree.cto.dtos.OrdersWorkTeamDTO;
import com.degree.cto.dtos.UsersDTO;
import com.degree.cto.repositorys.OrdersCheckRepository;
import com.degree.cto.repositorys.OrdersRepository;
import com.degree.cto.repositorys.OrdersWorkTeamRepository;
import com.degree.cto.repositorys.UsersRepository;
import org.bouncycastle.cms.PasswordRecipientId;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private OrdersWorkTeamRepository ordersWorkTeamRepository;

    @Autowired
    private OrdersCheckRepository ordersCheckRepository;

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public String orderCreate(OrdersDTO ordersDTO, String username) {

        if(orderValid(ordersDTO) == null) {
            orderCreateSave(ordersDTO);
            return null;
        } else {
            return orderValid(ordersDTO);
        }
    }

    private String orderValid(OrdersDTO ordersDTO) {
        return null;
    }

    private void orderCreateSave(OrdersDTO ordersDTO) {
        if (ordersDTO.getUsername() == null) {
            OrdersDTO ordersDTO1 = ordersRepository.findByOrderNumber(ordersDTO.orderNumber);
            ordersDTO.setUsername(ordersDTO1.username);
        }
        if (ordersDTO.getOrderNumber() == 0) {
            ordersDTO.setOrderNumber(orderCreateNumber());
        } else {
            ordersDTO.setId(ordersRepository.findByOrderNumber(ordersDTO.getOrderNumber()).id);
        }
        if (ordersDTO.getLink() == null) {
            ordersDTO.setLink("window.location='/order/"+ordersDTO.getOrderNumber() + "';");
        }
        if (ordersDTO.getDateCreateOrder() == null) {
            ordersDTO.setStatusWork("Опрацювання");
        }
        if (ordersDTO.getDateCreateOrder() == null) {
            ordersDTO.setDateCreateOrder(String.valueOf(timestamp));
        }
        if (ordersDTO.getPhoneCall() == null) {
            ordersDTO.setPhoneCall("Так");
        }
        if (ordersDTO.getArchive() == null) {
            ordersDTO.setArchive("Ні");
        }
        ordersRepository.save(ordersDTO);
    }

    private long orderCreateNumber() {
        long i;
        for(i = 1; ordersRepository.findByOrderNumber(i) != null; i++){ }
        return i;
    }

    public void orderAddTeam(OrdersWorkTeamDTO ordersWorkTeamDTO, long numberOrder) {
        ordersWorkTeamDTO.setNumberOrder(numberOrder);
        UsersDTO usersDTO = usersRepository.findByPersonalIndent(ordersWorkTeamDTO.username);
        ordersWorkTeamDTO.setStatusUser(usersDTO.getStatus());
        ordersWorkTeamDTO.setName(usersDTO.getName());
        ordersWorkTeamDTO.setLink("window.location='/@" + usersDTO.getPersonalIndent() + "';");
        ordersWorkTeamRepository.save(ordersWorkTeamDTO);
    }

    public void orderDellTeam(String delDto, long numberOrder) {
        OrdersWorkTeamDTO ordersWorkTeamDTO = ordersWorkTeamRepository.findByNumberOrderAndUsername(numberOrder, delDto);
        ordersWorkTeamRepository.delete(ordersWorkTeamDTO);
    }

    public void orderAddCheck(OrdersCheckDTO ordersCheckDTO, long numberOrder) {
        ordersCheckDTO.setNumberOrder(numberOrder);
        ordersCheckDTO.setNumberCheck(orderCreateNumberCheck());
        ordersCheckRepository.save(ordersCheckDTO);
        OrdersDTO ordersDTO = ordersRepository.findByOrderNumber(numberOrder);
        ordersDTO.setPrice(orderPrice(numberOrder));
        ordersRepository.save(ordersDTO);
    }

    private int orderPrice(long numberOrder) {
        long i;
        int price = 0;
        for(i = 1; ordersCheckRepository.findByNumberOrderAndNumberCheck(numberOrder, i) != null; i++){
            OrdersCheckDTO ordersCheckDTO = ordersCheckRepository.findByNumberCheck(i);
            price = price + ordersCheckDTO.getPrice();
        }

        return price;
    }

    private long orderCreateNumberCheck() {
        long i;
        for(i = 1; ordersCheckRepository.findByNumberCheck(i) != null; i++){ }
        return i;
    }

    public void orderDellCheck(long delDto, long orderNumber) {
        OrdersCheckDTO ordersCheckDTO = ordersCheckRepository.findByNumberCheck(delDto);
        ordersCheckRepository.delete(ordersCheckDTO);
        OrdersDTO ordersDTO = ordersRepository.findByOrderNumber(orderNumber);
        ordersDTO.setPrice(orderPrice(orderNumber));
        ordersRepository.save(ordersDTO);
    }
}
