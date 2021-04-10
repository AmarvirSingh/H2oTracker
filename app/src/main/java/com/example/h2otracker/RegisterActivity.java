package com.example.h2otracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.h2otracker.registerationFragments.FragmentCredential;
import com.example.h2otracker.registerationFragments.FragmentWeight;

public class RegisterActivity extends AppCompatActivity {

    public static final int TOTAL_PAGES = 2;
    private ScreenSlideAdapter slideAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        viewPager = findViewById(R.id.pager);

        slideAdapter = new ScreenSlideAdapter(getSupportFragmentManager());
        viewPager.setAdapter(slideAdapter);

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
            }
            return null;
        }

        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }


    }

}