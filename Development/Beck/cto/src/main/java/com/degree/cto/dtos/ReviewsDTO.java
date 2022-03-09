package com.degree.cto.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Getter
@Setter
@Document(collection = "Reviews")
public class ReviewsDTO {

    @Id
    public ObjectId id;
    public String text;
    public Timestamp timestamp;
    public String username;
}
