package com.thiago.demodentsu.model;

import lombok.Data;

import java.util.List;

@Data
public class Album {

    private String name;
    private String image;
    private String releasedDate;
    private String band;
    private String id;
    private List<Tracks> tracks;
}
