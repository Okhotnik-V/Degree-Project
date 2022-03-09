package com.degree.cto.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Orders")
public class OrdersDTO {

    @Id
    public ObjectId id;
    public long orderNumber;
    public String username;
    public String brandCar;
    public String modelCar;
    public String yearsCar;
    public String service;
    public int price;
    public String dateCreateOrder;
    public String dateWork;
    public String phoneCall;
    public String message;
    public String statusWork;
    public String archive;
    public String link;
    public long netProfit;
}
