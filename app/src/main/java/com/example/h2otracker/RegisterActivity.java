package com.example.h2otracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.h2otracker.registerationFragments.FragmentCredential;
import com.example.h2otracker.registerationFragments.FragmentWeight;
import com.example.h2otracker.registerationFragments.SelectionFragment;

public class RegisterActivity extends AppCompatActivity {

    public static final int TOTAL_PAGES = 3;
    private ScreenSlideAdapter slideAdapter;
    private ViewPager viewPager;
    Spinner spinner;
    String [] gender = {"Male","Female"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,gender);
        spinner.setAdapter(adapter);

        //viewPager = findViewById(R.id.pager);

      /*  slideAdapter = new ScreenSlideAdapter(getSupportFragmentManager());
        viewPager.setAdapter(slideAdapter);
        viewPager.setCurrentItem(viewPager.getCurrentItem());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });*/

    }

    private static class ScreenSlideAdapter extends FragmentStatePagerAdapter{

        public ScreenSlideAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    FragmentCredential fragmentCredential = new FragmentCredential();
                    return fragmentCredential;
                case 1:
                    FragmentWeight fragmentWeight = new FragmentWeight();
                    return fragmentWeight;
                case 2:
                    SelectionFragment selectionFragment = new SelectionFragment();
                    return selectionFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }


    }

}