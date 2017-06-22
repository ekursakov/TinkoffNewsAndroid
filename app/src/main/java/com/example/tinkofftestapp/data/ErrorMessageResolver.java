package com.example.tinkofftestapp.data;

import android.content.Context;

import com.example.tinkofftestapp.R;
import com.example.tinkofftestapp.data.network.TinkoffApiException;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.HttpException;

public class ErrorMessageResolver {
    private final Context context;

    @Inject
    public ErrorMessageResolver(Context context) {
        this.context = context;
    }

    public String getErrorMessage(Throwable e) {
        if (e instanceof IOException) {
            return context.getString(R.string.error_network);
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;

            if (httpException.code() == 504) {
                return context.getString(R.string.error_network);
            } else {
                return context.getString(R.string.error_server_failure);
            }
        } else if (e instanceof TinkoffApiException) {
            return context.getString(R.string.error_server_failure);
        } else {
            return context.getString(R.string.error_unknown_failure);
        }
    }
}
