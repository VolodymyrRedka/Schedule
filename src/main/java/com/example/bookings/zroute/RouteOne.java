package com.example.bookings.zroute;

import com.example.bookings.model.*;
import com.example.bookings.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Component
public class RouteOne {

    private final RouteService routeService;

    private final TrainService trainService;

    private final WagonService wagonService;
    private final SeatService seatService;
    private final SegmentWagonStatusService segmentWagonStatusService;



    @Autowired
    public RouteOne (RouteService routeService,
                     SeatService seatService,
                     TrainService trainService,
                     WagonService wagonService,
                     SegmentWagonStatusService segmentWagonStatusService
    ) {

        this.routeService = routeService;
        this.trainService = trainService;
        this.wagonService = wagonService;
        this.seatService = seatService;
        this.segmentWagonStatusService = segmentWagonStatusService;
    }

    @PostConstruct
    public void createRouteExample() {
        Train firstTrain = trainService.getTrainById(1L);
        if (firstTrain != null) {
            List<Long> stationIds = Arrays.asList(1L, 2L, 3L, 4L, 5L,5L);
            LocalDateTime arrivalTimeFirst = LocalDateTime.of(2023, 6, 23, 12, 0);

            // Створення маршруту
            Route createdRoute = routeService.createRoute(firstTrain, stationIds, arrivalTimeFirst);

            if (createdRoute != null) {
                // Створення вагонів та місць для маршруту
                Wagon wagon1 = wagonService.createWagon(Status.BUSINESS_CLASS, 10, firstTrain, createdRoute);
                seatService.createSeatsForWagon(wagon1, createdRoute,firstTrain);

                Wagon wagon2 = wagonService.createWagon(Status.FIRST_CLASS, 10, firstTrain, createdRoute);
                seatService.createSeatsForWagon(wagon2, createdRoute,firstTrain);

                Wagon wagon3 = wagonService.createWagon(Status.SECOND_CLASS, 10, firstTrain, createdRoute);
                seatService.createSeatsForWagon(wagon3, createdRoute,firstTrain);


                Wagon wagon4 = wagonService.createWagon(Status.SECOND_CLASS, 10, firstTrain, createdRoute);
                seatService.createSeatsForWagon(wagon4, createdRoute,firstTrain);

                List<RouteSegment> segments = createdRoute.getSegments();
                for (RouteSegment segment : segments) {
                    segmentWagonStatusService.createSegmentWagonStatus(segment, wagon1,firstTrain);
                    segmentWagonStatusService.createSegmentWagonStatus(segment, wagon2,firstTrain);
                    segmentWagonStatusService.createSegmentWagonStatus(segment, wagon3,firstTrain);
                    segmentWagonStatusService.createSegmentWagonStatus(segment, wagon4,firstTrain);
                }

                System.out.println("Створено новий маршрут: " + createdRoute.getName());

            } else {
                System.out.println("Не вдалося створити маршрут.");
            }
        } else {
            System.out.println("Не вдалося знайти жодного поїзда.");
        }
    }
}


