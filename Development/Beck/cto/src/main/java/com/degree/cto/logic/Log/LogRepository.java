package com.degree.cto.logic.Log;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository  extends MongoRepository<LogDTO, ObjectId> {
    List<LogDTO> findByType(String type);
    List<LogDTO> findByName(String name);
    List<LogDTO> findByNameAndType(String name, String type);
    List<LogDTO> findByTypeAndLogicDateDays(String type, String date);
    List<LogDTO> findByNameAndLogicDateDays(String name, String date);
    List<LogDTO> findByTypeAndNameAndLogicDateDays(String type, String name, String date);
}
