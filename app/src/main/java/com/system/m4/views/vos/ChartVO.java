package com.system.m4.views.vos;

import java.util.List;

/**
 * Created by eferraz on 06/08/17.
 */

public class ChartVO implements VOItemListInterface {

    private List<ChartItemVO> items;

    public List<ChartItemVO> getItems() {
        return items;
    }

    public void setItems(List<ChartItemVO> items) {
        this.items = items;
    }
}
