package com.example.tinkofftestapp.data.model;

import java.util.Date;

public class News {
    private final String id;
    private final String name;
    private final String text;
    private final Date publicationDate;
    private final Integer bankInfoTypeId;

    public News(String id, String name, String text, Date publicationDate, Integer bankInfoTypeId) {
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
