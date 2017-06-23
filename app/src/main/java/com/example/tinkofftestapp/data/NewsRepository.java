package com.example.tinkofftestapp.data;

import com.example.tinkofftestapp.data.model.NewsContent;
import com.example.tinkofftestapp.data.model.NewsTitle;
import com.example.tinkofftestapp.data.network.TinkoffApiException;
import com.example.tinkofftestapp.data.network.TinkoffApiService;
import com.example.tinkofftestapp.data.network.model.ApiResult;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class NewsRepository {
    private static final String CACHE_CONTROL_FORCE_NETWORK = "no-cache";

    private final TinkoffApiService apiService;

    @Inject
    NewsRepository(TinkoffApiService apiService) {
        this.apiService = apiService;
    }

    public Single<List<NewsTitle>> getNewsList(boolean force) {
        return apiService
                .getNews(force ? CACHE_CONTROL_FORCE_NETWORK : null)
                .map(this::handleServerError);
    }

    public Single<NewsContent> getNewsContent(String id, boolean force) {
        return apiService
                .getNewsContent(id, force ? CACHE_CONTROL_FORCE_NETWORK : null)
                .map(this::handleServerError);
    }

    private <T> T handleServerError(ApiResult<T> result) {
        if ("OK".equals(result.getResultCode())) {
            return result.getPayload();
        } else {
            throw new TinkoffApiException("Bad result code: " + result.getResultCode());
        }
    }
}
