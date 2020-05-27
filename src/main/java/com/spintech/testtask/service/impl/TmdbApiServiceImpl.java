package com.spintech.testtask.service.impl;

import com.spintech.testtask.entity.Actor;
import com.spintech.testtask.exception.ActorNotFoundException;
import com.spintech.testtask.service.TmdbApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Service
@Slf4j
public class TmdbApiServiceImpl implements TmdbApiService {
    @Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;
    @Value("${tmdb.api.credit.url}")
    private String tmdbApiCreditUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public TmdbApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String popularTVShows() throws IllegalArgumentException {
        String url = getTmdbUrl("/tv/popular");

        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        return response.getBody();
    }

    @Override
    public Actor getTmdbActor(String creditId) throws ActorNotFoundException {
        String url = getTmdbUrl(tmdbApiCreditUrl + "/" + creditId);
        ResponseEntity<Actor> response;
        try {
            response = restTemplate.getForEntity(url, Actor.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new ActorNotFoundException(creditId);
            }
            log.error("Could not get actor with id: " + creditId, ex);
            throw new RuntimeException("Error occurred during getting actor with id: " + creditId);
        }

        return response.getBody();
    }

    private String getTmdbUrl(String tmdbUrl) {
        try {
            URIBuilder uriBuilder = new URIBuilder(tmdbApiBaseUrl + tmdbUrl);
            uriBuilder.addParameter("language", tmdbLanguage);
            uriBuilder.addParameter("api_key", tmdbApiKey);
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            log.error("Couldn't build request for url: " + tmdbUrl);
        }

        return null;
    }
}
