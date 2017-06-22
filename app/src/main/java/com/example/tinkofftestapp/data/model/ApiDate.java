package com.example.tinkofftestapp.data.model;

public class ApiDate {
    private final long milliseconds;

    public ApiDate(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }
}
