package com.degree.cto.logic;

import com.degree.cto.dtos.OrderStatusDTO;
import com.degree.cto.repositorys.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoCreateCollections {
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    public void CheckCollectionsOrderStatus() {

        if (orderStatusRepository.findAllByNumberStatus(1) == null) {
            OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
            orderStatusDTO.setNumberStatus(1);
            orderStatusDTO.setStatus("Опрацювання");
            orderStatusRepository.save(orderStatusDTO);
        }
        if (orderStatusRepository.findAllByNumberStatus(2) == null) {
            OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
            orderStatusDTO.setNumberStatus(2);
            orderStatusDTO.setStatus("Підтверджено");
            orderStatusRepository.save(orderStatusDTO);
        }
        if (orderStatusRepository.findAllByNumberStatus(3) == null) {
            OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
            orderStatusDTO.setNumberStatus(3);
            orderStatusDTO.setStatus("Виконується");
            orderStatusRepository.save(orderStatusDTO);
        }
        if (orderStatusRepository.findAllByNumberStatus(4) == null) {
            OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
            orderStatusDTO.setNumberStatus(4);
            orderStatusDTO.setStatus("Виконано");
            orderStatusRepository.save(orderStatusDTO);
        }
    }
}
