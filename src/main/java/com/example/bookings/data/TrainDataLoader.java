package com.example.bookings.data;

import com.example.bookings.model.Status;
import com.example.bookings.model.Train;
import com.example.bookings.service.TrainService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TrainDataLoader {
    private final TrainService trainService;

    @Autowired
    public TrainDataLoader(TrainService trainService) {
        this.trainService = trainService;
    }


    @PostConstruct
    public void loadTrainData() {

        Train train1 = new Train();
        train1.setStatusTrain(Status.EXPRESS);
        train1.setSpeed(180);

        Train train2 = new Train();
        train2.setStatusTrain(Status.LOCAL);
        train2.setSpeed(80);

        Train train3 = new Train();
        train3.setStatusTrain(Status.LOCAL);
        train3.setSpeed(180);

        trainService.saveTrain(train1);
        trainService.saveTrain(train2);
        trainService.saveTrain(train3);


        System.out.println("Дані поїздів було успішно оновлено в базі даних.");
   }

    }


