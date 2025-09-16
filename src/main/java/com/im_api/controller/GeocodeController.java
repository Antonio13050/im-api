package com.im_api.controller;

import com.im_api.service.GeocodeService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/geocode")
public class GeocodeController {

    private final GeocodeService geocodeService;

    public GeocodeController(GeocodeService geocodeService) {
        this.geocodeService = geocodeService;
    }

    @GetMapping
    public Map<String, BigDecimal> buscarCoordenadas(@RequestParam String endereco) {
        BigDecimal[] latLong = geocodeService.buscarLatLong(endereco);

        Map<String, BigDecimal> response = new HashMap<>();
        response.put("latitude", latLong[0]);
        response.put("longitude", latLong[1]);
        return response;
    }
}