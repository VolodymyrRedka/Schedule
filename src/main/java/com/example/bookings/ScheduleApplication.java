package com.example.bookings;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication

public class ScheduleApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ScheduleApplication.class, args);

    }
}