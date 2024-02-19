package com.thiago.demodentsu.service;

import com.thiago.demodentsu.model.Band;
import com.thiago.demodentsu.webclient.BandFeingClient;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BandService {

    private final BandFeingClient bandFeingClient;

    public BandService(BandFeingClient bandFeingClient) {
        this.bandFeingClient = bandFeingClient;
    }

    public List<Band> findAll(){
        return bandFeingClient.getBands();
    }

    public List<Band> filterBands(String orderBy) {

        List<Band> bands = bandFeingClient.getBands();

        if ("alphabetical".equals(orderBy)) {
            return bands.stream()
                    .sorted(Comparator.comparing(Band::getName))
                    .collect(Collectors.toList());
        } else if ("numPlays".equals(orderBy)) {
            return bands.stream()
                    .sorted(Comparator.comparingInt(Band::getNumPlays).reversed())
                    .collect(Collectors.toList());
        } else {
            return bands;
        }
    }

    public List<Band> filterBandsByName(String name) {

        List<Band> bands = bandFeingClient.getBands();

        return bands.stream()
                .filter(band -> band.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
