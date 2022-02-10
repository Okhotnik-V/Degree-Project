package com.degree.cto.repositorys;

import com.degree.cto.dtos.OrdersDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends MongoRepository<OrdersDTO, ObjectId> {
    List<OrdersDTO> findAllByUsername(String username);
    OrdersDTO findByOrderNumber(long number);
    List<OrdersDTO> findByStatusWorkAndArchive(String status, String archive);
    List<OrdersDTO> findByOrderNumberAndStatusWorkAndArchive(long number, String status, String archive);
    List<OrdersDTO> findByArchive(String archive);
    List<OrdersDTO> findByArchiveAndOrderNumber(String archive, long number);
}
