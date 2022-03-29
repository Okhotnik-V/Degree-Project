package com.degree.cto.repositorys;

import com.degree.cto.dtos.ReviewsDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends MongoRepository<ReviewsDTO, ObjectId> {
    ReviewsDTO findByLogicId(long LogicId);
}
