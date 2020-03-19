package com.example.currencyex;

public class CountryItem {

    private String mCountryName;
    private int mFlagImage;



    public CountryItem(String countryName, int countryImage){

       mCountryName = countryName;
       mFlagImage = countryImage;

    }

    public String getCountryName() {
        return mCountryName;
    }

    public int getFlagImage(){

        return mFlagImage;
    }
}
