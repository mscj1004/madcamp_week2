package com.example.madcamp_week2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class WeatherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        RequestThread thread = new RequestThread();
        thread.start();

    }

    class RequestThread extends Thread {

        TextView degree;
        TextView rain;
        TextView snow;

        @Override
        public void run() {
            try {
                try {
                    //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL("http://10.0.2.2:5000/api/test"); //연결을 함
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");//POST방식으로 보냄
                        con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                        con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미

                        //서버로 부터 데이터를 받음
                        InputStream stream = con.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(stream));
                        StringBuffer buffer = new StringBuffer();

                        String line = "";

                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            buffer.append(line);
                        }

                        String reqData = buffer.toString();
                        JSONTokener tokener = new JSONTokener(reqData);

                        JSONObject object = (JSONObject) tokener.nextValue();
                        String tmp = object.getString("tmp").concat("도");
                        String snow_text = object.getString("snow");
                        String rain_text = object.getString("rain");

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              degree = (TextView)findViewById(R.id.degree);
                                              rain = (TextView)findViewById(R.id.rain);
                                              snow = (TextView)findViewById(R.id.snow);

                                              degree.setText(tmp);
                                              rain.setText(rain_text);
                                              snow.setText(snow_text);
                                          }
                                      }
                        );

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            con.disconnect();
                        }
                        try {
                            if (reader != null) {
                                reader.close();//버퍼를 닫아줌
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}