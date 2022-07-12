package com.example.madcamp_week2.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madcamp_week2.R;
import com.example.madcamp_week2.share.Share;
import com.example.madcamp_week2.share.SharedActivity;
import com.example.madcamp_week2.style.StyleActivity;
import com.example.madcamp_week2.weather.WeatherActivity;

public class Home_Fragment extends Fragment {

    TextView nickname_view;
    AppCompatButton go_Tab2, go_Tab3, go_Tab4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String nickname=intent.getStringExtra("nickname");
        String id = intent.getStringExtra("id");
        String gender = intent.getStringExtra("gender");

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        nickname_view=rootView.findViewById(R.id.nick_name);
        nickname_view.setText(nickname);

        go_Tab2 = rootView.findViewById(R.id.go_Tab2);
        go_Tab3 = rootView.findViewById(R.id.go_Tab3);
        go_Tab4 = rootView.findViewById(R.id.go_Tab4);

        go_Tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), WeatherActivity.class);
                intent1.putExtra("gender", gender);
                startActivity(intent1);
            }
        });

        go_Tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), StyleActivity.class);
                intent2.putExtra("id", id);
                intent2.putExtra("gender", gender);
                intent2.putExtra("nickname", nickname);
                startActivity(intent2);
            }
        });

        go_Tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getActivity(), SharedActivity.class);
                startActivity(intent3);
            }
        });

        return rootView;
    }
}