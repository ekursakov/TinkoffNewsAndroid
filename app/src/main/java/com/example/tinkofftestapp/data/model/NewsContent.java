package com.example.tinkofftestapp.data.model;

import com.squareup.moshi.Json;

import java.util.Date;

public class NewsContent {
    @Json(name = "title")
    private final NewsTitle title;
    @Json(name = "creationDate")
    private final Date creationDate;
    @Json(name = "lastModificationDate")
    private final Date lastModificationDate;
    @Json(name = "content")
    private final String content;

    public NewsContent(NewsTitle title, Date creationDate, Date lastModificationDate, String content) {
        this.title = title;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
        this.content = content;
    }

    public NewsTitle getTitle() {
        return title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public String getContent() {
        return content;
    }
}
