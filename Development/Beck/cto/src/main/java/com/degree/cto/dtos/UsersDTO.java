package com.degree.cto.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Users")
public class UsersDTO {
    @Id
    public ObjectId id;
    public String name;
    public String personalIndent;
    public String phone;
    public String email;
    public String status;
    public String role;
    public String photo_url;

}
