package com.example.kahyun.example.contextFeature;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {
    private String isbn;
    private String bookName;
    private String author;
}
