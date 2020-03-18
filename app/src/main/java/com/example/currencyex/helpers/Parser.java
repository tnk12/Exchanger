package com.example.currencyex.helpers;

import android.util.Log;
import com.example.currencyex.model.ConvertResultPOJO;
import com.example.currencyex.utils.L;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

//Making our POJO parser
public class Parser {

    public static ConvertResultPOJO parseConvertResult(String dataToConvert) {
        ConvertResultPOJO result = null;
        try {
            Type dataType = new TypeToken<ConvertResultPOJO>() {
            }.getType();
            result = new GsonBuilder().create().fromJson(dataToConvert, dataType);
        } catch (Exception e) {
            Log.e(L.D0, e.toString());
        }

        return result;
    }
}
