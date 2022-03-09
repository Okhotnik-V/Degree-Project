package com.degree.cto.repositorys;

import com.degree.cto.dtos.OrderStatusDTO;
import com.degree.cto.services.OrderService;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusRepository extends MongoRepository<OrderStatusDTO, ObjectId> {
    OrderStatusDTO findAllByNumberStatus(int number);
}
