package com.example.bookings.service;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsService {

    private static final String API_KEY = "AIzaSyC1ikIaHlOCNsNEV8gaWebmBPVE0uQh5Ek";

    private final GeoApiContext context;

    public GoogleMapsService() {
        this.context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
    }

    public double calculateDistance(String originAddress, String destinationAddress) {
        try {
            // Створення запиту до Directions API
            DirectionsApiRequest request = DirectionsApi.newRequest(context);

            // Налаштування параметрів запиту
            request.origin(originAddress);
            request.destination(destinationAddress);
            request.mode(TravelMode.DRIVING);

            // Виконання запиту і отримання результату
            DirectionsResult result = request.await();

            // Перевірка, чи отримано результат
            if (result.routes.length == 0 || result.routes[0].legs.length == 0) {
                System.out.println("Не вдалося отримати відстань для заданих адрес");
                return 0.0;
            }

            // Отримання відстані у кілометрах
            return result.routes[0].legs[0].distance.inMeters / 1000.0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}