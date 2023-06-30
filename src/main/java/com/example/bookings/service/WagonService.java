package com.example.bookings.service;

import com.example.bookings.model.*;
import com.example.bookings.repository.WagonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WagonService {
    private final WagonRepository wagonRepository;

    @Autowired
    public WagonService(WagonRepository wagonRepository) {
        this.wagonRepository = wagonRepository;
    }

    public Wagon createWagon(Status statusWagon, int seatCount, Train train, Route route) {
        Wagon wagon = new Wagon();
        wagon.setStatusWagon(statusWagon);
        wagon.setTrain(train);
        wagon.setRoute(route);
        wagon.setSeatCount(seatCount);
        Wagon savedWagon = wagonRepository.save(wagon);

        return savedWagon;
    }

    public Wagon updateWagon(Wagon wagon) {
        return wagonRepository.save(wagon);
    }

    public void deleteWagon(Long id) {
        wagonRepository.deleteById(id);
    }
    public Wagon saveWagon(Wagon wagon) {
        return wagonRepository.save(wagon);
    }

}