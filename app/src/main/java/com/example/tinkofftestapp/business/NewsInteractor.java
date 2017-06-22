package com.example.tinkofftestapp.business;

import com.example.tinkofftestapp.data.NewsRepository;
import com.example.tinkofftestapp.data.model.News;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

public class NewsInteractor {
    private final NewsRepository repository;

    @Inject
    public NewsInteractor(NewsRepository repository) {
        this.repository = repository;
    }

    public Single<List<News>> getNews(boolean force) {
        return repository.getNewsList(force)
                .onErrorResumeNext(e -> {
                    Timber.w(e);
                    return Single.error(e);
                });
    }
}
