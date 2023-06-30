package com.example.bookings.service;

import com.example.bookings.model.Route;
import com.example.bookings.model.Station;
import com.example.bookings.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationService {
    private final StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station getStationById(Long id) {
        return stationRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public Station createStation(String name, String coordinates, int stopDuration) {
        Station station = new Station();
        station.setName(name);
        station.setCoordinates(coordinates);
        station.setStopDuration(stopDuration);
        return stationRepository.save(station);
    }

    public Station updateStation(Station station) {
        return stationRepository.save(station);
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(Math.toIntExact(id));
    }

    public Station getStationByName(String stationName) {

        return stationRepository.findByName(stationName);
    }
}