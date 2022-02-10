package com.degree.cto.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "AdvertisementWorks")
public class AdvertisementWorksDTO {

    @Id
    public ObjectId id;
    public String name;
    public String message;
    public String username;
    public String date;
    public String link;
}
