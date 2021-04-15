package com.example.h2otracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

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
import java.util.Calendar;
import java.util.List;

public class StaticAndHistoryActivity extends AppCompatActivity {


    BarChart chart;
    Button selectDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_and_history);

        chart = findViewById(R.id.chart);
        selectDate = findViewById(R.id.selectDate);

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
        BarDataSet barDataSet = new BarDataSet(dataValues(),"Water Intake");
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        chart.setData(barData);



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