package com.example.tinkofftestapp.data.network.model;

public class ApiResult<T> {
    private final String resultCode;
    private final T payload;

    public ApiResult(String resultCode, T payload) {
        this.resultCode = resultCode;
        this.payload = payload;
    }

    public String getResultCode() {
        return resultCode;
    }

    public T getPayload() {
        return payload;
    }
}
