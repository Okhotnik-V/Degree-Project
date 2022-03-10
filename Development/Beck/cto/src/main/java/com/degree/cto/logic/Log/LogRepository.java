package com.degree.cto.logic.Log;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository  extends MongoRepository<LogDTO, ObjectId> {
    List<LogDTO> findByLogicDateDays(String date);
}
