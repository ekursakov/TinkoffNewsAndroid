package com.example.tinkofftestapp.data.network.model;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.Date;

public class DateJson {
    private final long milliseconds;

    public DateJson(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public static class Adapter {
        @ToJson
        DateJson toJson(Date date) {
            return new DateJson(date.getTime());
        }

        @FromJson
        Date fromJson(DateJson dateJson) {
            return new Date(dateJson.getMilliseconds());
        }
    }
}
