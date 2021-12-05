package com.example.routecalculator.service;

import com.example.routecalculator.domain.Country;
import com.example.routecalculator.exception.CountryNotFoundException;
import com.example.routecalculator.exception.RouteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.reverse;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class RouteFinder {
    private Map<String, List<String>> adjacencyList;

    private final CountryService countryService;

    @PostConstruct
    public void init() {
        adjacencyList = countryService.getCountries().stream().collect(toMap(Country::getCca3, Country::getBorders));
    }

    public List<String> getRoute(String origin, String destination) throws CountryNotFoundException, RouteNotFoundException {
        List<String> route = searchPath(origin, destination);
        if (!route.contains(destination) || !route.contains(origin))
            throw new RouteNotFoundException(format("Land route not found for origin %s and destination %s", origin, destination));
        return route;
    }

    private List<String> searchPath(String origin, String destination) throws CountryNotFoundException {
        Queue<String> countryQueue = new ArrayDeque<>();
        Set<String> visitedCountries = new HashSet<>();
        Map<String, String> prev = new HashMap<>();

        visitedCountries.add(origin);
        countryQueue.add(origin);

        while (!countryQueue.isEmpty()) {
            String country = countryQueue.poll();
            List<String> neighbours = adjacencyList.get(country);

            if (neighbours == null)
                throw new CountryNotFoundException(format("Country not found with cca3 '%s'", country));

            for (String neighbour : neighbours) {
                if (visitedCountries.contains(neighbour)) continue;

                countryQueue.add(neighbour);
                visitedCountries.add(neighbour);
                prev.put(neighbour, country);

                if (neighbour.equals(destination)) break;
            }
        }
        return reconstructPath(destination, prev);
    }

    private List<String> reconstructPath(String destination, Map<String, String> prev) {
        List<String> path = new ArrayList<>();
        String at = destination;
        path.add(at);

        while (prev.containsKey(at)) {
            path.add(prev.get(at));
            at = prev.get(at);
        }

        reverse(path);
        return path;
    }
}
