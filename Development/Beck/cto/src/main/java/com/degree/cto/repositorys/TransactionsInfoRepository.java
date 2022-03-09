package com.degree.cto.repositorys;

import com.degree.cto.dtos.TransactionsInfoDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionsInfoRepository extends MongoRepository<TransactionsInfoDTO, ObjectId> {
    TransactionsInfoDTO findByNumber(long number);
    List<TransactionsInfoDTO> findByLogicDate(String date);
    List<TransactionsInfoDTO> findAllByUsername(String username);
}
