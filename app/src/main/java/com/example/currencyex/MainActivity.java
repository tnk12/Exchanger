package com.example.currencyex;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currencyex.helpers.Parser;
import com.example.currencyex.model.ConvertResultPOJO;
import com.example.currencyex.network.Loader;
import com.example.currencyex.network.OnDataReceived;
import com.example.currencyex.utils.L;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TextView textView, tvConvertResult;
    private List<Movie> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Making spinnerFrom
        final Spinner spinnerFrom = findViewById(R.id.spinner_from);
        final ArrayAdapter<CharSequence> spinnerAdapterFrom = ArrayAdapter.createFromResource(this, R.array.values, android.R.layout.simple_spinner_item);
        spinnerAdapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(spinnerAdapterFrom);

        Spinner spinnerTo = findViewById(R.id.spinner_to);
        ArrayAdapter<CharSequence> spinnerAdapterTo = ArrayAdapter.createFromResource(this, R.array.values, android.R.layout.simple_spinner_item);
        spinnerAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(spinnerAdapterTo);

        tvConvertResult = findViewById(R.id.tv_convert_result);

        Button convertButton = findViewById(R.id.button_convert);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currencyFrom = (String) spinnerFrom.getSelectedItem();
                Log.d(L.D0, currencyFrom);
                runConvert("USD", "UAH", 10);
            }
        });
    }

    private void getCurrencysList() {
        Loader.loadCurrencyNameList(new OnDataReceived() {
            @Override
            public void onDataReceived(String result) {
                Log.d(L.D0, "loadCurrencyNameList: " + result);
            }

            @Override
            public void onFail(Exception e) {
                Log.d(L.D0, "onFail: " + e.getMessage());
            }
        });
    }

    private void runConvert(String currencyFrom, String currencyTo, int amount) {
        Loader.loadConvertedData("USD", "UAH", "10", new OnDataReceived() {
            @Override
            public void onDataReceived(String result) {
                Log.d(L.D0, "loadConvertedData: " + result);
                if (result != null) {
                    ConvertResultPOJO convertResultPOJO = Parser.parseConvertResult(result);
                    tvConvertResult.setText(convertResultPOJO.getResult().toString());
                } else {
                    showToast("Load data error");
                }
            }

            @Override
            public void onFail(Exception e) {
                showToast(e.getLocalizedMessage());
                Log.d(L.D0, "onFail: " + e.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
