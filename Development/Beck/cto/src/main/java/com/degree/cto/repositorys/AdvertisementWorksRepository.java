package com.degree.cto.repositorys;

import com.degree.cto.dtos.AdvertisementWorksDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementWorksRepository extends MongoRepository<AdvertisementWorksDTO, ObjectId> {
}
