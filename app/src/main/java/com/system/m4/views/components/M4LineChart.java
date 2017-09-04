package com.system.m4.views.components;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

/**
 * Created by eferraz on 10/06/17.
 * For M4
 */

public class M4LineChart extends LineChart {

    private float valuerRandom = 4000;

    public M4LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        configure();
        //setData(new LineData());
        addEntry();
        addEntry();
        addEntry();
        addEntry();
        addEntry();
    }

    private void configure() {

        // enable touch gestures
        setTouchEnabled(false);

        // enable scaling and dragging
        setDragEnabled(false);
        setScaleEnabled(false);
        setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        setPinchZoom(false);

        // set an alternative background color
       // setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        // data.setValueTextColor(Color.WHITE);

        // add empty data
        setData(data);

        // load the legend (only possible after setting data)
        Legend l = getLegend();
        l.setEnabled(false);

        // modify the legend ...
        //l.setForm(Legend.LegendForm.LINE);
        // l.setTypeface(mTfLight);
        //l.setTextColor(Color.WHITE);

        XAxis xl = getXAxis();
        // xl.setTypeface(mTfLight);
        // xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        // xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = getAxisLeft();
        //  leftAxis.setTypeface(mTfLight);
        //  leftAxis.setTextColor(Color.WHITE);
        //leftAxis.setAxisMaximum(100f);
        //leftAxis.setAxisMinimum(0f);
        //leftAxis.setDrawGridLines(true);
        leftAxis.setEnabled(false);

        YAxis rightAxis = getAxisRight();
        rightAxis.setEnabled(false);

    }


    private void addEntry() {

        LineData data = getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            //float valuerRandom = (float) (Math.random() * 40) + 30f;

            valuerRandom += 200;
            data.addEntry(new Entry(set.getEntryCount(), valuerRandom), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            notifyDataSetChanged();

            // limit the number of visible entries
            setVisibleXRangeMaximum(120);
            // setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Month");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        //set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        //set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        //set.setHighLightColor(Color.rgb(244, 117, 117));
        //set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(12f);
        set.setDrawValues(true);
        set.setDrawCircles(false);
        return set;
    }


}
