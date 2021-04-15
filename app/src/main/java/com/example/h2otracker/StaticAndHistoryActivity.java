package com.example.h2otracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class StaticAndHistoryActivity extends AppCompatActivity {

    LineChart lineChart;
    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_and_history);

        chart = findViewById(R.id.chart);
        lineChart = findViewById(R.id.lineChart);

// for bar chart
        BarDataSet barDataSet = new BarDataSet(dataValues(),"Water Intake");
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        chart.setData(barData);

        LineDataSet dataSet = new LineDataSet(dataValuesForLine(),"Water Intake");

        ArrayList<ILineDataSet> iLineDataSet = new ArrayList<>();
        iLineDataSet.add(dataSet);
        LineData lineData = new LineData(iLineDataSet);
        lineChart.setData(lineData);
        lineChart.canScrollVertically(1);

        lineChart.invalidate();


    }

    private List<Entry> dataValuesForLine() {
        ArrayList<Entry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(14,1500));
        entryArrayList.add(new Entry(15,1800));
        entryArrayList.add(new Entry(16,2250));
        return entryArrayList;
    }

    private List<BarEntry> dataValues() {
        ArrayList<BarEntry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new BarEntry(14,1500));
        entryArrayList.add(new BarEntry(15,1800));
        entryArrayList.add(new BarEntry(16,2250));
        return entryArrayList;
    }
}