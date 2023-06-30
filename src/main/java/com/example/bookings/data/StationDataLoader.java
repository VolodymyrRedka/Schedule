package com.example.bookings.data;

import com.example.bookings.model.Station;
import com.example.bookings.service.StationService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StationDataLoader {

    private final StationService stationService;

    @Autowired
    public StationDataLoader(StationService stationService) {
        this.stationService = stationService;
    }

    @PostConstruct
    public void loadStationData() {

        Station station1 = stationService.createStation("Стрий", "49.1544, 23.5121", 10);
        Station station2 = stationService.createStation("Львів", "49.5033, 24.0156", 10);
        Station station3 = stationService.createStation("Житомир", "50.1553, 28.4036", 10);
        Station station4 = stationService.createStation("Київ", "50.2700, 30.3125", 10);
        Station station5 = stationService.createStation("Одеса", "46.4825, 30.7233", 10);
        Station station6 = stationService.createStation("Дніпро", "48.4647, 35.0462", 10);
        Station station7 = stationService.createStation("Харків", "49.9935, 36.2304", 10);
        Station station8 = stationService.createStation("Херсон", "46.6354, 32.6169", 10);
        Station station9 = stationService.createStation("Луцьк", "50.7472, 25.3254", 10);
        Station station10 = stationService.createStation("Рівне", "50.6199, 26.2516", 10);
        Station station11 = stationService.createStation("Івано-Франківськ", "48.9226, 24.7111", 10);
        Station station12 = stationService.createStation("Чернівці", "48.2921, 25.9350", 10);

        System.out.println("Дані станцій було успішно додано до бази даних.");
    }
}