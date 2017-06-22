package com.example.tinkofftestapp.data.network.model;

public class DateJson {
    private final long milliseconds;

    public DateJson(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }
}
