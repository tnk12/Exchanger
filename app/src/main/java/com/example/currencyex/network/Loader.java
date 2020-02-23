package com.example.currencyex.network;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Loader {
    public static void loadCurrencyNameList(final OnDataReceived onDataReceived) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://restcountries-v1.p.rapidapi.com/name/norge")
                .get()
                .addHeader("x-rapidapi-host", "restcountries-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "930585e7admshbb5fbca527d317dp12463djsn260d42de2a05")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                onDataReceived.onDataReceived(response.body().string());
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onDataReceived.onFail(e);
            }
        });
    }

    public static void loadConvertedData(String currFrom, String currTo, String amount, final OnDataReceived result) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://fixer-fixer-currency-v1.p.rapidapi.com/convert?from="
                        + currFrom + "&to=" + currTo + "&amount=" + amount)
                .get()
                .addHeader("x-rapidapi-host", "fixer-fixer-currency-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "930585e7admshbb5fbca527d317dp12463djsn260d42de2a05")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                result.onFail(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                result.onDataReceived(response.body().string());
            }
        });
    }

}
