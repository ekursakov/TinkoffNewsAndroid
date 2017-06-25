package com.example.tinkofftestapp.data.network.converter;

import android.support.annotation.Nullable;

import com.example.tinkofftestapp.data.network.model.ApiResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class UnwrapTinkoffApiResponseConverterFactory extends Converter.Factory {
    private UnwrapTinkoffApiResponseConverterFactory() {
    }

    public static UnwrapTinkoffApiResponseConverterFactory create() {
        return new UnwrapTinkoffApiResponseConverterFactory();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {
        ParameterizedType wrappedType = new ParameterizedType() {
            @Override
            public Type getRawType() {
                return ApiResult.class;
            }

            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{type};
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };

        Converter<ResponseBody, ?> delegate =
                retrofit.nextResponseBodyConverter(this, wrappedType, annotations);

        //noinspection unchecked
        return new UnwrapTinkoffApiResponseConverter(delegate);
    }

}