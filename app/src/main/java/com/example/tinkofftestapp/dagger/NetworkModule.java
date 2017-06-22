package com.example.tinkofftestapp.dagger;

import android.content.Context;

import com.example.tinkofftestapp.data.network.TinkoffApiService;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024))
                .addInterceptor(chain -> {
                    try {
                        return chain.proceed(chain.request());
                    } catch (IOException exception) {
                        exception.printStackTrace();

                        int maxStale = 60 * 60 * 24 * 7 * 4; // 4 weeks cache
                        Request request = chain.request().newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(TinkoffApiService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    TinkoffApiService provideTinkoffApiService(Retrofit retrofit) {
        return retrofit.create(TinkoffApiService.class);
    }
}

