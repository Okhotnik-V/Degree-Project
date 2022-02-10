package com.degree.cto.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Getter
@Setter
@Document(collection = "TransactionsInfo")
public class TransactionsInfoDTO {

    @Id
    public ObjectId id;
    public long number;
    public String type;
    public int clocks_work;
    public String info;
    public String full_info;
    public int money;
    public String username;
    public Timestamp date;
    public String link;

}
