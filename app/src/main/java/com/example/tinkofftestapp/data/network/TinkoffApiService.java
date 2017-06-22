package com.example.tinkofftestapp.data.network;

import com.example.tinkofftestapp.data.model.News;
import com.example.tinkofftestapp.data.network.model.ApiResult;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TinkoffApiService {
    String BASE_URL = "https://api.tinkoff.ru/v1/";

    @GET("news")
    Single<ApiResult<List<News>>> getNews(@Header("Cache-Control") String cacheControlHeader);
}
