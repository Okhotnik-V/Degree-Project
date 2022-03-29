package com.degree.cto.repositorys;

import com.degree.cto.dtos.UsersDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends MongoRepository<UsersDTO, ObjectId> {
     UsersDTO findByPersonalIndent(String personal_indent);
     List<UsersDTO> findByRole(String role);
     List<UsersDTO> findByStatus(String status);
     List<UsersDTO> findByPersonalIndentAndStatus(String personal_indent, String status);
}
