package com.example.currencyex;

import android.graphics.Movie;
import android.util.Log;
import android.util.TypedValue;

import com.example.currencyex.utils.L;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {


    public static List<Movie> convertJSonToList(String dataToConvert) {
        List<Movie> list = new ArrayList<>();
        try {
            Type listType = new TypeToken<ArrayList<Movie>>() {
            }.getType();
            list = new GsonBuilder().create().fromJson(dataToConvert, listType);
        } catch (Exception e) {
            Log.e(L.D0, e.toString());
        }
        return list;
    }

}
