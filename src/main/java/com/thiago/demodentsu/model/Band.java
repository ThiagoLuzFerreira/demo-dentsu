package com.thiago.demodentsu.model;

import lombok.Data;

import java.util.List;

@Data
public class Band {

    private String name;
    private String image;
    private String genre;
    private String biography;
    private Integer numPlays;
    private String id;
    private List<Album> albums;
}
