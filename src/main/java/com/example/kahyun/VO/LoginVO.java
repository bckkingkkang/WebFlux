package com.example.kahyun.VO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "user")
public class LoginVO {
    @Id
    private String id;
    private String username;
    private String password;
    @Field("user_id")
    private String user_id;
    private String email;
    private LocalDateTime create_dt;

    private String user;

    private String auth;

}
