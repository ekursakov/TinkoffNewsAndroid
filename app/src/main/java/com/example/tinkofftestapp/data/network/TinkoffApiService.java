package com.example.tinkofftestapp.data.network;

import com.example.tinkofftestapp.data.model.NewsContent;
import com.example.tinkofftestapp.data.model.NewsTitle;
import com.example.tinkofftestapp.data.network.model.ApiResult;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TinkoffApiService {
    String BASE_URL = "https://api.tinkoff.ru/v1/";

    @GET("news")
    Single<ApiResult<List<NewsTitle>>> getNews(@Header("Cache-Control") String cacheControlHeader);

    @GET("news_content")
    Single<ApiResult<NewsContent>> getNewsContent(
            @Query("id") String id,
            @Header("Cache-Control") String cacheControlHeader);
}
