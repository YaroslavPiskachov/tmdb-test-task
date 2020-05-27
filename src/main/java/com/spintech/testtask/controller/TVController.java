package com.spintech.testtask.controller;

import com.spintech.testtask.service.TmdbApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/tv")
public class TVController {

    private final TmdbApiService tmdbApiService;

    @Autowired
    public TVController(TmdbApiService tmdbApiService) {
        this.tmdbApiService = tmdbApiService;
    }

    @RequestMapping(value = "/popular", method = GET)
    public ResponseEntity popular() {
        String popularMovies = tmdbApiService.popularTVShows();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(popularMovies);
    }
}
