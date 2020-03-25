package com.example.currencyex;

public class CountryItem {

    private String mCountryName;
    public int mFlagImage;
    private String mCountryDescription;



    public CountryItem(String countryName, int countryImage, String countryDescription){

       mCountryName = countryName;
       mFlagImage = countryImage;
       mCountryDescription = countryDescription;

    }

    public String getCountryName() {
        return mCountryName;
    }

    public int getFlagImage(){

        return mFlagImage;
    }

    public String getCountryDescription(){

        return mCountryDescription;
    }

}
