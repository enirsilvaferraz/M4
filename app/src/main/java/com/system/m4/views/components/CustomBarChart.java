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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.system.m4.views.vos.ChartVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 31/07/17.
 */

public class CustomBarChart extends BarChart {

    public CustomBarChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createView();
        bindView();
    }

    private void createView() {

        setDrawBarShadow(false);
        setDrawValueAboveBar(true);
        getDescription().setEnabled(false);
        setMaxVisibleValueCount(5);
        setDoubleTapToZoomEnabled(false);
        setDrawGridBackground(false);

        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new DayAxisValueFormatter());

        //getAxisLeft().setEnabled(false);
        getAxisRight().setEnabled(false);

        getLegend().setEnabled(false);

    }

    private void bindView() {

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, new float[]{1}));
        entries.add(new BarEntry(2, new float[]{2}));
        entries.add(new BarEntry(3, new float[]{3}));
        entries.add(new BarEntry(4, new float[]{2}));
        entries.add(new BarEntry(5, new float[]{1}));

        BarDataSet dataSet = new BarDataSet(entries, "2017");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        setData(data);

        getData().setHighlightEnabled(false);

    }

    public void setDataVO(ChartVO item) {

    }

    private class DayAxisValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return "F: " + value;
        }
    }
}
