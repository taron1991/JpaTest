package com.example.webmvctest.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Dto {

    @Size(min = 3,max = 12)
    @NotEmpty
    private String name;
    @Size(min = 3,max = 12)
    @NotEmpty
    private String author;
    @NotNull
    @Max(value = 5000)
    private Integer pages;

    public Dto(String name, String author, Integer pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
    }
}
