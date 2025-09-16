package com.im_api.service;

import com.im_api.dto.NominatimPlace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@Service
public class GeocodeService {

    private final RestTemplate restTemplate;
    private final String nominatimUrl;

    public GeocodeService(
            RestTemplate restTemplate,
            @Value("${nominatim.url}") String nominatimUrl
    ) {
        this.restTemplate = restTemplate;
        this.nominatimUrl = nominatimUrl;
    }

    public BigDecimal[] buscarLatLong(String enderecoCompleto) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(nominatimUrl)
                .queryParam("format", "json")
                .queryParam("q", enderecoCompleto)
                .queryParam("limit", 1)
                .encode()
                .build()
                .toUri();

        NominatimPlace[] places = restTemplate
                .getForObject(uri, NominatimPlace[].class);

        if (places == null || places.length == 0) {
            throw new RuntimeException("Nenhum resultado do Nominatim para: " + enderecoCompleto);
        }

        NominatimPlace p = places[0];
        return new BigDecimal[]{
                new BigDecimal(p.lat()),
                new BigDecimal(p.lon())
        };
    }
}