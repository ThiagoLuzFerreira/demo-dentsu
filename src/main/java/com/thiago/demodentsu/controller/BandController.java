package com.thiago.demodentsu.controller;

import com.thiago.demodentsu.model.Band;
import com.thiago.demodentsu.service.BandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/bands")
public class BandController {

    private final BandService bandService;

    public BandController(BandService bandService) {
        this.bandService = bandService;
    }

    @GetMapping("/byorder")
    public ResponseEntity<List<Band>> getFilteredBands(@RequestParam(required = false, defaultValue = "alphabetical") String orderBy) {
        return ResponseEntity.ok().body(bandService.filterBands(orderBy));
    }

    @GetMapping("/byname")
    public ResponseEntity<List<Band>> getFilteredBandsByName(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok().body(bandService.filterBandsByName(name));
        } else {
            return ResponseEntity.ok().body(bandService.findAll());
        }
    }


}