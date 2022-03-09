package com.degree.cto.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Getter
@Setter
@Document(collection = "Transactions")
public class TransactionsInfoDTO {

    @Id
    public ObjectId id;
    public long number;
    public String username;
    public String type;
    public int clocks_work;
    public String info;
    public String full_info;
    public int money;
    public String usernameCreator;
    public String date;
    public String logicDate;
    public String link;

}
