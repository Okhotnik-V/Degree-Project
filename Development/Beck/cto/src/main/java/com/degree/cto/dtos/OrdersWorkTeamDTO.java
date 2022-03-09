package com.degree.cto.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "OrdersWorkTeam")
public class OrdersWorkTeamDTO {

    @Id
    public ObjectId id;
    public long numberOrder;
    public String username;
    public String Name;
    public String statusUser;
    public String link;
}
