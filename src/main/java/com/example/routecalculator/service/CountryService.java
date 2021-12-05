package com.example.routecalculator.service;


import com.example.routecalculator.domain.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class CountryService {
    List<Country> countries;
    private final RestTemplate restTemplate;

    @Value("${URL}")
    private String url;

    @PostConstruct
    public void initCountries() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper om = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        countries = asList(om.readValue(response.getBody(), Country[].class));
    }

    public List<Country> getCountries() {
        return countries;
    }
}
