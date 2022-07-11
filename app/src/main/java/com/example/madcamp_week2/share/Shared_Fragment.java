package com.example.madcamp_week2.share;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.madcamp_week2.R;
import com.example.madcamp_week2.style.StyleActivity;

public class Shared_Fragment extends Fragment {

    Button move_shared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_4, container, false);

        move_shared = rootView.findViewById(R.id.move_shared);
        move_shared.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent(getActivity(), SharedActivity.class);
                                                 startActivity(intent);
                                             }
                                         }
        );
        return rootView;
    }
}