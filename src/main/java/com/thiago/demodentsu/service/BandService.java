package com.thiago.demodentsu.service;

import com.thiago.demodentsu.model.dto.BandDTO;
import com.thiago.demodentsu.model.dto.BandFullInfoDTO;

import java.util.List;

public interface BandService {

    List<BandDTO> findAll();
    List<BandDTO> filterBands(String orderBy);
    List<BandDTO> filterBandsByName(String name);
    List<BandFullInfoDTO> filterBandsByNameFullInfo(String name);
}
