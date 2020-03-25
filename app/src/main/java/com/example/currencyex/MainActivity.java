package com.example.currencyex;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.example.currencyex.CountryItem;

import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.currencyex.helpers.Parser;
import com.example.currencyex.model.ConvertResultPOJO;
import com.example.currencyex.network.Loader;
import com.example.currencyex.network.OnDataReceived;
import com.example.currencyex.utils.L;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private TextView tvConvertResult;
    TextView tvTextViewFrom, tvTextViewTo;
    EditText editText;
    ActionBar actionBar;
    ImageButton btnShare;
    Spinner spinnerFrom;
    ImageView imageView2;
    Spinner spinnerTo;
    TextView textViewConvertFrom, textViewConvertTo, textViewDate,textView1From,textView1To;
    private ArrayList<CountryItem> mCountryList;
    private CountryAdapter mAdapter;


    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewConvertFrom = findViewById(R.id.textViewConvertFrom);
        textViewConvertTo = findViewById(R.id.textViewConvertTo);
        textViewDate = findViewById(R.id.textViewDate);

        Date currentDate = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        final String timeText = timeFormat.format(currentDate);

        textViewDate.setText("Today "+timeText);



        initList();
        //adding ImageView
        imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = spinnerFrom.getSelectedItemPosition();
                int d = spinnerTo.getSelectedItemPosition();
                spinnerFrom.setSelection(d);
                spinnerTo.setSelection(c);

                String currencyFrom = ((CountryItem) spinnerFrom.getSelectedItem()).getCountryName();
                String currencyTo = ((CountryItem) spinnerTo.getSelectedItem()).getCountryName();
                String amount = editText.getText().toString();

                runConvert(currencyFrom, currencyTo, amount);
                runConvert2(currencyFrom,currencyTo,"1");
                String currencyFrom2 = ((CountryItem) spinnerTo.getSelectedItem()).getCountryName();
                String currencyTo2 = ((CountryItem) spinnerFrom.getSelectedItem()).getCountryName();

                runConvert3(currencyFrom2,currencyTo2,"1");

                Date currentDate = new Date();
                DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String timeText = timeFormat.format(currentDate);

                textViewDate.setText("Today "+timeText);
            }
        });

        //Changing toolbar background
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        //Making spinnerFrom
        spinnerFrom = findViewById(R.id.spinner_from);
        mAdapter = new CountryAdapter(this, mCountryList);
        spinnerFrom.setAdapter(mAdapter);

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text3 = (((CountryItem) spinnerFrom.getSelectedItem()).getCountryDescription());
                tvTextViewFrom.setText(text3);

                String currencyFrom1 = ((CountryItem) spinnerFrom.getSelectedItem()).getCountryName();
                textViewConvertFrom.setText(currencyFrom1);

                String currencyFrom = ((CountryItem) spinnerFrom.getSelectedItem()).getCountryName();
                String currencyTo = ((CountryItem) spinnerTo.getSelectedItem()).getCountryName();
                String amount = editText.getText().toString();

                runConvert(currencyFrom, currencyTo, amount);
                runConvert2(currencyFrom,currencyTo,"1");

               String currencyFrom2 = ((CountryItem) spinnerTo.getSelectedItem()).getCountryName();
               String currencyTo2 = ((CountryItem) spinnerFrom.getSelectedItem()).getCountryName();

                runConvert3(currencyFrom2,currencyTo2,"1");

                Date currentDate = new Date();
                DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String timeText = timeFormat.format(currentDate);

                textViewDate.setText("Today "+timeText);




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Making Spinner To
        spinnerTo = findViewById(R.id.spinner_to);
        tvTextViewTo = findViewById(R.id.textViewTo);
        tvTextViewFrom = findViewById(R.id.textViewFrom);
        mAdapter = new CountryAdapter(this, mCountryList);
        spinnerTo.setAdapter(mAdapter);

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text2 = (((CountryItem) spinnerTo.getSelectedItem()).getCountryDescription());
                tvTextViewTo.setText(text2);

                String text1 = ((CountryItem) spinnerTo.getSelectedItem()).getCountryName();
                textViewConvertTo.setText(text1);

                String currencyFrom = ((CountryItem) spinnerFrom.getSelectedItem()).getCountryName();
                String currencyTo = ((CountryItem) spinnerTo.getSelectedItem()).getCountryName();
                String amount = editText.getText().toString();

                runConvert(currencyFrom, currencyTo, amount);
                runConvert2(currencyFrom,currencyTo,"1");
                String currencyFrom2 = ((CountryItem) spinnerTo.getSelectedItem()).getCountryName();
                String currencyTo2 = ((CountryItem) spinnerFrom.getSelectedItem()).getCountryName();

                runConvert3(currencyFrom2,currencyTo2,"1");

                Date currentDate = new Date();
                DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String timeText = timeFormat.format(currentDate);

                textViewDate.setText("Today "+timeText);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Finding our elements
        tvConvertResult = findViewById(R.id.tv_convert_result);
        editText = findViewById(R.id.editTextCurr);
        textView1From = findViewById(R.id.textView1From);
        textView1To = findViewById(R.id.textView1To);

        //EditText
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String currencyFrom = ((CountryItem) spinnerFrom.getSelectedItem()).getCountryName();
                String currencyTo = ((CountryItem) spinnerTo.getSelectedItem()).getCountryName();
                String amount = editText.getText().toString();

                runConvert(currencyFrom, currencyTo, amount);
                runConvert2(currencyFrom,currencyTo,"1");
                String currencyFrom2 = ((CountryItem) spinnerTo.getSelectedItem()).getCountryName();
                String currencyTo2 = ((CountryItem) spinnerFrom.getSelectedItem()).getCountryName();

                runConvert3(currencyFrom2,currencyTo2,"1");

                Date currentDate = new Date();
                DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String timeText = timeFormat.format(currentDate);

                textViewDate.setText("Today "+timeText);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Button Share content

        btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currencyFrom = ((CountryItem) spinnerFrom.getSelectedItem()).getCountryName();
                String currencyTo = ((CountryItem) spinnerTo.getSelectedItem()).getCountryName();
                String amount = editText.getText().toString();
                String result = tvConvertResult.getText().toString();
                String currency1FromText = textView1From.getText().toString();
                String currency1ToText = textView1To.getText().toString();


                String share = ("Currency rate for Today "+timeText+" :"+"\n"+currency1FromText+"\n"+currency1ToText+"\n"+ amount+"\b"+ currencyFrom + " = " + result +"\b"+ currencyTo);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = share;
                String shareSub = share;
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share Using"));


            }
        });


    }


    private void initList() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new CountryItem("USD", R.drawable.usd, "US Dollar"));
        mCountryList.add(new CountryItem("UAH", R.drawable.uah, "Ukrainian Hrivnya"));
        mCountryList.add(new CountryItem("EUR", R.drawable.eur, "Euro"));
        mCountryList.add(new CountryItem("CNY", R.drawable.cny, "Chinese Yen"));
        mCountryList.add(new CountryItem("CAD", R.drawable.cad, "Canadian Dollar"));
        mCountryList.add(new CountryItem("BTC", R.drawable.btc, "Bitcoin"));
        mCountryList.add(new CountryItem("AUD", R.drawable.aud, "Australian Dollar"));
        mCountryList.add(new CountryItem("AFN", R.drawable.afn, "Afgan Afgani"));
        mCountryList.add(new CountryItem("ALL", R.drawable.all, "Albanian Lek"));
        mCountryList.add(new CountryItem("AMD", R.drawable.amd, "Armenian Dram"));
        mCountryList.add(new CountryItem("ANG", R.drawable.ang, "Netherlands Guilder"));
        mCountryList.add(new CountryItem("AOA", R.drawable.aoa, "Angolan Kwanza"));
        mCountryList.add(new CountryItem("ARS", R.drawable.ars, "Argentine Peso"));
        mCountryList.add(new CountryItem("AWG", R.drawable.awg, "Aruban Florin"));
        mCountryList.add(new CountryItem("AZN", R.drawable.azn, "Azerbaijani Manat"));
        mCountryList.add(new CountryItem("BAM", R.drawable.bam, "Bosnia-Herzegovina Mark"));
        mCountryList.add(new CountryItem("BBD", R.drawable.bbd, "Barbadian Dollar"));
        mCountryList.add(new CountryItem("BDT", R.drawable.bdt, "Bangladeshi Taka"));
        mCountryList.add(new CountryItem("BGN", R.drawable.bgn, "Bulgarian Lev"));
        mCountryList.add(new CountryItem("BHD", R.drawable.bhd, "Bahraini Dinar"));
        mCountryList.add(new CountryItem("BIF", R.drawable.bif, "Burundian Franc"));
        mCountryList.add(new CountryItem("BMD", R.drawable.bmd, "Bermudan Dollar"));
        mCountryList.add(new CountryItem("BND", R.drawable.bnd, "Brunei Dollar"));
        mCountryList.add(new CountryItem("BOB", R.drawable.bob, "Bolivian Boliviano"));
        mCountryList.add(new CountryItem("BRL", R.drawable.brl, "Brazilian Real"));
        mCountryList.add(new CountryItem("BSD", R.drawable.bsd, "Bahamian Dollar"));
        mCountryList.add(new CountryItem("BTN", R.drawable.btn, "Bhutanese Ngultrum"));
        mCountryList.add(new CountryItem("BWP", R.drawable.bwp, "Botswanan Pula"));
        mCountryList.add(new CountryItem("BYN", R.drawable.byn, "New Belarusian Ruble"));
        mCountryList.add(new CountryItem("BYR", R.drawable.byr, "Belarusian Ruble"));
        mCountryList.add(new CountryItem("BZD", R.drawable.bzd, "Belize Dollar"));
        mCountryList.add(new CountryItem("CDF", R.drawable.cdf, "Congolese Franc"));
        mCountryList.add(new CountryItem("CHF", R.drawable.chf, "Swiss Franc"));
        mCountryList.add(new CountryItem("CLP", R.drawable.clp, "Chilean Peso"));
        mCountryList.add(new CountryItem("COP", R.drawable.cop, "Colombian Peso"));
        mCountryList.add(new CountryItem("CRC", R.drawable.crc, "Costa Rican Colón"));
        mCountryList.add(new CountryItem("CUP", R.drawable.cup, "Cuban Peso"));
        mCountryList.add(new CountryItem("CVE", R.drawable.cve, "Cape Verdean Escudo"));
        mCountryList.add(new CountryItem("CZK", R.drawable.czk, "Czech Republic Koruna"));
        mCountryList.add(new CountryItem("DJF", R.drawable.djf, "Djiboutian Franc"));
        mCountryList.add(new CountryItem("DKK", R.drawable.dkk, "Danish Krone"));
        mCountryList.add(new CountryItem("DOP", R.drawable.dop, "Dominican Peso"));
        mCountryList.add(new CountryItem("DZD", R.drawable.dzd, "Algerian Dinar"));
        mCountryList.add(new CountryItem("EGP", R.drawable.egp, "Egyptian Pound"));
        mCountryList.add(new CountryItem("ERN", R.drawable.ern, "Eritrean Nakfa"));
        mCountryList.add(new CountryItem("ETB", R.drawable.etb, "Ethopian Birr"));
        mCountryList.add(new CountryItem("FJD", R.drawable.fjd, "Fijian Dollar"));
        mCountryList.add(new CountryItem("FKP", R.drawable.fkp, "Falkland Pound"));
        mCountryList.add(new CountryItem("GBP", R.drawable.gbp, "British Pound"));
        mCountryList.add(new CountryItem("GEL", R.drawable.gel, "Georgian Lari"));
        mCountryList.add(new CountryItem("GGP", R.drawable.ggp, "Guernsey Pound"));
        mCountryList.add(new CountryItem("GHS", R.drawable.ghs, "Ghanaian Cedi"));
        mCountryList.add(new CountryItem("GIP", R.drawable.gip, "Gibraltar Pound"));
        mCountryList.add(new CountryItem("GMD", R.drawable.gmd, "Gambian Dalasi"));
        mCountryList.add(new CountryItem("GNF", R.drawable.gnf, "Guinean Franc"));
        mCountryList.add(new CountryItem("GTQ", R.drawable.gtq, "Guatemalan Quetzal"));
        mCountryList.add(new CountryItem("GYD", R.drawable.gyd, "Guyanaese Dollar"));
        mCountryList.add(new CountryItem("HKD", R.drawable.hkd, "Hong Kong Dollar"));
        mCountryList.add(new CountryItem("HNL", R.drawable.hnl, "Honduran Lempira"));
        mCountryList.add(new CountryItem("HRK", R.drawable.hrk, "Croatian Kuna"));
        mCountryList.add(new CountryItem("HTG", R.drawable.htg, "Haitian Gourde"));
        mCountryList.add(new CountryItem("HUF", R.drawable.huf, "Hungarian Forint"));
        mCountryList.add(new CountryItem("IDR", R.drawable.idr, "Indonesian Rupiah"));
        mCountryList.add(new CountryItem("ILS", R.drawable.ils, "Israeli New Sheqel"));
        mCountryList.add(new CountryItem("INR", R.drawable.inr, "Indian Rupee"));
        mCountryList.add(new CountryItem("IQD", R.drawable.iqd, "Iraqi Dinar"));
        mCountryList.add(new CountryItem("IRR", R.drawable.irr, "Iranian Rial"));
        mCountryList.add(new CountryItem("ISK", R.drawable.isk, "Icelandic Krona"));
        mCountryList.add(new CountryItem("JMD", R.drawable.jmd, "Jamaican Dollar"));
        mCountryList.add(new CountryItem("JOD", R.drawable.jod, "Jordanian Dinar"));
        mCountryList.add(new CountryItem("KES", R.drawable.kes, "Kenyan Shilling"));
        mCountryList.add(new CountryItem("KGS", R.drawable.kgs, "Kyrgystani Som"));
        mCountryList.add(new CountryItem("KHR", R.drawable.khr, "Cambodian Riel"));
        mCountryList.add(new CountryItem("KMF", R.drawable.kmf, "Comorian Franc"));
        mCountryList.add(new CountryItem("KPW", R.drawable.kpw, "North Korean Won"));
        mCountryList.add(new CountryItem("KRW", R.drawable.krw, "South Korean Won"));
        mCountryList.add(new CountryItem("KWD", R.drawable.kwd, "Kuwaiti Dinar"));
        mCountryList.add(new CountryItem("KZT", R.drawable.kzt, "Kazakhstani Tenge"));
        mCountryList.add(new CountryItem("LAK", R.drawable.lak, "Laotian Kip"));
        mCountryList.add(new CountryItem("LBP", R.drawable.lbp, "Lebanese Pound"));
        mCountryList.add(new CountryItem("LKR", R.drawable.lkr, "Sri Lankan Rupee"));
        mCountryList.add(new CountryItem("LRD", R.drawable.lrd, "Liberian Dollar"));
        mCountryList.add(new CountryItem("LSL", R.drawable.lsl, "Lesotho Loti"));
        mCountryList.add(new CountryItem("LTL", R.drawable.ltl, "Lithuanian Litas"));
        mCountryList.add(new CountryItem("LVL", R.drawable.lvl, "Latvian Lats"));
        mCountryList.add(new CountryItem("LYD", R.drawable.lyd, "Libyan Dinar"));
        mCountryList.add(new CountryItem("MAD", R.drawable.mad, "Morrocan Dirham"));
        mCountryList.add(new CountryItem("MDL", R.drawable.mdl, "Moldovan Leu"));
        mCountryList.add(new CountryItem("MGA", R.drawable.mga, "Malgasy Ariary"));
        mCountryList.add(new CountryItem("MMK", R.drawable.mmk, "Myanma Kyat"));
        mCountryList.add(new CountryItem("MNT", R.drawable.mnt, "Mongolian Tugrik"));
        mCountryList.add(new CountryItem("MRO", R.drawable.mro, "Mauritanian Ouguiya"));
        mCountryList.add(new CountryItem("MUR", R.drawable.mur, "Mauritian Rupee"));
        mCountryList.add(new CountryItem("MVR", R.drawable.mvr, "Maldivian Rufiyaa"));
        mCountryList.add(new CountryItem("MWK", R.drawable.mwk, "Malawian Kwacha"));
        mCountryList.add(new CountryItem("MXN", R.drawable.mxn, "Mexican Peso"));
        mCountryList.add(new CountryItem("MZN", R.drawable.mzn, "Mozambican Metical"));
        mCountryList.add(new CountryItem("NAD", R.drawable.nad, "Namibian Dollar"));
        mCountryList.add(new CountryItem("NGN", R.drawable.ngn, "Nigerian Naira"));
        mCountryList.add(new CountryItem("NIO", R.drawable.nio, "Nicaraguan Cordoba"));
        mCountryList.add(new CountryItem("NOK", R.drawable.nok, "Norwegian Krone"));
        mCountryList.add(new CountryItem("NRP", R.drawable.npr, "Nepalese Rupee"));
        mCountryList.add(new CountryItem("NZD", R.drawable.nzd, "New Zealand Dollar"));
        mCountryList.add(new CountryItem("OMR", R.drawable.omr, "Omani Rial"));
        mCountryList.add(new CountryItem("PAB", R.drawable.pab, "Panamian Bolboa"));
        mCountryList.add(new CountryItem("PEN", R.drawable.pen, "Peruvian Nuevo Sol"));
        mCountryList.add(new CountryItem("PGK", R.drawable.pgk, "Papua New Guinean Kina"));
        mCountryList.add(new CountryItem("PHP", R.drawable.php, "Philippine Peso"));
        mCountryList.add(new CountryItem("PKR", R.drawable.pkr, "Pakistani Rupee"));
        mCountryList.add(new CountryItem("PLN", R.drawable.pln, "Polish Zloty"));
        mCountryList.add(new CountryItem("PYG", R.drawable.pyg, "Paraguayan Guarani"));
        mCountryList.add(new CountryItem("QAR", R.drawable.qar, "Qatari Rial"));
        mCountryList.add(new CountryItem("RON", R.drawable.ron, "Romanian Leu"));
        mCountryList.add(new CountryItem("RSD", R.drawable.rsd, "Serbian Dinar"));
        mCountryList.add(new CountryItem("RWF", R.drawable.rwf, "Rwandan Franc"));
        mCountryList.add(new CountryItem("SAR", R.drawable.sar, "Saudi Riyal"));
        mCountryList.add(new CountryItem("SCCR", R.drawable.sccr, "Seychellois Rupee"));
        mCountryList.add(new CountryItem("SDG", R.drawable.sdg, "Sudanese Pound"));
        mCountryList.add(new CountryItem("SEK", R.drawable.sek, "Swedish Krona"));
        mCountryList.add(new CountryItem("SDG", R.drawable.sdg, "Sudanese pound"));
        mCountryList.add(new CountryItem("SGD", R.drawable.sgd, "Singapore Dollar"));
        mCountryList.add(new CountryItem("SLL", R.drawable.sll, "Sierra Leonean Leone"));
        mCountryList.add(new CountryItem("SOS", R.drawable.sos, "Somali Shilling"));
        mCountryList.add(new CountryItem("SRD", R.drawable.srd, "Surinamese Dollar"));
        mCountryList.add(new CountryItem("STD", R.drawable.std, "Sao Tome Dobra"));
        mCountryList.add(new CountryItem("SVC", R.drawable.svc, "Salvadoran Colon"));
        mCountryList.add(new CountryItem("SYP", R.drawable.syp, "Syrian Pound"));
        mCountryList.add(new CountryItem("THB", R.drawable.thb, "Thai Baht"));
        mCountryList.add(new CountryItem("TJS", R.drawable.tjs, "Tajikistan Somoni"));
        mCountryList.add(new CountryItem("TMT", R.drawable.tmt, "Turkmenistani Manat"));
        mCountryList.add(new CountryItem("TND", R.drawable.tnd, "Tunisian Dinar"));
        mCountryList.add(new CountryItem("TOP", R.drawable.top, "Tongan Paʻanga"));
        mCountryList.add(new CountryItem("TRY", R.drawable.try_turkish, "Turkish Lira"));
        mCountryList.add(new CountryItem("TTD", R.drawable.ttd, "Trinidad and Tobago Dollar"));
        mCountryList.add(new CountryItem("TWD", R.drawable.twd, "New Taiwan Dollar"));
        mCountryList.add(new CountryItem("TZS", R.drawable.tzs, "Tanzanian Shilling"));
        mCountryList.add(new CountryItem("UGX", R.drawable.ugx, "Ugandan Shilling"));
        mCountryList.add(new CountryItem("UYU", R.drawable.uyu, "Uruguayan Peso"));
        mCountryList.add(new CountryItem("UZS", R.drawable.uzs, "Uzbekistan Som"));
        mCountryList.add(new CountryItem("VEF", R.drawable.vef, "Venezuelan Bolivar Fuerte"));
        mCountryList.add(new CountryItem("VND", R.drawable.vnd, "Vietnamese Dong"));
        mCountryList.add(new CountryItem("VUV", R.drawable.vuv, "Vanuatu Vatu"));
        mCountryList.add(new CountryItem("WST", R.drawable.wst, "Samoan Tala"));
        mCountryList.add(new CountryItem("YER", R.drawable.yer, "Yemeni Rial"));
        mCountryList.add(new CountryItem("ZAR", R.drawable.zar, "South African Rand"));
        mCountryList.add(new CountryItem("ZMK", R.drawable.zmk, "Zambian Kwacha"));
        mCountryList.add(new CountryItem("ZWL", R.drawable.zwl, "Zimbabwean Dollar"));
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


    public void runConvert(final String currencyFrom, final String currencyTo, String amount) {

        Loader.loadConvertedData(currencyFrom, currencyTo, amount, new OnDataReceived() {
            @Override
            public void onDataReceived(String result) {
                Log.d(L.D0, "loadConvertedData: " + result);

                if (result != null && editText.length() > 0) {
                    ConvertResultPOJO convertResultPOJO = Parser.parseConvertResult(result);

                    float test = convertResultPOJO.getResult();
                    DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(2);
                    tvConvertResult.setText(df.format(test));
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
    public void runConvert2(final String currencyFrom, final String currencyTo, String amount) {

        Loader.loadConvertedData(currencyFrom, currencyTo, amount, new OnDataReceived() {
            @Override
            public void onDataReceived(String result) {
                Log.d(L.D0, "loadConvertedData: " + result);

                if (result != null && editText.length() > 0) {
                    ConvertResultPOJO convertResultPOJO = Parser.parseConvertResult(result);

                    float test = convertResultPOJO.getResult();
                    DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(2);
                    textView1From.setText("1 "+currencyFrom+" = "+df.format(test)+"\b"+ currencyTo);
                } else {
                    showToast("Load data error");
                    textView1From.setText("");
                }
            }

            @Override
            public void onFail(Exception e) {
                showToast(e.getLocalizedMessage());
                Log.d(L.D0, "onFail: " + e.getMessage());
            }
        });
    }
    public void runConvert3(final String currencyFrom2, final String currencyTo2, String amount) {

        Loader.loadConvertedData(currencyFrom2, currencyTo2, amount, new OnDataReceived() {
            @Override
            public void onDataReceived(String result) {
                Log.d(L.D0, "loadConvertedData: " + result);

                if (result != null && editText.length() > 0) {
                    ConvertResultPOJO convertResultPOJO = Parser.parseConvertResult(result);

                    float test = convertResultPOJO.getResult();
                    DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(2);
                    textView1To.setText("1 "+currencyFrom2+" = "+df.format(test)+"\b"+ currencyTo2);
                } else {
                    showToast("Load data error");
                    textView1To.setText("");
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