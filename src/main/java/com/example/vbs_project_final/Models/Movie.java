package com.example.vbs_project_final.Models;

import lombok.Data;
import lombok.Generated;

@Data
public class Movie {
    private Double id;
    private String title;
    private String releaseYear;

    public Movie(String title, String releaseYear) {
        this.id = Math.random();
        this.title = title;
        this.releaseYear = releaseYear;
    }
}
