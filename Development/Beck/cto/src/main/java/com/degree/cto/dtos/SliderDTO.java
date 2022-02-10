package com.degree.cto.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Slider")
public class SliderDTO {

    @Id
    public ObjectId id;
    public String image_url;
    public String name;
    public String message;
}
