package com.degree.cto.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "OrderStatus")
public class OrderStatusDTO {

    @Id
    public ObjectId id;
    public int numberStatus;
    public String status;
}
