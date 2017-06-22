package com.example.tinkofftestapp.data;

import com.example.tinkofftestapp.data.model.News;
import com.example.tinkofftestapp.data.network.Result;
import com.example.tinkofftestapp.data.network.TinkoffApiException;
import com.example.tinkofftestapp.data.network.TinkoffApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsRepository {
    private final TinkoffApiService apiService;

    @Inject
    NewsRepository(TinkoffApiService apiService) {
        this.apiService = apiService;
    }

    public Single<List<News>> getNewsList() {
        return apiService.getNews()
                .map(this::handleServerError);
    }

    private <T> T handleServerError(Result<T> result) {
        if ("OK".equals(result.getResultCode())) {
            return result.getPayload();
        } else {
            throw new TinkoffApiException("Bad result code: " + result.getResultCode());
        }
    }
}
