package com.degree.cto.logic.Log;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Logs")
public class LogDTO {

    @Id
    public ObjectId objectId;
    public String type;
    public String name;
    public String title;
    public String message;
    public String date;
    public String logicDateDays;
}
