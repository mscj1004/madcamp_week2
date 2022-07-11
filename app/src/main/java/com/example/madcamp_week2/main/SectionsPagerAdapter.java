package com.example.madcamp_week2.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.madcamp_week2.share.Shared_Fragment;
import com.example.madcamp_week2.R;
import com.example.madcamp_week2.style.Style_Fragment;
import com.example.madcamp_week2.weather.Weather_Fragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_2, R.string.tab_text_1, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext = context;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new Home_Fragment();
                break;

            case 1:
                fragment = new Weather_Fragment();
                break;

            case 2:
                fragment = new Style_Fragment();
                break;

            case 3:
                fragment = new Shared_Fragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
