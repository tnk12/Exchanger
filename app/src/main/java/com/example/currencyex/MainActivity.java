package com.example.currencyex;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.currencyex.helpers.Parser;
import com.example.currencyex.model.ConvertResultPOJO;
import com.example.currencyex.network.Loader;
import com.example.currencyex.network.OnDataReceived;
import com.example.currencyex.utils.L;


public class MainActivity extends AppCompatActivity {
    private TextView  tvConvertResult;
    EditText editText;
    ActionBar actionBar;
    private VideoView videoView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding videoView
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView = (VideoView)findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.anim);
        videoView.setVideoURI(video);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
              finish();
            }
        });
        videoView.start();


        //Changing toolbar background
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        //Making spinnerFrom
        final Spinner spinnerFrom = findViewById(R.id.spinner_from);
        final ArrayAdapter<CharSequence> spinnerAdapterFrom = ArrayAdapter.createFromResource(this, R.array.values, android.R.layout.simple_spinner_item);
        spinnerAdapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(spinnerAdapterFrom);
        //Making spinnerTo
        final Spinner spinnerTo = findViewById(R.id.spinner_to);
        ArrayAdapter<CharSequence> spinnerAdapterTo = ArrayAdapter.createFromResource(this, R.array.values, android.R.layout.simple_spinner_item);
        spinnerAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(spinnerAdapterTo);
        //Finding our elements
        tvConvertResult = findViewById(R.id.tv_convert_result);
        editText = findViewById(R.id.editTextCurr);

        Button convertButton = findViewById(R.id.button_convert);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.length()>0) {
                    String currencyFrom = (String) spinnerFrom.getSelectedItem();
                    Log.d(L.D0, currencyFrom);
                    String currencyTo = (String) spinnerTo.getSelectedItem();
                    Log.d(L.D0, currencyTo);
                    runConvert(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString(), editText.getText());
                }else {
                    tvConvertResult.setText("");
                    showToast("Please,Enter summ!");
                }
            }
        });
    }

    private void getCurrenciesList() {
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

    public void runConvert(String currencyFrom, String currencyTo, Editable amount) {
       Spinner spinnerFrom = findViewById(R.id.spinner_from);
       final Spinner spinnerTo = findViewById(R.id.spinner_to);
       final EditText editText = findViewById(R.id.editTextCurr);
        Loader.loadConvertedData(spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString(), editText.getText().toString(), new OnDataReceived() {
            @Override
            public void onDataReceived(String result) {
                Log.d(L.D0, "loadConvertedData: " + result);
                if (result != null & editText.length()>0) {
                    ConvertResultPOJO convertResultPOJO = Parser.parseConvertResult(result);
                    tvConvertResult.setText(convertResultPOJO.getResult().toString()+" - "+spinnerTo.getSelectedItem().toString());
                } else {
                    showToast("Load data error");
                    tvConvertResult.setText("");
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
