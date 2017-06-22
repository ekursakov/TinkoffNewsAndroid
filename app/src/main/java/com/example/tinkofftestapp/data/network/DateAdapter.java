package com.example.tinkofftestapp.data.network;

import com.example.tinkofftestapp.data.network.model.DateJson;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.Date;

public class DateAdapter {
    @ToJson
    DateJson toJson(Date date) {
        return new DateJson(date.getTime());
    }

    @FromJson
    Date fromJson(DateJson dateJson) {
        return new Date(dateJson.getMilliseconds());
    }
}