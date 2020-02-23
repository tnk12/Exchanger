package com.example.currencyex.network;

public interface OnDataReceived {
    void onDataReceived(String result);

    void onFail(Exception e);
}
