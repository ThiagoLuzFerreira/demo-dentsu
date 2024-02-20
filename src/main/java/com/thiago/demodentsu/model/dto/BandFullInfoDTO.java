package com.thiago.demodentsu.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class BandFullInfoDTO {

    private String name;
    private String image;
    private String genre;
    private String biography;
    private Integer numPlays;
    private List<AlbumDTO> albums;
}
