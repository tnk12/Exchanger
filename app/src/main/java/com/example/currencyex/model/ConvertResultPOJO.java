package com.example.currencyex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConvertResultPOJO {

    private Boolean success;
    private Query query;
    private Info info;
    private String date;
    private Double result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

}
