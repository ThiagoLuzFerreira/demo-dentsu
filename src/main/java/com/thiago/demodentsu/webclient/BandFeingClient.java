package com.thiago.demodentsu.webclient;

import com.thiago.demodentsu.model.Band;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Component
@FeignClient(name="band", url="https://dws-recruiting-bands-api.dwsbrazil.io", path="/api/bands")
public interface BandFeingClient {

    @GetMapping
    List<Band> getBands();
}
