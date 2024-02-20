package com.thiago.demodentsu.service;

import com.thiago.demodentsu.mapper.GenericModelMapper;
import com.thiago.demodentsu.model.Band;
import com.thiago.demodentsu.model.dto.BandDTO;
import com.thiago.demodentsu.model.dto.BandFullInfoDTO;
import com.thiago.demodentsu.webclient.BandFeingClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BandServiceImpl implements BandService {

    private final BandFeingClient bandFeingClient;

    public BandServiceImpl(BandFeingClient bandFeingClient) {
        this.bandFeingClient = bandFeingClient;
    }

    @Cacheable(cacheNames = "bands")
    @Override
    public List<BandDTO> findAll(){
        log.info("finding all bands");

        var bands = bandFeingClient.getBands();
        return bands.stream().map(band -> GenericModelMapper.parseObject(band, BandDTO.class)).collect(Collectors.toList());
    }

    @CircuitBreaker(name = "filterBandsCB", fallbackMethod = "fallbackFilterBands")
    @Cacheable(cacheNames = "bandsSort")
    @Override
    public List<BandDTO> filterBands(String orderBy) {

        List<Band> bands = bandFeingClient.getBands();

        if ("alphabetical".equals(orderBy)) {
            log.info("ordering bands alphabetically");
            var orderedBands = bands.stream().sorted(Comparator.comparing(Band::getName)).toList();
            return orderedBands.stream().map(band -> GenericModelMapper.parseObject(band, BandDTO.class)).collect(Collectors.toList());
        } else if ("numPlays".equals(orderBy)) {
            log.info("ordering bands by number of plays");
            var orderedBands = bands.stream().sorted(Comparator.comparingInt(Band::getNumPlays).reversed()).toList();
            return orderedBands.stream().map(band -> GenericModelMapper.parseObject(band, BandDTO.class)).collect(Collectors.toList());
        } else {
            log.info("finding all bands");
            return bands.stream().map(band -> GenericModelMapper.parseObject(band, BandDTO.class)).collect(Collectors.toList());
        }
    }

    public List<BandDTO> fallbackFilterBands(Throwable throwable){
        return List.of(new BandDTO("The Best Band", 10));
    }
    @Cacheable(cacheNames = "bandsFilteredByName")
    @Override
    public List<BandDTO> filterBandsByName(String name) {

        log.info("filtering bands by name " + name);

        List<Band> bands = bandFeingClient.getBands();

        var filtredBands = bands.stream().filter(band -> band.getName().toLowerCase().contains(name.toLowerCase())).toList();
        return filtredBands.stream().map(band -> GenericModelMapper.parseObject(band, BandDTO.class)).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "bandsFilteredByNameFullInfo")
    @Override
    public List<BandFullInfoDTO> filterBandsByNameFullInfo(String name) {

        log.info("returning bands full info searched by name " + name);

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
