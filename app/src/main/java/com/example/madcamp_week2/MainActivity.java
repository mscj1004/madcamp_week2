package com.example.madcamp_week2;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.tabs.TabLayout;
import com.example.madcamp_week2.SectionsPagerAdapter;

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
        Drawable drawable1 = DrawableCompat.wrap(getResources().getDrawable(R.drawable.weather));
        drawable1.mutate();
        DrawableCompat.setTint(drawable1, getResources().getColor(R.color.login_bg));
        tabs.getTabAt(0).setIcon(R.drawable.weather);
        tabs.getTabAt(1).setIcon(R.drawable.home);
        tabs.getTabAt(2).setIcon(R.drawable.hanger);
        tabs.getTabAt(3).setIcon(R.drawable.heart);
    }

}