package com.example.tinkofftestapp.data.network.converter;

import com.example.tinkofftestapp.data.network.TinkoffApiException;
import com.example.tinkofftestapp.data.network.model.ApiResult;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class UnwrapTinkoffApiResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Converter<ResponseBody, ApiResult<T>> delegate;

    UnwrapTinkoffApiResponseConverter(Converter<ResponseBody, ApiResult<T>> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        ApiResult<T> result = delegate.convert(value);

        if ("OK".equals(result.getResultCode())) {
            return result.getPayload();
        } else {
            throw new TinkoffApiException("Bad result code: " + result.getResultCode());
        }
    }
}
