package com.example.routecalculator.controller;

import com.example.routecalculator.exception.CountryNotFoundException;
import com.example.routecalculator.exception.RouteNotFoundException;
import com.example.routecalculator.service.RouteFinder;
import com.example.routecalculator.view.RouteProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routing")
@RequiredArgsConstructor
public class RouteController {
    private final RouteFinder routeFinder;


    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<RouteProjection> getRoute(@PathVariable String origin,
                                                    @PathVariable String destination) throws CountryNotFoundException, RouteNotFoundException {
        return ResponseEntity.ok(new RouteProjection(routeFinder.getRoute(origin, destination)));
    }

}
