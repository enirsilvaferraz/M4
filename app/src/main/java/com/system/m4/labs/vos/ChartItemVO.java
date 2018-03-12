package com.system.m4.labs.vos;

/**
 * Created by eferraz on 31/07/17.
 * For M4
 */

public class ChartItemVO implements VOItemListInterface {

    private float value;
    private String title;

    public ChartItemVO(String title, float value) {
        this.title = title;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChartItemVO that = (ChartItemVO) o;

        return title.equals(that.title);

    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
