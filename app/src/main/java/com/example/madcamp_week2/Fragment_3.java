package com.example.madcamp_week2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

public class Fragment_3 extends Fragment {

    Button move_activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_3, container, false);

        move_activity = rootView.findViewById(R.id.move_style);
        move_activity.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(), StyleActivity.class);
                                        startActivity(intent);
                                    }
                                }
        );

        return rootView;
    }

}