package com.example.tinkofftestapp.dagger;

import android.content.Context;

import com.example.tinkofftestapp.data.network.TinkoffApiService;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {
    private final Interceptor USE_CACHE_ON_FAILURE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            try {
                return chain.proceed(request);
            } catch (IOException e) {
                if (request.cacheControl().noCache()) {
                    throw e;
                } else {
                    Request requestFromCache = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();

                    return chain.proceed(requestFromCache);
                }
            }
        }
    };

    private final Interceptor REMOVE_CACHE_HEADERS_FROM_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            return chain.proceed(chain.request()).newBuilder()
                    .removeHeader("Cache-Control")
                    .removeHeader("Pragma")
                    .build();
        }
    };

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024))
                .addInterceptor(USE_CACHE_ON_FAILURE_INTERCEPTOR)
                .addNetworkInterceptor(REMOVE_CACHE_HEADERS_FROM_RESPONSE_INTERCEPTOR)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(TinkoffApiService.BASE_URL)
                .client(client)
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

