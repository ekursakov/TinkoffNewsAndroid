package com.example.tinkofftestapp.data.network;

public class Result<T> {
    private final String resultCode;
    private final T payload;

    public Result(String resultCode, T payload) {
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
