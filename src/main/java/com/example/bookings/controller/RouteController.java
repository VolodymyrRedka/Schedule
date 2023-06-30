package com.example.bookings.controller;

import com.example.bookings.model.*;
import com.example.bookings.repository.RouteRepository;
import com.example.bookings.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RouteController {
    private final RouteService routeService;
    private final RouteRepository routeRepository;

    public RouteController(RouteService routeService, RouteRepository routeRepository) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
    }

    @GetMapping("/routes/station/{stationName}")
    public String getRoutesByStationName(@PathVariable String stationName, Model model) {
        List<Route> matchingRoutes = new ArrayList<>();

        List<Route> routes = routeRepository.findAll();

        for (Route route : routes) {
            List<RouteSegment> segments = route.getSegments();

            for (RouteSegment segment : segments) {
                if (segment.getDepartureStation().getName().equals(stationName)
                        || segment.getArrivalStation().getName().equals(stationName)) {
                    matchingRoutes.add(route);
                    break;
                }
            }
        }

        model.addAttribute("matchingRoutes", matchingRoutes);

        return "routes";
    }
    }