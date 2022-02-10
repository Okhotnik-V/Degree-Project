package com.degree.cto.repositorys;

import com.degree.cto.dtos.OrdersWorkTeamDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersWorkTeamRepository extends MongoRepository<OrdersWorkTeamDTO, ObjectId> {
    List<OrdersWorkTeamDTO> findAllByNumberOrder(long number);
    OrdersWorkTeamDTO findByNumberOrderAndUsername(long number, String username);
}
