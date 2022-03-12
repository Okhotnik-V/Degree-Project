package com.degree.cto.repositorys;

import com.degree.cto.dtos.PartnersDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnersRepository  extends MongoRepository<PartnersDTO, ObjectId> {
}
