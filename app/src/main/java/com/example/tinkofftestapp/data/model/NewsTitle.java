package com.example.tinkofftestapp.data.model;

import com.squareup.moshi.Json;

import java.util.Date;

public class NewsTitle {
    @Json(name = "id")
    private final String id;
    @Json(name = "name")
    private final String name;
    @Json(name = "text")
    private final String text;
    @Json(name = "publicationDate")
    private final Date publicationDate;
    @Json(name = "bankInfoTypeId")
    private final Integer bankInfoTypeId;

    public NewsTitle(String id, String name, String text, Date publicationDate, Integer bankInfoTypeId) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.publicationDate = publicationDate;
        this.bankInfoTypeId = bankInfoTypeId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public Integer getBankInfoTypeId() {
        return bankInfoTypeId;
    }
}
