package com.example.h2otracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StaticAndHistoryActivity extends AppCompatActivity {


    BarChart chart;
    Button selectDate;
    PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_and_history);

        chart = findViewById(R.id.chart);
        selectDate = findViewById(R.id.selectDate);
        pieChart = findViewById(R.id.pieChart);


        // for datePicker dialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(StaticAndHistoryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                selectDate.setText(day + "/" + month + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }

        });


        // for bar chart
        BarDataSet barDataSet = new BarDataSet(dataValues(), "Water Intake");
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        chart.setData(barData);

        // for pie chart
        int[] colorArray = new int[] {Color.GRAY,Color.RED};

        PieDataSet pieDataSet = new PieDataSet(dataValuesForPie(),"PieChart");
        PieData pieData = new PieData();
        pieData.addDataSet(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(colorArray);
        pieChart.invalidate();

    }

    private List<PieEntry> dataValuesForPie() {
        ArrayList<PieEntry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new PieEntry(25));
        entryArrayList.add(new PieEntry(75));
        return entryArrayList;
    }

    private List<BarEntry> dataValues() {
        ArrayList<BarEntry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new BarEntry(14, 1500));
        entryArrayList.add(new BarEntry(15, 1800));
        entryArrayList.add(new BarEntry(16, 2250));
        return entryArrayList;
    }
}