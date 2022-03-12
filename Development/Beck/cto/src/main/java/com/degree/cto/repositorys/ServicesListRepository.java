package com.degree.cto.repositorys;

import com.degree.cto.dtos.ServicesListDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServicesListRepository extends MongoRepository<ServicesListDTO, ObjectId> {
}
