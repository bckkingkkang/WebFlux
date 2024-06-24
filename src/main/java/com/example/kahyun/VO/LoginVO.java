package com.example.kahyun.VO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "user")
public class LoginVO {
    @Id
    private String seq;
    private String username;
    private String password;
    private String user_id;
    private String email;
    private LocalDateTime create_dt;

    public LoginVO(String username, String password, String user_id, String email, LocalDateTime create_dt) {
        this.username = username;
        this.password = password;
        this.user_id = user_id;
        this.email = email;
        this.create_dt = create_dt;
    }
}
