package com.system.m4.labs.vos;

/**
 * Created by eferraz on 17/06/17.
 */

public class SummaryVO implements VOItemListInterface {

    private String title;
    private Double summaryValue;

    public SummaryVO(String title, Double summaryValue) {
        this.title = title;
        this.summaryValue = summaryValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getSummaryValue() {
        return summaryValue;
    }

    public void setSummaryValue(Double summaryValue) {
        this.summaryValue = summaryValue;
    }
}
