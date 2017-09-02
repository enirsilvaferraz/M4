package com.system.m4.views.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.vos.ChartItemVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 31/07/17.
 */

public class CustomBarChart extends BarChart {

    public static final int CHART_SIZE = 10;
    private List<ChartItemVO> mItems;

    public CustomBarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createView();
    }

    private void createView() {

        setDrawValueAboveBar(true);
        getDescription().setEnabled(false);
        setDoubleTapToZoomEnabled(false);

        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(CHART_SIZE);
        xAxis.setValueFormatter(new DayAxisValueFormatter());
        xAxis.setLabelRotationAngle(25);

        getAxisRight().setEnabled(false);
        getLegend().setEnabled(false);

    }

    public void bindView(List<ChartItemVO> items) {

        this.mItems = items;

        int size = mItems.size();
        for (int i = size; i < CHART_SIZE; i++) {
            mItems.add(new ChartItemVO("", 0));
        }

        List<BarEntry> entries = new ArrayList<>();

        for (int index = 0; index < items.size(); index++) {
            float[] vals = {items.get(index).getValue()};
            entries.add(new BarEntry(index, vals));
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueFormatter(new MyValueFormatter());

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        setData(data);

        getData().setHighlightEnabled(false);

    }

    private class DayAxisValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String title = mItems.get(((int) value)).getTitle();
            if (title.length() >= 9) {
                return title.substring(0, 6) + "...";
            }
            return title;
        }
    }

    private class MyValueFormatter implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            Float aFloat = value;
            if (aFloat.intValue() == 0) return "";
            else return JavaUtils.NumberUtil.valueFormat(aFloat.doubleValue());
        }
    }
}
