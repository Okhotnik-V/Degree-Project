package com.degree.cto.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "ServicesList")
public class ServicesListDTO {

    @Id
    public ObjectId id;
    public String name;
    public String text;
    public String price;
    public String image_url;
}
