package com.example.h2otracker;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.FontRequest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class StaticAndHistoryActivity extends AppCompatActivity {


    private static final String TAG = "StaticAndHistoryActivity";
    BarChart chart;
    Button selectDate;
    PieChart pieChart;
    ProgressBar progressBar;

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    ArrayList<Float> amount = new ArrayList<>();
    ArrayList<Integer> date = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_and_history);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("User");

        for (int i = 1; i < 31; i++){
            getData(i);
        }

        chart = findViewById(R.id.chart);
        progressBar = findViewById(R.id.progressbarInHistoryPage);
        progressBar.setVisibility(View.VISIBLE);
        /*selectDate = findViewById(R.id.selectDate);
        pieChart = findViewById(R.id.pieChart);
*/

        // for datePicker dialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

/*        selectDate.setOnClickListener(new View.OnClickListener() {
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

        });*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // for bar chart
                BarDataSet barDataSet = new BarDataSet(dataValues(), "Water Intake");
                BarData barData = new BarData();
                barData.addDataSet(barDataSet);
                chart.setData(barData);
                barData.setValueTextColor(Color.CYAN);

                progressBar.setVisibility(View.INVISIBLE);
            }
        }, 4000);




      /*  // for pie chart
        int[] colorArray = new int[] {Color.rgb(10,10,25),Color.BLUE};

        PieDataSet pieDataSet = new PieDataSet(dataValuesForPie(),"PieChart");
        PieData pieData = new PieData();
        pieData.addDataSet(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(colorArray);
        pieChart.invalidate();
*/
    }

    private void getData(int i) {
        try {
            reference.child(mAuth.getCurrentUser().getUid()).child("History").child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    FirebaseHistoryClass historyClass = snapshot.getValue(FirebaseHistoryClass.class);
                    if (historyClass != null){
                        float _amount = historyClass.getAmount();
                        String _date = historyClass.getDate();

                        amount.add(_amount);
                        date.add(Integer.parseInt(_date));

                        Log.i("TAG", "onDataChange: "+_amount);
                        Log.i("TAG", "onDataChange: " +_date);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "DataLoaded", Toast.LENGTH_SHORT).show();
        }

    }

    private List<PieEntry> dataValuesForPie() {
        ArrayList<PieEntry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new PieEntry(25,"Coffee"));
        entryArrayList.add(new PieEntry(75,"Water"));
        return entryArrayList;
    }

    @SuppressLint("LongLogTag")
    private List<BarEntry> dataValues() {
        ArrayList<BarEntry> entryArrayList = new ArrayList<>();
        Log.i(TAG, "dataValues: "+ amount.size());
        for (int i = 0; i < amount.size() ; i++){
            entryArrayList.add(new BarEntry(date.get(i), amount.get(i)));
            Log.i(TAG, "dataValues: " + i);
        }
        Log.i(TAG, "dataValues: array size"+entryArrayList.size());
        return entryArrayList;
    }
}