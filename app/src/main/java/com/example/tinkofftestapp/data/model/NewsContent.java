package com.example.tinkofftestapp.data.model;

import java.util.Date;

public class NewsContent {
    private final NewsTitle title;
    private final Date creationDate;
    private final Date lastModificationDate;
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
