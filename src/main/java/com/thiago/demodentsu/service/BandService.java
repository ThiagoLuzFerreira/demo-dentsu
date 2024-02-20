package com.thiago.demodentsu.service;

import com.thiago.demodentsu.mapper.GenericModelMapper;
import com.thiago.demodentsu.model.Band;
import com.thiago.demodentsu.model.dto.BandDTO;
import com.thiago.demodentsu.model.dto.BandFullInfoDTO;
import com.thiago.demodentsu.webclient.BandFeingClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BandService {

    private final BandFeingClient bandFeingClient;

    public BandService(BandFeingClient bandFeingClient) {
        this.bandFeingClient = bandFeingClient;
    }

    public List<BandDTO> findAll(){
        var bands = bandFeingClient.getBands();
        return bands.stream().map(band -> GenericModelMapper.parseObject(band, BandDTO.class)).collect(Collectors.toList());
    }

    public List<BandDTO> filterBands(String orderBy) {

        List<Band> bands = bandFeingClient.getBands();

        if ("alphabetical".equals(orderBy)) {
            var orderedBands = bands.stream().sorted(Comparator.comparing(Band::getName)).toList();
            return orderedBands.stream().map(band -> GenericModelMapper.parseObject(band, BandDTO.class)).collect(Collectors.toList());
        } else if ("numPlays".equals(orderBy)) {
            var orderedBands = bands.stream().sorted(Comparator.comparingInt(Band::getNumPlays).reversed()).toList();
            return orderedBands.stream().map(band -> GenericModelMapper.parseObject(band, BandDTO.class)).collect(Collectors.toList());
        } else {
            return bands.stream().map(band -> GenericModelMapper.parseObject(band, BandDTO.class)).collect(Collectors.toList());
        }
    }

    public List<BandDTO> filterBandsByName(String name) {

        List<Band> bands = bandFeingClient.getBands();

        var filtredBands = bands.stream().filter(band -> band.getName().toLowerCase().contains(name.toLowerCase())).toList();
        return filtredBands.stream().map(band -> GenericModelMapper.parseObject(band, BandDTO.class)).collect(Collectors.toList());
    }

    public List<BandFullInfoDTO> filterBandsByNameFullInfo(String name) {
        List<Band> bands = bandFeingClient.getBands();
        List<Band> filteredBands = new ArrayList<>();
        for (Band band : bands) {
            if (band.getName().equalsIgnoreCase(name)) {
                filteredBands.add(band);
            }
        }
        return filteredBands.stream().map(band -> GenericModelMapper.parseObject(band, BandFullInfoDTO.class)).collect(Collectors.toList());
    }
}
