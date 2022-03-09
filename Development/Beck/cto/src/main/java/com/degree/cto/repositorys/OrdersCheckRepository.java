package com.degree.cto.repositorys;

import com.degree.cto.dtos.OrdersCheckDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersCheckRepository extends MongoRepository<OrdersCheckDTO, ObjectId> {
    List<OrdersCheckDTO> findAllByNumberOrder(long number);
    OrdersCheckDTO findByNumberCheck(long number);
    OrdersCheckDTO findByNumberOrder(long number);
    OrdersCheckDTO findByNumberOrderAndNumberCheck(long numberOrder, long numberCheck);
}
