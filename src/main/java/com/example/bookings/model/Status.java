package com.example.bookings.model;

public enum Status {
    FIRST_CLASS("First class"),
    SECOND_CLASS("Second class"),
    BUSINESS_CLASS("Business class"),
    EXPRESS("Експрес"),
    LOCAL("Місцевий"),

    PAID("Оплачено"),
    RESERVED("Зарезервоване"),
    AVAILABLE("Вільне");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}