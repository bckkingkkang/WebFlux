package com.example.kahyun.VO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "board")
public class BoardVO {
    @Id
    private String id;
    private String seq;
    private String title;
    private String content;
    private String authorId;
    private LocalDateTime create_dt;
    private LocalDateTime update_dt;

    private String locale;

}
