package com.system.m4.labs.chart;

import com.system.m4.labs.vos.ChartItemVO;
import com.system.m4.labs.vos.VOItemListInterface;

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
