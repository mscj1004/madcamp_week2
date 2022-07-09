package com.example.madcamp_week2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Fragment_2 extends Fragment {

    Button Test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_2, container, false);
        Intent intent = getActivity().getIntent();
        String gender=intent.getStringExtra("gender"); //0이 남자, 1(true)이 여자
        Test = rootView.findViewById(R.id.button_test);
        Test.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                     Intent intent = new Intent(getActivity(), WeatherActivity.class);
                     intent.putExtra("gender",gender);
                     startActivity(intent);
                     }
              }
        );

        return rootView;
    }

}