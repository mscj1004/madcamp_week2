package com.example.madcamp_week2.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.madcamp_week2.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);

        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.home);
        tabs.getTabAt(1).setIcon(R.drawable.weather);
        tabs.getTabAt(2).setIcon(R.drawable.hanger);
        tabs.getTabAt(3).setIcon(R.drawable.heart);
    }

}