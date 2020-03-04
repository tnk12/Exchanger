package com.example.currencyex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.example.currencyex.network.Loader;
import com.example.currencyex.network.OnDataReceived;
import com.example.currencyex.utils.L;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView textView;
    private List<Movie> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Loader.loadConvertedData("USD", "UAH", "10", new OnDataReceived() {
            @Override
            public void onDataReceived(String result) {
                Log.d(L.D0, "loadConvertedData: " + result);
            }

            @Override
            public void onFail(Exception e) {
                Log.d(L.D0, "onFail: " + e.getMessage());
            }
        });

        //Making spinner_1
        final Spinner spinner_1 = findViewById(R.id.spinner_1);
        spinner_1.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_1.setAdapter(adapter);
        Spinner spinner_2 = findViewById(R.id.spinner_2);
        spinner_2.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.values, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_2.setAdapter(adapter2);
        spinner_1.getTextAlignment();
        final EditText edittext;
        edittext = (EditText) findViewById(R.id.editText2);
        final TextView textView1 = findViewById(R.id.text_view1);


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

//Spinner 2

