package com.turatti.financy.data.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document("MDB_users")
public class User {

    public String user_email;
    public String user_password;
    public String user_name;
    @MongoId
    public String user_id;

}
