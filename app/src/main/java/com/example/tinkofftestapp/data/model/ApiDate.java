package com.example.tinkofftestapp.data.model;

public class ApiDate {
    private final Long milliseconds;

    public ApiDate(Long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Long getMilliseconds() {
        return milliseconds;
    }
}
