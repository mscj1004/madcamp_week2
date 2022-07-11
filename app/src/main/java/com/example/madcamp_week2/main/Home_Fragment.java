package com.example.madcamp_week2.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madcamp_week2.R;

public class Home_Fragment extends Fragment {

    TextView nickname_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String nickname=intent.getStringExtra("nickname");

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        nickname_view=rootView.findViewById(R.id.nick_name);
        nickname_view.setText(nickname);

        return rootView;
    }
}