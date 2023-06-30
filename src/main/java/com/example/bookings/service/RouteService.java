package com.example.bookings.service;

import com.example.bookings.model.*;
import com.example.bookings.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;




@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final StationService stationService;
    private final WagonService wagonService;
    private final StationRepository stationRepository;
    private final WagonRepository wagonRepository;
    private final RouteSegmentRepository routeSegmentRepository;

    private final SeatRepository seatRepository;

    @Autowired
    public RouteService(SeatRepository seatRepository, RouteRepository routeRepository, RouteSegmentRepository routeSegmentRepository, WagonRepository wagonRepository, WagonService wagonService, StationService stationService, StationRepository stationRepository) {
        this.routeRepository = routeRepository;
        this.stationService = stationService;
        this.stationRepository = stationRepository;
        this.wagonService = wagonService;
        this.wagonRepository = wagonRepository;
        this.routeSegmentRepository = routeSegmentRepository;
        this.seatRepository = seatRepository;
    }


    public Route createRoute(Train train, List<Long> stationIds, LocalDateTime arrivalTimeFirst /*,List<Long> wagonIds*/) {
        Route routeBuilder = new Route();
        routeBuilder.setTrain(train);
        routeBuilder.setStatusTrain(train.getStatusTrain());
        double totalDistance = 0.0;
        List<Station> stations = stationIds.stream()
                .map(stationService::getStationById)
                .collect(Collectors.toList());

        if (!stations.isEmpty()) {
            Station firstStation = stations.get(0);
            Station lastStation = stations.get(stations.size() - 1);
            String routeName = firstStation.getName() + " - " + lastStation.getName();
            routeBuilder.setName(routeName);
            routeBuilder.setFirstStationName(firstStation.getName());
            routeBuilder.setLastStationName(lastStation.getName());
        }

        routeBuilder.setArrivalTimeFirst(arrivalTimeFirst);

        for (int i = 0; i < stations.size() - 1; i++) {
            Station sourceStation = stations.get(i);
            Station destinationStation = stations.get(i + 1);

            String sourceStationName = sourceStation.getName();
            String destinationStationName = destinationStation.getName();

            RouteSegment segment = createRouteSegment(routeBuilder, sourceStationName, destinationStationName, train, arrivalTimeFirst, stations);
            segment.setRoute(routeBuilder);
            routeBuilder.getSegments().add(segment);

            String segmentDistance = segment.getDistance().replace(",", ".");
            double segmentDistanceValue = Double.parseDouble(segmentDistance.replace(" км", ""));
            totalDistance += segmentDistanceValue;
            arrivalTimeFirst = segment.getArrivalTime();

        }

        calculateDepartureAndArrivalTimes(routeBuilder, routeBuilder.getSegments(), train.getSpeed(), stations);

        String formattedTotalDistance = String.format("%.2f км", totalDistance);
        routeBuilder.setTotalDistance(formattedTotalDistance);

        Route savedRoute = routeRepository.save(routeBuilder);
        return savedRoute;
    }

    private RouteSegment createRouteSegment(Route route, String sourceStationName, String destinationStationName, Train train, LocalDateTime departureTime, List<Station> stations) {
        RouteSegment segment = new RouteSegment();
        segment.setRoute(route);
        segment.setTrain(train);
        Station sourceStation = findStationByName(stations, sourceStationName);
        Station destinationStation = findStationByName(stations, destinationStationName);

        if (sourceStation != null && destinationStation != null) {
            segment.setDepartureStation(sourceStation);
            segment.setArrivalStation(destinationStation);

            double distance = calculateDistance(sourceStation, destinationStation);
            String formattedDistance = String.format("%.2f км", distance);
            segment.setDistance(formattedDistance);

            String durationCombined = calculateDurationAsString(sourceStation, destinationStation, train.getSpeed());
            segment.setDuration(durationCombined);

            // Задати час зупинки для поточної станції
            int stopDuration = sourceStation.getStopDuration();
            segment.setStopDuration(stopDuration);

            List<RouteSegment> requiredSegments = new ArrayList<>(route.getSegments());
            calculateDepartureAndArrivalTimes(route, requiredSegments, train.getSpeed(), stations);
        } else {
            throw new IllegalArgumentException("Станція відправлення або прибуття не знайдена");
        }

        return segment;
    }

    private Station findStationByName(List<Station> stations, String stationName) {
        return stations.stream()
                .filter(station -> station.getName().equals(stationName))
                .findFirst()
                .orElse(null);
    }

    private double calculateDistance(Station sourceStation, Station destinationStation) {

        String[] sourceCoordinates = sourceStation.getCoordinates().split(", ");
        String[] destinationCoordinates = destinationStation.getCoordinates().split(", ");

        double sourceLatitude = Double.parseDouble(sourceCoordinates[0]);
        double sourceLongitude = Double.parseDouble(sourceCoordinates[1]);
        double destinationLatitude = Double.parseDouble(destinationCoordinates[0]);
        double destinationLongitude = Double.parseDouble(destinationCoordinates[1]);

        double earthRadius = 6371;  //

        double sourceLatRad = Math.toRadians(sourceLatitude);
        double destLatRad = Math.toRadians(destinationLatitude);
        double latDiffRad = Math.toRadians(destinationLatitude - sourceLatitude);
        double lonDiffRad = Math.toRadians(destinationLongitude - sourceLongitude);

        double a = Math.sin(latDiffRad / 2) * Math.sin(latDiffRad / 2) +
                Math.cos(sourceLatRad) * Math.cos(destLatRad) *
                        Math.sin(lonDiffRad / 2) * Math.sin(lonDiffRad / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = earthRadius * c;

        return distance;
    }

    private String calculateDurationAsString(Station sourceStation, Station destinationStation, double trainSpeed) {
        double distance = calculateDistance(sourceStation, destinationStation);
        double durationInHours = distance / trainSpeed;
        long hours = (long) durationInHours;
        long minutes = (long) ((durationInHours - hours) * 60);
        return hours + " год " + minutes + " хв";
    }

    private Duration calculateDuration(Station sourceStation, Station destinationStation, double trainSpeed) {
        double distance = calculateDistance(sourceStation, destinationStation);
        double durationInMinutes = distance / trainSpeed * 60;
        long hours = (long) durationInMinutes / 60;
        long minutes = Math.round(durationInMinutes % 60);
        return Duration.ofHours(hours).plusMinutes(minutes);
    }

    public void calculateDepartureAndArrivalTimes(Route route, List<RouteSegment> segments, int trainSpeed, List<Station> stations) {
        if (segments.isEmpty()) {
            return;
        }

        LocalDateTime departureTime = route.getArrivalTimeFirst();
        Duration totalStoppageTime = Duration.ZERO;

        for (int i = 0; i < segments.size(); i++) {
            RouteSegment segment = segments.get(i);
            String departureStationName = segment.getDepartureStation().getName();
            String arrivalStationName = segment.getArrivalStation().getName();

            Station departureStation = findStationByName(stations, departureStationName);
            Station arrivalStation = findStationByName(stations, arrivalStationName);

            if (departureStation != null && arrivalStation != null) {
                int stopDuration = departureStation.getStopDuration();
                totalStoppageTime = totalStoppageTime.plusMinutes(stopDuration);

                segment.setDepartureStation(departureStation);
                segment.setArrivalStation(arrivalStation);
                segment.setArrivalTime(departureTime);
                departureTime = departureTime.plusMinutes(stopDuration); // Додаємо час зупинки до часу відправлення

                Duration travelDuration = calculateDuration(departureStation, arrivalStation, trainSpeed);
                LocalDateTime arrivalTime = departureTime.plus(travelDuration);

                segment.setDepartureTime(departureTime);

                long durationInMinutes = travelDuration.toMinutes();
                long hours = durationInMinutes / 60;
                long minutes = durationInMinutes % 60;
                String durationCombined = hours + " год " + minutes + " хв";
                segment.setDuration(durationCombined);

                departureTime = arrivalTime;
            } else {
                throw new IllegalArgumentException("Станція відправлення або прибуття не знайдена");
            }
        }

        route.setArrivalTimeFinal(departureTime);

        long totalDurationInMinutes = segments.stream()
                .mapToLong(segment -> {
                    String[] durationParts = segment.getDuration().split(" ");
                    long hours = Long.parseLong(durationParts[0]);
                    long minutes = Long.parseLong(durationParts[2]);
                    Station departureStation = findStationByName(stations, String.valueOf(segment.getDepartureStation()));
                    long stopDuration = (departureStation != null) ? departureStation.getStopDuration() : 0;
                    return Duration.ofHours(hours).plusMinutes(minutes).toMinutes() + stopDuration;
                })
                .sum();

        long totalDurationInHours = totalDurationInMinutes / 60;
        long totalDurationMinutes = totalDurationInMinutes % 60;
        route.setTotalDuration(totalDurationInHours * 60 + totalDurationMinutes);
    }

}
