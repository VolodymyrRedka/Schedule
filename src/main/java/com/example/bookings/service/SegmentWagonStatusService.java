package com.example.bookings.service;

import com.example.bookings.model.*;
import com.example.bookings.repository.SegmentWagonStatusRepository;
import com.example.bookings.repository.WagonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

@Service
public class SegmentWagonStatusService {
    private final SegmentWagonStatusRepository segmentWagonStatusRepository;


    @Autowired
    public SegmentWagonStatusService(SegmentWagonStatusRepository segmentWagonStatusRepository) {
        this.segmentWagonStatusRepository = segmentWagonStatusRepository;

    }

    public void createSegmentWagonStatus(RouteSegment segment, Wagon wagon, Train trainStatus) {
        SegmentWagonStatus segmentWagonStatus = new SegmentWagonStatus();
        segmentWagonStatus.setSegment(segment);
        segmentWagonStatus.setWagonStatus(wagon);
        segmentWagonStatus.setTrainStatus(trainStatus.getStatusTrain());
        segmentWagonStatus.setWagonStatusName(wagon.getStatusWagon());
        segmentWagonStatus.setSegmentDistance(segment.getDistanceValue());

        double segmentDistance = segmentWagonStatus.getSegmentDistance();


        double totalCost = calculateCost(segmentDistance, trainStatus.getStatusTrain(), wagon.getStatusWagon());
        String formattedCost = formatCost(totalCost);

        segmentWagonStatus.setCost(formattedCost);
        segmentWagonStatusRepository.save(segmentWagonStatus);
    }

// Інші методи сервісу

    private String formatCost(double cost) {
        Currency currency = Currency.getInstance("UAH");
        String symbol = currency.getSymbol(new Locale("uk", "UA"));
        DecimalFormat decimalFormat = new DecimalFormat("#0.00 " + symbol);
        return decimalFormat.format(cost);
    }

    private double calculateCost(double segmentDistance, Status trainStatus, Status wagonStatus) {
        double baseCost = 100.0;  // Базова вартість
        double distanceMultiplier = 0.2;  // Множник для вартості на кілометр

        // Перевіряємо, чи є відстань заданою
        if (segmentDistance <= 0.0) {
            return 0.0;  // Повертаємо 0, якщо відстань не визначена
        }

        double distanceCost = segmentDistance * distanceMultiplier;  // Вартість на основі дистанції

        double trainStatusCost = getTrainStatusCost(trainStatus);  // Вартість на основі статусу поїзда
        double wagonStatusCost = getWagonStatusCost(wagonStatus);  // Вартість на основі статусу вагона

        double totalCost = baseCost + distanceCost + trainStatusCost + wagonStatusCost;

        return totalCost;
    }

    private double getTrainStatusCost(Status trainStatus) {
        double trainStatusCost = 0.0;

        switch (trainStatus) {

            case EXPRESS:
                trainStatusCost = 100.0;
                break;
            case LOCAL:
                trainStatusCost = 20.0;
                break;
        }

        return trainStatusCost;
    }

    private double getWagonStatusCost(Status wagonStatus) {
        double wagonStatusCost = 0.0;

        switch (wagonStatus) {
            case FIRST_CLASS:
                wagonStatusCost = 50.0;
                break;
            case SECOND_CLASS:
                wagonStatusCost = 30.0;
                break;
            case BUSINESS_CLASS:
                wagonStatusCost = 80.0;
                break;
        }

        return wagonStatusCost;
    }
}